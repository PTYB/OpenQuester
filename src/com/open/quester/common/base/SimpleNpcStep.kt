package com.open.quester.common.base

import com.open.quester.models.QuestInformation
import org.powbot.api.Condition
import org.powbot.api.Tile
import org.powbot.api.rt4.Npc

class SimpleNpcStep(
    noInteractableTile: Tile,
    conversation: Array<String>? = arrayOf(),
    val interactive: () -> Npc,
    val interaction: (Npc) -> Boolean,
    val interactionWait: (Npc) -> Boolean,
    val stepName: String,
    forceWeb: Boolean = false,
    val shouldExecute: () -> Boolean = { true },
    questInformation: QuestInformation,
): WalkToInteractiveStep<Npc>(noInteractableTile, conversation, forceWeb, questInformation)  {

    override fun shouldExecute(): Boolean {
        return shouldExecute.invoke()
    }

    override fun getInteractive(): Npc {
        return interactive.invoke()
    }

    override fun interact(interactive: Npc): Boolean {
        return interaction.invoke(interactive) && Condition.wait { interactionWait.invoke(interactive) }
    }

    override fun getInteractiveTile(interactive: Npc): Tile {
        return interactive.tile()
    }

    override fun stepName(): String {
        return stepName
    }
}