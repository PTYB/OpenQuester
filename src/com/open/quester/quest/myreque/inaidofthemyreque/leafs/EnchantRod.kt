package com.open.quester.quest.myreque.inaidofthemyreque.leafs

import com.open.quester.common.base.BaseQuestStep
import com.open.quester.extensions.count
import com.open.quester.quest.myreque.inaidofthemyreque.InAidOfTheMyrequeConstants.ITEM_SILVERTHRILL_ROD
import org.powbot.api.Condition
import org.powbot.api.rt4.Inventory
import org.powbot.api.rt4.Magic

class EnchantRod : BaseQuestStep() {
    override fun shouldExecute(): Boolean {
        return Inventory.count(7368) == 0 // Unehcanted
    }

    override fun run() {
        val item =Inventory.stream().name(ITEM_SILVERTHRILL_ROD).first()
        if (Magic.Spell.ENCHANT_LEVEL_1_JEWELLERY.cast()) {
            if (item.click()) {
                Condition.wait { Inventory.count(7368) == 1 }
            }
        }
    }

    override fun stepName(): String {
        return "Enchanting rod"
    }
}