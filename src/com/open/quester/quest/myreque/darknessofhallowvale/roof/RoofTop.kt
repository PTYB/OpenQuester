package com.open.quester.quest.myreque.darknessofhallowvale.roof

import com.open.quester.common.base.BaseQuestStep
import com.open.quester.quest.myreque.darknessofhallowvale.DarknessOfHallowvaleConstants.messageFromVyre
import org.powbot.api.Area
import org.powbot.api.Condition
import org.powbot.api.Tile
import org.powbot.api.rt4.*
import java.util.concurrent.Callable

class RoofTopJump(
    val area: Area,
    val targetTile: Tile,
    val obstacleName: String,
    val action: String,
    val condition: Callable<Boolean>? = null
) : BaseQuestStep() {
    private var obstacle = GameObject.Nil

    override fun shouldExecute(): Boolean {
        if (!area.contains(Players.local()) || !(condition == null || condition.call())) {
            return false
        }
        obstacle = Objects.stream(targetTile, 2).name(obstacleName).action(action)
            .nearest(targetTile).first()
        return obstacle != GameObject.Nil
    }

    override fun run() {
        if (Chat.chatting()) {
            Chat.completeChat(messageFromVyre)
        }

        if (!obstacle.inViewport() && Movement.step((obstacle))) {
            Camera.turnTo(obstacle)
        }

        if (obstacle.inViewport() && obstacle.interact(action)) {
            Condition.wait ({ !obstacle.valid() || (Players.local().tile() == targetTile && Players.local().animation() == -1)}, 500, 12)
            if (Chat.chatting()) {
                Chat.completeChat()
            }

        }
    }

    override fun stepName(): String {
        return "Jumping roofs"
    }
}