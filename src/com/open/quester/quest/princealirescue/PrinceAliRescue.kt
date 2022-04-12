package com.open.quester.quest.princealirescue

import com.open.quester.common.*
import com.open.quester.common.base.BaseQuest
import com.open.quester.common.base.BaseQuestStep
import com.open.quester.common.base.SimpleNpcStep
import com.open.quester.common.base.SimpleObjectStep
import com.open.quester.extensions.count
import com.open.quester.extensions.nearestNpc
import com.open.quester.helpers.InteractionsHelper
import com.open.quester.models.ItemRequirement
import com.open.quester.models.ItemRequirementCondition
import com.open.quester.models.QuestInformation
import com.open.quester.models.QuestRequirements
import com.open.quester.quest.Constants.BANK_AL_KHRAID
import com.open.quester.quest.Constants.BANK_DRAYNOR
import com.open.quester.quest.princealirescue.PrinceAliRescueConstants.AREA_INSIDE_JAIL
import com.open.quester.quest.princealirescue.PrinceAliRescueConstants.AREA_PRISON
import com.open.quester.quest.princealirescue.PrinceAliRescueConstants.CONVERSATION_AGGIE
import com.open.quester.quest.princealirescue.PrinceAliRescueConstants.CONVERSATION_HASSAN
import com.open.quester.quest.princealirescue.PrinceAliRescueConstants.CONVERSATION_JOE
import com.open.quester.quest.princealirescue.PrinceAliRescueConstants.CONVERSATION_KELI
import com.open.quester.quest.princealirescue.PrinceAliRescueConstants.CONVERSATION_LEELA
import com.open.quester.quest.princealirescue.PrinceAliRescueConstants.CONVERSATION_NED
import com.open.quester.quest.princealirescue.PrinceAliRescueConstants.CONVERSATION_OSMAN
import com.open.quester.quest.princealirescue.PrinceAliRescueConstants.ITEM_ASHES
import com.open.quester.quest.princealirescue.PrinceAliRescueConstants.ITEM_BALLS_OF_WOOOL
import com.open.quester.quest.princealirescue.PrinceAliRescueConstants.ITEM_BEERS
import com.open.quester.quest.princealirescue.PrinceAliRescueConstants.ITEM_BRONZE_BAR
import com.open.quester.quest.princealirescue.PrinceAliRescueConstants.ITEM_BRONZE_KEY
import com.open.quester.quest.princealirescue.PrinceAliRescueConstants.ITEM_BUCKET_OF_WATER
import com.open.quester.quest.princealirescue.PrinceAliRescueConstants.ITEM_DYED_WIG
import com.open.quester.quest.princealirescue.PrinceAliRescueConstants.ITEM_KEY_PRINT
import com.open.quester.quest.princealirescue.PrinceAliRescueConstants.ITEM_PASTE
import com.open.quester.quest.princealirescue.PrinceAliRescueConstants.ITEM_PINK_SKIRT
import com.open.quester.quest.princealirescue.PrinceAliRescueConstants.ITEM_POT_OF_FLOUR
import com.open.quester.quest.princealirescue.PrinceAliRescueConstants.ITEM_REDBERRIES
import com.open.quester.quest.princealirescue.PrinceAliRescueConstants.ITEM_ROPE
import com.open.quester.quest.princealirescue.PrinceAliRescueConstants.ITEM_SOFT_CLAY
import com.open.quester.quest.princealirescue.PrinceAliRescueConstants.ITEM_WIG
import com.open.quester.quest.princealirescue.PrinceAliRescueConstants.ITEM_YELLOW_DYE
import com.open.quester.quest.princealirescue.PrinceAliRescueConstants.NAME_AGGIE
import com.open.quester.quest.princealirescue.PrinceAliRescueConstants.NAME_HASSAN
import com.open.quester.quest.princealirescue.PrinceAliRescueConstants.NAME_JOE
import com.open.quester.quest.princealirescue.PrinceAliRescueConstants.NAME_KELI
import com.open.quester.quest.princealirescue.PrinceAliRescueConstants.NAME_LEELA
import com.open.quester.quest.princealirescue.PrinceAliRescueConstants.NAME_NED
import com.open.quester.quest.princealirescue.PrinceAliRescueConstants.NAME_PRINCE_ALI
import com.open.quester.quest.princealirescue.PrinceAliRescueConstants.NAME_PRISON_DOOR
import com.open.quester.quest.princealirescue.PrinceAliRescueConstants.Name_OSMAN
import com.open.quester.quest.princealirescue.PrinceAliRescueConstants.TILE_AGGIE
import com.open.quester.quest.princealirescue.PrinceAliRescueConstants.TILE_HASSAN
import com.open.quester.quest.princealirescue.PrinceAliRescueConstants.TILE_INSIDE_JAIL
import com.open.quester.quest.princealirescue.PrinceAliRescueConstants.TILE_JOE
import com.open.quester.quest.princealirescue.PrinceAliRescueConstants.TILE_KELI
import com.open.quester.quest.princealirescue.PrinceAliRescueConstants.TILE_LEELA
import com.open.quester.quest.princealirescue.PrinceAliRescueConstants.TILE_NED
import com.open.quester.quest.princealirescue.PrinceAliRescueConstants.TILE_OSMAN
import com.open.quester.quest.princealirescue.PrinceAliRescueConstants.TILE_PRINCE_ALI
import org.powbot.api.rt4.*

class PrinceAliRescue(information: QuestInformation) : BaseQuest(information) {

    private val ballsOfWool = ItemRequirementCondition(ItemRequirement(ITEM_BALLS_OF_WOOOL, false, 3))
    private val ashes = ItemRequirementCondition(ItemRequirement(ITEM_ASHES, false, 1))
    private val water = ItemRequirementCondition(ItemRequirement(ITEM_BUCKET_OF_WATER, false, 1))
    private val potOfFlour = ItemRequirementCondition(ItemRequirement(ITEM_POT_OF_FLOUR, false, 1))
    private val bronzeBar = ItemRequirementCondition(ItemRequirement(ITEM_BRONZE_BAR, false, 1))
    private val softClay = ItemRequirementCondition(ItemRequirement(ITEM_SOFT_CLAY, false, 1))
    private val yellowDye = ItemRequirementCondition(ItemRequirement(ITEM_YELLOW_DYE, false, 1))
    private val beer = ItemRequirementCondition(ItemRequirement(ITEM_BEERS, false, 3))
    private val pinkSkirt = ItemRequirementCondition(ItemRequirement(ITEM_PINK_SKIRT, false, 1))
    private val redBerries = ItemRequirementCondition(ItemRequirement(ITEM_REDBERRIES, false, 1))
    private val rope = ItemRequirementCondition(ItemRequirement(ITEM_ROPE, false, 1))
    private val coins = ItemRequirementCondition(ItemRequirement("Coins", false, 150))

    private val questItemsRequiredForRescueBank = listOf(
        ItemRequirementCondition(ITEM_WIG, true, 1),
        ItemRequirementCondition(ITEM_PASTE, true, 1),
        ItemRequirementCondition(ITEM_BRONZE_KEY, true, 1),
        pinkSkirt,
        rope,
        beer,
        ItemRequirementCondition.emptySlots(1)
    )


    private val startingQuest = QuestTaskList(
        BankStep(
            listOf(ballsOfWool, rope, yellowDye, redBerries, potOfFlour, ashes, water, softClay, coins, bronzeBar),
            BANK_AL_KHRAID, information, foodRequired = true
        ),
        SimpleConversationStep(NAME_HASSAN, TILE_HASSAN, CONVERSATION_HASSAN, "Starting quest", information)
    )

    private val talkToOsman = SimpleConversationStep(
        Name_OSMAN, TILE_OSMAN, CONVERSATION_OSMAN, "Talking to Osman",
        information
    )

    private val makeDisguise = QuestTaskList(
        SimpleConversationStep(NAME_NED, TILE_NED, CONVERSATION_NED, "Getting wig", information,
            shouldExecute = { Inventory.count(ITEM_WIG, ITEM_DYED_WIG) == 0 }),
        CombineItemStep(ITEM_YELLOW_DYE, ITEM_WIG, "Dying wig", {
            Inventory.count(ITEM_YELLOW_DYE) == 1 && Inventory.count(ITEM_WIG) == 1
        }),
        SimpleConversationStep(NAME_AGGIE, TILE_AGGIE, CONVERSATION_AGGIE, "Getting skin paste",
            information, shouldExecute = { Inventory.count(ITEM_PASTE) == 0 }),
        WalkToExactTile(TILE_INSIDE_JAIL, "Walking inside jail", {
            !AREA_INSIDE_JAIL.contains(Players.local()) &&
                    Inventory.count(ITEM_KEY_PRINT) == 0 && Inventory.count(ITEM_SOFT_CLAY) == 1
        }, information),
        closeDoorStep {
            Objects.stream().name("Door")
                .within(TILE_INSIDE_JAIL, 2).first().actions()
                .contains("Close") && Inventory.count(ITEM_KEY_PRINT) == 0 && Inventory.count(ITEM_SOFT_CLAY) == 1
        },
        SimpleConversationStep(NAME_KELI, TILE_KELI, CONVERSATION_KELI, "Getting keyprints", information,
            shouldExecute = {
                AREA_INSIDE_JAIL.contains(Players.local()) &&
                        Inventory.count(ITEM_KEY_PRINT) == 0 && Inventory.count(ITEM_SOFT_CLAY) == 1
            }),
        SimpleConversationStep(
            Name_OSMAN,
            TILE_OSMAN,
            CONVERSATION_OSMAN,
            "Talking to Osman",
            information,
            shouldExecute = { Inventory.count(ITEM_KEY_PRINT) == 1 && Inventory.count(ITEM_SOFT_CLAY) == 1 }
        ),
        BankStep(questItemsRequiredForRescueBank, BANK_DRAYNOR, information, foodRequired = true),
        SimpleConversationStep(
            NAME_LEELA, TILE_LEELA, CONVERSATION_LEELA, "Talking to Leela", information
        ) { Inventory.count(ITEM_SOFT_CLAY) == 0 && Inventory.count(ITEM_BRONZE_KEY) == 0 },
    )

    private val rescueThePrince = QuestTaskList(
        WalkToExactTile(
            TILE_INSIDE_JAIL, "Walking inside jail", {
                !AREA_INSIDE_JAIL.contains(Players.local()) &&
                        Inventory.count(ITEM_PINK_SKIRT) == 1
            },
            information
        ),
        closeDoorStep {
            Objects.stream().name("Door")
                .within(TILE_INSIDE_JAIL, 2).first().actions()
                .contains("Close") &&
                    Inventory.count(ITEM_PINK_SKIRT) == 1
        },
        SimpleConversationStep(NAME_JOE, TILE_JOE, CONVERSATION_JOE, "Giving joe the good stuff", information,
            shouldExecute = { Inventory.count(ITEM_PINK_SKIRT) == 1 })
    )

    private val tieingUpKeli = SimpleNpcStep(
        TILE_KELI, arrayOf(), { Npcs.nearestNpc(NAME_KELI) }, { npc: Npc ->
            InteractionsHelper.useItemOnInteractive(
                ITEM_ROPE, npc
            )
        }, { npc: Npc -> Chat.chatting() }, "Making it look like an accident", questInformation = information
    )

    private val lettingHimGo = QuestTaskList(
        SimpleObjectStep(TILE_INSIDE_JAIL, arrayOf(), NAME_PRISON_DOOR, "Open",
            {go :GameObject -> AREA_PRISON.contains(Players.local())}, "Visiting the prince", information,
            { !AREA_INSIDE_JAIL.contains(Players.local())}),
        SimpleConversationStep(NAME_PRINCE_ALI, TILE_PRINCE_ALI, arrayOf(), "Making our princess", information,
        shouldExecute = { AREA_INSIDE_JAIL.contains(Players.local())})
    )

    private val finishQuest = QuestTaskList(
        SimpleObjectStep(TILE_INSIDE_JAIL, arrayOf(), NAME_PRISON_DOOR, "Open",
            {go :GameObject -> !AREA_PRISON.contains(Players.local())}, "Exiting prison", information,
            { AREA_INSIDE_JAIL.contains(Players.local())}),
        SimpleConversationStep(NAME_HASSAN, TILE_HASSAN, CONVERSATION_HASSAN, "Finishing quest", information)
    )


    private fun closeDoorStep(condition: () -> Boolean): SimpleObjectStep {
        return SimpleObjectStep(TILE_INSIDE_JAIL, null, {
            Objects.stream().name("Door")
                .within(TILE_INSIDE_JAIL, 2).first()
        }, { go: GameObject -> go.interact("Close") }, { go: GameObject -> !go.valid() }, "Closing door",
            information, condition
        )
    }

    override fun addRequirements(): QuestRequirements {
        return QuestRequirements(
            listOf(
                ballsOfWool, ashes, water, potOfFlour, bronzeBar, softClay,
                yellowDye, beer, pinkSkirt, redBerries, rope, coins
            ),
            listOf()
        )
    }

    override fun getQuestStep(stepPosition: Int): BaseQuestStep? {
        return when (stepPosition) {
            0 -> startingQuest.processStep()
            10 -> talkToOsman
            20 -> makeDisguise.processStep()
            30, 31, 32, 33 -> rescueThePrince.processStep()
            40 -> tieingUpKeli
            50 -> lettingHimGo.processStep()
            100 -> finishQuest.processStep()
            else -> {
                information.complete = true
                CommonMethods.closeQuestComplete()
                null
            }
        }
    }
}