package com.open.quester.quest.ernestthechicken

import com.open.quester.common.*
import com.open.quester.common.base.BaseQuest
import com.open.quester.common.base.BaseQuestStep
import com.open.quester.common.base.SimpleObjectStep
import com.open.quester.extensions.Conditions
import com.open.quester.extensions.count
import com.open.quester.helpers.InteractionsHelper
import com.open.quester.models.ItemRequirementCondition
import com.open.quester.models.QuestInformation
import com.open.quester.models.QuestRequirements
import com.open.quester.quest.Constants.BANK_DRAYNOR
import org.powbot.api.Condition
import org.powbot.api.event.MessageEvent
import org.powbot.api.rt4.*
import org.powbot.mobile.script.ScriptManager
import com.open.quester.quest.ernestthechicken.ErnestTheChickenConstants.CONVERSATION_ODDENSTEIN
import com.open.quester.quest.ernestthechicken.ErnestTheChickenConstants.CONVERSATION_VERONICA
import com.open.quester.quest.ernestthechicken.ErnestTheChickenConstants.ITEM_FISH_FOOD
import com.open.quester.quest.ernestthechicken.ErnestTheChickenConstants.ITEM_KEY
import com.open.quester.quest.ernestthechicken.ErnestTheChickenConstants.ITEM_OIL_CAN
import com.open.quester.quest.ernestthechicken.ErnestTheChickenConstants.ITEM_POISON
import com.open.quester.quest.ernestthechicken.ErnestTheChickenConstants.ITEM_POISONED_FISH_FOOD
import com.open.quester.quest.ernestthechicken.ErnestTheChickenConstants.ITEM_PRESSURE_GAUGE
import com.open.quester.quest.ernestthechicken.ErnestTheChickenConstants.ITEM_RUBBER_TUBE
import com.open.quester.quest.ernestthechicken.ErnestTheChickenConstants.ITEM_SPADE
import com.open.quester.quest.ernestthechicken.ErnestTheChickenConstants.NAME_COMPOST_HEAP
import com.open.quester.quest.ernestthechicken.ErnestTheChickenConstants.NAME_FOUNTAIN
import com.open.quester.quest.ernestthechicken.ErnestTheChickenConstants.NAME_ODDENSTEIN
import com.open.quester.quest.ernestthechicken.ErnestTheChickenConstants.NAME_VERONICA
import com.open.quester.quest.ernestthechicken.ErnestTheChickenConstants.TILE_COMPOST_HEAP
import com.open.quester.quest.ernestthechicken.ErnestTheChickenConstants.TILE_FISH_FOOD
import com.open.quester.quest.ernestthechicken.ErnestTheChickenConstants.TILE_FOUTAIN
import com.open.quester.quest.ernestthechicken.ErnestTheChickenConstants.TILE_GAUGE
import com.open.quester.quest.ernestthechicken.ErnestTheChickenConstants.TILE_ODDENSTEIN
import com.open.quester.quest.ernestthechicken.ErnestTheChickenConstants.TILE_POISON
import com.open.quester.quest.ernestthechicken.ErnestTheChickenConstants.TILE_SPADE
import com.open.quester.quest.ernestthechicken.ErnestTheChickenConstants.TILE_VERONICA

class ErnestTheChicken(information: QuestInformation) : BaseQuest(information) {

    companion object {
        var poisoned = false
    }

    private val leverConditions = LeverConditions(information)

    private val finishQuest = SimpleConversationStep(
        NAME_ODDENSTEIN, TILE_ODDENSTEIN, CONVERSATION_ODDENSTEIN, "Finishing quest.", information
    ) {
        Inventory.count(ITEM_OIL_CAN, ITEM_RUBBER_TUBE, ITEM_PRESSURE_GAUGE) == 3
    }

    private val startQuest = startQuest()
    private val getItems = getItems()

    override fun addRequirements(): QuestRequirements {
        return QuestRequirements(listOf(), listOf())
    }

    override fun getQuestStep(stepPosition: Int): BaseQuestStep? {
        return when (stepPosition) {
            0 -> startQuest.processStep()
            1 -> getItems.processStep()
            2 -> finishQuest
            3 -> {
                Chat.completeChat()
                information.complete = true
                CommonMethods.closeQuestComplete()
                null
            }
            else -> {
                logger.info("Missing step $stepPosition")
                ScriptManager.stop()
                null
            }
        }
    }


    private fun getItems(): QuestTaskList {
        return QuestTaskList(
            *getGaugeAndTube(),
            *leverConditions.getOilCan(),
            finishQuest,
        )
    }

    private fun getGaugeAndTube(): Array<BaseQuestStep> {
        val pickupFishFood = PickupItemStep(
            TILE_FISH_FOOD,
            { GroundItems.stream().name(ITEM_FISH_FOOD).first() },
            {
                !poisoned && Inventory.count(
                    ITEM_FISH_FOOD,
                    ITEM_POISONED_FISH_FOOD,
                    ITEM_PRESSURE_GAUGE,
                    ITEM_RUBBER_TUBE
                ) == 0
            },
            "Take",
            "Picking up fish food",
            information
        )
        val pickupPoison = PickupItemStep(
            TILE_POISON,
            { GroundItems.stream().name(ITEM_POISON).first() },
            {
                !poisoned && Inventory.count(
                    ITEM_POISON,
                    ITEM_POISONED_FISH_FOOD,
                    ITEM_PRESSURE_GAUGE,
                    ITEM_RUBBER_TUBE
                ) == 0
            },
            "Take",
            "Picking up poison",
            information
        )
        val makePoisonedFood = CombineItemStep(
            ITEM_FISH_FOOD, ITEM_POISON, "Making poisoned food",
            { !poisoned && Inventory.count(ITEM_FISH_FOOD) == 1 && Inventory.count(ITEM_POISON) == 1 }, false
        )

        val pickupSpade = PickupItemStep(
            TILE_SPADE, { GroundItems.stream().name(ITEM_SPADE).first() },
            { Inventory.count(ITEM_SPADE) == 0 }, "Take", "Picking up spade", information
        )

        val getKey = SimpleObjectStep(
            TILE_COMPOST_HEAP,
            arrayOf(),
            NAME_COMPOST_HEAP,
            "Search",
            { Condition.wait { Inventory.count(ITEM_KEY) == 1 } },
            "Getting key",
            information,
            { Inventory.count(ITEM_KEY, ITEM_RUBBER_TUBE) == 0 },
            false
        )

        val usePoisonedFoodOnFountain = SimpleObjectStep(
            TILE_FOUTAIN,
            arrayOf(),
            { Objects.stream().name(NAME_FOUNTAIN).first() },
            { go: GameObject ->
                InteractionsHelper.useItemOnGameObject(
                    Inventory.stream().name(ITEM_POISONED_FISH_FOOD).first(), go
                )
            },
            { go: GameObject ->
                val result = !go.valid()
                poisoned = result
                result
            },
            "Killing the fishies",
            information,
            { !poisoned && Inventory.count(ITEM_POISONED_FISH_FOOD) == 1 },
            false
        )

        val grabGauge = SimpleObjectStep(
            TILE_FOUTAIN,
            arrayOf(),
            NAME_FOUNTAIN,
            "Search",
            { Condition.wait { Chat.chatting() } },
            "Grabbing gauge",
            information,
            { poisoned && Inventory.count(ITEM_PRESSURE_GAUGE) == 0 }
        )

        val grabTube = PickupItemStep(
            TILE_GAUGE, { GroundItems.stream().name(ITEM_RUBBER_TUBE).first() },
            { Inventory.count(ITEM_RUBBER_TUBE) == 0 && Inventory.count(ITEM_KEY) == 1 },
            "Take", "Grabbing tube", information
        )

        return arrayOf(
            pickupFishFood,
            pickupPoison,
            makePoisonedFood,
            pickupSpade,
            getKey,
            usePoisonedFoodOnFountain,
            grabGauge,
            grabTube
        )
    }

    private fun startQuest(): QuestTaskList {
        val food = BankStep(
            listOf(ItemRequirementCondition.emptySlots(5)),
            BANK_DRAYNOR, information, combat = false, foodRequired = true
        )
        val startQuest =
            SimpleConversationStep(NAME_VERONICA, TILE_VERONICA, CONVERSATION_VERONICA, "Starting quest", information)
        return QuestTaskList(
            food,
            startQuest
        )
    }

    override fun handleMessage(me: MessageEvent) {

        if (me.message.contains("... then die and float to the surface.")) {
            poisoned = true
        }
    }

}