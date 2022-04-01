package com.open.quester.quest.templeoftheeye.tasks

import com.open.quester.common.PickupItemStep
import com.open.quester.extensions.nearestGameObject
import com.open.quester.quest.templeoftheeye.TempleOfTheEyeConstants
import org.powbot.api.Condition
import org.powbot.api.Tile
import org.powbot.api.rt4.Game
import org.powbot.api.rt4.GroundItem
import org.powbot.api.rt4.Movement
import org.powbot.api.rt4.Objects
import org.powbot.api.rt4.walking.FailureReason
import org.powbot.api.rt4.walking.WebWalkingResult

class PickupItemIngame(
    destinationLocation: Tile,
    getGroundItem: () -> GroundItem,
    shouldExecute: () -> Boolean,
    interaction: String,
    stepText: String,
    val offset: Tile
) : PickupItemStep(destinationLocation, getGroundItem, shouldExecute, interaction, stepText) {

    override val destinationLocation: Tile
        get() {
            if (TalkToNpcIngame.portalTile == Tile.Nil) {
                TalkToNpcIngame.portalTile = Objects.nearestGameObject(TempleOfTheEyeConstants.MASSIVE_PORTAL_ID).tile
            }

            return if (TalkToNpcIngame.portalTile == Tile.Nil) Game.mapOffset()
                .derive(offset.x, offset.y) else TalkToNpcIngame.portalTile.derive(offset.x, offset.y)
        }

    override fun walkToDestination(): WebWalkingResult {
        val superResult = super.walkToDestination()
        logger.info("Super result ${superResult.success}, ${superResult.failureReason}")
        if (superResult.failureReason == FailureReason.NoPath) {
            if (Movement.step(destinationLocation)) {
                val result =
                    Condition.wait { Movement.destination() == Tile.Nil || Movement.destination().distance() <= 4 }
                return WebWalkingResult(false, result, FailureReason.Unknown)
            }
        }
        return superResult
    }
}