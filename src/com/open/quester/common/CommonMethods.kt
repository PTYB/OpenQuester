package com.open.quester.common

import org.powbot.api.Condition
import org.powbot.api.Random
import org.powbot.api.rt4.Widget
import org.powbot.api.rt4.Widgets

object CommonMethods {

    fun isInCutscene(): Boolean {
        val i: Widget = Widgets.widget(601)
        for (c in i.components()) {
            if (c.textureId() == 900 && c.visible()) {
                return false
            }
        }
        return true
    }

    // TODO Change this stuff to static
    fun closeQuestComplete(): Boolean {
        val closeComponent = Widgets.component(153, 17)
        if (!closeComponent.valid()) {
            return true
        }
        if (closeComponent.textureId() == 537 && closeComponent.click()) {
            Condition.wait({
                !Widgets.component(537, 17).valid()
            }, Random.nextInt(2500, 3500), 100)
        }
        return false
    }
}