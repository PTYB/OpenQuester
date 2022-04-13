package com.open.quester.quest.insearchofthemyreeque.leafs

import com.open.quester.common.base.SimpleNpcStep
import com.open.quester.extensions.nearestNpc
import com.open.quester.models.QuestInformation
import com.open.quester.quest.insearchofthemyreeque.InSearchOfTheMyrequeConstants
import com.open.quester.quest.insearchofthemyreeque.InSearchOfTheMyrequeConstants.NAME_CURPILE
import org.powbot.api.Condition
import org.powbot.api.rt4.*

class HandleQuiz(information: QuestInformation) : SimpleNpcStep(
    InSearchOfTheMyrequeConstants.TILE_CURPILE,
    null,
    { Npcs.nearestNpc(NAME_CURPILE) },
    { npc: Npc -> npc.interact("Talk-to") },
    { Chat.chatting() },
    "Talking to Curpile",
    questInformation = information,
    shouldExecute = { InSearchOfTheMyrequeConstants.AREA_NORTH_OF_BRIDGE.contains(Players.local()) }
) {

    override fun run() {
        if (Chat.chatting()) {

            val chatMessage = Chat.getChatMessage()
            val chatSender = Chat.getChattingName()

            val questionTitle = Widgets.component(219, 1, 0)
            if (questionTitle.visible()) {
                val lowerCaseText = questionTitle.text().lowercase()
                logger.info("Text: $lowerCaseText")
                when {
                    lowerCaseText.contains("female") -> Chat.continueChat("Sani Piliu")
                    lowerCaseText.contains("youngest") -> Chat.continueChat("Ivan Strom")
                    lowerCaseText.contains("leader") -> Chat.continueChat("Veliaf Hurtz")
                    lowerCaseText.contains("boatman") -> Chat.continueChat("Cyreg Paddlehorn")
                    lowerCaseText.contains("vampyre") -> Chat.continueChat("Drakan")
                    lowerCaseText.contains("scholar") -> Chat.continueChat("Polmafi Ferdygris")
                    else -> Chat.continueChat("I've come to help the Myreque. I've brought weapons.")
                }
            } else if (Chat.canContinue()) {
                Chat.clickContinue()
            } else {
                logger.info("Else")
            }
            Condition.wait { Chat.chatting() && (Chat.getChatMessage() != chatMessage || Chat.getChattingName() != chatSender) }
        } else {
            super.run()
        }
    }
}