package com.open.quester.quest.priestinperil

import com.open.quester.common.*
import com.open.quester.common.base.BaseQuest
import com.open.quester.common.base.BaseQuestStep
import com.open.quester.common.base.SimpleObjectStep
import com.open.quester.extensions.count
import com.open.quester.extensions.nearestGameObject
import com.open.quester.extensions.nearestNpc
import com.open.quester.helpers.InteractionsHelper
import com.open.quester.models.ItemRequirement
import com.open.quester.models.ItemRequirementCondition
import com.open.quester.models.QuestInformation
import com.open.quester.models.QuestRequirements
import com.open.quester.quest.Constants.BANK_VARROCK_EAST
import com.open.quester.quest.priestinperil.steps.GoUpLadder
import com.open.quester.quest.priestinperil.steps.temple.BankForEssence
import com.open.quester.quest.priestinperil.steps.temple.GrabSilverKey
import org.powbot.api.Point
import org.powbot.api.Tile
import org.powbot.api.rt4.*
import org.powbot.quests.quest.priestinperil.PriestInPerilConstants
import org.powbot.quests.quest.priestinperil.PriestInPerilConstants.AREA_SECOND_UNDERGROUND
import org.powbot.quests.quest.priestinperil.PriestInPerilConstants.CONVERSATION_KILL_NPC
import org.powbot.quests.quest.priestinperil.PriestInPerilConstants.CONVERSATION_START_QUEST
import org.powbot.quests.quest.priestinperil.PriestInPerilConstants.CONVERSATION_TALK_TO_DREZEL
import org.powbot.quests.quest.priestinperil.PriestInPerilConstants.ITEM_BUCKET
import org.powbot.quests.quest.priestinperil.PriestInPerilConstants.ITEM_GOLDEN_KEY
import org.powbot.quests.quest.priestinperil.PriestInPerilConstants.ITEM_IRON_KEY
import org.powbot.quests.quest.priestinperil.PriestInPerilConstants.ITEM_PURE_ESSENCE
import org.powbot.quests.quest.priestinperil.PriestInPerilConstants.ITEM_RUNE_ESSENCE
import org.powbot.quests.quest.priestinperil.PriestInPerilConstants.NAME_BLESSED_WATER
import org.powbot.quests.quest.priestinperil.PriestInPerilConstants.NAME_COFFIN
import org.powbot.quests.quest.priestinperil.PriestInPerilConstants.NAME_DREZEL
import org.powbot.quests.quest.priestinperil.PriestInPerilConstants.NAME_KING_RONALD
import org.powbot.quests.quest.priestinperil.PriestInPerilConstants.NAME_MURKY_WATER
import org.powbot.quests.quest.priestinperil.PriestInPerilConstants.NAME_TEMPLE_GUARDIAN
import org.powbot.quests.quest.priestinperil.PriestInPerilConstants.NAME_TRAP_DOOR
import org.powbot.quests.quest.priestinperil.PriestInPerilConstants.TILE_CENTER_BOTTOM_FLOOR
import org.powbot.quests.quest.priestinperil.PriestInPerilConstants.TILE_DREZEL_THIRD_FLOOR
import org.powbot.quests.quest.priestinperil.PriestInPerilConstants.TILE_DREZEL_UNDERGROUND
import org.powbot.quests.quest.priestinperil.PriestInPerilConstants.TILE_KING_RONALD
import org.powbot.quests.quest.priestinperil.PriestInPerilConstants.TILE_NEAR_TEMPLE_DOOR
import org.powbot.quests.quest.priestinperil.PriestInPerilConstants.TILE_SECOND_FLOOR
import org.powbot.quests.quest.priestinperil.PriestInPerilConstants.TILE_TRAPDOOR

class PriestInPeril(information: QuestInformation) : BaseQuest(information) {

    val runeEssence = ItemRequirement(ITEM_RUNE_ESSENCE, false, 50)
    val pureEssence = ItemRequirement(ITEM_PURE_ESSENCE, false, 50)
    private val bucketRequirement = ItemRequirementCondition(ITEM_BUCKET, false, 1)
    private val essenceCondition = ItemRequirementCondition(runeEssence, pureEssence)

    private val talktoRonald = SimpleConversationStep(
        NAME_KING_RONALD, TILE_KING_RONALD, CONVERSATION_START_QUEST,
        "Talking to King Roald", information
    )

    private val talkToDrezelUnderground = SimpleConversationStep(
        NAME_DREZEL,
        TILE_DREZEL_UNDERGROUND, arrayOf(), "Giving essence", information
    )

    private val talkToDrezelInPrisonAgain = SimpleConversationStep(NAME_DREZEL, TILE_DREZEL_THIRD_FLOOR,
        CONVERSATION_TALK_TO_DREZEL, "Talking to Drezel in prison", information,
        shouldExecute = { Inventory.count(NAME_BLESSED_WATER) == 0 })
        .also { it.pointVariance = Point(-4, 0) }

    private val talkToDrezelInsidePrison = SimpleConversationStep(NAME_DREZEL, TILE_DREZEL_THIRD_FLOOR,
        CONVERSATION_TALK_TO_DREZEL, "Talking to Drezel inside prison", information,
        shouldExecute = { Inventory.count(NAME_BLESSED_WATER) == 0 })

    private val talkToMonk = SimpleObjectStep(
        TILE_NEAR_TEMPLE_DOOR,
        CONVERSATION_KILL_NPC,
        { Objects.stream(TILE_NEAR_TEMPLE_DOOR, 5, GameObject.Type.BOUNDARY).name("Large door").first() },
        { go: GameObject -> go.interact("Open") },
        { Chat.chatting() },
        "Getting tricked",
        information
    )
    private val killDogSteps: QuestTaskList = QuestTaskList(
        BankStep(listOf(), BANK_VARROCK_EAST, information, combat = true, foodRequired = true),
        SetupWeaponStep(information),
        talkToMonk,
    )

    private val goDownAndKillDog = QuestTaskList(
        SimpleObjectStep(
            TILE_TRAPDOOR,
            arrayOf("Yes."),
            { Objects.nearestGameObject(NAME_TRAP_DOOR) },
            { go: GameObject ->
                val action = if (go.actions().contains("Climb-down")) "Climb-down" else "Open"
                go.interact(action)
            },
            { go: GameObject -> !go.valid() }, "Climbing down trapdoor",
            information, { Npcs.nearestNpc(NAME_TEMPLE_GUARDIAN) == Npc.Nil }
        ),
        KillNpcStep(
            Tile.Nil, { Npcs.nearestNpc(NAME_TEMPLE_GUARDIAN) }, null, null,
            { Npcs.nearestNpc(NAME_TEMPLE_GUARDIAN) != Npc.Nil }, information, "Killing dodge"
        )
    )

    private val reportBackToRoland = QuestTaskList(GoUpLadder(information), talktoRonald)
    private val talkingToDrezel = talkingToDrezel()
    private val burnTheVampire = QuestTaskList(
        talkToDrezelInsidePrison,
        SimpleObjectStep(TILE_SECOND_FLOOR, arrayOf(),
            { Objects.nearestGameObject(NAME_COFFIN) },
            { go: GameObject -> InteractionsHelper.useItemOnInteractive(NAME_BLESSED_WATER, go) },
            { Inventory.count(NAME_BLESSED_WATER) == 0 }, "Making the vampire wet", information,
            shouldExecute = { Inventory.count(NAME_BLESSED_WATER) == 1 }),
    )
    private val getKeys: QuestTaskList = getKeys()

    private val giveDrezelEssence = QuestTaskList(BankForEssence(essenceCondition), talkToDrezelUnderground)

    override fun addRequirements(): QuestRequirements {
        val itemRequirements: MutableList<ItemRequirementCondition> = mutableListOf()

        itemRequirements.add(bucketRequirement)
        itemRequirements.add(essenceCondition)
        return QuestRequirements(itemRequirements, listOf())
    }

    override fun getQuestStep(stepPosition: Int): BaseQuestStep? {
        return when (stepPosition) {
            0 -> talktoRonald
            1 -> {
                killDogSteps.processStep()
            }
            2 -> {
                logger.info("Going down and killing dog")
                goDownAndKillDog.processStep()
            }
            3 -> reportBackToRoland.processStep()
            4 -> talkingToDrezel.processStep()
            5 -> getKeys.processStep()
            6 -> burnTheVampire.processStep()
            7 -> talkToDrezelInsidePrison
            in 8..59 -> giveDrezelEssence.processStep()
            60 -> {
                information.complete = true
                CommonMethods.closeQuestComplete()
                null
            }
            61 -> {
                information.complete = true
                CommonMethods.closeQuestComplete()
                null
            }
            else -> {
                null
            }
        }
    }

    private fun getKeys(): QuestTaskList {
        return QuestTaskList(
            GrabSilverKey(information),
            SimpleObjectStep(Tile.Nil, arrayOf(), { Objects.nearestGameObject("Well") },
                { go: GameObject -> InteractionsHelper.useItemOnInteractive(ITEM_BUCKET, go) },
                { Inventory.count(NAME_MURKY_WATER) > 0 }, "Grabbing holy water", information,
                shouldExecute = {
                    Inventory.count(NAME_MURKY_WATER, NAME_BLESSED_WATER) == 0 &&
                            AREA_SECOND_UNDERGROUND.contains(Players.local()) && Inventory.count(ITEM_IRON_KEY) == 1
                }
            ),
            SimpleObjectStep(TILE_SECOND_FLOOR, arrayOf(),
                { Objects.nearestGameObject(NAME_COFFIN) },
                { go: GameObject -> InteractionsHelper.useItemOnInteractive(NAME_BLESSED_WATER, go) },
                { Inventory.count(NAME_BLESSED_WATER) == 0 }, "Making the vampire wet", information,
                shouldExecute = { Inventory.count(NAME_BLESSED_WATER) == 1 }),
            SimpleObjectStep(TILE_DREZEL_THIRD_FLOOR, arrayOf(), "Cell door","Open", {Inventory.count(ITEM_IRON_KEY) == 0},
                "Opening door with key", information,
                {Inventory.count(ITEM_IRON_KEY) == 1}).also { it.pointVariance = Point(-4, 0) },
            talkToDrezelInsidePrison
        )
    }

    private fun talkingToDrezel(): QuestTaskList {
        return QuestTaskList(
            BankStep(
                listOf(bucketRequirement),
                BANK_VARROCK_EAST,
                information,
                combat = true,
                foodRequired = true,
                shouldExecute = {
                    Inventory.count(ITEM_GOLDEN_KEY) == 0 && (Inventory.count(ITEM_BUCKET) == 0 ||
                            Inventory.count(*information.foodName) == 0)
                }),
            SetupWeaponStep(information),
            KillNpcStep(TILE_CENTER_BOTTOM_FLOOR,
                {
                    Npcs.stream().name(PriestInPerilConstants.NAME_MONK).filtered { it.combatLevel == 30 }.nearest()
                        .first()
                }, arrayOf(ITEM_GOLDEN_KEY), { GroundItems.stream().name(ITEM_GOLDEN_KEY).nearest().toList() },
                { Inventory.count(ITEM_GOLDEN_KEY) == 0 },
                information,
                "Getting golden key"
            ),
            talkToDrezelInPrisonAgain
        )
    }
}