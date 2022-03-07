package com.open.quester.models

import com.open.quester.Varpbits
import com.open.quester.common.base.BaseQuestStep
import org.powbot.api.rt4.Item
import org.powbot.api.rt4.Magic

class QuestInformation(
    val questVarbits: Varpbits,
    val foodName: Array<String>,
    val weaponName: Item? = null,
    var spell: Magic.Spell? = null
) {

    var lowerHpToEatAt: Int = 40
    var higherHpToEatAt: Int = 50

    var currentQuest: BaseQuestStep? = null
    var currentQuestStatus: String? = null
    var complete: Boolean = false

}