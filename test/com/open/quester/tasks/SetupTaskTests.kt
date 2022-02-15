package com.open.quester.tasks

import com.open.quester.models.*
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class SetupTaskTests {
    private val mockItemRequirement = ItemRequirement("TEST", false)
    private val mockItemRequirementCondition = ItemRequirementCondition(mockItemRequirement)

    private val mockUntradableItemRequirement = ItemRequirement("TEST", true)
    private val mockUntradableItemRequirementCondition = ItemRequirementCondition(mockUntradableItemRequirement)

    @Test
    fun hasNoRequirements_ReturnsComplete() {
        // Arrange
        val requirement = QuestRequirements(listOf(), listOf())
        val subject = createSubject(requirement)

        // Act
        assertEquals(SetupResult.COMPLETE, subject.complete())
    }

    @Test
    fun belowSkillRequirement_ReturnsFalse() {
        // Arrange
        val requirement = QuestRequirements(listOf(), listOf(SkillRequirement(1, 50)))
        val subject = createSubject(requirement)

        // Act
        assertEquals(SetupResult.FAILURE, subject.complete())
        assert(!subject.testCheckSkillRequirements())
    }

    @Test
    fun aboveSkillRequirement_ReturnsTrue() {
        // Arrange
        val requirement = QuestRequirements(listOf(), listOf(SkillRequirement(1, 50)))
        val subject = createSubject(requirement)
        subject.mockSkillids = mapOf(Pair(1, 51))

        // Act
        assertEquals(SetupResult.COMPLETE, subject.complete())
        assert(subject.testCheckSkillRequirements())
    }

    @Test
    fun equalSkillRequirement_ReturnsFalse() {
        // Arrange
        val requirement = QuestRequirements(listOf(), listOf(SkillRequirement(1, 50)))
        val subject = createSubject(requirement)
        subject.mockSkillids = mapOf(Pair(1, 50))

        // Act
        assertEquals(SetupResult.COMPLETE, subject.complete())
        assert(subject.testCheckSkillRequirements())
    }

    @Test
    fun hasSkillRequirementWithAnotherRequirements_ReturnsUnknown() {
        // Arrange
        val requirement = QuestRequirements(listOf(mockItemRequirementCondition), listOf(SkillRequirement(1, 50)))
        val subject = createSubject(requirement)
        subject.mockSkillids = mapOf(Pair(1, 50))
        subject.mockBankItems = mapOf(Pair(mockItemRequirement.name, 1))

        // Act
        assert(subject.testCheckSkillRequirements())
        assertEquals(SetupResult.UNKNOWN, subject.complete())
    }

    @Test
    fun hasAllItemsInInventory_ReturnsComplete() {
        // Arrange
        val requirement = QuestRequirements(listOf(mockItemRequirementCondition), listOf())
        val subject = createSubject(requirement)
        subject.mockInventoryItems = mapOf(Pair(mockItemRequirement.name, 1))

        // Act
        assertEquals(SetupResult.COMPLETE, subject.complete())
    }

    @Test
    fun hasMissingItemInInventory_ReturnsUnknown() {
        // Arrange
        val requirement = QuestRequirements(listOf(mockItemRequirementCondition), listOf())
        val subject = createSubject(requirement)

        // Act
        assertEquals(SetupResult.UNKNOWN, subject.complete())
    }

    @Test
    fun hasMissingItemInBank_ReturnsIncomplete() {
        // Arrange
        val requirement = QuestRequirements(listOf(mockItemRequirementCondition), listOf())
        val subject = createSubject(requirement)

        // Act
        assertEquals(SetupResult.UNKNOWN, subject.complete())
        assertEquals(SetupResult.UNKNOWN, subject.complete())
        assertEquals(SetupResult.INCOMPLETE, subject.complete())
    }

    @Test
    fun hasMissingUntradableItemInBank_ReturnsIncomplete() {
        // Arrange
        val requirement = QuestRequirements(listOf(mockUntradableItemRequirementCondition), listOf())
        val subject = createSubject(requirement)

        // Act
        assertEquals(SetupResult.UNKNOWN, subject.complete())
        assertEquals(SetupResult.UNKNOWN, subject.complete())
        assertEquals(SetupResult.FAILURE, subject.complete())
    }

    @Test
    fun hasItemInBank_ReturnsComplete() {
        // Arrange
        val requirement = QuestRequirements(listOf(mockItemRequirementCondition), listOf())
        val subject = createSubject(requirement)
        subject.mockBankItems = mapOf(Pair(mockItemRequirement.name, 1))

        // Act
        assertEquals(SetupResult.UNKNOWN, subject.complete())
        assertEquals(SetupResult.COMPLETE, subject.complete())
    }

    private fun createSubject(requirement: QuestRequirements): SetupTasksTestHarness {
        return SetupTasksTestHarness(requirement.itemRequirements, requirement.skillRequirements)
    }
}

class SetupTasksTestHarness(
    itemRequirements: List<ItemRequirementCondition>,
    skillRequirement: List<SkillRequirement>
) : SetupTask(itemRequirements, skillRequirement) {

    var mockBankItems = mapOf<String, Int>()
    var mockSkillids = mapOf<Int, Int>()
    var mockInventoryItems = mapOf<String, Int>()

    override fun getBankItems(): Map<String, Int> {
        return mockBankItems
    }

    override fun getSkillLevel(skillId: Int): Int {
        return mockSkillids.getOrDefault(skillId, 0)
    }

    override fun getInventoryItemCount(name: String): Int {
        return mockInventoryItems.getOrDefault(name, 0)
    }

    fun testCheckSkillRequirements(): Boolean {
        return checkSkillRequirements()
    }

    override fun bankOpened(): Boolean {
        return true
    }

    override fun closeBank(): Boolean {
        return true
    }
}