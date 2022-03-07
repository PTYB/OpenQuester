package com.open.quester.quest.romeoandjuliet

import com.open.quester.common.BankStep
import com.open.quester.common.CommonMethods.closeQuestComplete
import com.open.quester.common.QuestTaskList
import com.open.quester.common.SimpleConversationStep
import com.open.quester.common.base.BaseQuest
import com.open.quester.common.base.BaseQuestStep
import com.open.quester.models.ItemRequirement
import com.open.quester.models.ItemRequirementCondition
import com.open.quester.models.QuestInformation
import com.open.quester.models.QuestRequirements
import com.open.quester.quest.romeoandjuliet.RomeoAndJulietConstants.ITEM_CADAVA_POTION
import org.powbot.api.rt4.Inventory
import com.open.quester.quest.romeoandjuliet.RomeoAndJulietConstants as Constants
import com.open.quester.quest.Constants as QuestConstants

class RomeoAndJuliet(information: QuestInformation) : BaseQuest(information) {

    private val cadavaRequirement = ItemRequirementCondition(ItemRequirement(Constants.ITEM_CADAVA_BERRIES, false))

    private val cadavaBank = BankStep(listOf(cadavaRequirement), QuestConstants.BANK_VARROCK_WEST_SOUTH_SIDE, information, {
        Inventory.stream().name(Constants.ITEM_CADAVA_BERRIES).count() < 1
    })

    private var startQuestStep = QuestTaskList(
        cadavaBank, SimpleConversationStep(
            Constants.NAME_ROMEO, Constants.TILE_ROMEO, Constants.CONVERSATION_START_QUEST,
            "Talking to Romeo.", information
        )
    )

    private var talkToJuliet = SimpleConversationStep(
        Constants.NAME_JULIET, Constants.TILE_JULIET, Constants.CONVERSATION_FIRST_JULIET,
        "Talking to Juliet", information
    )

    private var giveLetterToRomeo = SimpleConversationStep(
        Constants.NAME_ROMEO, Constants.TILE_ROMEO, arrayOf(), "Talking to Romeo.", information
    )

    private var talkToFatherLawrence = SimpleConversationStep(
        Constants.NAME_FATHER_LAWRENCE, Constants.TILE_FATHER_LAWRENCE, Constants.CONVERSATION_FATHER_LAWRENCE,
        "Talking to Father Lawrence.", information
    )

    private var talkToApocethary = QuestTaskList(
        cadavaBank, SimpleConversationStep(
            Constants.NAME_APOTHECARY, Constants.TILE_APOTHECARY, Constants.CONVERSATION_APOTHECARY,
            "Talking to Apothecary", information
        )
    )

    private var giveJulietPotion = QuestTaskList(
        SimpleConversationStep(
            Constants.NAME_APOTHECARY, Constants.TILE_APOTHECARY, Constants.CONVERSATION_APOTHECARY,
            "Talking to Apothecary", information,
            shouldExecute = { Inventory.stream().name(ITEM_CADAVA_POTION).count().toInt() == 0 }
        ), talkToJuliet
    )

    private var finishQuestStep = SimpleConversationStep(
        Constants.NAME_ROMEO, Constants.TILE_ROMEO, arrayOf(),
        "Talking to Romeo.", information
    )

    override fun addRequirements(): QuestRequirements {
        val itemRequirements = listOf(cadavaRequirement)
        return QuestRequirements(itemRequirements, listOf())
    }

    override fun getQuestStep(stepPosition: Int): BaseQuestStep? {
        return when (stepPosition) {
            0 -> startQuestStep.processStep()
            10 -> talkToJuliet
            20 -> giveLetterToRomeo
            30 -> talkToFatherLawrence
            40 -> talkToApocethary.processStep()
            50 -> giveJulietPotion.processStep()
            60 -> finishQuestStep
            else -> {
                information.complete = true
                closeQuestComplete()
                null
            }
        }
    }
}