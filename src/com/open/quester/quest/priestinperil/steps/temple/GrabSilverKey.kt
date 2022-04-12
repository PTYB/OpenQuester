package com.open.quester.quest.priestinperil.steps.temple

import com.open.quester.common.base.WalkToInteractiveStep
import com.open.quester.extensions.count
import com.open.quester.helpers.InteractionsHelper
import com.open.quester.models.QuestInformation
import org.powbot.api.Condition
import org.powbot.api.Tile
import org.powbot.api.rt4.*
import org.powbot.mobile.rscache.loader.ItemLoader
import org.powbot.quests.quest.priestinperil.PriestInPerilConstants.ACTION_STUDY
import org.powbot.quests.quest.priestinperil.PriestInPerilConstants.IDS_MONUMENT
import org.powbot.quests.quest.priestinperil.PriestInPerilConstants.ITEM_COMPONENT
import org.powbot.quests.quest.priestinperil.PriestInPerilConstants.ITEM_GOLDEN_KEY
import org.powbot.quests.quest.priestinperil.PriestInPerilConstants.ITEM_IRON_KEY
import org.powbot.quests.quest.priestinperil.PriestInPerilConstants.KEY_WIDGET
import org.powbot.quests.quest.priestinperil.PriestInPerilConstants.TILE_UNDERGROUND

class GrabSilverKey(information: QuestInformation) : WalkToInteractiveStep<GameObject>(TILE_UNDERGROUND, arrayOf(), questInformation = information) {

    private val checkedMonuments = arrayOf(false, false, false, false, false, false, false)
    private var currentMonument = -1
    private var keyFound = false

    override fun shouldExecute(): Boolean {
        return Inventory.stream().name(ITEM_GOLDEN_KEY).count()
            .toInt() == 1
    }

    override fun run() {
        val keyItem = getItemWidget()
        if (keyItem != Component.Nil) {
            val item = ItemLoader.load(keyItem.itemId())
            if (currentMonument == -1) {
                if (getCloseWidget().click()) {
                    currentMonument = -1
                    return
                }
            }
            if (item?.name?.contains("Golden")!!) {
                logger.info("$currentMonument set to true")
                checkedMonuments[currentMonument] = true
                getCloseWidget().click()
            } else if (item.name.contains("Iron")) {
                logger.info("Found key")
                checkedMonuments.forEachIndexed { index, _ ->
                    if (index != currentMonument) {
                        checkedMonuments[index] = true
                    }
                }
                keyFound = true
                getCloseWidget().click()
            }
        } else {
            if (keyFound) {
                if (InteractionsHelper.useItemOnInteractive(ITEM_GOLDEN_KEY, getInteractive())) {
                    Condition.wait { Inventory.count(ITEM_IRON_KEY) > 0 }
                }
            } else {
                super.run()
            }
        }
    }

    private fun getItemWidget(): Component {
        return Widgets.component(KEY_WIDGET, ITEM_COMPONENT)
    }

    private fun getCloseWidget(): Component {
        // TODO Constant
        return Widgets.component(KEY_WIDGET, 1, 3)
    }

    private fun getGameObject(): IntArray {
        val list = mutableListOf<Int>()

        checkedMonuments.forEachIndexed { idx, bool ->
            if (!bool) {
                list.add(IDS_MONUMENT[idx])
            }
        }
        return list.toIntArray()
    }

    override fun stepName(): String {
        return "Getting silver key"
    }

    override fun getInteractive(): GameObject {
        return Objects.stream().id(*getGameObject()).nearest().first()
    }

    override fun interact(interactive: GameObject): Boolean {
        currentMonument = IDS_MONUMENT.indexOf(interactive.id())
        return interactive.interact(ACTION_STUDY) && Condition.wait  { getItemWidget().visible() }
    }

    override fun getInteractiveTile(interactive: GameObject): Tile {
        return interactive.tile
    }
}