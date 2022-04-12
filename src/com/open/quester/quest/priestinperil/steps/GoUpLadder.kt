package com.open.quester.quest.priestinperil.steps

import com.open.quester.common.base.WalkToInteractiveStep
import com.open.quester.models.QuestInformation
import org.powbot.api.Condition
import org.powbot.api.Tile
import org.powbot.api.rt4.GameObject
import org.powbot.api.rt4.Objects
import org.powbot.api.rt4.Players
import org.powbot.quests.quest.priestinperil.PriestInPerilConstants
import org.powbot.quests.quest.priestinperil.PriestInPerilConstants.AREA_SECOND_UNDERGROUND
import org.powbot.quests.quest.priestinperil.PriestInPerilConstants.NAME_GATE

class GoUpLadder(information: QuestInformation) : WalkToInteractiveStep<GameObject>(PriestInPerilConstants.TILE_TRAPDOOR, questInformation = information) {

    override fun shouldExecute(): Boolean {
        val ladder = getInteractive()
        if (ladder == GameObject.Nil) {
            return false
        }
        val gate = getGate()

        if (gate == GameObject.Nil) {
            return false
        }
        // To be double sure we are in right area compare distance
        return gate.tile.y + 12 == ladder.tile.y && !AREA_SECOND_UNDERGROUND.contains(Players.local())
    }

    private fun getGate(): GameObject {
        return Objects.stream().name(NAME_GATE).nearest().first()
    }

    override fun stepName(): String {
        return "Going up ladder"
    }

    override fun getInteractive(): GameObject {
        return  Objects.stream().name("Ladder").nearest().first()
    }

    override fun interact(interactive: GameObject): Boolean {
       return interactive.interact("Climb") &&  Condition.wait { !interactive.valid() }
    }

    override fun getInteractiveTile(interactive: GameObject): Tile {
        return interactive.tile
    }
}