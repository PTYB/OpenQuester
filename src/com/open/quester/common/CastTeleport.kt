package com.open.quester.common

import com.open.quester.common.base.BaseQuestStep
import org.powbot.api.Condition
import org.powbot.api.rt4.Magic
import org.powbot.api.rt4.Players

class CastTeleport(val spell: Magic.Spell, val condition: () -> Boolean, val action: String = "Cast") :
    BaseQuestStep() {
    override fun shouldExecute(): Boolean {
        return condition.invoke()
    }

    override fun run() {
        if (spell.cast(action)) {
            if (Condition.wait { Players.local().animation() != -1 }) {
                Condition.wait { Players.local().animation() == -1 }
            }
        }
    }

    override fun stepName(): String {
        return "Casting spell ${spell.name}"
    }
}