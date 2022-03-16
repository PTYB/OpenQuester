package com.open.quester.quest.sheepshearer.branches

import com.open.quester.common.QuestBranchStep
import com.open.quester.common.QuestTree
import com.open.quester.extensions.count
import com.open.quester.quest.sheepshearer.SheepShearer
import com.open.quester.quest.sheepshearer.SheepShearerConstants.ITEM_BALL_OF_WOOL
import com.open.quester.quest.sheepshearer.SheepShearerConstants.ITEM_SHEARS
import com.open.quester.quest.sheepshearer.SheepShearerConstants.ITEM_WOOL
import com.open.quester.quest.sheepshearer.leafs.GatherWool
import com.open.quester.quest.sheepshearer.leafs.SpinBallsOfWool
import com.open.quester.quest.sheepshearer.leafs.TalkToFred
import org.powbot.api.rt4.Inventory

class HasBallsOfWool(quest: SheepShearer) : QuestBranchStep<SheepShearer>(quest, "Has balls of wool?") {
    override val successElement: QuestTree<SheepShearer> = TalkToFred(quest)
    override val failedElement: QuestTree<SheepShearer> = HasComponents(quest)

    override fun validate(): Boolean {
        return Inventory.count(ITEM_BALL_OF_WOOL) >= 20
    }
}

class HasComponents(quest: SheepShearer) : QuestBranchStep<SheepShearer>(quest, "Has Components?") {
    override val successElement: QuestTree<SheepShearer> = SpinBallsOfWool(quest)
    override val failedElement: QuestTree<SheepShearer> = HasShears(quest)

    override fun validate(): Boolean {
        return Inventory.count(ITEM_BALL_OF_WOOL, ITEM_WOOL) >= 20
    }
}

class HasShears(quest: SheepShearer) : QuestBranchStep<SheepShearer>(quest, "Has Shears?") {
    override val successElement: QuestTree<SheepShearer> = GatherWool(quest)
    override val failedElement: QuestTree<SheepShearer> = TalkToFred(quest)

    override fun validate(): Boolean {
        return Inventory.count(ITEM_SHEARS) > 0
    }
}