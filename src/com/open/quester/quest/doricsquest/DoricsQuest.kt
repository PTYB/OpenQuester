package com.open.quester.quest.doricsquest

import com.open.quester.common.BankStep
import com.open.quester.common.CommonMethods.closeQuestComplete
import com.open.quester.common.QuestTaskList
import com.open.quester.common.SimpleConversationStep
import com.open.quester.common.base.BaseQuest
import com.open.quester.common.base.BaseQuestStep
import com.open.quester.models.ItemRequirementCondition
import com.open.quester.models.QuestInformation
import com.open.quester.models.QuestRequirements
import com.open.quester.quest.Constants.BANK_WEST_FALADOR
import com.open.quester.quest.doricsquest.DoricsQuestConstants.FIRST_CONVERSATION
import com.open.quester.quest.doricsquest.DoricsQuestConstants.ITEM_CLAY
import com.open.quester.quest.doricsquest.DoricsQuestConstants.ITEM_COPPER_ORE
import com.open.quester.quest.doricsquest.DoricsQuestConstants.ITEM_IRON_ORE
import com.open.quester.quest.doricsquest.DoricsQuestConstants.NAME_DORICS
import com.open.quester.quest.doricsquest.DoricsQuestConstants.TILE_DORICS

class DoricsQuest(information: QuestInformation) : BaseQuest(information) {

    private var doricsFinished = SimpleConversationStep(
        NAME_DORICS, TILE_DORICS, FIRST_CONVERSATION, "Talking to Doric",
        information
    )

    private val requirements = listOf(
        ItemRequirementCondition(ITEM_COPPER_ORE, false, 4),
        ItemRequirementCondition(ITEM_CLAY, false, 6),
        ItemRequirementCondition(ITEM_IRON_ORE, false, 2)
    )

    private var questStepList = QuestTaskList(BankStep(requirements, BANK_WEST_FALADOR, information), doricsFinished)

    override fun addRequirements(): QuestRequirements {
        return QuestRequirements(requirements, listOf())
    }

    override fun getQuestStep(stepPosition: Int): BaseQuestStep? {
        return when (stepPosition) {
            0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10 -> questStepList.processStep()
            else -> {
                information.complete = true
                closeQuestComplete()
                null
            }
        }
    }
}