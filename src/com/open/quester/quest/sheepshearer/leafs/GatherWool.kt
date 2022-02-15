package com.open.quester.quest.sheepshearer.leafs

import com.open.quester.common.QuestLeafStep
import com.open.quester.extensions.Conditions.Companion.waitUntilItemEntersInventory
import com.open.quester.quest.sheepshearer.SheepShearer
import com.open.quester.quest.sheepshearer.SheepShearerConstants.ACTION_SHEAR
import com.open.quester.quest.sheepshearer.SheepShearerConstants.AREA_SHEEP_PEN
import com.open.quester.quest.sheepshearer.SheepShearerConstants.ITEM_WOOL
import org.powbot.api.Condition
import org.powbot.api.rt4.*
import kotlin.random.Random

class GatherWool(val quest: SheepShearer) : QuestLeafStep<SheepShearer>(quest, "Gathering wool") {
    private val SheepIds = intArrayOf(2693, 2695, 2699, 2786, 2787, 2878)

    override fun run() {
        val sheep = Npcs.stream().id(*SheepIds).nearest().first()
        if (sheep == Npc.Nil) {
            Movement.builder(AREA_SHEEP_PEN.randomTile)
                .setRunMin(Random.nextInt(0, 8))
                .setWalkUntil { AREA_SHEEP_PEN.contains(Players.local()) }
                .move()
        } else {
            if (sheep.reachable() && sheep.inViewport(true)) {
                val woolCount = Inventory.stream().name(ITEM_WOOL).count().toInt()
                if (sheep.interact(ACTION_SHEAR)) {
                    Condition.wait(waitUntilItemEntersInventory(ITEM_WOOL, woolCount))
                }
            } else {
                Movement.builder(AREA_SHEEP_PEN.randomTile)
                    .setRunMin(Random.nextInt(0, 8))
                    .move()
            }
        }
    }
}