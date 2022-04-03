package com.open.quester.quest.witcheshouse.helpers

import com.open.quester.extensions.Conditions
import com.open.quester.extensions.nearestNpc
import org.powbot.api.Condition
import org.powbot.api.Tile
import org.powbot.api.rt4.Movement
import org.powbot.api.rt4.Npc
import org.powbot.api.rt4.Npcs
import org.powbot.api.rt4.Players
import org.powbot.quests.quest.witcheshouse.WitchesHouseConstants
import java.util.logging.Logger

object GardenHelper {
    private var logger: Logger = Logger.getLogger(this.javaClass.simpleName)

    fun walkToOtherSide(bottom: Boolean, walkingEast: Boolean): Boolean {
        if (Players.local().inMotion() && !Condition.wait(Conditions.waitUntilNotMoving())) {
            return false
        }
        val nora = Npcs.nearestNpc(WitchesHouseConstants.NAME_WITCH)
        if (nora === Npc.Nil) {
            logger.info("Nora is nil")
            return walkNextTile(nora, bottom, walkingEast)
        } else if (WitchesHouseConstants.AREA_LEFT_TURNING_AREA.contains(nora) ||
            WitchesHouseConstants.AREA_RIGHT_TURNING_AREA.contains(nora)
        ) {

            logger.info("Nora in dead zone")
            return false
        } else if (!witchFacingTile(nora, Players.local().tile())) {

            logger.info("Witch not facing")
            return walkNextTile(nora, bottom, walkingEast)
        }

        return false
    }

    fun walkNextTile(npc: Npc, bottom: Boolean, walkingEast: Boolean): Boolean {
        val tileList =
            if (bottom) WitchesHouseConstants.TILE_BOTTOM_WALKING_TILES else WitchesHouseConstants.TILE_TOP_WALKING_TILES
        val finalList = if (walkingEast) tileList else tileList.reversedArray()
        val playerPosition = Players.local().tile()

        val currentIndex = finalList.indexOfFirst { playerPosition == it }

        if (currentIndex == -1) {
            logger.info("Walking to nearest tile because something dun goofed.")
            Movement.moveTo(nearestTile(tileList), false)
        } else {
            val targetIndex = currentIndex + 1
            if (targetIndex >= finalList.size) {
                logger.info("Walking to to finished.")
                return true
            } else {
                val targetTile = finalList[targetIndex]
                if (!witchFacingTile(npc, targetTile)) {
                    logger.info("Stepping to tile $targetTile")
                    if (Movement.step(targetTile, 0)) {
                        Condition.wait { Players.local().tile() == targetTile }
                    }
                } else {
                    logger.info("Waiting for npc to be infront of tile")
                }
            }
        }
        return false
    }

    private fun nearestTile(tileList: Array<Tile>): Tile {
        val currentTile = Players.local().tile()
        var minDistance = 1000.0
        var finalTile = Tile.Nil
        tileList.forEach {
            val dist = currentTile.distanceTo(it)
            if (dist < minDistance) {
                finalTile = it
                minDistance = dist
            }
        }
        return finalTile
    }

    private fun witchFacingTile(npc: Npc, targetTile: Tile): Boolean {
        if (npc === Npc.Nil) {
            return false
        }

        val x = targetTile.x
        val facingEast = npc.tile().x - npc.facingTile().x < 0
        val eastOfNpc = npc.x() - x < 0

        return facingEast == eastOfNpc
    }

}