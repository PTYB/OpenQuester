package com.open.quester.quest.runemysteries

import com.open.quester.common.CommonMethods
import com.open.quester.common.SimpleConversationStep
import com.open.quester.common.base.BaseQuest
import com.open.quester.common.base.BaseQuestStep
import com.open.quester.models.ItemRequirementCondition
import com.open.quester.models.QuestInformation
import com.open.quester.models.QuestRequirements
import com.open.quester.quest.runemysteries.RuneMysteriesConstants.CONVERSATION_AUBURY
import com.open.quester.quest.runemysteries.RuneMysteriesConstants.CONVERSATION_DUKE_START
import com.open.quester.quest.runemysteries.RuneMysteriesConstants.CONVERSATION_SEDRIDOR
import com.open.quester.quest.runemysteries.RuneMysteriesConstants.NAME_AUBURY
import com.open.quester.quest.runemysteries.RuneMysteriesConstants.NAME_DUKE_HORACIO
import com.open.quester.quest.runemysteries.RuneMysteriesConstants.NAME_SEDRIDOR
import com.open.quester.quest.runemysteries.RuneMysteriesConstants.TILE_AUBURY
import com.open.quester.quest.runemysteries.RuneMysteriesConstants.TILE_DUKE_HORACIO
import com.open.quester.quest.runemysteries.RuneMysteriesConstants.TILE_SEDRIDOR

class RuneMysteries(information: QuestInformation) : BaseQuest(information) {
    private val startQuestStep = SimpleConversationStep(
        NAME_DUKE_HORACIO, TILE_DUKE_HORACIO, CONVERSATION_DUKE_START,
        "Talking to Duke."
    )
    private val talkToSedridor = SimpleConversationStep(
        NAME_SEDRIDOR, TILE_SEDRIDOR, CONVERSATION_SEDRIDOR,
        "Talking to Sedridor"
    )
    private val talkToAubury = SimpleConversationStep(
        NAME_AUBURY, TILE_AUBURY, CONVERSATION_AUBURY,
        "Talking to Aubury"
    )
    private val talkToSedridorAgain = SimpleConversationStep(
        NAME_SEDRIDOR, TILE_SEDRIDOR, arrayOf(),
        "Finishing Quest"
    )

    override fun addRequirements(): QuestRequirements {
        val itemRequirements: List<ItemRequirementCondition> = ArrayList()
        return QuestRequirements(itemRequirements, listOf())
    }

    override fun getQuestStep(stepPosition: Int): BaseQuestStep? {
        when (stepPosition) {
            0 -> return startQuestStep
            1, 2 -> return talkToSedridor
            3, 4 -> return talkToAubury
            5 -> return talkToSedridorAgain
            6 -> {
                information.complete = true
                CommonMethods.closeQuestComplete()
            }
        }
        return null
    }
}