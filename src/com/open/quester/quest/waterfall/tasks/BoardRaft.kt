package com.open.quester.quest.waterfall.tasks

import com.open.quester.common.base.SimpleObjectStep
import com.open.quester.extensions.nearestGameObject
import com.open.quester.models.QuestInformation
import org.powbot.api.rt4.Objects
import org.powbot.api.rt4.Players
import com.open.quester.quest.waterfall.WaterfallConstants.AREA_HUDON_ISLAND
import com.open.quester.quest.waterfall.WaterfallConstants.NAME_RAFT
import com.open.quester.quest.waterfall.WaterfallConstants.TILE_RAFT
import org.powbot.api.rt4.GameObject

class BoardRaft(shouldRun: () -> Boolean, information: QuestInformation) : SimpleObjectStep(
    TILE_RAFT,
    arrayOf(),
    { Objects.nearestGameObject(NAME_RAFT) },
    { go: GameObject -> go.interact("Board") },// TODO Check either instance or non instance area,
    { AREA_HUDON_ISLAND.contains(Players.local()) },
    "Boarding Raft",
    information,
    shouldRun,
)