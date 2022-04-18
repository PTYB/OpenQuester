package com.open.quester.quest.myreque.insearchofthemyreeque

import com.open.quester.common.*
import com.open.quester.common.base.BaseQuest
import com.open.quester.common.base.BaseQuestStep
import com.open.quester.common.base.SimpleObjectStep
import com.open.quester.extensions.nearestNpc
import com.open.quester.models.QuestInformation
import com.open.quester.models.QuestRequirements
import com.open.quester.models.SkillRequirement
import com.open.quester.quest.Constants.BANK_CANIFIS
import com.open.quester.quest.myreque.MyrequeConstants.NAME_VELIAF
import com.open.quester.quest.myreque.insearchofthemyreeque.InSearchOfTheMyrequeConstants.ACTION_BASE_DOOR
import com.open.quester.quest.myreque.insearchofthemyreeque.InSearchOfTheMyrequeConstants.AREA_BASE
import com.open.quester.quest.myreque.insearchofthemyreeque.InSearchOfTheMyrequeConstants.AREA_BRIDGE
import com.open.quester.quest.myreque.insearchofthemyreeque.InSearchOfTheMyrequeConstants.AREA_HIDDEN_BASE
import com.open.quester.quest.myreque.insearchofthemyreeque.InSearchOfTheMyrequeConstants.AREA_UNDER_PUB
import com.open.quester.quest.myreque.insearchofthemyreeque.InSearchOfTheMyrequeConstants.CONVERSATION_BOATY
import com.open.quester.quest.myreque.insearchofthemyreeque.InSearchOfTheMyrequeConstants.CONVERSATION_CYREG
import com.open.quester.quest.myreque.insearchofthemyreeque.InSearchOfTheMyrequeConstants.CONVERSATION_VANSTORM
import com.open.quester.quest.myreque.insearchofthemyreeque.InSearchOfTheMyrequeConstants.CONVERSATION_VELIAF
import com.open.quester.quest.myreque.insearchofthemyreeque.InSearchOfTheMyrequeConstants.LEAVE_VELIAF
import com.open.quester.quest.myreque.insearchofthemyreeque.InSearchOfTheMyrequeConstants.MASK_TALK
import com.open.quester.quest.myreque.insearchofthemyreeque.InSearchOfTheMyrequeConstants.NAME_BASE_DOOR
import com.open.quester.quest.myreque.insearchofthemyreeque.InSearchOfTheMyrequeConstants.NAME_BOATY
import com.open.quester.quest.myreque.insearchofthemyreeque.InSearchOfTheMyrequeConstants.NAME_CRYEG
import com.open.quester.quest.myreque.insearchofthemyreeque.InSearchOfTheMyrequeConstants.NAME_HAROLD
import com.open.quester.quest.myreque.insearchofthemyreeque.InSearchOfTheMyrequeConstants.NAME_IVAN
import com.open.quester.quest.myreque.insearchofthemyreeque.InSearchOfTheMyrequeConstants.NAME_POLMAFI
import com.open.quester.quest.myreque.insearchofthemyreeque.InSearchOfTheMyrequeConstants.NAME_RADIGAD
import com.open.quester.quest.myreque.insearchofthemyreeque.InSearchOfTheMyrequeConstants.NAME_SANI
import com.open.quester.quest.myreque.insearchofthemyreeque.InSearchOfTheMyrequeConstants.NAME_VANSTROM
import com.open.quester.quest.myreque.insearchofthemyreeque.InSearchOfTheMyrequeConstants.TILE_BASE_ENTRANCE
import com.open.quester.quest.myreque.insearchofthemyreeque.InSearchOfTheMyrequeConstants.TILE_BOATY
import com.open.quester.quest.myreque.insearchofthemyreeque.InSearchOfTheMyrequeConstants.TILE_CYREG
import com.open.quester.quest.myreque.insearchofthemyreeque.InSearchOfTheMyrequeConstants.TILE_HAROLD
import com.open.quester.quest.myreque.insearchofthemyreeque.InSearchOfTheMyrequeConstants.TILE_IVAN
import com.open.quester.quest.myreque.insearchofthemyreeque.InSearchOfTheMyrequeConstants.TILE_NEAR_STALAG
import com.open.quester.quest.myreque.insearchofthemyreeque.InSearchOfTheMyrequeConstants.TILE_NEAR_TREE_SOUTH
import com.open.quester.quest.myreque.insearchofthemyreeque.InSearchOfTheMyrequeConstants.TILE_POLMAFI
import com.open.quester.quest.myreque.insearchofthemyreeque.InSearchOfTheMyrequeConstants.TILE_RAGIGAD
import com.open.quester.quest.myreque.insearchofthemyreeque.InSearchOfTheMyrequeConstants.TILE_SANI
import com.open.quester.quest.myreque.insearchofthemyreeque.InSearchOfTheMyrequeConstants.TILE_VANSTORM_PUB
import com.open.quester.quest.myreque.insearchofthemyreeque.InSearchOfTheMyrequeConstants.TILE_VELIAF
import com.open.quester.quest.myreque.insearchofthemyreeque.InSearchOfTheMyrequeConstants.TILE_WALL
import com.open.quester.quest.myreque.insearchofthemyreeque.InSearchOfTheMyrequeConstants.VARP_SHIFT_HAROLD
import com.open.quester.quest.myreque.insearchofthemyreeque.InSearchOfTheMyrequeConstants.VARP_SHIFT_IVAN
import com.open.quester.quest.myreque.insearchofthemyreeque.InSearchOfTheMyrequeConstants.VARP_SHIFT_POLMAFI
import com.open.quester.quest.myreque.insearchofthemyreeque.InSearchOfTheMyrequeConstants.VARP_SHIFT_RADIGAD
import com.open.quester.quest.myreque.insearchofthemyreeque.InSearchOfTheMyrequeConstants.VARP_SHIFT_SANI
import com.open.quester.quest.myreque.insearchofthemyreeque.InSearchOfTheMyrequeConstants.VARP_TALKING
import com.open.quester.quest.myreque.insearchofthemyreeque.InSearchOfTheMyrequeConstants.allItemRequirements
import com.open.quester.quest.myreque.insearchofthemyreeque.leafs.HandleBridge
import com.open.quester.quest.myreque.insearchofthemyreeque.leafs.HandleQuiz
import org.powbot.api.Point
import org.powbot.api.Tile
import org.powbot.api.rt4.*

class InSearchOfTheMyreque(information: QuestInformation) : BaseQuest(information) {

    private val talkToVanstorm = SimpleConversationStep(
        NAME_VANSTROM, TILE_VANSTORM_PUB, CONVERSATION_VANSTORM,
        "Talking to Vanstorm", information
    )

    private val talkToCyreg = SimpleConversationStep(
        NAME_CRYEG, TILE_CYREG, CONVERSATION_CYREG, "Giving stuff to Cyreg",
        information
    )

    private val startQuest = QuestTaskList(
        BankStep(allItemRequirements, BANK_CANIFIS, information, combat = true, foodRequired = true),
        talkToVanstorm
    )

    private val fixingTheBoat = QuestTaskList(
        WalkToExactTile(Tile(3545, 3280), "Losing aggro", {
            Npcs.stream().interactingWithMe()
                .filtered { it.actions.contains("Attack") }.first() != Npc.Nil
        }, information),
        talkToCyreg
    )

    private val boardingTheBoat = SimpleObjectStep(
        TILE_BOATY, CONVERSATION_BOATY, NAME_BOATY,
        "Quick-board", { go: GameObject -> !go.valid() }, "Boarding the boat", information
    ).also { it.pointVariance = Point(-2, 0) }

    private val answerQuiz = QuestTaskList(
        HandleQuiz(information),
        HandleBridge(true),
        SimpleObjectStep(
            TILE_NEAR_TREE_SOUTH, arrayOf(), "Tree", "Climb",
            { AREA_BRIDGE.contains(Players.local()) }, "Climbing tree", information
        )
    )

    private val talkToVeliaf = QuestTaskList(
        SimpleConversationStep(NAME_VELIAF, TILE_VELIAF, CONVERSATION_VELIAF, "Talking to Veliaf",
            information, shouldExecute = { AREA_HIDDEN_BASE.contains(Players.local()) }),
        SimpleObjectStep(TILE_NEAR_STALAG,
            arrayOf(),
            { Objects.stream().name("Cave entrance").nearest(TILE_NEAR_STALAG).first() },
            { go: GameObject -> go.interact("Enter") },
            { AREA_HIDDEN_BASE.contains(Players.local()) },
            "Entering cave",
            information,
            { AREA_BASE.contains(Players.local()) }
        ),
        SimpleObjectStep(TILE_BASE_ENTRANCE,
            arrayOf(),
            NAME_BASE_DOOR,
            ACTION_BASE_DOOR,
            { AREA_BASE.contains(Players.local()) },
            "Entering base",
            information,
            { !AREA_BASE.contains(Players.local()) && !AREA_HIDDEN_BASE.contains(Players.local()) })
    )

    private val talktoCrew = QuestTaskList(
        SimpleConversationStep(NAME_HAROLD,
            TILE_HAROLD,
            arrayOf("I'll be back later."),
            "Talking to Harold",
            information,
            shouldExecute = { Varpbits.varpbit(VARP_TALKING, VARP_SHIFT_HAROLD, MASK_TALK) == 0 }),
        SimpleConversationStep(NAME_RADIGAD,
            TILE_RAGIGAD,
            arrayOf("I'll be back later."),
            "Talking to Radigad",
            information,
            shouldExecute = { Varpbits.varpbit(VARP_TALKING, VARP_SHIFT_RADIGAD, MASK_TALK) == 0 }),
        SimpleConversationStep(NAME_SANI, TILE_SANI, arrayOf("I'll be back later."), "Talking to Sani", information,
            shouldExecute = { Varpbits.varpbit(VARP_TALKING, VARP_SHIFT_SANI, MASK_TALK) == 0 }),
        SimpleConversationStep(NAME_POLMAFI,
            TILE_POLMAFI,
            arrayOf("I'll be back later."),
            "Talking to Polmafi",
            information,
            shouldExecute = { Varpbits.varpbit(VARP_TALKING, VARP_SHIFT_POLMAFI, MASK_TALK) == 0 }),
        SimpleConversationStep(NAME_IVAN, TILE_IVAN, arrayOf("I'll be back later."), "Talking to Ivan", information,
            shouldExecute = { Varpbits.varpbit(VARP_TALKING, VARP_SHIFT_IVAN, MASK_TALK) == 0 }),
        SimpleConversationStep(
            NAME_VELIAF, TILE_VELIAF, CONVERSATION_VELIAF, "Talking to Veliaf",
            information
        ),
    )

    private val killDog = KillNpcStep(
        Tile.Nil, { Npcs.nearestNpc("Skeleton Hellhound") },
        null, null, { Npcs.nearestNpc("Skeleton Hellhound") != Npc.Nil }, information, "Killing Dog"
    ).also { it.protectionPrayer = Prayer.Effect.PROTECT_FROM_MELEE }

    private val learnExit = QuestTaskList(
        DisablePrayerIfActive(Prayer.Effect.PROTECT_FROM_MELEE),
        SimpleConversationStep(NAME_VELIAF, TILE_VELIAF, LEAVE_VELIAF, "Talking to Veliaf", information),
    )

    private val finishQuest = QuestTaskList(
        SimpleObjectStep(Tile.Nil, arrayOf("Okay, thanks."), "Cave entrance", "Enter",
            { !AREA_HIDDEN_BASE.contains(Players.local()) }, "Exiting base", information,
            { AREA_HIDDEN_BASE.contains(Players.local()) }),
        SimpleObjectStep(Tile.Nil, arrayOf(), "Ladder", "Climb-up", { !AREA_UNDER_PUB.contains(Players.local()) },
            "Climbing ladder", information, { AREA_UNDER_PUB.contains(Players.local()) }),
        SimpleObjectStep(TILE_WALL,
            arrayOf(),
            "Wall",
            "Search",
            { AREA_UNDER_PUB.contains(Players.local()) },
            "Searching wall",
            information,
            { AREA_BASE.contains(Players.local()) }).also { it.pointVariance = Point(0, -1) },
        SimpleConversationStep("Stranger", TILE_VANSTORM_PUB, arrayOf(), "Finishing quest", information)
    )

    override fun addRequirements(): QuestRequirements {
        return QuestRequirements(allItemRequirements, listOf(SkillRequirement(Constants.SKILLS_AGILITY, 25)))
    }

    override fun getQuestStep(stepPosition: Int): BaseQuestStep? {
        return when (stepPosition) {
            0 -> startQuest.processStep()
            5, 10, 15 -> fixingTheBoat.processStep()
            20 -> boardingTheBoat
            25, 52 -> answerQuiz.processStep()
            55, 60 -> talkToVeliaf.processStep()
            65, 67, 70 -> talktoCrew.processStep()
            71, 72, 73, 74, 75, 76, 77, 78, 79, 80 -> killDog
            85 -> learnExit.processStep()
            90, 95, 97, 100, 105 -> finishQuest.processStep()
            110 -> {
                information.complete = true
                CommonMethods.closeQuestComplete()
                null
            }
            else -> TODO("Missing ID $stepPosition")
        }
    }
}