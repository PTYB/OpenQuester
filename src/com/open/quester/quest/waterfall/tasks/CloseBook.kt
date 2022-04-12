package com.open.quester.quest.waterfall.tasks

import com.open.quester.common.base.BaseQuestStep
import org.powbot.api.Condition
import org.powbot.api.rt4.Component
import org.powbot.api.rt4.Components
import org.powbot.api.rt4.Widgets
import com.open.quester.quest.waterfall.WaterfallConstants

class CloseBook : BaseQuestStep() {

    var component: Component = Component.Nil
    override fun shouldExecute(): Boolean {
        component = Widgets.component(WaterfallConstants.WIDGET_BOOK, WaterfallConstants.COMPONENT_CLOSE)
        return component != Component.Nil
    }

    override fun run() {
        if (component.click()) {
            Condition.wait { Components.stream(
                WaterfallConstants.WIDGET_BOOK,
                WaterfallConstants.COMPONENT_CLOSE
            ).first() == Component.Nil }
        }
    }

    override fun stepName(): String {
        return "Close book"
    }
}