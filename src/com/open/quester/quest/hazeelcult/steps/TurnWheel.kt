package com.open.quester.quest.hazeelcult.steps

import com.open.quester.common.base.SimpleObjectStep
import com.open.quester.models.QuestInformation
import org.powbot.api.Tile
import org.powbot.api.rt4.*
import org.powbot.quests.quest.hazeelcult.HazeelCultConstants.AREA_ALOMONE

class TurnWheel(private val expectedRotation: Rotation, private val wheelTile: Tile, information: QuestInformation) :
    SimpleObjectStep(
        wheelTile, arrayOf(),
        { Objects.stream().name("Sewer valve").within(wheelTile, 5).first() },
        { go: GameObject -> go.interact("Turn-${expectedRotation.toString().lowercase()}") },
        { Chat.chatting() },
        "Turning wheel $expectedRotation",
        information
    ) {

    var currentRotation = Rotation.Unknown

    override fun shouldExecute(): Boolean {
        return currentRotation != expectedRotation && !AREA_ALOMONE.contains(Players.local())
    }

    override fun run() {
        if (Players.local().tile().distanceTo(wheelTile) < 5 &&
            chatMessage().contains("metal valve to the ${expectedRotation.toString().toLowerCase()}")
        ) {
            currentRotation = expectedRotation
        } else {
            super.run()
        }
    }

    private fun chatMessage(): String {
        return Widgets.component(229, 1).text().lowercase()
    }

    override fun stepName(): String {
        return "Turning wheel $expectedRotation"
    }
}

enum class Rotation {
    Unknown, Left, Right
}