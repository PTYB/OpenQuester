package com.open.quester.quest.witcheshouse.tasks

import com.open.quester.common.base.BaseQuestStep
import com.open.quester.extensions.Conditions
import com.open.quester.extensions.nearestGameObject
import org.powbot.api.Condition
import org.powbot.api.Random
import org.powbot.api.Tile
import org.powbot.api.rt4.*
import org.powbot.quests.quest.witcheshouse.WitchesHouseConstants
import org.powbot.quests.quest.witcheshouse.WitchesHouseConstants.AREA_FOUNTAIN_AREA
import org.powbot.quests.quest.witcheshouse.WitchesHouseConstants.AREA_OUTSIDE
import org.powbot.quests.quest.witcheshouse.WitchesHouseConstants.ITEM_SHED_KEY
import org.powbot.quests.quest.witcheshouse.WitchesHouseConstants.TILE_BOTTOM_START
import org.powbot.quests.quest.witcheshouse.WitchesHouseConstants.TILE_TOP_END
import com.open.quester.quest.witcheshouse.helpers.GardenHelper

class GetShedKey : BaseQuestStep() {
    override fun shouldExecute(): Boolean {
        return Inventory.stream().name(ITEM_SHED_KEY).count().toInt() == 0
    }

    override fun run() {
        if (!Movement.running() && Movement.energyLevel() > Random.nextInt(5, 20)) {
            Movement.running(true)
        }

        val playerTile = Players.local().tile()
        if (!AREA_OUTSIDE.contains(playerTile)) {
            logger.info("Area outside does not contain player")
            Movement.walkTo(TILE_BOTTOM_START)
        } else if (AREA_FOUNTAIN_AREA.contains(Players.local())) {
            getKeyFromFountain()
        } else if (WitchesHouseConstants.AREA_LEFT_SAFE_SIDE.contains(playerTile)) {
            walkToTile(TILE_BOTTOM_START)
        } else if (WitchesHouseConstants.AREA_RIGHT_SIDE.contains(Players.local())) {
            logger.info("Right side contains")
            if (playerTile == TILE_TOP_END) {
                val crossedToOtherSide = GardenHelper.walkToOtherSide(false, false)
                if (crossedToOtherSide) {
                    getKeyFromFountain()
                }
            } else {
                walkToTile(TILE_TOP_END)
            }
        } else if (Players.local().y() == 3460) {
            val crossedToOtherSide = GardenHelper.walkToOtherSide(true, true)
            if (crossedToOtherSide) {
                Movement.walkTo(TILE_TOP_END)
            }
        } else if (Players.local().y() == 3466) {
            val crossedToOtherSide = GardenHelper.walkToOtherSide(false, false)
            if (crossedToOtherSide) {
                getKeyFromFountain()
            }
        }
    }

    private fun getKeyFromFountain() {
        val fountain = Objects.nearestGameObject("Fountain")

        if (fountain.inViewport() && fountain.interact("Check") && Condition.wait(Conditions.waitUntilChatting())) {
            Chat.completeChat()
        } else {
            Movement.builder(fountain.tile)
                .setRunMin(10)
                .setRunMin(35)
                .setWalkUntil { Objects.nearestGameObject("Fountain").inViewport() }
                .move()
        }
    }

    private fun walkToTile(tile: Tile) {
        if (Movement.step(tile, 0)) {
            Condition.wait { Players.local().tile() == tile }
        }
    }

    override fun stepName(): String {
        return "Getting Shed Key."
    }
}