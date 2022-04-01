package com.open.quester.common

import com.open.quester.common.base.BaseQuestStep
import com.open.quester.extensions.Conditions
import com.open.quester.extensions.count
import com.open.quester.models.QuestInformation
import org.powbot.api.Condition
import org.powbot.api.Tile
import org.powbot.api.rt4.GroundItem
import org.powbot.api.rt4.Inventory
import org.powbot.api.rt4.Movement
import org.powbot.api.rt4.Players
import org.powbot.api.rt4.walking.FailureReason
import org.powbot.api.rt4.walking.WebWalkingResult

open class PickupItemStep(
    protected open val destinationLocation: Tile,
    private val getGroundItem: () -> GroundItem,
    private val shouldExecute: () -> Boolean,
    private val interaction: String,
    private val stepText: String
) : BaseQuestStep() {

    override fun shouldExecute(): Boolean {
        return shouldExecute.invoke()
    }

    override fun run() {
        // TODO Check if need to eat later

        val groundItem = getGroundItem.invoke()

        if (groundItem != GroundItem.Nil) {
            if (groundItem.inViewport() && groundItem.reachable() && groundItem.interact(interaction)) {
                val itemCount = Inventory.count(groundItem.name())
                Condition.wait(Conditions.waitUntilItemEntersInventory(groundItem.name(), itemCount))
            } else {
                val result = Movement.builder(groundItem.tile)
                    .setRunMax(10)
                    .setRunMax(50)
                    .setWalkUntil { groundItem.inViewport() && groundItem.reachable() }
                    .move()
                if (result.failureReason == FailureReason.NoPath) {
                    walkToDestination()
                }
            }
        } else {
            if (destinationLocation.tile().floor == Players.local()
                    .tile().floor && destinationLocation.matrix().inViewport(true)
            ) {
                logger.info("Waiting for item to spawn")
                Condition.wait {
                    getGroundItem.invoke() != GroundItem.Nil // TODO Check if need to eat later
                }
            } else {
                walkToDestination()
            }
        }
    }
    protected open fun walkToDestination() : WebWalkingResult {
        logger.info("Walking to destination")
        return Movement.builder(destinationLocation)
            .setRunMax(10)
            .setRunMax(50)
            .setWalkUntil {
                // TODO Eat food if needed later
                destinationLocation.tile().floor == Players.local()
                    .tile().floor && destinationLocation.matrix().inViewport(true)
            }
            .move()
    }

    override fun stepName(): String {
        return stepText
    }
}