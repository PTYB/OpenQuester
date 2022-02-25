package com.open.quester.common.base

import org.powbot.api.Tile
import org.powbot.api.rt4.GameObject
import org.powbot.api.rt4.Objects

class SimpleObjectStep(
    noInteractableTile: Tile,
    conversation: Array<String>? = arrayOf(),
    val interactiveName: String,
    val interaction: String,
    val stepName: String,
    val interactionWait: () -> Boolean,
    val shouldExecute: () -> Boolean = { true },
    forceWeb: Boolean = false,
) : WalkToInteractiveStep<GameObject>(noInteractableTile, conversation, forceWeb) {

    override fun shouldExecute(): Boolean {
        return shouldExecute.invoke()
    }

    override fun getInteractive(): GameObject {
        return Objects.stream().name(interactiveName).nearest().first()
    }

    override fun interact(interactive: GameObject): Boolean {
        return interactive.interact(interaction) && interactionWait.invoke()
    }

    override fun getInteractiveTile(interactive: GameObject): Tile {
        return interactive.tile
    }

    override fun stepName(): String {
        return stepName
    }

}