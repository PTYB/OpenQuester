package org.powbot.quests.quest.priestinperil.steps.temple

import org.powbot.api.Tile
import org.powbot.api.rt4.GameObject
import org.powbot.api.rt4.Inventory
import org.powbot.api.rt4.Objects
import org.powbot.api.rt4.Players
import org.powbot.common.helpers.InteractionsHelper
import org.powbot.quests.QuestConstants
import org.powbot.quests.common.steps.base.WalkToInteractiveStep
import org.powbot.quests.quest.priestinperil.PriestInPerilConstants.AREA_THIRD_FLOOR_OUTSIDE_JAIL
import org.powbot.quests.quest.priestinperil.PriestInPerilConstants.AREA_THIRD_INSIDE_JAIL
import org.powbot.quests.quest.priestinperil.PriestInPerilConstants.NAME_CELL_DOOR
import org.powbot.quests.quest.priestinperil.PriestInPerilConstants.NAME_MURKY_WATER

class OpenJailDoor : WalkToInteractiveStep(Tile.Nil, arrayOf()) {

    override fun shouldExecute(): Boolean {
        return AREA_THIRD_FLOOR_OUTSIDE_JAIL.contains(Players.local()) && Inventory.stream().name(NAME_MURKY_WATER)
            .count().toInt() == 1
    }

    override fun stepName(): String {
        return "Opening jail door"
    }

    override val gameObject: GameObject
        get() = Objects.stream().name(NAME_CELL_DOOR).nearest().first()


    override fun interactWithGameObject(gameObject: GameObject): Boolean {
        return InteractionsHelper.interactWithGameObjectUntilConditionMet(
            gameObject,
            QuestConstants.ACTION_OPEN,
            5000,
            { AREA_THIRD_INSIDE_JAIL.contains(Players.local()) }
        )
    }
}