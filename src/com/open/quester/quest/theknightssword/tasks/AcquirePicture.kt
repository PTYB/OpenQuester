package com.open.quester.quest.theknightssword.tasks

import com.open.quester.common.base.BaseQuestStep
import com.open.quester.extensions.count
import com.open.quester.quest.theknightssword.TheKnightsSwordConstants.AREA_PICTURE_ROOM
import com.open.quester.quest.theknightssword.TheKnightsSwordConstants.ITEM_PORTRAIT
import com.open.quester.quest.theknightssword.TheKnightsSwordConstants.NAME_SIR_VYVIN
import com.open.quester.quest.theknightssword.TheKnightsSwordConstants.TILE_PICTURE_ROOM
import org.powbot.api.Condition
import org.powbot.api.rt4.*

class AcquirePicture : BaseQuestStep() {

    override fun shouldExecute(): Boolean {
        return Inventory.count(ITEM_PORTRAIT) == 0
    }

    override fun run() {
        if (Chat.chatting()) {
            Chat.completeChat()
        } else if (AREA_PICTURE_ROOM.contains(Players.local().tile())) {
            gettingPortrait()
        } else {
            Movement.builder(TILE_PICTURE_ROOM)
                .setRunMin(5)
                .setRunMax(50)
                .setWalkUntil { AREA_PICTURE_ROOM.contains(Players.local().tile()) }
                .move()
        }
    }

    private fun gettingPortrait() {
        val vyrin = Npcs.stream().name(NAME_SIR_VYVIN).first()

        // TODO World hopping
        if (AREA_PICTURE_ROOM.contains(vyrin)) {
            Condition.wait({ !AREA_PICTURE_ROOM.contains(vyrin) }, 1000, 5)
        } else {
            val cupboard = getCupboard()
            if (cupboard.inViewport(true)) {
                if (cupboard.id() == 2271) {
                    if (cupboard.interact("Open")) {
                        Condition.wait { getCupboard().id() == 2272 }
                    }
                } else {
                    if (cupboard.interact("Search")) {
                        Condition.wait { Chat.chatting() }
                    }
                }
            } else {
                Movement.step(cupboard)
            }
        }
    }

    private fun getCupboard(): GameObject {
        return Objects.stream().name("Cupboard").nearest().first()
    }

    override fun stepName(): String {
        return "Getting photo"
    }
}