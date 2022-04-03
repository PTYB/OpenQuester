package com.open.quester.common

import com.open.quester.common.base.SimpleNpcStep
import com.open.quester.extensions.Conditions
import com.open.quester.helpers.CombatHelper
import com.open.quester.models.QuestInformation
import org.powbot.api.Condition
import org.powbot.api.Tile
import org.powbot.api.rt4.*
import java.util.concurrent.Callable

class KillNpcStep(
    destinationTile: Tile,
    getNpc: () -> Npc,
    groundItemNames: Array<String>?,
    val getGroundItem: Callable<List<GroundItem>>?,
    shouldExecute: () -> Boolean,
    information: QuestInformation,
    stepText: String,
) : SimpleNpcStep(
    destinationTile, arrayOf(), getNpc, { go -> go.interact("Attack") },
    { Players.local().interacting() != Actor.Nil }, stepText,
    shouldExecute = shouldExecute,
    questInformation = information
) {

    private val itemNames = groundItemNames?.toList()?.toTypedArray() ?: emptyArray()

    override fun run() {
        val interactingNpc = Npcs.stream().filtered { CombatHelper.isNpcAttackingMe(it) }.first()
        if (interactingNpc == Npc.Nil) {
            super.run()
        } else {
            if (interactingNpc.healthPercent() == 0) {
                val result = Condition.wait { getGroundItem != null && getGroundItem.call().isNotEmpty() }
                if (result) {
                    lootItems()
                }
            } else {
                attackOrEat(interactingNpc)
            }
        }
    }

    private fun lootItems() {
        if (getGroundItem == null || itemNames.isEmpty()) {
            return
        }

        val groundItems = getGroundItem.call()
        groundItems.forEach { groundItem ->
            if (!groundItem.inViewport()) {
                Movement.builder(groundItem.tile)
                    .setRunMin(10)
                    .setRunMax(40)
                    .setWalkUntil { groundItem.tile.matrix().inViewport() }
                    .move()
            }

            val originalCount = Inventory.stream().name(groundItem.name()).sumOf { obj: Item -> obj.stack }
            if (groundItem.inViewport() && groundItem.interact("Take")) {
                Conditions.waitUntilItemEntersInventory(groundItem.name(), originalCount)
            }
        }

    }

    private fun attackOrEat(npc: Npc) {
        if (Players.local().interacting() != Actor.Nil) {
            if (CombatHelper.shouldEat(*questInformation.foodName)) {
                CombatHelper.eatFood(*questInformation.foodName)
            } else if (Chat.canContinue()) {
                Chat.clickContinue()
            }
        } else {
            attackNpc(npc)
        }
    }

    private fun attackNpc(npc: Npc) {
        if (Players.local().interacting() != npc && npc.interact("Attack")) {
            Condition.wait({ CombatHelper.isNpcAttackingMe(npc) }, 300, 10)
        }
    }

}