package com.open.quester.quest.witcheshouse.tasks

import com.open.quester.common.base.BaseQuestStep
import com.open.quester.extensions.nearestGameObject
import com.open.quester.helpers.InteractionsHelper
import org.powbot.api.Condition
import org.powbot.api.Tile
import org.powbot.api.rt4.Inventory
import org.powbot.api.rt4.Movement
import org.powbot.api.rt4.Objects
import org.powbot.api.rt4.Players
import org.powbot.api.rt4.walking.local.Utils.getWalkableNeighbor
import org.powbot.quests.quest.witcheshouse.WitchesHouseConstants.AREA_FOUNTAIN_AREA
import org.powbot.quests.quest.witcheshouse.WitchesHouseConstants.AREA_RIGHT_SIDE
import org.powbot.quests.quest.witcheshouse.WitchesHouseConstants.AREA_SHACK
import org.powbot.quests.quest.witcheshouse.WitchesHouseConstants.ITEM_SHED_KEY
import org.powbot.quests.quest.witcheshouse.WitchesHouseConstants.TILE_TOP_END
import org.powbot.quests.quest.witcheshouse.WitchesHouseConstants.TILE_TOP_START
import com.open.quester.quest.witcheshouse.helpers.GardenHelper

class EnterShack : BaseQuestStep() {

    override fun shouldExecute(): Boolean {
        return Inventory.stream().name(ITEM_SHED_KEY).count().toInt() > 0 && !AREA_SHACK.contains(Players.local())
    }

    override fun run() {
        val playerTile = Players.local().tile()
        if (AREA_FOUNTAIN_AREA.contains(playerTile)) {
            walkToTile(TILE_TOP_START)
        } else if (AREA_RIGHT_SIDE.contains(Players.local()) || TILE_TOP_END == playerTile) {
            walkToEndOrDoor()
        } else if (playerTile.y == 3466) {
            walkToEndOrDoor()
        }
    }

    private fun walkToEndOrDoor() {
        val crossedToOtherSide = GardenHelper.walkToOtherSide(false, true)
        if (crossedToOtherSide) {
            openDoor()
        }
    }

    private fun openDoor() {
        val door = Objects.nearestGameObject("Door")
        if (door.inViewport()) {
            val result = InteractionsHelper.useItemOnInteractive(Inventory.stream().name(ITEM_SHED_KEY).first(), door)
            if (result) {
                Condition.wait { AREA_SHACK.contains(Players.local()) }
            }
        } else {
            Movement.builder(door.getWalkableNeighbor(false))
                .setRunMin(15)
                .setRunMax(35)
                .setWalkUntil { door.inViewport() }
                .move()
        }
    }

    private fun walkToTile(tile: Tile) {
        if (Movement.step(tile)) {
            Condition.wait { Players.local().tile() == tile }
        }
    }

    override fun stepName(): String {
        return "Entering Shack"
    }
}