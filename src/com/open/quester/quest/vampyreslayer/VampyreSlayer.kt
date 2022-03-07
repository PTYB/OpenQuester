package com.open.quester.quest.vampyreslayer

import com.open.quester.common.BankStep
import com.open.quester.common.CommonMethods.closeQuestComplete
import com.open.quester.common.QuestTaskList
import com.open.quester.common.SimpleConversationStep
import com.open.quester.common.base.BaseQuest
import com.open.quester.common.base.BaseQuestStep
import com.open.quester.common.base.CombatStep
import com.open.quester.common.base.SimpleObjectStep
import com.open.quester.extensions.Conditions
import com.open.quester.extensions.count
import com.open.quester.extensions.nearestNpc
import com.open.quester.models.ItemRequirement
import com.open.quester.models.ItemRequirementCondition
import com.open.quester.models.QuestInformation
import com.open.quester.models.QuestRequirements
import com.open.quester.quest.Constants.BANK_DRAYNOR
import com.open.quester.quest.Constants.ITEM_COINS
import com.open.quester.quest.Constants.ITEM_GARLIC
import com.open.quester.quest.Constants.ITEM_HAMMER
import com.open.quester.quest.vampyreslayer.VampyreSlayerConstants.CONVERSATION_BARTENDER
import com.open.quester.quest.vampyreslayer.VampyreSlayerConstants.CONVERSATION_HARLOW
import com.open.quester.quest.vampyreslayer.VampyreSlayerConstants.CONVERSATION_MORGAN
import com.open.quester.quest.vampyreslayer.VampyreSlayerConstants.NAME_BARTENDER
import com.open.quester.quest.vampyreslayer.VampyreSlayerConstants.NAME_COFFIN
import com.open.quester.quest.vampyreslayer.VampyreSlayerConstants.NAME_DRACULA
import com.open.quester.quest.vampyreslayer.VampyreSlayerConstants.NAME_HARLOW
import com.open.quester.quest.vampyreslayer.VampyreSlayerConstants.NAME_MORGAN
import com.open.quester.quest.vampyreslayer.VampyreSlayerConstants.NAME_STAKE
import com.open.quester.quest.vampyreslayer.VampyreSlayerConstants.TILE_BARTENDER
import com.open.quester.quest.vampyreslayer.VampyreSlayerConstants.TILE_COFFIN
import com.open.quester.quest.vampyreslayer.VampyreSlayerConstants.TILE_HARLOW
import com.open.quester.quest.vampyreslayer.VampyreSlayerConstants.TILE_MORGAN
import org.powbot.api.Condition
import org.powbot.api.Tile
import org.powbot.api.rt4.*
import org.powbot.mobile.script.ScriptManager

class VampyreSlayer(information: QuestInformation) : BaseQuest(information) {
    private val hammer = ItemRequirementCondition(ItemRequirement(ITEM_HAMMER, false))
    private val coins = ItemRequirementCondition(ItemRequirement(ITEM_COINS, true, 2))

    private val startQuest = getStartQuest()
    private val getGarlicAndStake = getGarlicAndStake()
    private val killDracula = killDracula()

    override fun addRequirements(): QuestRequirements {
        return QuestRequirements(listOf(hammer, coins), listOf())
    }

    override fun getQuestStep(stepPosition: Int): BaseQuestStep? {
        return when (stepPosition) {
            0 -> startQuest.processStep()
            1 -> getGarlicAndStake.processStep()
            2 -> killDracula.processStep()
            3 -> {
                information.complete = true
                closeQuestComplete()
                null
            }
            else -> {
                logger.info("Missing step $stepPosition")
                ScriptManager.stop()
                null
            }
        }
    }

    private fun getStartQuest(): QuestTaskList {
        val bank = BankStep(
            listOf(hammer, coins, ItemRequirementCondition.emptySlots(2)),
            BANK_DRAYNOR,
            information,
            combat = true,
            foodRequired = true
        )
        return QuestTaskList(
            bank,
            SimpleConversationStep(NAME_MORGAN, TILE_MORGAN, CONVERSATION_MORGAN, "Staring quest", information)
        )
    }

    private fun getGarlicAndStake(): QuestTaskList {
        val cupboardTile = Tile(3097, 3269, 1)
        val getGarlic = SimpleObjectStep(
            cupboardTile, arrayOf(),
            { Objects.stream().name("Cupboard").within(cupboardTile, 2).first() },
            { go ->
                val action = if (go.actions().contains("Open")) "Open" else "Search"
                go.interact(action)
            },
            { go ->
                val condition = if (go.actions().contains("Open")) {
                    { !go.valid() }
                } else {
                    { Inventory.count(ITEM_GARLIC) > 0 }
                }
                Condition.wait(condition)
            },
            "Getting Garlic",
            information,
            { Inventory.count(ITEM_GARLIC) == 0 },
            false
        )

        val talkToHarlow = SimpleConversationStep(NAME_HARLOW, TILE_HARLOW, CONVERSATION_HARLOW, "Talking to Harlow", information)
        val buyBeer = SimpleConversationStep(
            NAME_BARTENDER, TILE_BARTENDER, CONVERSATION_BARTENDER, "Buying beer", information, false
        ) { Inventory.count("Coins") > 1 && Inventory.count("Beer") == 0 }

        return QuestTaskList(
            getGarlic,
            buyBeer,
            talkToHarlow,
        )
    }

    private fun killDracula(): QuestTaskList {
        val talkToHarlow =
            SimpleConversationStep(NAME_HARLOW, TILE_HARLOW, CONVERSATION_HARLOW, "Talking to Harlow", information,false) {
                Inventory.count(NAME_STAKE) == 0
            }

        val killNpc = CombatStep(
            TILE_COFFIN,
            information,
            NAME_DRACULA,
            "Killing Count"
        ) { Npcs.stream().name(NAME_DRACULA).action("Attack").first() != Npc.Nil }

        val openCoffin = SimpleObjectStep(
            TILE_COFFIN,
            arrayOf(),
            { Objects.stream().name(NAME_COFFIN).within(TILE_COFFIN, 10).first() },
            { go -> go.interact("Open") },
            { _ -> Condition.wait(Conditions.waitUntilNpcAppears(NAME_DRACULA)) },
            "Opening coffin",
            information,
            { Npcs.nearestNpc(NAME_DRACULA) == Npc.Nil },
            false
        )

        return QuestTaskList(
            talkToHarlow,
            openCoffin,
            killNpc,
        )
    }
}