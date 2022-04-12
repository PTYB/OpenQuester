package com.open.quester.quest.lostcity

import com.open.quester.common.*
import com.open.quester.common.base.BaseQuest
import com.open.quester.common.base.BaseQuestStep
import com.open.quester.common.base.SimpleObjectStep
import com.open.quester.extensions.count
import com.open.quester.extensions.nearestGameObject
import com.open.quester.extensions.nearestNpc
import com.open.quester.models.ItemRequirementCondition
import com.open.quester.models.QuestInformation
import com.open.quester.models.QuestRequirements
import com.open.quester.quest.Constants
import com.open.quester.quest.Constants.BANK_LUMBRIDGE
import org.powbot.api.Tile
import org.powbot.api.rt4.*
import com.open.quester.quest.lostcity.LostCityConstants.AREA_END_ROOM
import com.open.quester.quest.lostcity.LostCityConstants.AREA_LOST_CITY_DUNGEON
import com.open.quester.quest.lostcity.LostCityConstants.AREA_SOUTH_TREE
import com.open.quester.quest.lostcity.LostCityConstants.CONVERSATIONS_WARRIOR
import com.open.quester.quest.lostcity.LostCityConstants.CONVERSATION_CHATTING
import com.open.quester.quest.lostcity.LostCityConstants.NAME_BRANCH
import com.open.quester.quest.lostcity.LostCityConstants.NAME_SHAMUS
import com.open.quester.quest.lostcity.LostCityConstants.NAME_STAFF
import com.open.quester.quest.lostcity.LostCityConstants.NAME_WARRIOR
import com.open.quester.quest.lostcity.LostCityConstants.TILE_NEAR_ZANARIS
import com.open.quester.quest.lostcity.LostCityConstants.TILE_NPC_SPOT
import com.open.quester.quest.lostcity.LostCityConstants.TILE_SAFESPOT
import com.open.quester.quest.lostcity.LostCityConstants.TILE_WARRIOR
import com.open.quester.quest.lostcity.tasks.CastHomeTeleport
import com.open.quester.quest.lostcity.tasks.DepositAllEquipment
import com.open.quester.quest.lostcity.tasks.ManualCastSpells
import com.open.quester.quest.lostcity.tasks.WalkToSafespot

class LostCity(information: QuestInformation) : BaseQuest(information) {

    private val axeRequirement = ItemRequirementCondition(*Constants.ItemRequirements.REQUIREMENT_ITEM_AXE)
    private val startQuest = startQuest()
    private val chopTree = chopTree()
    private val getStaff = getDramenStaff()
    private val makeStaff = getStaff()
    private val returnStaff = returnStaff()

    override fun addRequirements(): QuestRequirements {
        return QuestRequirements(
            listOf(axeRequirement),
            listOf()
        )
    }

    override fun getQuestStep(stepPosition: Int): BaseQuestStep? {
        return when (stepPosition) {
            0 -> startQuest.processStep()
            1 -> chopTree.processStep()
            2 -> getStaff.processStep()
            3, 4 -> makeStaff.processStep()
            5 -> returnStaff.processStep()
            6 -> {
                information.complete = true
                CommonMethods.closeQuestComplete()
                null
            }
            else -> TODO("Missing ID $stepPosition")
        }
    }

    private fun returnStaff(): QuestTaskList {
        val openDoor = SimpleObjectStep(
            TILE_NEAR_ZANARIS, arrayOf(), { Objects.stream().name("Door").within(TILE_NEAR_ZANARIS, 2).first() },
            { go: GameObject -> go.interact("Open") },
            { Varpbits.varpbit(getQuestVarpbit()) == 6 },
            "Entering Zanaris", information,
            { !AREA_END_ROOM.contains(Players.local()) },
        )
        return QuestTaskList(
            EquipItemStep(NAME_STAFF, "Wield", Equipment.Slot.MAIN_HAND),
            CastHomeTeleport(),
            openDoor
        )
    }

    private fun getStaff(): QuestTaskList {
        val cutTree = SimpleObjectStep(
            Tile(2860, 9733), arrayOf(), { Objects.nearestGameObject("Dramen tree") },
            { go: GameObject -> go.interact("Chop down") },
            { Inventory.count(NAME_BRANCH) >= 1 }, "Chopping tree", information,
            {
                Inventory.stream().name(NAME_BRANCH, NAME_STAFF)
                    .count() == 0L && Equipment.itemAt(Equipment.Slot.MAIN_HAND).name() != NAME_STAFF
            },
        )

        val makeStaff = CombineItemStep(
            "Knife", NAME_BRANCH, "Making staff",
            { Inventory.stream().name(NAME_BRANCH).count() != 0L },
            true
        )

        return QuestTaskList(
            cutTree,
            makeStaff,
        )
    }

    private fun getDramenStaff(): QuestTaskList {
        val climbDownLadder = SimpleObjectStep(
            Tile(2821, 3373, 0), arrayOf("Well that is a risk I will have to take."),
            {
                Objects.stream().name("Ladder").within(Tile(2821, 3373), 3).first()
            },
            { go: GameObject -> go.interact("Climb-down") },
            { Chat.chatting() },
            "Going to dungeon",
            information,
            { !AREA_LOST_CITY_DUNGEON.contains(Players.local()) }
        )

        val getBronzeAxe = ManualCastSpells("Zombie", "Bronze axe", information, {
            Inventory.stream().name("Bronze axe")
                .count() == 0L && AREA_LOST_CITY_DUNGEON.contains(Players.local())
        }, Tile(2836, 9772, 0))

        val walkToRoom = WalkToArea(
            AREA_END_ROOM,
            Tile(2860, 9732, 0),
            "Walking to spirit room",
            { !AREA_END_ROOM.contains(Players.local()) && Inventory.stream().name("Bronze axe").count() > 0 },
            information
        )

        val walkToSouthTree = WalkToArea(
            AREA_SOUTH_TREE,
            Tile(2860, 9733), "Walking south of tree", {
                AREA_END_ROOM.contains(Players.local()) && !AREA_SOUTH_TREE.contains(Players.local()) &&
                        Npcs.nearestNpc("Tree spirit") == Npc.Nil
            }, information
        )

        val cutTree = SimpleObjectStep(Tile(2860, 9733),
            arrayOf(),
            { Objects.nearestGameObject("Dramen tree") },
            { go: GameObject -> go.interact("Chop down") },
            { Npcs.nearestNpc("Tree spirit") != Npc.Nil },
            "Chopping tree",
            information,
            { Npcs.nearestNpc("Tree spirit") == Npc.Nil }
        )

        val attackNpc = ManualCastSpells("Tree spirit", null, information, {
            Players.local().tile() == TILE_SAFESPOT && Npcs.nearestNpc("Tree spirit").tile() == TILE_NPC_SPOT
        }, TILE_SAFESPOT)

        return QuestTaskList(
            DepositAllEquipment(information),
            climbDownLadder,
            getBronzeAxe,
            attackNpc,
            WalkToSafespot(),
            walkToRoom,
            walkToSouthTree,
            cutTree,
            SleepTask { Players.local().tile() == TILE_SAFESPOT && Npcs.nearestNpc("Tree spirit") != Npc.Nil }
        )
    }

    private fun chopTree(): QuestTaskList {
        val chopTree = SimpleObjectStep(
            Tile(3139, 3213, 0),
            CONVERSATION_CHATTING,
            { Objects.nearestGameObject(2409) },
            { go -> go.interact("Chop") },
            { Npcs.nearestNpc(NAME_SHAMUS) != Npc.Nil },
            "Cutting tree",
            information,
            { Inventory.stream().filter { it.name().contains(" axe") }.any() },
        )
        val npcStep = SimpleConversationStep(
            NAME_SHAMUS,
            Tile(3139, 3213, 0),
            CONVERSATION_CHATTING,
            "Talking to Shamus",
            information,
            shouldExecute = { Npcs.stream().name(NAME_SHAMUS).first() != Npc.Nil },
        )

        return QuestTaskList(npcStep, chopTree)
    }

    private fun startQuest(): QuestTaskList {
        val startQuest = SimpleConversationStep(
            NAME_WARRIOR, TILE_WARRIOR, CONVERSATIONS_WARRIOR, "Starting Quest",
            information
        )

        return QuestTaskList(
            BankStep(
                listOf(axeRequirement), BANK_LUMBRIDGE, information
            ),
            startQuest,
        )

    }
}