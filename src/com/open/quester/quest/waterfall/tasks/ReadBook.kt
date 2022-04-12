package com.open.quester.quest.waterfall.tasks

import com.open.quester.common.base.BaseQuestStep
import com.open.quester.extensions.Conditions
import org.powbot.api.Condition
import org.powbot.api.rt4.*
import com.open.quester.quest.waterfall.WaterfallConstants.COMPONENT_CLOSE
import com.open.quester.quest.waterfall.WaterfallConstants.ITEM_BOOK
import com.open.quester.quest.waterfall.WaterfallConstants.WIDGET_BOOK

class ReadBook : BaseQuestStep() {
    var item: Item = Item.Nil
    override fun shouldExecute(): Boolean {
        item = Inventory.stream().name(ITEM_BOOK).first()
        return item != Item.Nil
    }

    override fun run() {
        item.interact("Read")
        if (Condition.wait(Conditions.Companion.waitUntilComponentAppears(WIDGET_BOOK, COMPONENT_CLOSE))) {
            if (Widgets.component(WIDGET_BOOK, COMPONENT_CLOSE).click()) {
                Condition.wait { Widgets.component(WIDGET_BOOK, COMPONENT_CLOSE) == Component.Nil }
            }
        }

    }

    override fun stepName(): String {
        return "Reading book"
    }
}