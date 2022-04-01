package com.open.quester.quest.gertrudescat

import com.open.quester.common.*
import com.open.quester.common.CommonMethods.closeQuestComplete
import com.open.quester.common.base.BaseQuest
import com.open.quester.common.base.BaseQuestStep
import com.open.quester.common.base.SimpleNpcStep
import com.open.quester.extensions.Conditions
import com.open.quester.extensions.count
import com.open.quester.extensions.nearestNpc
import com.open.quester.helpers.InteractionsHelper
import com.open.quester.models.ItemRequirement
import com.open.quester.models.ItemRequirementCondition
import com.open.quester.models.QuestInformation
import com.open.quester.models.QuestRequirements
import com.open.quester.quest.Constants.BANK_VARROCK_WEST_SOUTH_SIDE
import com.open.quester.quest.Constants.ITEM_COINS
import com.open.quester.quest.Constants.ItemRequirements.NAME_MILK
import com.open.quester.quest.gertrudescat.GertrudesCatConstants.CONVERSATION_GERTRUDE
import com.open.quester.quest.gertrudescat.GertrudesCatConstants.CONVERSATION_SHILOP
import com.open.quester.quest.gertrudescat.GertrudesCatConstants.ITEM_DOOGLE_LEAVES
import com.open.quester.quest.gertrudescat.GertrudesCatConstants.ITEM_SARDINE
import com.open.quester.quest.gertrudescat.GertrudesCatConstants.ITEM_SEASONED_SARDINE
import com.open.quester.quest.gertrudescat.GertrudesCatConstants.NAME_GERTRUDE
import com.open.quester.quest.gertrudescat.GertrudesCatConstants.NAME_GERTRUDES_CAT
import com.open.quester.quest.gertrudescat.GertrudesCatConstants.NAME_SHILOP
import com.open.quester.quest.gertrudescat.GertrudesCatConstants.TILE_DOOGLE_LEAVES
import com.open.quester.quest.gertrudescat.GertrudesCatConstants.TILE_GERTRUDE
import com.open.quester.quest.gertrudescat.GertrudesCatConstants.TILE_GERTRUDES_CAT
import com.open.quester.quest.gertrudescat.GertrudesCatConstants.TILE_SHILOP
import com.open.quester.quest.gertrudescat.tasks.GetKitten
import org.powbot.api.rt4.*

class GertrudesCat(information: QuestInformation) : BaseQuest(information) {

    private val sardineRequirement = ItemRequirementCondition(
        ItemRequirement(ITEM_SEASONED_SARDINE, false, 1),
        ItemRequirement(ITEM_SARDINE, true, 1),
    )
    private val milkRequirement = ItemRequirementCondition(NAME_MILK, false, 1)
    private val coinRequirement = ItemRequirementCondition(ITEM_COINS, true, 100)

    private val startQuest = SimpleConversationStep(
        NAME_GERTRUDE, TILE_GERTRUDE, CONVERSATION_GERTRUDE,
        "Talking to Gertrude", information
    )

    private val withdrawItems = BankStep(
        listOf(sardineRequirement, milkRequirement, coinRequirement),
        BANK_VARROCK_WEST_SOUTH_SIDE, information
    )

    private val pickupLeaves = PickupItemStep(
        TILE_DOOGLE_LEAVES,
        { GroundItems.stream().name(ITEM_DOOGLE_LEAVES).first() },
        { Inventory.count(ITEM_SEASONED_SARDINE, ITEM_DOOGLE_LEAVES) == 0 },
        "Take",
        "Picking up doogle leaves"
    )

    private val makeSeasonedSardine = CombineItemStep(
        ITEM_DOOGLE_LEAVES, ITEM_SARDINE, "Making Seasoned Sardines",
        { Inventory.count(ITEM_SARDINE) == 1 && Inventory.count(ITEM_DOOGLE_LEAVES) == 1 }, false
    )

    private val startQuestStepList = QuestTaskList(withdrawItems, pickupLeaves, makeSeasonedSardine, startQuest)

    private val talkToChildren = SimpleConversationStep(
        NAME_SHILOP, TILE_SHILOP, CONVERSATION_SHILOP, "Talking to children.", information
    )

    private val giveMilkToCat = SimpleNpcStep(
        TILE_GERTRUDES_CAT, arrayOf(),
        { Npcs.nearestNpc(NAME_GERTRUDES_CAT) },
        { cat ->
            val milk = Inventory.stream().name(NAME_MILK).first()
            InteractionsHelper.useItemOnInteractive(milk, cat)
        },
        { Conditions.waitUntilItemLeavesInventory(NAME_MILK, 1).invoke() },
        "Using milk on cat",
        questInformation = information
    )
    private val giveSardineToCat = SimpleNpcStep(
        TILE_GERTRUDES_CAT, arrayOf(),
        { Npcs.nearestNpc(NAME_GERTRUDES_CAT) },
        { cat ->
            val sardine = Inventory.stream().name(ITEM_SEASONED_SARDINE).first()
            InteractionsHelper.useItemOnInteractive(sardine, cat)
        },
        { Conditions.waitUntilItemLeavesInventory(ITEM_SEASONED_SARDINE, 1).invoke() },
        "Using milk on cat",
        questInformation = information
    )

    private val finishQuest = SimpleConversationStep(
        NAME_GERTRUDE, TILE_GERTRUDE, arrayOf(),
        "Talking to Gertrude",
        questInformation = information
    )

    private val getKitten = GetKitten(information)
    private val returnKitten = SimpleNpcStep(
        TILE_GERTRUDES_CAT, arrayOf(), { Npcs.nearestNpc(NAME_GERTRUDES_CAT) },
        { cat ->
            val kitten = Inventory.stream().name(GertrudesCatConstants.ITEM_KITTEN).first()
            InteractionsHelper.useItemOnInteractive(kitten, cat)
        },
        { Conditions.waitUntilItemLeavesInventory(GertrudesCatConstants.ITEM_KITTEN, 1).invoke() },
        "Returning kitten",
        questInformation = information
    )

    private val returnKittenToCat = QuestTaskList(
        getKitten, returnKitten
    )

    override fun addRequirements(): QuestRequirements {
        return QuestRequirements(listOf(sardineRequirement, milkRequirement, coinRequirement), emptyList())
    }

    override fun getQuestStep(stepPosition: Int): BaseQuestStep? {
        return when (stepPosition) {
            0 -> startQuestStepList.processStep()
            1 -> talkToChildren
            2 -> giveMilkToCat
            3 -> giveSardineToCat
            4 -> returnKittenToCat.processStep()!!
            5 -> finishQuest
            6 -> {
                information.complete = true
                closeQuestComplete()
                null
            }
            else -> TODO("Missing ID $stepPosition")
        }
    }
}