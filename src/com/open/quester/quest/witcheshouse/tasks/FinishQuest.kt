package com.open.quester.quest.witcheshouse.tasks

import com.open.quester.common.SimpleConversationStep
import com.open.quester.models.QuestInformation
import org.powbot.api.Condition
import org.powbot.api.Tile
import org.powbot.api.rt4.Inventory
import org.powbot.api.rt4.Movement
import org.powbot.api.rt4.Players
import org.powbot.quests.quest.witcheshouse.WitchesHouseConstants
import org.powbot.quests.quest.witcheshouse.WitchesHouseConstants.AREA_LEFT_SAFE_SIDE
import org.powbot.quests.quest.witcheshouse.WitchesHouseConstants.AREA_OUTSIDE
import org.powbot.quests.quest.witcheshouse.WitchesHouseConstants.AREA_RIGHT_SIDE
import org.powbot.quests.quest.witcheshouse.WitchesHouseConstants.TILE_BOTTOM_END
import com.open.quester.quest.witcheshouse.helpers.GardenHelper

class FinishQuest(information: QuestInformation) : SimpleConversationStep(
    WitchesHouseConstants.NAME_BOY,
    WitchesHouseConstants.TILE_BOY,
    WitchesHouseConstants.CONVERSATION_BOY,
    "Finishing quest",
    information
) {

    override fun shouldExecute(): Boolean {
        return Inventory.stream().name(WitchesHouseConstants.ITEM_BALL).count().toInt() == 1
    }

    override fun run() {
        val playerLoc = Players.local().tile()
        if (AREA_OUTSIDE.contains(playerLoc)) {
            if (playerLoc == TILE_BOTTOM_END) {
                if (GardenHelper.walkToOtherSide(true, false)) {
                    super.run()
                }
            } else if (AREA_RIGHT_SIDE.contains(playerLoc)) {
                moveToTile(TILE_BOTTOM_END)
            } else if (Players.local().y() == 3460) {
                if (GardenHelper.walkToOtherSide(true, false)) {
                    super.run()
                }
            } else if (AREA_LEFT_SAFE_SIDE.contains(playerLoc)) {
                super.run()
            }
        } else {
            super.run()
        }
    }

    fun moveToTile(tile: Tile) {
        if (Movement.step(tile)) {
            Condition.wait { Players.local().tile() == tile }
        }
    }
}