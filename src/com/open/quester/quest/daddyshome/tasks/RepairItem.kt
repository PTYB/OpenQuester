package com.open.quester.quest.daddyshome.tasks

import com.open.quester.common.base.SimpleObjectStep
import com.open.quester.models.QuestInformation
import com.open.quester.quest.daddyshome.DaddysHomeConstants.HOME_ITEM_MASK
import com.open.quester.quest.daddyshome.DaddysHomeConstants.WIDGET_CONSTRUCTION
import org.powbot.api.Condition
import org.powbot.api.Tile
import org.powbot.api.rt4.*
import org.powbot.mobile.rscache.loader.ItemLoader

class RepairItem(
    tile: Tile,
    hotspotId: Int,
    val itemName: String,
    val shift: Int,
    val information: QuestInformation,
    val roomTile: Tile
) :
    SimpleObjectStep(
        tile,
        null,
        { Objects.stream().id(hotspotId).nearest(roomTile).first() },
        { go -> go.interact { it.action == "Build" } },
        { Widgets.widget(WIDGET_CONSTRUCTION).valid() },
        "Construction $itemName",
        information,
        {
            Varpbits.varpbit(information.questVarbits.questVarbit, shift, HOME_ITEM_MASK) == 2
        }
    ) {

    override fun run() {
        if (constructionMenuisOpen()) {
            val itemWidget = Components.stream(WIDGET_CONSTRUCTION)
                .filtered { ItemLoader.load(it.itemId())?.name == itemName }.first()

            if (itemWidget.valid()) {
                if (itemWidget.interact("Build")) {
                    Condition.wait {
                        Varpbits.varpbit(
                            information.questVarbits.questVarbit,
                            shift,
                            HOME_ITEM_MASK
                        ) == 3
                    }
                }
            } else {
                if (Widgets.component(WIDGET_CONSTRUCTION, 1, 3).interact("Close")) {
                    Condition.wait { !constructionMenuisOpen() }
                }
            }

        } else {
            super.run()
        }
    }

    private fun constructionMenuisOpen(): Boolean {
        return Widgets.widget(WIDGET_CONSTRUCTION).valid()
    }
}