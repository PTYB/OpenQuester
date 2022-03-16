package com.open.quester.quest.sheepshearer.leafs

import com.open.quester.common.QuestLeafStep
import com.open.quester.common.SimpleConversationStep
import com.open.quester.quest.sheepshearer.SheepShearer
import com.open.quester.quest.sheepshearer.SheepShearerConstants

class TalkToFred(val quest: SheepShearer) : QuestLeafStep<SheepShearer>(quest, "Talking to fred") {
    private val talkToFred = SimpleConversationStep(
        SheepShearerConstants.NAME_FRED,
        SheepShearerConstants.TILE_FRED,
        SheepShearerConstants.CONVERSATION_FRED,
        "Talk to Fred",
        quest.information
    )

    override fun run() {
        talkToFred.execute()
    }
}