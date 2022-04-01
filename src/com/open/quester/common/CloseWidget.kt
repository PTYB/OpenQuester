package com.open.quester.common

import com.open.quester.common.base.BaseQuestStep
import org.powbot.api.Condition
import org.powbot.api.rt4.Component
import org.powbot.api.rt4.Components
import org.powbot.api.rt4.Widgets
import java.util.concurrent.Callable

class CloseWidget(val widgetId: Int, val componentId: Int, val callable: Callable<Boolean>? = null) : BaseQuestStep() {
    override fun shouldExecute(): Boolean {
        if (callable != null && !callable.call()) {
            return false
        }

        return Widgets.component(widgetId, componentId).visible()
    }

    override fun run() {
        val component = Widgets.component(widgetId, componentId)
        if (component.click()) {
            val closed = Condition.wait { !Widgets.component(widgetId, componentId).visible() }

            // Fallback incase it can't close
            if (!closed && !component.actions().contains("Close")) {
                val closeComponent = Components.stream(widgetId).action("Close").first()
                if (closeComponent != Component.Nil && closeComponent.click()) {
                    Condition.wait { !Widgets.component(widgetId, componentId).visible() }
                }
            }
        }
    }

    override fun stepName(): String {
        return "Closing widget $widgetId,$componentId"
    }
}