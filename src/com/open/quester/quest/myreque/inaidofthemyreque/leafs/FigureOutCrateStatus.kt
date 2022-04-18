package com.open.quester.quest.myreque.inaidofthemyreque.leafs

import com.open.quester.common.base.BaseQuestStep
import com.open.quester.quest.myreque.inaidofthemyreque.InAidOfTheMyreque
import com.open.quester.quest.myreque.inaidofthemyreque.InAidOfTheMyrequeConstants
import com.open.quester.quest.myreque.inaidofthemyreque.InAidOfTheMyrequeConstants.ITEM_CRATE
import org.powbot.api.Condition
import org.powbot.api.rt4.*

class FigureOutCrateStatus : BaseQuestStep() {
    var status = InAidOfTheMyreque.CrateStatus.UNKNOWN

    override fun shouldExecute(): Boolean {
        return status == InAidOfTheMyreque.CrateStatus.UNKNOWN && Varpbits.varpbit(
            com.open.quester.Varpbits.IN_AID_OF_THE_MYREQUE.questVarbit,
            InAidOfTheMyrequeConstants.VARP_SHIFT_SECOND_ITEM,
            InAidOfTheMyrequeConstants.VARP_MASK_BRONZE_AXE
        ) != 10
    }

    override fun run() {
        if (Bank.opened()) {
            Bank.close()
        }

        if (Chat.chatting()) {
            val chatMessage = Chat.getChatMessage()
            if (chatMessage.contains("snails")) {
                status = InAidOfTheMyreque.CrateStatus.SNAIL
            } else if (chatMessage.contains("mackerel")) {
                status = InAidOfTheMyreque.CrateStatus.MACKEREL
            } else {
                if (Players.local().tile().matrix().interact("Walk here")) {
                    Condition.wait { Chat.chatting() }
                }
            }
            Chat.completeChat()
        } else {
            if (Inventory.stream().name(ITEM_CRATE).first().interact("Search")) {
                Condition.wait { Chat.chatting() }
            }
        }
    }

    override fun stepName(): String {
        return "Finding out goal"
    }
}