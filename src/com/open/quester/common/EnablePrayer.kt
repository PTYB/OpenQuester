package com.open.quester.common

import com.open.quester.common.base.BaseQuestStep
import org.powbot.api.rt4.Constants
import org.powbot.api.rt4.Prayer
import org.powbot.api.rt4.Skills
import java.util.concurrent.Callable

class EnablePrayer(val prayer: Prayer.Effect, val condition: Callable<Boolean>? = null) : BaseQuestStep() {
    override fun shouldExecute(): Boolean {
        return !Prayer.prayerActive(prayer) && prayer.level() <=
                Skills.realLevel(Constants.SKILLS_PRAYER) &&
                Skills.level(Constants.SKILLS_PRAYER) > 0 && (condition == null || condition.call())
    }

    override fun run() {
        Prayer.prayer(prayer, true)
    }

    override fun stepName(): String {
        return "Enabling prayer ${prayer.name}"
    }
}