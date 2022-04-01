package com.open.quester.quest.entertheabyss.tasks

import com.open.quester.common.base.BaseQuestStep
import com.open.quester.extensions.nearestGameObject
import org.powbot.api.Condition
import org.powbot.api.InteractableEntity
import org.powbot.api.Random
import org.powbot.api.rt4.*

class LeaveEssense : BaseQuestStep() {

    private var mine: GameObject = GameObject.Nil

    override fun shouldExecute(): Boolean {
        mine = Objects.nearestGameObject("Rune Essence")
        return mine != GameObject.Nil
    }

    override fun run() {
        var entity: InteractableEntity = Npcs.stream().name("Portal").nearest().first()
        if (entity == Npc.Nil) {
            entity = Objects.stream().name("Portal").nearest().first()
            if (entity == GameObject.Nil) {
                stepToMine()
            }
        }
        val action = if(entity.actions().contains("Use")) "Use" else "Exit"
        if (entity.inViewport(true) && entity.interact(action)) {
            Condition.wait({ !entity.valid() }, 1000, 8)
        } else {
            Movement.step(entity.tile(), 3)
        }
    }

    private fun stepToMine() {
        if (Movement.step(mine)) {
            Condition.sleep(Random.nextInt(2500, 4000))
        }
    }

    override fun stepName(): String {
        return "Leaving mine"
    }
}