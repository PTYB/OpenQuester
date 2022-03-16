package com.open.quester.quest.druidicritual

import com.open.quester.common.BankStep
import com.open.quester.common.CommonMethods
import com.open.quester.common.QuestTaskList
import com.open.quester.common.SimpleConversationStep
import com.open.quester.common.base.BaseQuest
import com.open.quester.common.base.BaseQuestStep
import com.open.quester.common.base.SimpleObjectStep
import com.open.quester.extensions.Conditions
import com.open.quester.extensions.nearestGameObject
import com.open.quester.helpers.InteractionsHelper
import com.open.quester.models.ItemRequirementCondition
import com.open.quester.models.QuestInformation
import com.open.quester.models.QuestRequirements
import com.open.quester.quest.Constants.BANK_WEST_FALADOR
import com.open.quester.quest.druidicritual.DruidicRitualConstants.ITEM_BEAR_MEAT
import org.powbot.api.Tile
import org.powbot.api.rt4.Inventory
import org.powbot.api.rt4.Objects
import com.open.quester.quest.druidicritual.DruidicRitualConstants.ITEM_ENCHANTED_BEAR
import com.open.quester.quest.druidicritual.DruidicRitualConstants.ITEM_ENCHANTED_BEEF
import com.open.quester.quest.druidicritual.DruidicRitualConstants.ITEM_ENCHANTED_CHICKEN
import com.open.quester.quest.druidicritual.DruidicRitualConstants.ITEM_ENCHANTED_RAT
import com.open.quester.quest.druidicritual.DruidicRitualConstants.ITEM_RAT_MEAT
import com.open.quester.quest.druidicritual.DruidicRitualConstants.ITEM_RAW_BEEF
import com.open.quester.quest.druidicritual.DruidicRitualConstants.ITEM_RAW_CHICKEN
import com.open.quester.quest.druidicritual.DruidicRitualConstants.NAME_CAULDRON_DOOR
import com.open.quester.quest.druidicritual.DruidicRitualConstants.NPC_SANFEW
import com.open.quester.quest.druidicritual.DruidicRitualConstants.SANFEW_CONVERESATION
import com.open.quester.quest.druidicritual.DruidicRitualConstants.TILE_SANFER_UPSTAIRS
import org.powbot.api.rt4.Chat
import org.powbot.api.rt4.GameObject

class DruidicRitual(information: QuestInformation) : BaseQuest(information) {

    private val itemRequirements = listOf(
        ItemRequirementCondition(ITEM_RAW_CHICKEN, false, 1),
        ItemRequirementCondition(ITEM_RAT_MEAT, false, 1),
        ItemRequirementCondition(ITEM_RAW_BEEF, false, 1),
        ItemRequirementCondition(ITEM_BEAR_MEAT, false, 1),
    )

    private val talkToKaqemeex = SimpleConversationStep(
        DruidicRitualConstants.NPC_KAQEMEEX, DruidicRitualConstants.TILE_KAQEMEEX,
        DruidicRitualConstants.FIRST_CONVERSATION, "Talking to Kaqemeex",
        information
    )

    private val startQuest = startQuest()
    private val talkToSanfew = SimpleConversationStep(
        NPC_SANFEW, TILE_SANFER_UPSTAIRS, SANFEW_CONVERESATION,
        "Talking to Sanfew", information
    )
    private val talkToSanfewAgain = SimpleConversationStep(
        NPC_SANFEW, TILE_SANFER_UPSTAIRS, arrayOf(),
        "Talking to Sanfew", information, false
    ) {
        Inventory.stream()
            .name(ITEM_ENCHANTED_BEEF, ITEM_ENCHANTED_BEAR, ITEM_ENCHANTED_CHICKEN, ITEM_ENCHANTED_RAT)
            .count().toInt() == 4
    }


    private val convertItems = convertItems()

    override fun addRequirements(): QuestRequirements {
        return QuestRequirements(itemRequirements, listOf())
    }

    override fun getQuestStep(stepPosition: Int): BaseQuestStep? {
        return when (stepPosition) {
            0 -> startQuest.processStep()
            1 -> talkToSanfew
            2 -> convertItems.processStep()
            3 -> talkToKaqemeex
            else -> {
                Chat.completeChat()
                information.complete = true
                CommonMethods.closeQuestComplete()
                null
            }
        }
    }

    private fun convertItems(): QuestTaskList {
        return QuestTaskList(
            useItemOnCauldron(ITEM_BEAR_MEAT),
            useItemOnCauldron(ITEM_RAW_BEEF),
            useItemOnCauldron(ITEM_RAW_CHICKEN),
            useItemOnCauldron(ITEM_RAT_MEAT),
            talkToSanfewAgain,
        )
    }

    private fun useItemOnCauldron(itemName: String): SimpleObjectStep {
        return SimpleObjectStep(
            Tile(2891, 9831), arrayOf(),
            { Objects.nearestGameObject(NAME_CAULDRON_DOOR) },
            { go: GameObject ->
                val item = Inventory.stream().name(itemName).first()
                InteractionsHelper.useItemOnInteractive(item, go)
            },
            { Conditions.waitUntilItemLeavesInventory(itemName, 1).invoke() },
            "Converting $itemName",
            information,
            { Inventory.stream().name(itemName).count() > 0 }
        )

    }

    private fun startQuest(): QuestTaskList {
        return QuestTaskList(
            BankStep(itemRequirements, BANK_WEST_FALADOR, information, foodRequired = true),
            talkToKaqemeex,
        )
    }


}