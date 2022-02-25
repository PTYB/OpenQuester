package com.open.quester.quest.xmarksthespot

import com.open.quester.common.BankStep
import com.open.quester.common.CommonMethods
import com.open.quester.common.QuestTaskList
import com.open.quester.common.SimpleConversationStep
import com.open.quester.common.base.BaseQuest
import com.open.quester.common.base.BaseQuestStep
import com.open.quester.models.ItemRequirement
import com.open.quester.models.ItemRequirementCondition
import com.open.quester.models.QuestInformation
import com.open.quester.models.QuestRequirements
import com.open.quester.quest.Constants.BANK_LUMBRIDGE
import com.open.quester.quest.Constants.ITEM_SPADE
import com.open.quester.quest.xmarksthespot.XMarksTheSpotConstants.NAME_VEOS
import com.open.quester.quest.xmarksthespot.XMarksTheSpotConstants.TILE_DIG_BOBS
import com.open.quester.quest.xmarksthespot.XMarksTheSpotConstants.TILE_DIG_CASTLE
import com.open.quester.quest.xmarksthespot.XMarksTheSpotConstants.TILE_DIG_DRAYNOR
import com.open.quester.quest.xmarksthespot.XMarksTheSpotConstants.TILE_DIG_PIG_PEN
import com.open.quester.quest.xmarksthespot.XMarksTheSpotConstants.TILE_LUMB_VEOS
import com.open.quester.quest.xmarksthespot.XMarksTheSpotConstants.TILE_PORT_VEOS
import com.open.quester.quest.xmarksthespot.XMarksTheSpotConstants.VARPBIT_STATE_MASK
import com.open.quester.quest.xmarksthespot.XMarksTheSpotConstants.VEOS_START_QUEST
import com.open.quester.quest.xmarksthespot.steps.DigStep
import org.powbot.api.rt4.Inventory
import org.powbot.api.rt4.Varpbits

class XMarksTheSpot(information: QuestInformation) : BaseQuest(information) {

    private val spadeRequirement = ItemRequirement(ITEM_SPADE, false)
    private val spadeCondition = ItemRequirementCondition(spadeRequirement)
    private var startQuestStep = SimpleConversationStep(
        NAME_VEOS, TILE_LUMB_VEOS, VEOS_START_QUEST,
        "Starting Quest"
    )
    private val spadeBank = BankStep(listOf(spadeCondition), BANK_LUMBRIDGE, information, {
        Inventory.stream().name(ITEM_SPADE).count() < 1
    })
    private val startQuestWithSpade = QuestTaskList(spadeBank, startQuestStep)
    private val digAtBobs = DigStep(TILE_DIG_BOBS, "Digging at Bob's")
    private val digAtCastle = DigStep(TILE_DIG_CASTLE, "Digging at castle")
    private val digAtDraynor = DigStep(TILE_DIG_DRAYNOR, "Digging at draynor")
    private val digAtPigPen = DigStep(TILE_DIG_PIG_PEN, "Digging at pig pen")
    private val finishQuest = SimpleConversationStep(NAME_VEOS, TILE_PORT_VEOS, arrayOf(), "Finishing Quest")

    override fun addRequirements(): QuestRequirements {
        return QuestRequirements(listOf(spadeCondition), listOf())
    }

    override fun getQuestStep(stepPosition: Int): BaseQuestStep? {
        val questStateId: Int = information.questVarbits.questVarbit
        when (Varpbits.varpbit(questStateId, VARPBIT_STATE_MASK)) {
            0, 1 -> return startQuestWithSpade.processStep()
            2 -> return digAtBobs
            3 -> return digAtCastle
            4 -> return digAtDraynor
            5 -> return digAtPigPen
            6, 7 -> return finishQuest
            else -> {
                information.complete = true
                CommonMethods.closeQuestComplete()
            }
        }
        return null
    }
}