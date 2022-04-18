package com.open.quester.quest.myreque.darknessofhallowvale.tasks

import com.open.quester.common.base.BaseQuestStep
import com.open.quester.quest.myreque.darknessofhallowvale.DarknessOfHallowvaleConstants
import org.powbot.api.Condition
import org.powbot.api.rt4.Chat
import org.powbot.api.rt4.Objects
import org.powbot.api.rt4.Players
import org.powbot.api.rt4.walking.local.LocalPathFinder

class GoDown : BaseQuestStep() {
    override fun shouldExecute(): Boolean {
        return DarknessOfHallowvaleConstants.AREA_FIRST_WALL.contains(Players.local())
    }

    override fun run() {
        if (Chat.chatting()) {
            Chat.completeChat(*DarknessOfHallowvaleConstants.CONVERSATION_KICK_FLOOR)
        }

        val floor = Objects.stream().name("Floor").action("Search", "Climb-down").first()
        if (!floor.inViewport()) {
            LocalPathFinder.findWalkablePath(DarknessOfHallowvaleConstants.TILE_NEAR_FLOORBOARD).traverseUntilReached(2.0)
        }

        if (floor.inViewport()) {
            val action = if (floor.actions().contains("Search")) "Search" else "Climb-down"
            if (floor.interact(action)) {
                Condition.wait { Chat.chatting() || !floor.valid() }
            }
        }
    }

    override fun stepName(): String {
        return "Going down"
    }
}