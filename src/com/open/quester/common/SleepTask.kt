package com.open.quester.common

import com.open.quester.common.base.BaseQuestStep
import org.powbot.api.Condition
import org.powbot.api.Random

class SleepTask(val condition: () -> Boolean) : BaseQuestStep() {
    override fun shouldExecute(): Boolean {
        return condition.invoke()
    }

    override fun run() {
        Condition.sleep(Random.nextInt(300, 400))
    }

    override fun stepName(): String {
        return "Waiting"
    }
}