package com.open.quester.common

import com.open.quester.common.base.BaseQuestStep
import com.open.quester.extensions.Conditions
import com.open.quester.extensions.count
import com.open.quester.models.QuestInformation
import org.powbot.api.Condition
import org.powbot.api.Tile
import org.powbot.api.rt4.*
import org.powbot.api.rt4.walking.FailureReason
import org.powbot.api.rt4.walking.WebWalkingResult

open class PickupItemStep(
    protected open val destinationLocation: Tile,
    private val getGroundItem: () -> GroundItem,
    private val shouldExecute: () -> Boolean,
    private val interaction: String,
    private val stepText: String,
    private val information: QuestInformation
) : BaseQuestStep() {

    override fun shouldExecute(): Boolean {
        return shouldExecute.invoke()
    }

    override fun run() {
        val groundItem = getGroundItem.invoke()
        logger.info("Ground item $groundItem")
        if (groundItem != GroundItem.Nil) {
            if (Inventory.isFull()) {
                Inventory.stream().name(*information.foodName).first().interact("Drop")
            }
            if (interaction == "Telegrab") {
                telegrabItem(groundItem)
            } else {
                pickupItem(groundItem)
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

    private fun telegrabItem(groundItem: GroundItem) {
        if (Players.local().tile() != destinationLocation) {
            Movement.builder(destinationLocation)
                .setRunMax(10)
                .setRunMax(50)
                .move()
        } else {
            if (Magic.Spell.TELEKINETIC_GRAB.cast()) {
                val itemName = groundItem.name()
                val count = Inventory.count(itemName)
                if (groundItem.click()) {
                    Condition.wait({ Inventory.count(itemName) > count }, 1000, 8)
                }
            }
        }
    }

    private fun pickupItem(groundItem: GroundItem) {
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
    }

    protected open fun walkToDestination(): WebWalkingResult {
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