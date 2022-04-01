package com.open.quester.quest.entertheabyss

import com.open.quester.common.BankStep
import com.open.quester.common.CommonMethods
import com.open.quester.common.QuestTaskList
import com.open.quester.common.SimpleConversationStep
import com.open.quester.common.base.BaseQuest
import com.open.quester.common.base.BaseQuestStep
import com.open.quester.common.base.SimpleNpcStep
import com.open.quester.extensions.count
import com.open.quester.extensions.nearestNpc
import com.open.quester.models.ItemRequirementCondition
import com.open.quester.models.QuestInformation
import com.open.quester.models.QuestRequirements
import com.open.quester.quest.Constants.BANK_DRAYNOR
import com.open.quester.quest.Constants.BANK_EDGEVILLE_NE
import com.open.quester.quest.Constants.ITEM_COINS
import com.open.quester.quest.entertheabyss.EnterTheAbyssConstants.CONVERSATION_VARROCK
import com.open.quester.quest.entertheabyss.EnterTheAbyssConstants.CONVERSATION_WILDERNESS
import com.open.quester.quest.entertheabyss.EnterTheAbyssConstants.ITEM_SCRYING_ORB
import com.open.quester.quest.entertheabyss.EnterTheAbyssConstants.NAME_AUBURY
import com.open.quester.quest.entertheabyss.EnterTheAbyssConstants.NAME_COMPERTY
import com.open.quester.quest.entertheabyss.EnterTheAbyssConstants.NAME_MAGE_OF_ZAMORAK
import com.open.quester.quest.entertheabyss.EnterTheAbyssConstants.NAME_SEDRIDOR
import com.open.quester.quest.entertheabyss.EnterTheAbyssConstants.TILE_AUBURY
import com.open.quester.quest.entertheabyss.EnterTheAbyssConstants.TILE_CROMPERTY
import com.open.quester.quest.entertheabyss.EnterTheAbyssConstants.TILE_MAGE_IN_VARROCK
import com.open.quester.quest.entertheabyss.EnterTheAbyssConstants.TILE_MAGE_IN_WILDY
import com.open.quester.quest.entertheabyss.EnterTheAbyssConstants.TILE_SEDRIDOR
import com.open.quester.quest.entertheabyss.EnterTheAbyssConstants.VARPBIT_MASK_TELEPORT
import com.open.quester.quest.entertheabyss.EnterTheAbyssConstants.VARPBIT_SHIFT_ARDOUGNE
import com.open.quester.quest.entertheabyss.EnterTheAbyssConstants.VARPBIT_SHIFT_VARROCK
import com.open.quester.quest.entertheabyss.EnterTheAbyssConstants.VARPBIT_SHIFT_WIZ_TOWER
import com.open.quester.quest.entertheabyss.EnterTheAbyssConstants.VARPBIT_TELEPORTS
import com.open.quester.quest.entertheabyss.tasks.LeaveEssense
import org.powbot.api.Tile
import org.powbot.api.rt4.*

class EnterTheAbyss(information: QuestInformation) : BaseQuest(information) {

    private val boatCoinsRequirement = ItemRequirementCondition(ITEM_COINS, false, 60)
    private val scryingOrbRequirement = ItemRequirementCondition(ITEM_SCRYING_ORB, false, 1)

    private val startQuest = QuestTaskList(
        BankStep(listOf(ItemRequirementCondition.emptySlots(2)), BANK_EDGEVILLE_NE, information, foodRequired = true),
        SimpleConversationStep(
            NAME_MAGE_OF_ZAMORAK,
            TILE_MAGE_IN_WILDY,
            CONVERSATION_WILDERNESS,
            "Talking to mage in wildy",
            information
        )
    )
    private val talkToMageInVarrock =
        SimpleNpcStep(
            TILE_MAGE_IN_VARROCK,
            CONVERSATION_VARROCK,
            { Npcs.stream().name(NAME_MAGE_OF_ZAMORAK).within(TILE_MAGE_IN_VARROCK, 20).first() },
            { npc: Npc -> npc.interact("Talk") },
            { Chat.chatting() },
            "Talking to mage in varrock",
            questInformation = information
        )
    private val chargeOrbs = chargeOrb()

    private fun chargeOrb(): QuestTaskList {
        return QuestTaskList(
            LeaveEssense(),
            teleportNpc(TILE_AUBURY, NAME_AUBURY, VARPBIT_SHIFT_VARROCK),
            teleportNpc(TILE_SEDRIDOR, NAME_SEDRIDOR, VARPBIT_SHIFT_WIZ_TOWER),
            BankStep(
                listOf(boatCoinsRequirement, scryingOrbRequirement),
                BANK_DRAYNOR,
                information,
                { Inventory.count(ITEM_COINS) == 0 }),
            teleportNpc(TILE_CROMPERTY, NAME_COMPERTY, VARPBIT_SHIFT_ARDOUGNE),
            talkToMageInVarrock
        )
    }

    private fun teleportNpc(tile: Tile, name: String, shift: Int): SimpleNpcStep {
        return SimpleNpcStep(
            tile,
            null,
            { Npcs.nearestNpc(name) },
            { npc: Npc -> npc.interact("Teleport") },
            { npc: Npc -> !npc.valid() },
            "Teleporting from $name",
            shouldExecute = { Varpbits.varpbit(VARPBIT_TELEPORTS, shift, VARPBIT_MASK_TELEPORT) == 0 },
            questInformation = information
        )
    }

    override fun addRequirements(): QuestRequirements {
        return QuestRequirements(listOf(boatCoinsRequirement), listOf())
    }

    override fun getQuestStep(stepPosition: Int): BaseQuestStep? {
        return when (stepPosition) {
            0 -> startQuest.processStep()
            1 -> talkToMageInVarrock
            2 -> chargeOrbs.processStep()
            3 -> talkToMageInVarrock
            4 -> {
                information.complete = true
                CommonMethods.closeQuestComplete()
                null
            }
            else -> TODO("Missing ID $stepPosition")
        }
    }
}