package com.open.quester.common.base

import com.open.quester.models.QuestInformation
import org.powbot.api.Condition
import org.powbot.api.Tile
import org.powbot.api.rt4.GameObject
import org.powbot.api.rt4.Objects

open class SimpleObjectStep(
    noInteractableTile: Tile,
    conversation: Array<String>? = arrayOf(),
    val interactive: () -> GameObject,
    val interaction: (GameObject) -> Boolean,
    val interactionWait: (GameObject) -> Boolean,
    val stepName: String,
    questInformation: QuestInformation,
    val shouldExecute: () -> Boolean = { true },
    forceWeb: Boolean = false,
) : WalkToInteractiveStep<GameObject>(noInteractableTile, conversation, forceWeb, questInformation) {

    constructor (
        noInteractableTile: Tile,
        conversation: Array<String>? = arrayOf(),
        interactiveName: String,
        interaction: String,
        interactionWait: (GameObject) -> Boolean,
        stepName: String,
        questInformation: QuestInformation,
        shouldExecute: () -> Boolean = { true },
        forceWeb: Boolean = false
    ) : this(
        noInteractableTile, conversation,
        { Objects.stream().name(interactiveName).action(interaction).nearest().first() },
        { go -> go.interact(interaction) },
        interactionWait, stepName, questInformation, shouldExecute, forceWeb
    )

    override fun shouldExecute(): Boolean {
        return shouldExecute.invoke()
    }

    override fun getInteractive(): GameObject {
        return interactive.invoke()
    }

    override fun interact(interactive: GameObject): Boolean {
        return interaction.invoke(interactive) && Condition.wait { interactionWait.invoke(interactive) }
    }

    override fun getInteractiveTile(interactive: GameObject): Tile {
        return interactive.tile
    }

    override fun stepName(): String {
        return stepName
    }

}