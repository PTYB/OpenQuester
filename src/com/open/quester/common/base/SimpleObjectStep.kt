package com.open.quester.common.base

import org.powbot.api.Tile
import org.powbot.api.rt4.GameObject
import org.powbot.api.rt4.Objects

class SimpleObjectStep(
    noInteractableTile: Tile,
    conversation: Array<String>? = arrayOf(),
    val interactive: () -> GameObject,
    val interaction: (GameObject) -> Boolean,
    val interactionWait: (GameObject) -> Boolean,
    val stepName: String,
    val shouldExecute: () -> Boolean = { true },
    forceWeb: Boolean = false,
) : WalkToInteractiveStep<GameObject>(noInteractableTile, conversation, forceWeb) {

    constructor (
        noInteractableTile: Tile,
        conversation: Array<String>? = arrayOf(),
        interactiveName: String,
        interaction: String,
        interactionWait: (GameObject) -> Boolean,
        stepName: String,
        shouldExecute: () -> Boolean = { true },
        forceWeb: Boolean = false,
    ) : this(
        noInteractableTile, conversation,
        { Objects.stream().name(interactiveName).nearest().first() },
        { go -> go.interact(interaction) },
        interactionWait, stepName, shouldExecute, forceWeb
    )

    override fun shouldExecute(): Boolean {
        return shouldExecute.invoke()
    }

    override fun getInteractive(): GameObject {
        return interactive.invoke()
    }

    override fun interact(interactive: GameObject): Boolean {
        return interaction.invoke(interactive) && interactionWait.invoke(interactive)
    }

    override fun getInteractiveTile(interactive: GameObject): Tile {
        return interactive.tile
    }

    override fun stepName(): String {
        return stepName
    }

}