package com.open.quester.quest.treegnomevillage

import com.open.quester.common.*
import com.open.quester.common.base.BaseQuest
import com.open.quester.common.base.BaseQuestStep
import com.open.quester.common.base.SimpleObjectStep
import com.open.quester.models.ItemRequirementCondition
import com.open.quester.models.QuestInformation
import com.open.quester.models.QuestRequirements
import com.open.quester.quest.Constants.BANK_NORTH_ARDOUGNE
import org.powbot.api.rt4.*
import org.powbot.quests.quest.treegnomevillage.TreeGnomeVillageConstants.AREA_INNER_GNOME
import org.powbot.quests.quest.treegnomevillage.TreeGnomeVillageConstants.AREA_INSIDE_VILLAGE
import org.powbot.quests.quest.treegnomevillage.TreeGnomeVillageConstants.CONVERSATION_BOLREN
import org.powbot.quests.quest.treegnomevillage.TreeGnomeVillageConstants.CONVERSATION_BOLREN_2
import org.powbot.quests.quest.treegnomevillage.TreeGnomeVillageConstants.CONVERSATION_ELKOY
import org.powbot.quests.quest.treegnomevillage.TreeGnomeVillageConstants.CONVERSATION_MONTAI
import org.powbot.quests.quest.treegnomevillage.TreeGnomeVillageConstants.ITEM_LOGS
import org.powbot.quests.quest.treegnomevillage.TreeGnomeVillageConstants.NAME_BOLREN
import org.powbot.quests.quest.treegnomevillage.TreeGnomeVillageConstants.NAME_ELKOY
import org.powbot.quests.quest.treegnomevillage.TreeGnomeVillageConstants.NAME_MONTAI
import org.powbot.quests.quest.treegnomevillage.TreeGnomeVillageConstants.NAME_TRACKER_GNOME_1
import org.powbot.quests.quest.treegnomevillage.TreeGnomeVillageConstants.NAME_TRACKER_GNOME_2
import org.powbot.quests.quest.treegnomevillage.TreeGnomeVillageConstants.NAME_TRACKER_GNOME_3
import org.powbot.quests.quest.treegnomevillage.TreeGnomeVillageConstants.TILE_BOLREN
import org.powbot.quests.quest.treegnomevillage.TreeGnomeVillageConstants.TILE_CLOSED_CHEST
import org.powbot.quests.quest.treegnomevillage.TreeGnomeVillageConstants.TILE_ELKOY
import org.powbot.quests.quest.treegnomevillage.TreeGnomeVillageConstants.TILE_INSIDE_VILLAGE
import org.powbot.quests.quest.treegnomevillage.TreeGnomeVillageConstants.TILE_MONTAI
import org.powbot.quests.quest.treegnomevillage.TreeGnomeVillageConstants.TILE_TRACKER_GNOME_1
import org.powbot.quests.quest.treegnomevillage.TreeGnomeVillageConstants.TILE_TRACKER_GNOME_2
import org.powbot.quests.quest.treegnomevillage.TreeGnomeVillageConstants.TILE_TRACKER_GNOME_3
import org.powbot.quests.quest.treegnomevillage.TreeGnomeVillageConstants.TILE_UPSTAIRS_TOWER
import org.powbot.quests.quest.treegnomevillage.TreeGnomeVillageConstants.VARP_TRACKER
import org.powbot.quests.quest.treegnomevillage.TreeGnomeVillageConstants.VARP_TRACKER_GNOME_1_SHIFT
import org.powbot.quests.quest.treegnomevillage.TreeGnomeVillageConstants.VARP_TRACKER_GNOME_2_SHIFT
import org.powbot.quests.quest.treegnomevillage.TreeGnomeVillageConstants.VARP_TRACKER_GNOME_3_SHIFT
import com.open.quester.quest.treegnomevillage.tasks.FireBallista
import com.open.quester.quest.treegnomevillage.tasks.GetOrbs
import org.powbot.api.Point

class TreeGnomeVillage(information: QuestInformation) : BaseQuest(information) {

    private val logRequirement = ItemRequirementCondition(ITEM_LOGS, false, 6)

    private val startQuest = startQuest()
    private val talkToMontai = SimpleConversationStep(
        NAME_MONTAI, TILE_MONTAI, CONVERSATION_MONTAI,
        "Talking to Commander Montai", information
    )
    private val talkToTrackers = talkToTrackers()
    private val getOrb = getOrb()
    private val returnOrb = returnOrb()
    private val getOrbsSecond = getKhazardOrbs()
    private val finishQuest = finishQuest()

    override fun addRequirements(): QuestRequirements {
        return QuestRequirements(listOf(logRequirement), listOf())
    }

    override fun getQuestStep(stepPosition: Int): BaseQuestStep? {
        return when (stepPosition) {
            0 -> startQuest.processStep()
            1, 2, 3 -> talkToMontai
            4 -> talkToTrackers.processStep()
            5 -> getOrb
            6 -> returnOrb
            7 -> getOrbsSecond.processStep()
            8 -> finishQuest.processStep()
            9 -> {
                information.complete = true
                CommonMethods.closeQuestComplete()
                return null
            }
            else -> TODO("Unable to find step $stepPosition")
        }
    }

    private fun finishQuest(): QuestTaskList {
        val talkToKing = SimpleConversationStep(
            NAME_BOLREN, TILE_BOLREN, CONVERSATION_BOLREN_2, "Returning orbs to king", information,
            shouldExecute = { AREA_INNER_GNOME.contains(Players.local()) },
        )
        val talkToElkoy =
            SimpleConversationStep(
                NAME_ELKOY, TILE_ELKOY, CONVERSATION_ELKOY, "Talking to Elkoy to skip maze",
                information
            )

        return QuestTaskList(
            talkToKing,
            talkToElkoy,
        )
    }

    private fun getKhazardOrbs(): QuestTaskList {
        return QuestTaskList(
            SetupWeaponStep(information),
            GetOrbs(information)
        )
    }

    private fun returnOrb(): SimpleConversationStep {
        return SimpleConversationStep(
            NAME_BOLREN, TILE_BOLREN, CONVERSATION_BOLREN_2, "Returning orbs to king", information
        )
    }

    private fun getOrb(): SimpleObjectStep {
        return SimpleObjectStep(
            TILE_UPSTAIRS_TOWER,
            arrayOf(),
            { Objects.stream().name("Closed Chest", "Open Chest").at(TILE_CLOSED_CHEST).first() },
            { go: GameObject ->
                if (go.actions().contains("Open")) {
                    go.interact("Open")
                } else {
                    go.interact("Search")
                }
            },
            { go: GameObject ->
                if (go.actions().contains("Open")) {
                    !go.valid()
                } else {
                    Inventory.stream().name("Orb of protection").count() > 0
                }
            },
            "Grabbing first orb",
            information,
            { true },
        )
    }

    private fun talkToTrackers(): QuestTaskList {
        val gnomeOne = SimpleConversationStep(
            NAME_TRACKER_GNOME_1, TILE_TRACKER_GNOME_1, arrayOf(), "Talking to Tracker 1", information,
            shouldExecute = { Varpbits.varpbit(VARP_TRACKER, VARP_TRACKER_GNOME_1_SHIFT, 0x1) == 0 }
        )
        val gnomeTwo = SimpleConversationStep(
            NAME_TRACKER_GNOME_2, TILE_TRACKER_GNOME_2, arrayOf(), "Talking to Tracker 2",
            information,
            shouldExecute = { Varpbits.varpbit(VARP_TRACKER, VARP_TRACKER_GNOME_2_SHIFT, 0x1) == 0 }
        )
        gnomeTwo.pointVariance = Point(0, -2)
        val gnomeThree = SimpleConversationStep(
            NAME_TRACKER_GNOME_3, TILE_TRACKER_GNOME_3, arrayOf(), "Talking to Tracker 3", information,
            shouldExecute = { Varpbits.varpbit(VARP_TRACKER, VARP_TRACKER_GNOME_3_SHIFT, 0x1) == 0 },
        )

        return QuestTaskList(
            gnomeOne,
            gnomeTwo,
            gnomeThree,
            FireBallista(information),
        )
    }

    private fun startQuest(): QuestTaskList {
        val startQuest = SimpleConversationStep(
            NAME_BOLREN, TILE_BOLREN, CONVERSATION_BOLREN, "Talking to Bolren", information
        )
        return QuestTaskList(
            BankStep(listOf(logRequirement), BANK_NORTH_ARDOUGNE, information, { true }, true, true),
            WalkToArea(AREA_INSIDE_VILLAGE, TILE_INSIDE_VILLAGE, "Walking to center", {
                !AREA_INSIDE_VILLAGE.contains(
                    Players.local()
                )
            }, information),
            startQuest,
        )
    }

}