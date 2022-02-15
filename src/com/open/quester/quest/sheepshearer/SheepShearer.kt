package com.open.quester.quest.sheepshearer

import com.open.quester.common.CommonMethods.closeQuestComplete
import com.open.quester.common.SimpleConversationStep
import com.open.quester.common.base.BaseQuest
import com.open.quester.common.base.BaseQuestStep
import com.open.quester.models.QuestInformation
import com.open.quester.models.QuestRequirements
import com.open.quester.quest.sheepshearer.SheepShearerConstants.CONVERSATION_FRED
import com.open.quester.quest.sheepshearer.SheepShearerConstants.NAME_FRED
import com.open.quester.quest.sheepshearer.SheepShearerConstants.TILE_FRED
import com.open.quester.quest.sheepshearer.branches.HasBallsOfWool

class SheepShearer(information: QuestInformation) : BaseQuest(information) {
    private var talkToFred = SimpleConversationStep(NAME_FRED, TILE_FRED, CONVERSATION_FRED, "Talk to Fred")
    private val handBallsTree = HasBallsOfWool(this)

    override fun addRequirements(): QuestRequirements {
        return QuestRequirements(listOf(), listOf())
    }

    override fun getQuestStep(stepPosition: Int): BaseQuestStep? {
        return when (stepPosition) {
            0 -> talkToFred
            in 1..19 -> handBallsTree.getLeaf()
            20 -> talkToFred
            else -> {
                information.complete = true
                closeQuestComplete()
                null
            }
        }
    }
}