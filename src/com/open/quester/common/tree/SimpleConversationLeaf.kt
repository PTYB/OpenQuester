package com.open.quester.common.tree

import com.open.quester.common.QuestLeafStep
import com.open.quester.common.SimpleConversationStep
import com.open.quester.common.base.BaseQuest
import com.open.quester.models.QuestInformation
import org.powbot.api.Tile

class SimpleConversationLeaf<T : BaseQuest>(
    quest: T,
    npcName: String,
    npcTile: Tile,
    conversation: Array<String>,
    stepText: String,
    questInformation: QuestInformation
) : QuestLeafStep<T>(quest, stepText) {
    private val conversationStep = SimpleConversationStep(npcName, npcTile, conversation, stepText, questInformation)
    override fun run() {
        conversationStep.run()
    }
}