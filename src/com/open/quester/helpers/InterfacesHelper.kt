package com.open.quester.helpers

import org.powbot.api.rt4.Component
import org.powbot.api.rt4.Components
import org.powbot.api.rt4.Widgets
import org.powbot.mobile.rscache.loader.ItemLoader

object InterfacesHelper {
    const val INTERFACE_COMBINE_ROOT = 270

    fun combineInterfaceOpen(): Boolean {
        val component = Widgets.component(INTERFACE_COMBINE_ROOT, 0)
        return component.visible()
    }

    fun createItemInterface(itemName: String): Component {
        return Components.stream().widget(INTERFACE_COMBINE_ROOT)
            .filtered {
                ItemLoader.load(it.itemId())?.name == itemName
            }.first()
    }
}