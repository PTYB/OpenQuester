package com.open.quester.quest.myreque.darknessofhallowvale.tasks

import com.open.quester.common.CommonMethods
import com.open.quester.common.base.BaseQuestStep
import com.open.quester.helpers.CombatHelper
import com.open.quester.models.QuestInformation
import com.open.quester.quest.myreque.darknessofhallowvale.DarknessOfHallowvaleConstants.NAME_VANSTROM_KALUSE
import org.powbot.api.Condition
import org.powbot.api.rt4.Chat
import org.powbot.api.rt4.Constants
import org.powbot.api.rt4.Prayer
import org.powbot.api.rt4.Skills

class TankVanstrom(val information: QuestInformation) : BaseQuestStep() {
    override fun shouldExecute(): Boolean {
        return true
    }

    override fun run() {
        if (Chat.chatting()) {
            Chat.continueChat()
        } else if (CommonMethods.isInCutscene()) {
            Condition.wait({ Chat.chatting() || !CommonMethods.isInCutscene() }, 150, 25)
        } else {

            if (Skills.realLevel(Constants.SKILLS_PRAYER) >= 43 && Skills.level(Constants.SKILLS_PRAYER) > 0 &&
                !Prayer.prayerActive(Prayer.Effect.PROTECT_FROM_MELEE)
            ) {
                Prayer.prayer(Prayer.Effect.PROTECT_FROM_MELEE, true)
            }
            if (CombatHelper.shouldEat(*information.foodName)) {
                CombatHelper.eatFood(*information.foodName)
            }
        }
    }

    override fun stepName(): String {
        return "Tanking $NAME_VANSTROM_KALUSE"
    }
}