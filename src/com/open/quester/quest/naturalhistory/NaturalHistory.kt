package com.open.quester.quest.naturalhistory

import com.open.quester.common.CommonMethods
import com.open.quester.common.QuestTaskList
import com.open.quester.common.SimpleConversationStep
import com.open.quester.common.base.BaseQuest
import com.open.quester.common.base.BaseQuestStep
import com.open.quester.models.QuestInformation
import com.open.quester.models.QuestRequirements
import org.powbot.quests.quest.naturalhistory.NaturalHistoryConstants.ANSWER_CAMEL
import org.powbot.quests.quest.naturalhistory.NaturalHistoryConstants.ANSWER_DRAGON
import org.powbot.quests.quest.naturalhistory.NaturalHistoryConstants.ANSWER_KALPHITE_QUEEN
import org.powbot.quests.quest.naturalhistory.NaturalHistoryConstants.ANSWER_LEECH
import org.powbot.quests.quest.naturalhistory.NaturalHistoryConstants.ANSWER_LIZARD
import org.powbot.quests.quest.naturalhistory.NaturalHistoryConstants.ANSWER_MOLE
import org.powbot.quests.quest.naturalhistory.NaturalHistoryConstants.ANSWER_MONKEY
import org.powbot.quests.quest.naturalhistory.NaturalHistoryConstants.ANSWER_PENGUIN
import org.powbot.quests.quest.naturalhistory.NaturalHistoryConstants.ANSWER_SEA_SLUGS
import org.powbot.quests.quest.naturalhistory.NaturalHistoryConstants.ANSWER_SNAIL
import org.powbot.quests.quest.naturalhistory.NaturalHistoryConstants.ANSWER_SNAKE
import org.powbot.quests.quest.naturalhistory.NaturalHistoryConstants.ANSWER_TERRORBIRD
import org.powbot.quests.quest.naturalhistory.NaturalHistoryConstants.ANSWER_TORTOISE
import org.powbot.quests.quest.naturalhistory.NaturalHistoryConstants.ANSWER_WYVERN
import org.powbot.quests.quest.naturalhistory.NaturalHistoryConstants.CAMEL_SHIFT_COUNT
import org.powbot.quests.quest.naturalhistory.NaturalHistoryConstants.CONVERSATION_FINISH_ORLANDO_SMITH
import org.powbot.quests.quest.naturalhistory.NaturalHistoryConstants.CONVERSATION_ORLANDO_SMITH
import org.powbot.quests.quest.naturalhistory.NaturalHistoryConstants.DRAGON_SHIFT_COUNT
import org.powbot.quests.quest.naturalhistory.NaturalHistoryConstants.KALPHITE_QUEEN_SHIFT_COUNT
import org.powbot.quests.quest.naturalhistory.NaturalHistoryConstants.LEECH_SHIFT_COUNT
import org.powbot.quests.quest.naturalhistory.NaturalHistoryConstants.LIZARD_SHIFT_COUNT
import org.powbot.quests.quest.naturalhistory.NaturalHistoryConstants.MOLE_SHIFT_COUNT
import org.powbot.quests.quest.naturalhistory.NaturalHistoryConstants.MONKEY_SHIFT_COUNT
import org.powbot.quests.quest.naturalhistory.NaturalHistoryConstants.NAME_ORLANDO_SMITH
import org.powbot.quests.quest.naturalhistory.NaturalHistoryConstants.PENGUIN_SHIFT_COUNT
import org.powbot.quests.quest.naturalhistory.NaturalHistoryConstants.SEA_SLUGS_SHIFT_COUNT
import org.powbot.quests.quest.naturalhistory.NaturalHistoryConstants.SNAIL_SHIFT_COUNT
import org.powbot.quests.quest.naturalhistory.NaturalHistoryConstants.SNAKE_SHIFT_COUNT
import org.powbot.quests.quest.naturalhistory.NaturalHistoryConstants.TERRORBIRD_SHIFT_COUNT
import org.powbot.quests.quest.naturalhistory.NaturalHistoryConstants.TILE_CAMEL
import org.powbot.quests.quest.naturalhistory.NaturalHistoryConstants.TILE_DRAGON
import org.powbot.quests.quest.naturalhistory.NaturalHistoryConstants.TILE_KALPHITE_QUEEN
import org.powbot.quests.quest.naturalhistory.NaturalHistoryConstants.TILE_LEECH
import org.powbot.quests.quest.naturalhistory.NaturalHistoryConstants.TILE_LIZARD
import org.powbot.quests.quest.naturalhistory.NaturalHistoryConstants.TILE_MOLE
import org.powbot.quests.quest.naturalhistory.NaturalHistoryConstants.TILE_MONKEY
import org.powbot.quests.quest.naturalhistory.NaturalHistoryConstants.TILE_ORLANDO_SMITH
import org.powbot.quests.quest.naturalhistory.NaturalHistoryConstants.TILE_PENGUIN
import org.powbot.quests.quest.naturalhistory.NaturalHistoryConstants.TILE_SEA_SLUGS
import org.powbot.quests.quest.naturalhistory.NaturalHistoryConstants.TILE_SNAIL
import org.powbot.quests.quest.naturalhistory.NaturalHistoryConstants.TILE_SNAKE
import org.powbot.quests.quest.naturalhistory.NaturalHistoryConstants.TILE_TERRORBIRD
import org.powbot.quests.quest.naturalhistory.NaturalHistoryConstants.TILE_TORTOISE
import org.powbot.quests.quest.naturalhistory.NaturalHistoryConstants.TILE_WYVERN
import org.powbot.quests.quest.naturalhistory.NaturalHistoryConstants.TORTOISE_SHIFT_COUNT
import org.powbot.quests.quest.naturalhistory.NaturalHistoryConstants.WYVERN_SHIFT_COUNT
import com.open.quester.quest.naturalhistory.tasks.InteractWithPlaque

class NaturalHistory(information: QuestInformation) : BaseQuest(information) {

    private val startQuest = SimpleConversationStep(
        NAME_ORLANDO_SMITH,
        TILE_ORLANDO_SMITH,
        CONVERSATION_ORLANDO_SMITH,
        "Talking to Orlando Smith",
        information
    )

    private val finishQuest = SimpleConversationStep(
        NAME_ORLANDO_SMITH,
        TILE_ORLANDO_SMITH,
        CONVERSATION_FINISH_ORLANDO_SMITH,
        "Finishing Quest",
        information
    )

    override fun addRequirements(): QuestRequirements {
        return QuestRequirements(listOf(), listOf())
    }

    override fun getQuestStep(stepPosition: Int): BaseQuestStep? {
        if (stepPosition == 0) {
            return startQuest
        } else if (stepPosition == 1073741822) {
            return finishQuest
        } else if (stepPosition == -1073741826){
            information.complete = true
            CommonMethods.closeQuestComplete()
            return null
        }else {
            logger.info("Step $stepPosition")
            return answerQuestions().processStep()
        }
    }

    private fun answerQuestions(): QuestTaskList {
        return QuestTaskList(
            InteractWithPlaque(LIZARD_SHIFT_COUNT, TILE_LIZARD, ANSWER_LIZARD, information),
            InteractWithPlaque(TORTOISE_SHIFT_COUNT, TILE_TORTOISE, ANSWER_TORTOISE, information),
            InteractWithPlaque(DRAGON_SHIFT_COUNT, TILE_DRAGON, ANSWER_DRAGON, information),
            InteractWithPlaque(WYVERN_SHIFT_COUNT, TILE_WYVERN, ANSWER_WYVERN, information),
            InteractWithPlaque(SNAIL_SHIFT_COUNT, TILE_SNAIL, ANSWER_SNAIL, information),
            InteractWithPlaque(SNAKE_SHIFT_COUNT, TILE_SNAKE, ANSWER_SNAKE, information),
            InteractWithPlaque(SEA_SLUGS_SHIFT_COUNT, TILE_SEA_SLUGS, ANSWER_SEA_SLUGS, information),
            InteractWithPlaque(MONKEY_SHIFT_COUNT, TILE_MONKEY, ANSWER_MONKEY, information),
            InteractWithPlaque(KALPHITE_QUEEN_SHIFT_COUNT, TILE_KALPHITE_QUEEN, ANSWER_KALPHITE_QUEEN, information),
            InteractWithPlaque(TERRORBIRD_SHIFT_COUNT, TILE_TERRORBIRD, ANSWER_TERRORBIRD, information),
            InteractWithPlaque(PENGUIN_SHIFT_COUNT, TILE_PENGUIN, ANSWER_PENGUIN, information),
            InteractWithPlaque(MOLE_SHIFT_COUNT, TILE_MOLE, ANSWER_MOLE, information),
            InteractWithPlaque(CAMEL_SHIFT_COUNT, TILE_CAMEL, ANSWER_CAMEL, information),
            InteractWithPlaque(LEECH_SHIFT_COUNT, TILE_LEECH, ANSWER_LEECH, information),
        )
    }
}