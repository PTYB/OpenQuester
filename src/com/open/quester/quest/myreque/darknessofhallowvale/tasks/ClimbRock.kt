package com.open.quester.quest.myreque.darknessofhallowvale.tasks

import com.open.quester.common.base.BaseQuestStep
import com.open.quester.quest.myreque.darknessofhallowvale.DarknessOfHallowvaleConstants
import org.powbot.api.Condition
import org.powbot.api.rt4.Camera
import org.powbot.api.rt4.Objects
import org.powbot.api.rt4.Players

class ClimbRock : BaseQuestStep(){
    override fun shouldExecute(): Boolean {
        return Players.local().tile() == DarknessOfHallowvaleConstants.TILE_JUST_OFF_BOAT
    }

    override fun run() {
        val rock = Objects.stream().name("Rock").action("Climb-up").first()

        if (!rock.inViewport()) {
            Camera.turnTo(rock)
        }

        if (rock.interact("Climb-up")) {
            Condition.wait { Players.local().animation() != -1 } && Condition.wait { Players.local().animation() == -1 }
        }
    }

    override fun stepName(): String {
        return "Climb rock"
    }
}