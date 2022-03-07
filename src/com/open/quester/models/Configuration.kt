package com.open.quester.models

import org.powbot.api.rt4.Skills

class QuestRequirements constructor(
    val itemRequirements: List<ItemRequirementCondition>,
    val skillRequirements: List<SkillRequirement>,
)

class SkillRequirement(val skillId: Int, var skillLevel: Int)

class ItemRequirementCondition(vararg itemRequirements: ItemRequirement) {

    val itemRequirements = itemRequirements.toList()
    var chosenRequirement: ItemRequirement? = null

    init {
        if (itemRequirements.size == 1) {
            chosenRequirement = itemRequirements.first()
        }
    }

    constructor(itemName: String, untradable: Boolean, itemCount: Int) : this(
        ItemRequirement(
            itemName,
            untradable,
            itemCount
        )
    )

    companion object {
        fun emptySlots(count: Int): ItemRequirementCondition {
            return ItemRequirementCondition(ItemRequirement("", true, count))
        }
    }

}

class ItemRequirement(
    val name: String,
    val isUntradable: Boolean,
    var countRequired: Int = 1,
    val skillRequirements: Array<SkillRequirement> = arrayOf(),
    val stackable: Boolean = false,
) {
    var hasRequirement = false
    val hasItemRequirements: Boolean
        get() = skillRequirements.isEmpty() || skillRequirements.all { Skills.realLevel(it.skillId) >= it.skillLevel }
}
