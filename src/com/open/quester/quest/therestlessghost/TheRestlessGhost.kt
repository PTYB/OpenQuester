package com.open.quester.quest.therestlessghost

import com.open.quester.common.CommonMethods
import com.open.quester.common.QuestTaskList
import com.open.quester.common.SimpleConversationStep
import com.open.quester.common.base.BaseQuest
import com.open.quester.common.base.BaseQuestStep
import com.open.quester.common.base.SimpleObjectStep
import com.open.quester.extensions.count
import com.open.quester.helpers.InteractionsHelper
import com.open.quester.models.QuestInformation
import com.open.quester.models.QuestRequirements
import com.open.quester.quest.therestlessghost.TheRestlessGhostConstants.ACTION_OPEN
import com.open.quester.quest.therestlessghost.TheRestlessGhostConstants.CONVERSATION_FATHER_AERECK
import com.open.quester.quest.therestlessghost.TheRestlessGhostConstants.CONVERSATION_FATHER_URHNEY
import com.open.quester.quest.therestlessghost.TheRestlessGhostConstants.CONVERSATION_GHOST
import com.open.quester.quest.therestlessghost.TheRestlessGhostConstants.ID_COFFIN
import com.open.quester.quest.therestlessghost.TheRestlessGhostConstants.ID_RESTLESS_GHOST
import com.open.quester.quest.therestlessghost.TheRestlessGhostConstants.ID_SEARCH_COFFIN
import com.open.quester.quest.therestlessghost.TheRestlessGhostConstants.ID_SEARCH_COFFIN2
import com.open.quester.quest.therestlessghost.TheRestlessGhostConstants.ITEM_GHOSTSPEAK
import com.open.quester.quest.therestlessghost.TheRestlessGhostConstants.ITEM_SKULL
import com.open.quester.quest.therestlessghost.TheRestlessGhostConstants.NAME_ALTAR
import com.open.quester.quest.therestlessghost.TheRestlessGhostConstants.NAME_FATHER_AERECK
import com.open.quester.quest.therestlessghost.TheRestlessGhostConstants.NAME_FATHER_URHNEY
import com.open.quester.quest.therestlessghost.TheRestlessGhostConstants.TILE_AERECK
import com.open.quester.quest.therestlessghost.TheRestlessGhostConstants.TILE_COFFIN
import com.open.quester.quest.therestlessghost.TheRestlessGhostConstants.TILE_GHOST
import com.open.quester.quest.therestlessghost.TheRestlessGhostConstants.TILE_SKULL
import com.open.quester.quest.therestlessghost.TheRestlessGhostConstants.TILE_URHNEY
import com.open.quester.quest.therestlessghost.TheRestlessGhostConstants.VARPBIT_SKULL
import com.open.quester.quest.therestlessghost.requirements.EquipAmulet
import org.powbot.api.rt4.*
import org.powbot.api.rt4.Equipment.Slot

class TheRestlessGhost(information: QuestInformation) : BaseQuest(information) {

    private val amulet = EquipAmulet()
    private val talkToGhost =
        SimpleConversationStep("Restless ghost", TILE_COFFIN, CONVERSATION_GHOST, "Talking to ghost",
            information,
            shouldExecute = {
                Equipment.itemAt(Slot.NECK).name() == ITEM_GHOSTSPEAK &&
                        Npcs.stream().id(ID_RESTLESS_GHOST).first() != Npc.Nil
            })

    private val getSkull = SimpleObjectStep(TILE_SKULL, arrayOf(), NAME_ALTAR, "Search",
        { Varpbits.varpbit(VARPBIT_SKULL) == 1 }, "Grabbing skull", information,
        { Varpbits.varpbit(VARPBIT_SKULL) < 13 })

    private val openCoffin = SimpleObjectStep(TILE_GHOST, CONVERSATION_GHOST,
        { Objects.stream().name("Coffin").within(TILE_GHOST, 5).first() },
        { go: GameObject -> go.interact(ACTION_OPEN) }, { go: GameObject -> !go.valid() }, "Opening coffin",
        information, { Equipment.itemAt(Slot.NECK).name() == ITEM_GHOSTSPEAK })

    private val returnSkull = SimpleObjectStep(
        TILE_COFFIN,
        CONVERSATION_GHOST,
        { Objects.stream().name("Coffin").within(TILE_GHOST, 5).first() },
        { go: GameObject ->
            if (go.actions().contains("Search")) {
                InteractionsHelper.useItemOnInteractive(ITEM_SKULL, go)
            } else {
                go.interact(ACTION_OPEN)
            }
        }, { go: GameObject ->
            if (go.actions().contains("Search")) {
                Inventory.count(ITEM_SKULL) == 0
            } else {
                !go.valid()
            }
        }, "Returning skull", information, { Varpbits.varpbit(VARPBIT_SKULL) == 13 }
    )

    private val startQuest = SimpleConversationStep(
        NAME_FATHER_AERECK,
        TILE_AERECK,
        CONVERSATION_FATHER_AERECK,
        "Talk to Fred to start quest",
        information
    )


    private val amuletCallable = {
        val item: Item = Equipment.itemAt(Slot.NECK)
        val ghostspeakInvo: Item = Inventory.stream().name(ITEM_GHOSTSPEAK).first()
        val hasGhostSpeakOn = item !== Item.Nil && item.name() == ITEM_GHOSTSPEAK
        val hasGhostSpeakInventory = ghostspeakInvo != Item.Nil && ghostspeakInvo.valid()
        !hasGhostSpeakOn && !hasGhostSpeakInventory
    }

    private val talkToFatherUrhney = SimpleConversationStep(
        NAME_FATHER_URHNEY,
        TILE_URHNEY,
        CONVERSATION_FATHER_URHNEY,
        "Talking to Father Urhney",
        information,
        shouldExecute = amuletCallable,
    )
    private var walkToCoffin = QuestTaskList(talkToFatherUrhney, amulet, talkToGhost, openCoffin)
    private var getAndPlaceSkull = QuestTaskList(talkToFatherUrhney, amulet, getSkull, returnSkull)

    override fun addRequirements(): QuestRequirements {
        return QuestRequirements(listOf(), listOf())
    }

    override fun getQuestStep(stepPosition: Int): BaseQuestStep? {
        when (stepPosition) {
            0 -> return startQuest
            1 -> return talkToFatherUrhney
            2 -> return walkToCoffin.processStep()
            3, 4 -> return getAndPlaceSkull.processStep()
            5 -> {
                information.complete = true
                CommonMethods.closeQuestComplete()
                return null
            }
        }
        return null
    }
}