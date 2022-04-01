package com.open.quester.quest.lostcity.tasks

import com.open.quester.common.base.BaseQuestStep
import com.open.quester.extensions.Conditions
import com.open.quester.extensions.count
import com.open.quester.extensions.nearestNpc
import com.open.quester.helpers.CombatHelper
import com.open.quester.models.QuestInformation
import com.open.quester.quest.Constants.ItemRequirements.ITEM_RUNE_CHAOS
import com.open.quester.quest.Constants.ItemRequirements.ITEM_RUNE_EARTH
import com.open.quester.quest.Constants.ItemRequirements.ITEM_RUNE_FIRE
import com.open.quester.quest.Constants.ItemRequirements.ITEM_RUNE_MIND
import com.open.quester.quest.Constants.ItemRequirements.ITEM_RUNE_WATER
import org.powbot.api.Condition
import org.powbot.api.Random
import org.powbot.api.Tile
import org.powbot.api.rt4.*
import java.util.concurrent.Callable

class ManualCastSpells(
    private val npcName: String,
    private val lootItem: String?,
    private val information: QuestInformation,
    private val shouldExecute: Callable<Boolean>,
    private val fallbackTile: Tile,
) :  BaseQuestStep() {

    var spell: Magic.Spell? = null

    override fun shouldExecute(): Boolean {
        if (spell == null) {
            getSpell()
        }

        return shouldExecute.call()
    }

    private fun getSpell() {
        if (Inventory.count(ITEM_RUNE_CHAOS) > 0)
            if (Inventory.count(ITEM_RUNE_FIRE) > 0) {
                spell = Magic.Spell.FIRE_BOLT
            } else if (Inventory.count(ITEM_RUNE_EARTH) > 0) {
                spell = Magic.Spell.EARTH_BOLT
            } else if (Inventory.count(ITEM_RUNE_WATER) > 0) {
                spell = Magic.Spell.WATER_BOLT
            } else {
                spell = Magic.Spell.WIND_BOLT
            }
        else if (Inventory.count(ITEM_RUNE_MIND) > 0) {
            if (Inventory.count(ITEM_RUNE_FIRE) > 0) {
                spell = Magic.Spell.FIRE_STRIKE
            } else if (Inventory.count(ITEM_RUNE_EARTH) > 0) {
                spell = Magic.Spell.EARTH_STRIKE
            } else if (Inventory.count(ITEM_RUNE_WATER) > 0) {
                spell = Magic.Spell.WATER_STRIKE
            } else {
                spell = Magic.Spell.WIND_STRIKE
            }
        } else {
            spell = Magic.Spell.WIND_STRIKE
        }
    }

    override fun run() {
        var npc = Npcs.stream().name(npcName).interactingWithMe().nearest().first()

        if (npc.tile() == Tile.Nil) {
            npc = Npcs.nearestNpc(npcName)
        } else {
            logger.info("Attacking interacting NPC")
        }
        if (npc.tile() == Tile.Nil) {
            Movement.builder(fallbackTile)
                .setRunMin(10)
                .setRunMax(20)
                .setWalkUntil { fallbackTile.matrix().inViewport() || CombatHelper.shouldEat(*information.foodName) }
                .move()
            logger.info("Unable to find NPC walking to fallback tile")
            return
        } else if (!npc.inViewport()) {
            Movement.builder(npc.tile())
                .setRunMin(10)
                .setRunMax(20)
                .setWalkUntil { npc.tile().matrix().inViewport() || CombatHelper.shouldEat(*information.foodName) }
                .move()
            logger.info("Walking to npc toi attack")
        }

        if (npc.tile().matrix().inViewport()) {
            logger.info("Killing NPC")
            killNpc(npc)
        }
    }

    private fun killNpc(npc: Npc) {
        if (CombatHelper.shouldEat(*information.foodName)) {
            CombatHelper.eatFood(*information.foodName)
        }

        if (npc.healthPercent() == 0) {
            logger.info("Waiting for loot")
            lootItems(npc.tile())
            return
        }

        if (spell!!.cast()) {
            var shouldEatFood = false
            Condition.sleep(Random.nextInt(300, 650))
            if (npc.interact("Cast")) {
                Condition.wait {
                    Players.local().animation() != -1 || CombatHelper.shouldEat(*information.foodName)
                        .also { shouldEatFood = it }
                }
                if (shouldEatFood) {
                    return
                }
                Condition.wait { Players.local().animation() == -1 || CombatHelper.shouldEat(*information.foodName) }
            }
        }

        if (npc.healthPercent() == 0) {
            logger.info("Waiting for loot at ${npc.tile()}")
            lootItems(npc.tile())
        }

    }

    private fun lootItems(tile: Tile) {
        if (lootItem == null) {
            logger.info("No loot item specified $lootItem")
            return
        }

        val originalCount = GroundItems.stream().at(tile).count()
        logger.info("Original count $originalCount")
        val waitforLootAtTile = Condition.wait({
            GroundItems.stream().at(tile).count() > originalCount || CombatHelper.shouldEat(*information.foodName)
        }, 450, 12)

        if (CombatHelper.shouldEat(*information.foodName)) {
            CombatHelper.eatFood(*information.foodName)
        }

        if (!waitforLootAtTile) {
            logger.info("Failed to wait for loot")
            return
        }

        val groundItem = GroundItems.stream().name(lootItem).within(tile,  1).first()
        if (!groundItem.inViewport()) {
            Movement.builder(groundItem.tile)
                .setRunMin(10)
                .setRunMax(20)
                .setWalkUntil { groundItem.tile.matrix().inViewport() }
                .move()
        }

        val itemCount = Inventory.stream().name(lootItem).count(true)
        if (groundItem.interact("Take")) {

            logger.info("Took item")
            Condition.wait(Conditions.waitUntilItemEntersInventory(lootItem, itemCount.toInt()))
        }
    }

    override fun stepName(): String {
        return "Casting spell on $npcName"
    }


}