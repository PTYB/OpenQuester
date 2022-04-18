package com.open.quester.quest.myreque.darknessofhallowvale.tasks

import com.open.quester.common.base.BaseQuestStep
import com.open.quester.quest.myreque.darknessofhallowvale.DarknessOfHallowvaleConstants
import org.powbot.api.Condition
import org.powbot.api.rt4.Camera
import org.powbot.api.rt4.Objects
import org.powbot.api.rt4.Players

class JumpFromBoat : BaseQuestStep() {
    override fun shouldExecute(): Boolean {
        return Players.local().tile() == DarknessOfHallowvaleConstants.TILE_JUST_ON_BOAT
    }

    override fun run() {
        val rock = Objects.stream().name("Rock").action("Jump-onto").first()

        if (!rock.inViewport()) {
            Camera.turnTo(rock)
        }

        if (rock.interact("Jump-onto")) {
            Condition.wait { Players.local().tile() == rock.tile }
        }
    }

    override fun stepName(): String {
        return "Jumping from boat"
    }
}