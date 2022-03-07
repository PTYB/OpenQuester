package com.open.quester.common

import com.open.quester.common.base.WalkToInteractiveStep
import com.open.quester.extensions.Conditions
import com.open.quester.models.QuestInformation
import org.powbot.api.Condition
import org.powbot.api.Random
import org.powbot.api.Tile
import org.powbot.api.rt4.Npc
import org.powbot.api.rt4.Npcs

/**
 * This step is used to handle a conversation with an npc and complete it
 *  @param npcTile Tile of the NPC to walk towards
 *  @param conversation dialogue prompts which we would select, not sequential and count limited
 *  @param forceWeb if you wish to force web walking to the NPC
 *  @param stepName Text you wish to display in paint for the current step
 *  @param npcName Name of the npc you wish to talk to
 */
class SimpleConversationStep(
    val npcName: String,
    npcTile: Tile,
    conversation: Array<String>,
    val stepName: String,
    questInformation: QuestInformation,
    forceWeb: Boolean = false,
    val shouldExecute: () -> Boolean = { true },
) : WalkToInteractiveStep<Npc>(npcTile, conversation, forceWeb, questInformation) {

    /**
     * {@inheritDoc}
     */
    override fun shouldExecute(): Boolean {
        return shouldExecute.invoke()
    }

    /**
     * {@inheritDoc}
     */
    override fun getInteractive(): Npc {
        return Npcs.stream().name(npcName).action("Talk-to").nearest().first()
    }

    /**
     * {@inheritDoc}
     */
    override fun interact(interactive: Npc): Boolean {
        return interactive.interact("Talk-to") && Condition.wait(
            Conditions.waitUntilChatting(),
            Random.nextInt(450, 550),
            10
        )
    }

    /**
     * {@inheritDoc}
     */
    override fun getInteractiveTile(interactive: Npc): Tile {
        return interactive.tile()
    }

    /**
     * {@inheritDoc}
     */
    override fun stepName(): String {
        return stepName
    }
}