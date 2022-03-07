package com.open.quester.quest.ernestthechicken

import com.open.quester.common.PickupItemStep
import com.open.quester.common.WalkToArea
import com.open.quester.common.base.BaseQuestStep
import com.open.quester.common.base.SimpleObjectStep
import com.open.quester.extensions.count
import com.open.quester.models.QuestInformation
import com.open.quester.quest.ernestthechicken.ErnestTheChickenConstants.AREA_BOTTOM_LEFT
import com.open.quester.quest.ernestthechicken.ErnestTheChickenConstants.AREA_CD_LEVER_ROOM
import com.open.quester.quest.ernestthechicken.ErnestTheChickenConstants.AREA_EF_LEVER_ROOM
import com.open.quester.quest.ernestthechicken.ErnestTheChickenConstants.AREA_MAIN_AREA
import com.open.quester.quest.ernestthechicken.ErnestTheChickenConstants.AREA_MIDDLE_BOTTOM
import com.open.quester.quest.ernestthechicken.ErnestTheChickenConstants.AREA_MIDDLE_TOP
import com.open.quester.quest.ernestthechicken.ErnestTheChickenConstants.AREA_OIL_CAN
import com.open.quester.quest.ernestthechicken.ErnestTheChickenConstants.ITEM_OIL_CAN
import com.open.quester.quest.ernestthechicken.ErnestTheChickenConstants.TILE_GATE_CAN
import com.open.quester.quest.ernestthechicken.ErnestTheChickenConstants.TILE_LEVER_A
import com.open.quester.quest.ernestthechicken.ErnestTheChickenConstants.TILE_LEVER_B
import com.open.quester.quest.ernestthechicken.ErnestTheChickenConstants.TILE_LEVER_C
import com.open.quester.quest.ernestthechicken.ErnestTheChickenConstants.TILE_LEVER_D
import com.open.quester.quest.ernestthechicken.ErnestTheChickenConstants.TILE_LEVER_E
import com.open.quester.quest.ernestthechicken.ErnestTheChickenConstants.TILE_LEVER_F
import com.open.quester.quest.ernestthechicken.ErnestTheChickenConstants.TILE_GATE_ONE
import com.open.quester.quest.ernestthechicken.ErnestTheChickenConstants.TILE_GATE_TWO
import com.open.quester.quest.ernestthechicken.ErnestTheChickenConstants.TILE_GATE_THREE
import com.open.quester.quest.ernestthechicken.ErnestTheChickenConstants.TILE_GATE_FOUR
import com.open.quester.quest.ernestthechicken.ErnestTheChickenConstants.TILE_GATE_FIVE
import com.open.quester.quest.ernestthechicken.ErnestTheChickenConstants.TILE_GATE_SIX
import com.open.quester.quest.ernestthechicken.ErnestTheChickenConstants.TILE_GATE_SEVEN
import com.open.quester.quest.ernestthechicken.ErnestTheChickenConstants.TILE_GATE_EIGHT
import com.open.quester.quest.ernestthechicken.ErnestTheChickenConstants.VARPBIT_LEVERS
import org.powbot.api.Condition
import org.powbot.api.Point
import org.powbot.api.Tile
import org.powbot.api.rt4.*

class LeverConditions(val questInformation: QuestInformation) {

    fun getOilCan(): Array<BaseQuestStep> {
        return arrayOf(
            WalkToArea(
                ErnestTheChickenConstants.AREA_UNDERGROUND, TILE_LEVER_A, "Walking to maze",
                {
                    !ErnestTheChickenConstants.AREA_UNDERGROUND.contains(Players.local()) && Inventory.count(
                        ITEM_OIL_CAN
                    ) == 0
                }, questInformation
            ),
            pullLever(TILE_LEVER_A, "A", pullLeverAWait, pullLeverA),
            pullLever(TILE_LEVER_B, "B", pullLeverBWait, pullLeverB),
            openGate(TILE_GATE_ONE, openFirstGateWait, openFirstGate, 0, -1),
            pullLever(TILE_LEVER_D, "D", pullLeverDWait, pullLeverD),
            openGate(TILE_GATE_TWO, openSecondGateWait, openSecondGate),
            openGate(TILE_GATE_THREE, openThirdGateWait, openThirdGate),
            pullLever(TILE_LEVER_A, "A", pullLeverAAgainWait, pullLeverAAgain),
            pullLever(TILE_LEVER_B, "B", pullLeverBAgainWait, pullLeverBAgain),
            openGate(TILE_GATE_THREE, openFourthGateWait, openFourthGate, 0, -1),
            openGate(TILE_GATE_FOUR, openFifthGateWait, openFifthGate),
            openGate(TILE_GATE_FIVE, openSixthGateWait, openSixthGate, 0, -1),
            pullLever(TILE_LEVER_E, "E", pullLeverEWait, pullLeverE),
            pullLever(TILE_LEVER_F, "F", pullLeverFWait, pullLeverF),
            openGate(TILE_GATE_SIX, openSeventhGateWait, openSeventhGate, -1, 0),
            openGate(TILE_GATE_SEVEN, openEighthGateWait, openEighthGate, -1, 0),
            pullLever(TILE_LEVER_C, "C", pullLeverCWait, pullLeverC),
            openGate(TILE_GATE_SEVEN, openNinthGateWait, openNinthGate),
            openGate(TILE_GATE_SIX, openTenthGateWait, openTenthGate),
            pullLever(TILE_LEVER_E, "E", pullLeverEAgainWait, pullLeverEAgain),
            openGate(TILE_GATE_SIX, openEleventhGateWait, openEleventhGate, -1, 0),
            openGate(TILE_GATE_EIGHT, openTwelthGateWait, openTwelthGate),
            openGate(TILE_GATE_THREE, openThirteenGateWait, openThirteenGate),
            openGate(TILE_GATE_CAN, openOilGateWait, openOilGate),
            PickupItemStep(
                AREA_OIL_CAN.randomTile,
                { GroundItems.stream().name(ITEM_OIL_CAN).first() },
                {
                    AREA_OIL_CAN.contains(Players.local()) && Inventory.count(ITEM_OIL_CAN) == 0
                },
                "Take",
                "Picking up oil can",
                questInformation
            ),
            openGate(TILE_GATE_CAN, leaveOilGateWait, leaveOilGate, -1, 0),
        )
    }

    private fun openGate(
        gateTile: Tile,
        conditionWait: (GameObject) -> Boolean,
        shouldExecute: () -> Boolean,
        xVariance: Int = 0,
        yVariance: Int = 0,
    ): SimpleObjectStep {
        val step = SimpleObjectStep(
            gateTile,
            null,
            { Objects.stream().name("Door").within(gateTile, 2).first() },
            { go: GameObject -> go.interact("Open") },
            conditionWait,
            "Opening door",
            questInformation,
            shouldExecute,
        )
        step.pointVariance = Point(xVariance, yVariance)
        return step
    }

    private fun pullLever(
        tile: Tile, lever: String, conditionWait: (GameObject) -> Boolean,
        shouldExecute: () -> Boolean
    ): SimpleObjectStep {
        return SimpleObjectStep(
            tile,
            null,
            "Lever $lever",
            "Pull",
            conditionWait,
            "Pulling Lever $lever",
            questInformation,
            shouldExecute
        )
    }

    val pullLeverA = { isUnderground() && leverPulled() && noOilCan() }
    val pullLeverAWait = { _: GameObject -> Condition.wait { aPulled() } }

    val pullLeverB = { isUnderground() && leverPulled(a = true) && noOilCan() }
    val pullLeverBWait = { _: GameObject -> Condition.wait { bPulled() } }

    private val openFirstGate = { AREA_MAIN_AREA.contains(Players.local()) && leverPulled(a = true, b = true) }
    private val openFirstGateWait = { _: GameObject -> Condition.wait { AREA_CD_LEVER_ROOM.contains(Players.local()) } }

    private val pullLeverD = { leverPulled(a = true, b = true) && AREA_CD_LEVER_ROOM.contains(Players.local()) }
    private val pullLeverDWait = { _: GameObject -> Condition.wait { dPulled() } }

    private val openSecondGate =
        { AREA_CD_LEVER_ROOM.contains(Players.local()) && leverPulled(a = true, b = true, d = true) }
    private val openSecondGateWait =
        { _: GameObject -> Condition.wait { AREA_MIDDLE_BOTTOM.contains(Players.local()) } }

    private val openThirdGate =
        { AREA_MIDDLE_BOTTOM.contains(Players.local()) && leverPulled(a = true, b = true, d = true) }
    private val openThirdGateWait = { _: GameObject -> Condition.wait { AREA_MAIN_AREA.contains(Players.local()) } }

    private val pullLeverAAgain =
        { AREA_MAIN_AREA.contains(Players.local()) && leverPulled(a = true, b = true, d = true) }
    private val pullLeverAAgainWait = { _: GameObject -> Condition.wait { !aPulled() } }

    private val pullLeverBAgain = { AREA_MAIN_AREA.contains(Players.local()) && leverPulled(b = true, d = true) }
    private val pullLeverBAgainWait = { _: GameObject -> Condition.wait { !bPulled() } }

    private val openFourthGate = { AREA_MAIN_AREA.contains(Players.local()) && leverPulled(d = true) }
    private val openFourthGateWait =
        { _: GameObject -> Condition.wait { AREA_MIDDLE_BOTTOM.contains(Players.local()) } }

    private val openFifthGate = { AREA_MIDDLE_BOTTOM.contains(Players.local()) && leverPulled(d = true) }
    private val openFifthGateWait = { _: GameObject -> Condition.wait { AREA_BOTTOM_LEFT.contains(Players.local()) } }

    private val openSixthGate = { AREA_BOTTOM_LEFT.contains(Players.local()) && leverPulled(d = true) }
    private val openSixthGateWait = { _: GameObject -> Condition.wait { AREA_EF_LEVER_ROOM.contains(Players.local()) } }

    private val pullLeverE = { AREA_EF_LEVER_ROOM.contains(Players.local()) && leverPulled(d = true) }
    private val pullLeverEWait = { _: GameObject -> Condition.wait { ePulled() } }

    private val pullLeverF =
        { AREA_EF_LEVER_ROOM.contains(Players.local()) && leverPulled(d = true, e = true) }
    private val pullLeverFWait = { _: GameObject -> Condition.wait { fPulled() } }

    private val openSeventhGate = {
        AREA_EF_LEVER_ROOM.contains(Players.local()) && leverPulled(
            d = true,
            e = true,
            f = true
        )
    }
    private val openSeventhGateWait =
        { _: GameObject -> Condition.wait { AREA_MIDDLE_TOP.contains(Players.local()) } }

    private val openEighthGate = {
        AREA_MIDDLE_TOP.contains(Players.local()) && leverPulled(
            d = true,
            e = true,
            f = true
        )
    }
    private val openEighthGateWait =
        { _: GameObject -> Condition.wait { AREA_CD_LEVER_ROOM.contains(Players.local()) } }

    private val pullLeverC =
        { AREA_CD_LEVER_ROOM.contains(Players.local()) && leverPulled(d = true, e = true, f = true) }
    private val pullLeverCWait = { _: GameObject -> Condition.wait { cPulled() } }

    private val openNinthGate =
        { AREA_CD_LEVER_ROOM.contains(Players.local()) && leverPulled(c = true, d = true, e = true, f = true) }
    private val openNinthGateWait = { _: GameObject -> Condition.wait { AREA_MIDDLE_TOP.contains(Players.local()) } }

    private val openTenthGate = {
        AREA_MIDDLE_TOP.contains(Players.local()) && leverPulled(
            c = true,
            d = true,
            e = true,
            f = true
        )
    }
    private val openTenthGateWait = { _: GameObject -> Condition.wait { AREA_EF_LEVER_ROOM.contains(Players.local()) } }

    private val pullLeverEAgain = {
        AREA_EF_LEVER_ROOM.contains(Players.local()) && leverPulled(
            c = true,
            d = true,
            e = true,
            f = true
        )
    }
    private val pullLeverEAgainWait = { _: GameObject -> Condition.wait { !ePulled() } }

    private val openEleventhGate = {
        AREA_EF_LEVER_ROOM.contains(Players.local()) && leverPulled(
            c = true,
            d = true,
            f = true
        )
    }
    private val openEleventhGateWait = { _: GameObject -> Condition.wait { AREA_MIDDLE_TOP.contains(Players.local()) } }

    private val openTwelthGate = {
        AREA_MIDDLE_TOP.contains(Players.local()) && leverPulled(
            c = true,
            d = true,
            f = true
        )
    }
    private val openTwelthGateWait =
        { _: GameObject -> Condition.wait { AREA_MIDDLE_BOTTOM.contains(Players.local()) } }

    private val openThirteenGate =
        { AREA_MIDDLE_BOTTOM.contains(Players.local()) && leverPulled(c = true, d = true, f = true) }
    private val openThirteenGateWait = { _: GameObject -> Condition.wait { AREA_MAIN_AREA.contains(Players.local()) } }

    private val openOilGate = {
        noOilCan() && AREA_MAIN_AREA.contains(Players.local()) &&
                leverPulled(c = true, d = true, f = true)
    }
    private val openOilGateWait = { _: GameObject -> Condition.wait { AREA_OIL_CAN.contains(Players.local()) } }

    private val leaveOilGate = {
        AREA_OIL_CAN.contains(Players.local()) && leverPulled(
            c = true,
            d = true,
            f = true
        )
    }
    private val leaveOilGateWait = { _: GameObject -> Condition.wait { AREA_MAIN_AREA.contains(Players.local()) } }

    private fun isUnderground(): Boolean {
        return AREA_MAIN_AREA.contains(Players.local())
    }

    private fun noOilCan(): Boolean {
        return Inventory.count(ITEM_OIL_CAN) == 0
    }

    private fun leverPulled(
        a: Boolean = false,
        b: Boolean = false,
        c: Boolean = false,
        d: Boolean = false, e: Boolean = false, f: Boolean = false
    ): Boolean {
        return aPulled() == a && bPulled() == b && cPulled() == c && dPulled() == d &&
                ePulled() == e && fPulled() == f
    }

    private fun fPulled(): Boolean {
        return Varpbits.varpbit(VARPBIT_LEVERS, 6, 0x1) == 1
    }

    private fun ePulled(): Boolean {
        return Varpbits.varpbit(VARPBIT_LEVERS, 5, 0x1) == 1
    }

    private fun dPulled(): Boolean {
        return Varpbits.varpbit(VARPBIT_LEVERS, 4, 0x1) == 1
    }

    private fun cPulled(): Boolean {
        return Varpbits.varpbit(VARPBIT_LEVERS, 3, 0x1) == 1
    }

    private fun bPulled(): Boolean {
        return Varpbits.varpbit(VARPBIT_LEVERS, 2, 0x1) == 1
    }

    private fun aPulled(): Boolean {
        return Varpbits.varpbit(VARPBIT_LEVERS, 1, 0x1) == 1
    }
}