package com.open.quester.quest.witcheshouse.tasks

import com.open.quester.common.base.BaseQuestStep
import com.open.quester.extensions.nearestGameObject
import org.powbot.api.Condition
import org.powbot.api.rt4.Inventory
import org.powbot.api.rt4.Movement
import org.powbot.api.rt4.Objects
import org.powbot.api.rt4.Players
import org.powbot.api.rt4.walking.local.Utils.getWalkableNeighbor
import org.powbot.quests.quest.witcheshouse.WitchesHouseConstants

class LeaveShack : BaseQuestStep() {

    override fun shouldExecute(): Boolean {
        return WitchesHouseConstants.AREA_SHACK.contains(Players.local()) &&
                Inventory.stream().name(WitchesHouseConstants.ITEM_BALL).count().toInt() == 1
    }

    override fun run() {
        val door = Objects.nearestGameObject("Door")
        if (door.inViewport() && door.interact("Open")) {
            Condition.wait { !WitchesHouseConstants.AREA_SHACK.contains(Players.local()) }
        } else {
            Movement.builder(door.getWalkableNeighbor(false))
                .setWalkUntil { door.inViewport() }
                .move()
        }
    }

    override fun stepName(): String {
        return "Leaving shack"
    }
}