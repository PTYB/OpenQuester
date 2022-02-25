package com.open.quester.quest.witchespotion

import com.open.quester.common.*
import com.open.quester.common.base.BaseQuest
import com.open.quester.common.base.BaseQuestStep
import com.open.quester.common.base.CombatStep
import com.open.quester.common.base.SimpleObjectStep
import com.open.quester.extensions.count
import com.open.quester.models.ItemRequirement
import com.open.quester.models.ItemRequirementCondition
import com.open.quester.models.QuestInformation
import com.open.quester.models.QuestRequirements
import com.open.quester.quest.Constants.BANK_EAST_FALADOR
import com.open.quester.quest.witchespotion.WitchesPotionConstants.ACTION_CAULDRON
import com.open.quester.quest.witchespotion.WitchesPotionConstants.CONVERSATION_HETTY
import com.open.quester.quest.witchespotion.WitchesPotionConstants.ITEM_BURNT_MEAT
import com.open.quester.quest.witchespotion.WitchesPotionConstants.ITEM_EYE_OF_NEWT
import com.open.quester.quest.witchespotion.WitchesPotionConstants.ITEM_ONION
import com.open.quester.quest.witchespotion.WitchesPotionConstants.ITEM_RATS_TAIL
import com.open.quester.quest.witchespotion.WitchesPotionConstants.NAME_CAULDRON
import com.open.quester.quest.witchespotion.WitchesPotionConstants.NAME_RAT
import com.open.quester.quest.witchespotion.WitchesPotionConstants.NAME_WITCH
import com.open.quester.quest.witchespotion.WitchesPotionConstants.TILE_CAULDRON
import com.open.quester.quest.witchespotion.WitchesPotionConstants.TILE_HETTY
import com.open.quester.quest.witchespotion.WitchesPotionConstants.TILE_RAT
import org.powbot.api.Condition
import org.powbot.api.rt4.Chat
import org.powbot.api.rt4.GroundItems
import org.powbot.api.rt4.Inventory

class WitchesPotion(information: QuestInformation) : BaseQuest(information) {

    val itemRequirementsList: List<ItemRequirementCondition> = listOf(
        ItemRequirementCondition(ItemRequirement(ITEM_EYE_OF_NEWT, false)),
        ItemRequirementCondition(ItemRequirement(ITEM_ONION, false)),
        ItemRequirementCondition(ItemRequirement(ITEM_BURNT_MEAT, true))
    )

    val bankForItems = BankStep(itemRequirementsList, BANK_EAST_FALADOR, information, {
        itemRequirementsList.any { Inventory.stream().name(it.chosenRequirement!!.name).count().toInt() == 0 }
    }, true)

    private val startQuest = QuestTaskList(
        bankForItems, SimpleConversationStep(NAME_WITCH, TILE_HETTY, CONVERSATION_HETTY, "Starting quest")
    )
    private val killRat = killRat()
    private val finishQuest = SimpleObjectStep(
        TILE_CAULDRON, arrayOf(), NAME_CAULDRON, ACTION_CAULDRON,
        "Finishing quest", { Condition.wait { Chat.chatting() } },
    )

    override fun addRequirements(): QuestRequirements {
        return QuestRequirements(itemRequirementsList, listOf())
    }

    private fun killRat(): QuestTaskList {
        val returnWithItems = SimpleConversationStep(NAME_WITCH, TILE_HETTY, CONVERSATION_HETTY, "Return items")
        val pickupRatTail = PickupItemStep(
            TILE_RAT,
            { GroundItems.stream().name(ITEM_RATS_TAIL).nearest().first() },
            { GroundItems.stream().name(ITEM_RATS_TAIL).count() > 0 && Inventory.count(ITEM_RATS_TAIL) == 0 },
            "Take",
            "Picking up rat tail",
            information
        )
        val killRat = CombatStep(TILE_RAT, information, NAME_RAT, "Killing rat")
        { Inventory.count(ITEM_RATS_TAIL) == 0 }
        return QuestTaskList(pickupRatTail, killRat, returnWithItems)
    }

    override fun getQuestStep(stepPosition: Int): BaseQuestStep? {
        return when (stepPosition) {
            0 -> startQuest.processStep()!!
            1 -> killRat.processStep()!!
            2 -> finishQuest
            3 -> {
                information.complete = true
                CommonMethods.closeQuestComplete()
                null
            }
            else -> TODO()
        }
    }
}