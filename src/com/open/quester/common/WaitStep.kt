package com.open.quester.common

import com.open.quester.common.base.BaseQuestStep
import org.powbot.api.Condition
import org.powbot.api.Random

class WaitStep(val condition : () -> Boolean?, val stepText: String) : BaseQuestStep() {
    override fun shouldExecute(): Boolean {
        return condition.invoke() ?: true
    }

    override fun run() {
        Condition.sleep(Random.nextInt(100,150))
    }

    override fun stepName(): String {
        return stepText
    }
}