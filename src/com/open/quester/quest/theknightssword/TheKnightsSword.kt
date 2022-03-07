package com.open.quester.quest.theknightssword

import com.open.quester.common.BankStep
import com.open.quester.common.CommonMethods
import com.open.quester.common.QuestTaskList
import com.open.quester.common.SimpleConversationStep
import com.open.quester.common.base.BaseQuest
import com.open.quester.common.base.BaseQuestStep
import com.open.quester.extensions.count
import com.open.quester.models.*
import com.open.quester.quest.Constants.BANK_EAST_FALADOR
import com.open.quester.quest.Constants.ItemRequirements.REQUIREMENT_ITEM_PICKAXE
import com.open.quester.quest.theknightssword.TheKnightsSwordConstants.CONVERSATION_RELDO
import com.open.quester.quest.theknightssword.TheKnightsSwordConstants.CONVERSATION_TALK_TO_SQUIRE
import com.open.quester.quest.theknightssword.TheKnightsSwordConstants.CONVERSATION_THURGO
import com.open.quester.quest.theknightssword.TheKnightsSwordConstants.CONVERSATION_THURGO_AGAIN
import com.open.quester.quest.theknightssword.TheKnightsSwordConstants.CONVERSATION_THURGO_ORE
import com.open.quester.quest.theknightssword.TheKnightsSwordConstants.CONVERSATION_THURGO_PORTRAIT
import com.open.quester.quest.theknightssword.TheKnightsSwordConstants.ITEM_BLURITE_ORE
import com.open.quester.quest.theknightssword.TheKnightsSwordConstants.ITEM_BLURITE_SWORD
import com.open.quester.quest.theknightssword.TheKnightsSwordConstants.ITEM_IRON_BAR
import com.open.quester.quest.theknightssword.TheKnightsSwordConstants.ITEM_PORTRAIT
import com.open.quester.quest.theknightssword.TheKnightsSwordConstants.ITEM_REDBERRY_PIE
import com.open.quester.quest.theknightssword.TheKnightsSwordConstants.NAME_RELDO
import com.open.quester.quest.theknightssword.TheKnightsSwordConstants.NAME_SQUIRE
import com.open.quester.quest.theknightssword.TheKnightsSwordConstants.NAME_THURGO
import com.open.quester.quest.theknightssword.TheKnightsSwordConstants.TILE_RELDO
import com.open.quester.quest.theknightssword.TheKnightsSwordConstants.TILE_SQUIRE
import com.open.quester.quest.theknightssword.TheKnightsSwordConstants.TILE_THURGO
import com.open.quester.quest.theknightssword.tasks.AcquirePicture
import com.open.quester.quest.theknightssword.tasks.MineTheOre
import org.powbot.api.rt4.Constants.SKILLS_MINING
import org.powbot.api.rt4.Inventory

class TheKnightsSword(information: QuestInformation) : BaseQuest(information) {

    private val redBerryPieRequirement: ItemRequirementCondition = ItemRequirementCondition(ITEM_REDBERRY_PIE, false, 1)
    private val ironBarRequirement = ItemRequirementCondition(ITEM_IRON_BAR, false, 2)
    private val pickaxeRequirement = ItemRequirementCondition(*REQUIREMENT_ITEM_PICKAXE)
    private val portraitRequirement = ItemRequirementCondition(ITEM_PORTRAIT, true, 1)

    private val startQuest = SimpleConversationStep(
        NAME_SQUIRE, TILE_SQUIRE, CONVERSATION_TALK_TO_SQUIRE, "Talk to Squire", information
    )

    private val talkToReldo = SimpleConversationStep(
        NAME_RELDO, TILE_RELDO, CONVERSATION_RELDO, "Talk to Reldo", information
    )

    private var talkToThurgo: QuestTaskList = createThurgo()

    private var talkToThurgoAgain = SimpleConversationStep(
        NAME_THURGO, TILE_THURGO, CONVERSATION_THURGO_AGAIN, "Talk to Thurgo again", information
    )

    private var talkToSquireAgain = SimpleConversationStep(
        NAME_SQUIRE, TILE_SQUIRE, arrayOf("Talk about other things."), // Christmas event
        "Talking to Squire", information
    )
    private var getPicture = getPictureStep()
    private val mineOre = mineBluriteOre()

    override fun addRequirements(): QuestRequirements {
        logger.info("Adding quest requirements")
        val itemRequirements: MutableList<ItemRequirementCondition> =
            mutableListOf(redBerryPieRequirement, ironBarRequirement, pickaxeRequirement)

        return QuestRequirements(
            itemRequirements, listOf(
                SkillRequirement(SKILLS_MINING, 10)
            )
        )
    }

    private fun getPictureStep(): QuestTaskList {
        val bankTask = BankStep(
            listOf(ironBarRequirement, pickaxeRequirement, portraitRequirement, ItemRequirementCondition.emptySlots(2)),
            BANK_EAST_FALADOR,
            information,
            { Inventory.count(ITEM_PORTRAIT) > 0 },
            false,
            true
        )

        val conversation = SimpleConversationStep(
            NAME_THURGO, TILE_THURGO,
            CONVERSATION_THURGO_PORTRAIT, "Talk to Thurgo again", information
        )

        return QuestTaskList(AcquirePicture(), bankTask, conversation)
    }

    private fun createThurgo(): QuestTaskList {
        val conversation = SimpleConversationStep(NAME_THURGO, TILE_THURGO, CONVERSATION_THURGO, "Talking to Thurgo", information)
        val bankStep = BankStep(
            listOf(redBerryPieRequirement),
            BANK_EAST_FALADOR,
            information,
            { Inventory.count(ITEM_REDBERRY_PIE) == 0 },
            false
        )

        return QuestTaskList(bankStep, conversation)
    }

    private fun mineBluriteOre(): QuestTaskList {
        val returnOre = SimpleConversationStep(
            NAME_THURGO, TILE_THURGO,
            CONVERSATION_THURGO_ORE, "Give Thurgo ore", information,
            false
        ) {
            Inventory.count(ITEM_BLURITE_SWORD) == 0 &&
                    Inventory.count(ITEM_BLURITE_ORE) == 1
        }

        val returnSword = SimpleConversationStep(
            NAME_SQUIRE, TILE_SQUIRE, arrayOf(), "Give sword to squire", information,
            false
        ) { Inventory.stream().name(ITEM_BLURITE_SWORD).count().toInt() > 0 }

        return QuestTaskList(
            MineTheOre(*information.foodName),
            returnOre,
            returnSword
        )
    }

    override fun getQuestStep(stepPosition: Int): BaseQuestStep? {
        return when (stepPosition) {
            0 -> startQuest
            1 -> talkToReldo
            2 -> talkToThurgo.processStep()!!
            3 -> talkToThurgoAgain
            4 -> talkToSquireAgain
            5 -> getPicture.processStep()!!
            6 -> mineOre.processStep()!!
            7 -> {
                information.complete = true
                CommonMethods.closeQuestComplete()
                null
            }
            else -> TODO()
        }
    }
}