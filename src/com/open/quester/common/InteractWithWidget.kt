package com.open.quester.common

import com.open.quester.common.base.BaseQuestStep
import org.powbot.api.Condition
import org.powbot.api.rt4.Widgets
import java.util.concurrent.Callable

class InteractWithWidget(val widgetId: Int,val componentId: Int, val action: String,val waitCondition: Callable<Boolean>? = null) : BaseQuestStep() {
    override fun shouldExecute(): Boolean {
        return Widgets.component(widgetId,componentId).visible()
    }

    override fun run() {
        val component = Widgets.component(widgetId,componentId)
        if (component.visible() && component.interact(action)) {
            Condition.wait { !component.visible() && (waitCondition == null || waitCondition.call())}
        }
    }

    override fun stepName(): String {
        return "Interacting with $widgetId, $componentId"
    }
}