package com.open.quester.quest.templeoftheeye.tasks

import com.open.quester.common.SimpleConversationStep
import com.open.quester.common.base.WalkToInteractiveStep
import com.open.quester.extensions.Conditions
import com.open.quester.extensions.nearestGameObject
import com.open.quester.extensions.nearestNpc
import com.open.quester.models.QuestInformation
import com.open.quester.quest.templeoftheeye.TempleOfTheEyeConstants.MASSIVE_PORTAL_ID
import com.open.quester.quest.templeoftheeye.TempleOfTheEyeConstants.NAME_GREAT_GUARDIAN
import org.powbot.api.Condition
import org.powbot.api.Point
import org.powbot.api.Random
import org.powbot.api.Tile
import org.powbot.api.rt4.*
import org.powbot.api.rt4.walking.FailureReason
import org.powbot.api.rt4.walking.WebWalkingResult
import org.powbot.api.rt4.walking.local.LocalPathFinder
import javax.print.attribute.standard.Destination

class TalkToNpcIngame(
    npcName: String,
    npcTile: Tile,
    conversation: Array<String>,
    stepName: String,
    questInformation: QuestInformation,
    shouldExecute: () -> Boolean = { true },
    val offset: Tile
) : SimpleConversationStep(npcName, npcTile, conversation, stepName, questInformation, false, shouldExecute) {

    companion object {
        var portalTile = Tile.Nil
    }

    override fun interact(interactive: Npc): Boolean {
        if (npcName == NAME_GREAT_GUARDIAN) {
            return interactive.interact("Power-up") && Condition.wait(
                Conditions.waitUntilChatting(),
                Random.nextInt(450, 550),
                10
            )
        }
        return super.interact(interactive)
    }

    override fun getInteractive(): Npc {
        return Npcs.nearestNpc(npcName)
    }

    override val noInteractableTile: Tile
        get() {
            // Ghetto fix for instance changing by checking distance. Will do a proper fix next time I go through quest.
            if (portalTile == Tile.Nil || portalTile.distanceTo(Players.local()) > 100) {
                portalTile = Objects.nearestGameObject(MASSIVE_PORTAL_ID).tile
            }

            return if (portalTile == Tile.Nil) Game.mapOffset()
                .derive(offset.x, offset.y) else portalTile.derive(offset.x, offset.y)
        }

    override fun walkToTile(tile: Tile): WebWalkingResult {
        val result = LocalPathFinder.findWalkablePath(tile).traverseUntilReached(4.0)
        return WebWalkingResult(false, result, null)
    }
}
