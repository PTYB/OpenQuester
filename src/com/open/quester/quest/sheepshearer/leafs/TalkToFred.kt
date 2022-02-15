package com.open.quester.quest.sheepshearer.leafs

import com.open.quester.common.QuestLeafStep
import com.open.quester.common.SimpleConversationStep
import com.open.quester.quest.sheepshearer.SheepShearer
import com.open.quester.quest.sheepshearer.SheepShearerConstants

class TalkToFred(val quest: SheepShearer) : QuestLeafStep<SheepShearer>(quest, "Gathering wool") {
    private val talkToFred = SimpleConversationStep(
        SheepShearerConstants.NAME_FRED,
        SheepShearerConstants.TILE_FRED,
        SheepShearerConstants.CONVERSATION_FRED,
        "Talk to Fred"
    )

    override fun run() {
        talkToFred.execute()
    }
}