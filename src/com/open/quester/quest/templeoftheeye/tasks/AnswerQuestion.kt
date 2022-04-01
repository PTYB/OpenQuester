package com.open.quester.quest.templeoftheeye.tasks

import com.open.quester.common.base.BaseQuestStep
import org.powbot.api.Condition
import org.powbot.api.Random
import org.powbot.api.rt4.Chat

class AnswerQuestion : BaseQuestStep() {
    override fun shouldExecute(): Boolean {
        return Chat.pendingInput()
    }

    override fun run() {
        if (Chat.sendInput(11)) {
            Condition.wait { !Chat.chatting() }
            Condition.wait { Chat.chatting() }
        }
    }

    override fun stepName(): String {
        return "Entering answer"
    }
}