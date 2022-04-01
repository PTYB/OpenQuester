package com.open.quester.quest.templeoftheeye.tasks

import com.open.quester.common.base.SimpleObjectStep
import com.open.quester.extensions.nearestGameObject
import com.open.quester.helpers.InteractionsHelper
import com.open.quester.models.QuestInformation
import com.open.quester.quest.templeoftheeye.TempleOfTheEyeConstants
import com.open.quester.quest.templeoftheeye.TempleOfTheEyeConstants.ACTION_ENTER_ALTAR
import com.open.quester.quest.templeoftheeye.TempleOfTheEyeConstants.NAME_FIRE_ALTAR_PORTAL
import com.open.quester.quest.templeoftheeye.TempleOfTheEyeConstants.NAME_GUARDIAN_OF_WATER
import com.open.quester.quest.templeoftheeye.tasks.TalkToNpcIngame.Companion.portalTile
import org.powbot.api.Condition
import org.powbot.api.Tile
import org.powbot.api.rt4.*
import org.powbot.api.rt4.walking.WebWalkingResult
import org.powbot.api.rt4.walking.local.LocalPathFinder

class InteractWithObjectIngame(
    noInteractableTile: Tile,
    conversation: Array<String>? = arrayOf(),
    val interactiveName: String,
    val interactionAction: String,
    interactionWait: (GameObject) -> Boolean,
    stepName: String,
    questInformation: QuestInformation,
    val offset: Tile,
    shouldExecute: () -> Boolean = { true },
) : SimpleObjectStep(
    noInteractableTile, conversation, interactiveName,
    interactionAction,
    interactionWait,
    stepName,
    questInformation,
    shouldExecute,
    false,
) {
    private var count = 0

    override fun shouldExecute(): Boolean {
        return HintArrow.type() == 2 && super.shouldExecute()
    }

    override val noInteractableTile: Tile
        get() {
            if (portalTile == Tile.Nil) {
                portalTile = Objects.nearestGameObject(TempleOfTheEyeConstants.MASSIVE_PORTAL_ID).tile
            }

            return if (portalTile == Tile.Nil) Game.mapOffset()
                .derive(offset.x, offset.y) else portalTile.derive(offset.x, offset.y)
        }

    override fun getInteractive(): GameObject {
        return if (interactiveName == NAME_GUARDIAN_OF_WATER) {
            Objects.nearestGameObject(interactiveName)
        } else {
            Objects.stream().name(interactiveName).action(interactionAction).nearest(noInteractableTile).first()
        }
    }

    override fun interact(interactive: GameObject): Boolean {
        count = 0
        if (interactiveName == NAME_GUARDIAN_OF_WATER && !interactive.actions().contains(ACTION_ENTER_ALTAR)) {
            return InteractionsHelper.useItemOnInteractive(
                Inventory.stream().name("Portal talisman (water)").first(), interactive
            ) && Condition.wait { interactionWait.invoke(interactive) }
        }
        return super.interact(interactive)
    }

    override fun walkToTile(tile: Tile): WebWalkingResult {
        val result = LocalPathFinder.findWalkablePath(tile).traverseUntilReached(4.0)
        logger.info("Walking locally $count")
        if (result) {
            count = 0
        } else {
            count++
        }

        if (count >= 3) {
            Movement.step(tile)
            count = 0
        }

        return WebWalkingResult(false, result, null)
    }
}