package com.open.quester.quest.treegnomevillage.tasks

import com.open.quester.common.base.SimpleObjectStep
import com.open.quester.extensions.Conditions
import com.open.quester.extensions.nearestGameObject
import com.open.quester.models.QuestInformation
import org.powbot.api.Condition
import org.powbot.api.Point
import org.powbot.api.rt4.Chat
import org.powbot.api.rt4.GameObject
import org.powbot.api.rt4.Objects
import org.powbot.api.rt4.Varpbits
import org.powbot.quests.quest.treegnomevillage.TreeGnomeVillageConstants
import org.powbot.quests.quest.treegnomevillage.TreeGnomeVillageConstants.VARP_TRACKER
import org.powbot.quests.quest.treegnomevillage.TreeGnomeVillageConstants.VARP_TRACKER_GNOME_1_SHIFT
import org.powbot.quests.quest.treegnomevillage.TreeGnomeVillageConstants.VARP_TRACKER_GNOME_2_SHIFT
import org.powbot.quests.quest.treegnomevillage.TreeGnomeVillageConstants.VARP_TRACKER_GNOME_3_SHIFT

class FireBallista(information: QuestInformation) : SimpleObjectStep(
    TreeGnomeVillageConstants.TILE_BALLISTA,
    arrayOf(),
    { Objects.nearestGameObject(TreeGnomeVillageConstants.NAME_BALLISTA) },
    { go: GameObject ->
        go.interact("Fire")
    },
    { Chat.chatting() },
    "Firing Ballista",
    information,
    {
        Varpbits.varpbit(VARP_TRACKER, VARP_TRACKER_GNOME_1_SHIFT, 0x1) == 1 &&
                Varpbits.varpbit(VARP_TRACKER, VARP_TRACKER_GNOME_2_SHIFT, 0x1) == 1 &&
                Varpbits.varpbit(VARP_TRACKER, VARP_TRACKER_GNOME_3_SHIFT, 0x1) == 1
    }
) {
    init {
        pointVariance = Point(0, -2)
    }

    override fun run() {
        val coordinate = Varpbits.varpbit(279, 15, 0x3) + 1
        val conversation = arrayOf(
            "000${coordinate}"
        )
        if (Chat.chatting()) {
            logger.info("Completing conversation/cutscene")
            Chat.completeChat(*conversation)
            val secondConversation = Condition.wait(Conditions.waitUntilChatting(), 100, 20)
            if (secondConversation) {
                Chat.completeChat()
            }
            logger.info("First conversation finished")
        } else {
            super.run()
        }
    }
}