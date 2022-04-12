package com.open.quester.common

import com.open.quester.common.base.BaseQuestStep
import com.open.quester.helpers.CombatHelper
import com.open.quester.models.QuestInformation
import org.powbot.api.Tile
import org.powbot.api.rt4.Movement
import org.powbot.api.rt4.Players
import java.util.concurrent.Callable

class WalkToExactTile(
    val tile: Tile,
    val stepName: String,
    val callable: Callable<Boolean>,
    val information: QuestInformation
) : BaseQuestStep() {
    override fun shouldExecute(): Boolean {
        return callable.call()
    }

    override fun run() {
        Movement.builder(tile)
            .setRunMin(10)
            .setRunMax(30)
            .setWalkUntil {
                if (CombatHelper.shouldEat(*information.foodName)) {
                    CombatHelper.eatFood(*information.foodName)
                }
                tile == Players.local().tile()
            }
            .move()
    }

    override fun stepName(): String {
        return stepName
    }
}