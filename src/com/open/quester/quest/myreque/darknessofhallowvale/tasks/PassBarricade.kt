package com.open.quester.quest.myreque.darknessofhallowvale.tasks

import com.open.quester.common.base.BaseQuestStep
import com.open.quester.extensions.nearestGameObject
import com.open.quester.quest.myreque.darknessofhallowvale.DarknessOfHallowvaleConstants.areaRockySurface
import org.powbot.api.Condition
import org.powbot.api.rt4.Camera
import org.powbot.api.rt4.Chat
import org.powbot.api.rt4.Objects
import org.powbot.api.rt4.Players

class PassBarricade: BaseQuestStep() {
    private var hasActivatedTrap = false

    override fun shouldExecute(): Boolean {
        return areaRockySurface.contains(Players.local())
    }

    override fun run() {
        if (hasActivatedTrap) {
            val barricade = Objects.nearestGameObject("Barricade")
            if (!barricade.inViewport()) {
                Camera.turnTo(barricade)
            }
            if (barricade.inViewport() && barricade.interact("Open")) {
                Condition.wait { Players.local().y() >= 3215 }
            }
        } else {
            val wallTrap = Objects.nearestGameObject("Rocky surface")
            if (!wallTrap.inViewport()) {
                Camera.turnTo(wallTrap)
            }
            if (wallTrap.inViewport() && wallTrap.interact("Search")) {
                if (Chat.chatting() && Chat.completeChat()) {
                    if (!Chat.chatting()) {
                        hasActivatedTrap = true
                    }
                }
            }
        }
    }

    override fun stepName(): String {
        return "Passing barricade"
    }
}