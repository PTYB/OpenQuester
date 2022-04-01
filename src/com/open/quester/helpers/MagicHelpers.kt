package com.open.quester.helpers

import org.powbot.api.Condition
import org.powbot.api.rt4.*

object MagicHelpers {
    private const val WIDGET_AUTOCAST = 201
    private const val WIDGET_AUTOCAST_SPELLS_COMPONENT = 1
    private const val VARPBIT_AUTOCASTING = 43
    private const val VARPBIT_AUTOCASTING_SPELL = 108

    fun isAutoCasting(): Boolean {
        return Varpbits.varpbit(VARPBIT_AUTOCASTING) == 4
    }

    private fun getAutocastComponent(): Component {
        return Components.stream(WIDGET_AUTOCAST, WIDGET_AUTOCAST_SPELLS_COMPONENT).first()
    }

    fun isAutoCastOpen(): Boolean {
        return getAutocastComponent().visible()
    }

    fun openAutocastTab(): Boolean {
        if (isAutoCastOpen()) {
            return true
        }

        if (Game.tab(Game.Tab.ATTACK) && Widgets.component(593, 26).interact("Choose spell")) {
            return Condition.wait { isAutoCastOpen() }
        }

        return false
    }

    fun getAutoCastSpell(): AutoCastSpell {
        val varpbitValue = Varpbits.varpbit(VARPBIT_AUTOCASTING_SPELL)

        if (varpbitValue == 0) {
            return AutoCastSpell.NONE
        }

        return AutoCastSpell.values().firstOrNull { it.varpbitValue == varpbitValue } ?: AutoCastSpell.NONE
    }

    fun setAutoCast(spell: AutoCastSpell): Boolean {
        val component = Widgets.component(WIDGET_AUTOCAST, WIDGET_AUTOCAST_SPELLS_COMPONENT, spell.component)
        if (component == Component.Nil || spell == AutoCastSpell.NONE) {
            return false
        }
        if (component.click()) {
            return Condition.wait { getAutoCastSpell() == spell }
        }
        return false
    }
}

enum class AutoCastSpell(val spell: Magic.Spell?, val varpbitValue: Int, val component: Int) {
    NONE(null, 0, 0),
    AIR_STRIKE(Magic.Spell.WIND_STRIKE, 3, 1),
    WATER_STRIKE(Magic.Spell.WATER_STRIKE, 5, 2),
    EARTH_STRIKE(Magic.Spell.EARTH_STRIKE, 19, 3),
    FIRE_STRIKE(Magic.Spell.FIRE_STRIKE, 9, 4),
    WIND_BOLT(Magic.Spell.WIND_BOLT, 11, 5),
    WATER_BOLT(Magic.Spell.WATER_BOLT, 13, 6),
    EARTH_BOLT(Magic.Spell.EARTH_BOLT, 15, 7),
    FIRE_BOLT(Magic.Spell.FIRE_BOLT, 17, 8),
}