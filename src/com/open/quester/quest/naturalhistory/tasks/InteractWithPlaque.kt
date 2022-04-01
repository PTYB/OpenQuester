package com.open.quester.quest.naturalhistory.tasks

import com.open.quester.common.base.SimpleObjectStep
import com.open.quester.extensions.Conditions
import com.open.quester.models.QuestInformation
import org.powbot.api.Condition
import org.powbot.api.Random
import org.powbot.api.Tile
import org.powbot.api.rt4.*
import org.powbot.quests.quest.naturalhistory.NaturalHistoryConstants.ACTION_PLAQUE
import org.powbot.quests.quest.naturalhistory.NaturalHistoryConstants.COMPONENT_QUESTION
import org.powbot.quests.quest.naturalhistory.NaturalHistoryConstants.NAME_PLAQUE
import org.powbot.quests.quest.naturalhistory.NaturalHistoryConstants.WIDGET_ANSWER
import org.powbot.quests.quest.naturalhistory.Question

class InteractWithPlaque(
    shiftCount: Int,
    buttonTile: Tile,
    private val answers: Array<Question>,
    information: QuestInformation
) : SimpleObjectStep(
    buttonTile,
    arrayOf(),
    { Objects.stream().name(NAME_PLAQUE).within(buttonTile, 2).first() },
    { go: GameObject -> go.interact(ACTION_PLAQUE) },
    { Conditions.waitUntilComponentAppears(WIDGET_ANSWER, COMPONENT_QUESTION).call() },
    "Interacting with plaque",
    information,
    { Varpbits.varpbit(1014, shiftCount, 0x3) != 3 },
) {

    override fun run() {
        val question = questionComponent()
        if (questionComponent() != Component.Nil && question.text().isNotEmpty()) {
            val question = question.text()
            val answer = answers.firstOrNull { it.question.equals(question, true) }
            if (answer == null) {
                logger.info("Unable to find answer for question $question")
                return
            }
            logger.info("Answering question: $question with $answer")
            val answerComponent =
                Components.stream(WIDGET_ANSWER).filtered { it.text() != question }.text(answer.answer).first()
            Condition.sleep(Random.nextInt(200, 450)) // Otherwise way too fast
            if (answerComponent != Component.Nil && answerComponent.click()) {
                var result = Condition.wait {
                    val component = questionComponent()
                    component == Component.Nil || component.text() != question
                }
                logger.info("Answer result is $result")
            }
        } else {
            super.run()
        }
    }

    private fun questionComponent(): Component {
        return Widgets.component(WIDGET_ANSWER, COMPONENT_QUESTION)
    }
}