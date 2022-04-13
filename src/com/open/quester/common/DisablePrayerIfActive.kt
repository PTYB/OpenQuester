package com.open.quester.common

import com.open.quester.common.base.BaseQuestStep
import org.powbot.api.rt4.Prayer
import java.util.concurrent.Callable

class DisablePrayerIfActive(val prayer: Prayer.Effect, val condition: Callable<Boolean>? = null) : BaseQuestStep() {
    override fun shouldExecute(): Boolean {
        return Prayer.prayerActive(prayer) && (condition == null || condition.call())
    }

    override fun run() {
        Prayer.prayer(prayer, false)
    }

    override fun stepName(): String {
        return "Disabling prayer"
    }
}