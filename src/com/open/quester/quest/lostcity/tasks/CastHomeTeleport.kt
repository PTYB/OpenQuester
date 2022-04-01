package com.open.quester.quest.lostcity.tasks

import com.open.quester.common.base.BaseQuestStep
import com.open.quester.extensions.Conditions
import com.open.quester.helpers.MessageListener
import com.open.quester.helpers.SystemMessageManager
import org.powbot.api.Condition
import org.powbot.api.rt4.Magic
import org.powbot.api.rt4.Players
import com.open.quester.quest.lostcity.LostCityConstants.AREA_END_ROOM
import org.powbot.api.Random

class CastHomeTeleport : BaseQuestStep() {

    private val minutesCast = "You need to wait another "

    override fun shouldExecute(): Boolean {
        return AREA_END_ROOM.contains(Players.local())
    }

    override fun run() {
        val message = MessageListener(1, arrayOf(minutesCast))
        SystemMessageManager.addMessageToListen(message)
        if (Magic.Spell.HOME_TELEPORT.cast("Cast")) {
            Condition.wait(Conditions.waitUntilNonIdleAnimation())
            if (!Condition.wait(Conditions.waitUntilIdleAnimation(), 1000, 20) && message.count == 0)
            {
                Condition.sleep(Random.nextInt(55000, 85000))
                return
            }
            Condition.wait({ AREA_END_ROOM.contains(Players.local()) }, 1000, 20)
        }
    }

    override fun stepName(): String {
        return "Casting home teleport"
    }
}