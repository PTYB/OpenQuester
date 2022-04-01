package com.open.quester.quest.templeoftheeye.tasks

import com.open.quester.common.base.SimpleObjectStep
import com.open.quester.models.QuestInformation
import com.open.quester.quest.templeoftheeye.TempleOfTheEyeConstants
import org.powbot.api.Tile
import org.powbot.api.rt4.GameObject
import org.powbot.api.rt4.HintArrow

class AlterInteraction(
    noInteractableTile: Tile,
    conversation: Array<String>? = arrayOf(),
    val interactiveName: String,
    interactionAction: String,
    interactionWait: (GameObject) -> Boolean,
    stepName: String,
    questInformation: QuestInformation,
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
    override val noInteractableTile: Tile
        get() {
            // Reset base offset since you leave the area you can come back to a different instance.
            TalkToNpcIngame.portalTile = Tile.Nil
            // Make sure its walkable
            return Tile(HintArrow.x(), HintArrow.y()).derive(-2, -2)
        }

    override fun interact(interactive: GameObject): Boolean {
        if (interactive.name == TempleOfTheEyeConstants.NAME_FIRE_ALTAR_PORTAL) {
            // You can spawn into difference instances which messes up offset
            TalkToNpcIngame.portalTile = Tile.Nil
        }
        return super.interact(interactive)
    }
}