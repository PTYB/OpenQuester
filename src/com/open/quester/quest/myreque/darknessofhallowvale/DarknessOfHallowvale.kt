package com.open.quester.quest.myreque.darknessofhallowvale

import com.open.quester.common.*
import com.open.quester.common.base.BaseQuest
import com.open.quester.common.base.BaseQuestStep
import com.open.quester.common.base.SimpleObjectStep
import com.open.quester.extensions.count
import com.open.quester.extensions.nearestGameObject
import com.open.quester.helpers.InteractionsHelper
import com.open.quester.models.QuestInformation
import com.open.quester.models.QuestRequirements
import com.open.quester.models.SkillRequirement
import com.open.quester.quest.Constants.BANK_BURG
import com.open.quester.quest.Constants.BANK_CANIFIS
import com.open.quester.quest.myreque.MyrequeConstants.AREA_BURG
import com.open.quester.quest.myreque.MyrequeConstants.AREA_UNDER_PUB
import com.open.quester.quest.myreque.MyrequeConstants.NAME_DREZEL
import com.open.quester.quest.myreque.MyrequeConstants.NAME_KING_RONALD
import com.open.quester.quest.myreque.MyrequeConstants.NAME_VELIAF
import com.open.quester.quest.myreque.MyrequeConstants.TILE_DREZEL_UNDERGROUND
import com.open.quester.quest.myreque.MyrequeConstants.TILE_KING_RONALD
import com.open.quester.quest.myreque.MyrequeConstants.TILE_VELIAF_BURG_PUB
import com.open.quester.quest.myreque.darknessofhallowvale.DarknessOfHallowvaleConstants.AREA_BASE
import com.open.quester.quest.myreque.darknessofhallowvale.DarknessOfHallowvaleConstants.AREA_FIRST_AREA
import com.open.quester.quest.myreque.darknessofhallowvale.DarknessOfHallowvaleConstants.AREA_INSIDE_BASE_DOOR
import com.open.quester.quest.myreque.darknessofhallowvale.DarknessOfHallowvaleConstants.AREA_KNIFE_ROOM
import com.open.quester.quest.myreque.darknessofhallowvale.DarknessOfHallowvaleConstants.AREA_LAB
import com.open.quester.quest.myreque.darknessofhallowvale.DarknessOfHallowvaleConstants.AREA_SAAFALAAN_WALL
import com.open.quester.quest.myreque.darknessofhallowvale.DarknessOfHallowvaleConstants.AREA_SAAFALAAN_WALL_GROUND
import com.open.quester.quest.myreque.darknessofhallowvale.DarknessOfHallowvaleConstants.AREA_STATUE_ROOM
import com.open.quester.quest.myreque.darknessofhallowvale.DarknessOfHallowvaleConstants.AREA_UNDERGROUND_BASE
import com.open.quester.quest.myreque.darknessofhallowvale.DarknessOfHallowvaleConstants.CONVERSAITON_VELIAF_SECOND
import com.open.quester.quest.myreque.darknessofhallowvale.DarknessOfHallowvaleConstants.CONVERSATION_BOAT
import com.open.quester.quest.myreque.darknessofhallowvale.DarknessOfHallowvaleConstants.CONVERSATION_CITIZEN
import com.open.quester.quest.myreque.darknessofhallowvale.DarknessOfHallowvaleConstants.CONVERSATION_RAL
import com.open.quester.quest.myreque.darknessofhallowvale.DarknessOfHallowvaleConstants.CONVERSATION_ROALD
import com.open.quester.quest.myreque.darknessofhallowvale.DarknessOfHallowvaleConstants.CONVERSATION_SAAFALAAN
import com.open.quester.quest.myreque.darknessofhallowvale.DarknessOfHallowvaleConstants.CONVERSATION_SEND_ME_TO_MINE
import com.open.quester.quest.myreque.darknessofhallowvale.DarknessOfHallowvaleConstants.CONVERSATION_VELIAF_START
import com.open.quester.quest.myreque.darknessofhallowvale.DarknessOfHallowvaleConstants.CONVERSATION_VERTIDA
import com.open.quester.quest.myreque.darknessofhallowvale.DarknessOfHallowvaleConstants.ITEM_CHARCOAL
import com.open.quester.quest.myreque.darknessofhallowvale.DarknessOfHallowvaleConstants.ITEM_DOOR_KEY
import com.open.quester.quest.myreque.darknessofhallowvale.DarknessOfHallowvaleConstants.ITEM_HAEM_BOOK
import com.open.quester.quest.myreque.darknessofhallowvale.DarknessOfHallowvaleConstants.ITEM_LADDER_PIECE
import com.open.quester.quest.myreque.darknessofhallowvale.DarknessOfHallowvaleConstants.ITEM_ORNATE_KEY
import com.open.quester.quest.myreque.darknessofhallowvale.DarknessOfHallowvaleConstants.ITEM_PAPYRUS
import com.open.quester.quest.myreque.darknessofhallowvale.DarknessOfHallowvaleConstants.ITEM_PLANK
import com.open.quester.quest.myreque.darknessofhallowvale.DarknessOfHallowvaleConstants.ITEM_SEALED_MESSAGE
import com.open.quester.quest.myreque.darknessofhallowvale.DarknessOfHallowvaleConstants.ITEM_SKETCH_3
import com.open.quester.quest.myreque.darknessofhallowvale.DarknessOfHallowvaleConstants.NAME_CITIZEN
import com.open.quester.quest.myreque.darknessofhallowvale.DarknessOfHallowvaleConstants.NAME_RAL
import com.open.quester.quest.myreque.darknessofhallowvale.DarknessOfHallowvaleConstants.NAME_SAFALAAN
import com.open.quester.quest.myreque.darknessofhallowvale.DarknessOfHallowvaleConstants.NAME_STATUE
import com.open.quester.quest.myreque.darknessofhallowvale.DarknessOfHallowvaleConstants.NAME_TAPESTRY
import com.open.quester.quest.myreque.darknessofhallowvale.DarknessOfHallowvaleConstants.NAME_VERTIDA
import com.open.quester.quest.myreque.darknessofhallowvale.DarknessOfHallowvaleConstants.NAME_VYREWATCH
import com.open.quester.quest.myreque.darknessofhallowvale.DarknessOfHallowvaleConstants.TILE_BASE_LADDER_FINAL
import com.open.quester.quest.myreque.darknessofhallowvale.DarknessOfHallowvaleConstants.TILE_BEFORE_AGILITY
import com.open.quester.quest.myreque.darknessofhallowvale.DarknessOfHallowvaleConstants.TILE_BELOW_KNIFE
import com.open.quester.quest.myreque.darknessofhallowvale.DarknessOfHallowvaleConstants.TILE_BUSHES
import com.open.quester.quest.myreque.darknessofhallowvale.DarknessOfHallowvaleConstants.TILE_NEAR_BOAT
import com.open.quester.quest.myreque.darknessofhallowvale.DarknessOfHallowvaleConstants.TILE_NEAR_BOAT_BOARD
import com.open.quester.quest.myreque.darknessofhallowvale.DarknessOfHallowvaleConstants.TILE_NEAR_BOAT_CHUTE
import com.open.quester.quest.myreque.darknessofhallowvale.DarknessOfHallowvaleConstants.TILE_NEAR_CITIZEN
import com.open.quester.quest.myreque.darknessofhallowvale.DarknessOfHallowvaleConstants.TILE_NEAR_TAPESTRY
import com.open.quester.quest.myreque.darknessofhallowvale.DarknessOfHallowvaleConstants.TILE_NORTH_WALL
import com.open.quester.quest.myreque.darknessofhallowvale.DarknessOfHallowvaleConstants.TILE_RAL
import com.open.quester.quest.myreque.darknessofhallowvale.DarknessOfHallowvaleConstants.TILE_SAAFALAAN
import com.open.quester.quest.myreque.darknessofhallowvale.DarknessOfHallowvaleConstants.TILE_SOUTH_WALL
import com.open.quester.quest.myreque.darknessofhallowvale.DarknessOfHallowvaleConstants.TILE_VERTIDA
import com.open.quester.quest.myreque.darknessofhallowvale.DarknessOfHallowvaleConstants.TILE_WEST_WALL
import com.open.quester.quest.myreque.darknessofhallowvale.DarknessOfHallowvaleConstants.VARP_INFORATION
import com.open.quester.quest.myreque.darknessofhallowvale.DarknessOfHallowvaleConstants.VARP_SHIFT_BOAT_PUSHED
import com.open.quester.quest.myreque.darknessofhallowvale.DarknessOfHallowvaleConstants.allItemRequirements
import com.open.quester.quest.myreque.darknessofhallowvale.DarknessOfHallowvaleConstants.areaRockySurface
import com.open.quester.quest.myreque.darknessofhallowvale.DarknessOfHallowvaleConstants.areaSecondWall
import com.open.quester.quest.myreque.darknessofhallowvale.DarknessOfHallowvaleConstants.firstBankRequirements
import com.open.quester.quest.myreque.darknessofhallowvale.DarknessOfHallowvaleConstants.messageFromVyre
import com.open.quester.quest.myreque.darknessofhallowvale.DarknessOfHallowvaleConstants.secondBankRequirements
import com.open.quester.quest.myreque.darknessofhallowvale.DarknessOfHallowvaleConstants.talkInPubRequirement
import com.open.quester.quest.myreque.darknessofhallowvale.roof.RoofInformation.ACTION_CLIMB_DOWN
import com.open.quester.quest.myreque.darknessofhallowvale.roof.RoofInformation.ACTION_CLIMB_INTO
import com.open.quester.quest.myreque.darknessofhallowvale.roof.RoofInformation.ACTION_CLIMB_UP
import com.open.quester.quest.myreque.darknessofhallowvale.roof.RoofInformation.ACTION_FLOORBOARDS
import com.open.quester.quest.myreque.darknessofhallowvale.roof.RoofInformation.ACTION_OPEN
import com.open.quester.quest.myreque.darknessofhallowvale.roof.RoofInformation.ACTION_SEARCH
import com.open.quester.quest.myreque.darknessofhallowvale.roof.RoofInformation.ACTION_WALK_ACROSS
import com.open.quester.quest.myreque.darknessofhallowvale.roof.RoofInformation.ACTION_WALL
import com.open.quester.quest.myreque.darknessofhallowvale.roof.RoofInformation.ACTION_WALL_CRAWL
import com.open.quester.quest.myreque.darknessofhallowvale.roof.RoofInformation.AREA_ABOVE_BASE
import com.open.quester.quest.myreque.darknessofhallowvale.roof.RoofInformation.AREA_BEFORE_BASE
import com.open.quester.quest.myreque.darknessofhallowvale.roof.RoofInformation.AREA_EIGHTH_ROOF
import com.open.quester.quest.myreque.darknessofhallowvale.roof.RoofInformation.AREA_EIGHTTEEN_ROOF
import com.open.quester.quest.myreque.darknessofhallowvale.roof.RoofInformation.AREA_ELEVEN_ROOF
import com.open.quester.quest.myreque.darknessofhallowvale.roof.RoofInformation.AREA_FIFTEEN_ROOF
import com.open.quester.quest.myreque.darknessofhallowvale.roof.RoofInformation.AREA_FIFTH_ROOF
import com.open.quester.quest.myreque.darknessofhallowvale.roof.RoofInformation.AREA_FIRST_ROOF
import com.open.quester.quest.myreque.darknessofhallowvale.roof.RoofInformation.AREA_FOURTEEN_ROOF
import com.open.quester.quest.myreque.darknessofhallowvale.roof.RoofInformation.AREA_FOURTH_ROOF
import com.open.quester.quest.myreque.darknessofhallowvale.roof.RoofInformation.AREA_HIDEOUT_BASE
import com.open.quester.quest.myreque.darknessofhallowvale.roof.RoofInformation.AREA_NINETEEN_ROOF
import com.open.quester.quest.myreque.darknessofhallowvale.roof.RoofInformation.AREA_NINTH_ROOF
import com.open.quester.quest.myreque.darknessofhallowvale.roof.RoofInformation.AREA_SECOND_ROOF
import com.open.quester.quest.myreque.darknessofhallowvale.roof.RoofInformation.AREA_SEVENTEEN_ROOF
import com.open.quester.quest.myreque.darknessofhallowvale.roof.RoofInformation.AREA_SEVENTH_ROOF
import com.open.quester.quest.myreque.darknessofhallowvale.roof.RoofInformation.AREA_SIXTEEN_ROOF
import com.open.quester.quest.myreque.darknessofhallowvale.roof.RoofInformation.AREA_SIXTH_ROOF
import com.open.quester.quest.myreque.darknessofhallowvale.roof.RoofInformation.AREA_TENTH_ROOF
import com.open.quester.quest.myreque.darknessofhallowvale.roof.RoofInformation.AREA_THIRD_ROOF
import com.open.quester.quest.myreque.darknessofhallowvale.roof.RoofInformation.AREA_THIRTEEN_ROOF
import com.open.quester.quest.myreque.darknessofhallowvale.roof.RoofInformation.AREA_TWELVE_ROOF
import com.open.quester.quest.myreque.darknessofhallowvale.roof.RoofInformation.AREA_TWENTYEIGHT_ROOF
import com.open.quester.quest.myreque.darknessofhallowvale.roof.RoofInformation.AREA_TWENTYFIVE_ROOF
import com.open.quester.quest.myreque.darknessofhallowvale.roof.RoofInformation.AREA_TWENTYFOUR_ROOF
import com.open.quester.quest.myreque.darknessofhallowvale.roof.RoofInformation.AREA_TWENTYONE_ROOF
import com.open.quester.quest.myreque.darknessofhallowvale.roof.RoofInformation.AREA_TWENTYSEVEN_ROOF
import com.open.quester.quest.myreque.darknessofhallowvale.roof.RoofInformation.AREA_TWENTYSIX_ROOF
import com.open.quester.quest.myreque.darknessofhallowvale.roof.RoofInformation.AREA_TWENTYTHREE_ROOF
import com.open.quester.quest.myreque.darknessofhallowvale.roof.RoofInformation.AREA_TWENTYTWO_ROOF
import com.open.quester.quest.myreque.darknessofhallowvale.roof.RoofInformation.AREA_TWENTY_ROOF
import com.open.quester.quest.myreque.darknessofhallowvale.roof.RoofInformation.NAME_DOOR
import com.open.quester.quest.myreque.darknessofhallowvale.roof.RoofInformation.NAME_FLOOR
import com.open.quester.quest.myreque.darknessofhallowvale.roof.RoofInformation.NAME_FLOORBOARDS
import com.open.quester.quest.myreque.darknessofhallowvale.roof.RoofInformation.NAME_LADDER
import com.open.quester.quest.myreque.darknessofhallowvale.roof.RoofInformation.NAME_POTS
import com.open.quester.quest.myreque.darknessofhallowvale.roof.RoofInformation.NAME_SHELF
import com.open.quester.quest.myreque.darknessofhallowvale.roof.RoofInformation.NAME_STAIRS_DOWN
import com.open.quester.quest.myreque.darknessofhallowvale.roof.RoofInformation.NAME_TABLE
import com.open.quester.quest.myreque.darknessofhallowvale.roof.RoofInformation.NAME_TRAPDOOR_TABLE
import com.open.quester.quest.myreque.darknessofhallowvale.roof.RoofInformation.NAME_TRAPDOOR_TUNNEL
import com.open.quester.quest.myreque.darknessofhallowvale.roof.RoofInformation.NAME_WALL
import com.open.quester.quest.myreque.darknessofhallowvale.roof.RoofInformation.NAME_WASHING_LINE
import com.open.quester.quest.myreque.darknessofhallowvale.roof.RoofInformation.TILE_ABOVE_BASE_ACTION
import com.open.quester.quest.myreque.darknessofhallowvale.roof.RoofInformation.TILE_ABOVE_BASE_REVERSE_ACTION
import com.open.quester.quest.myreque.darknessofhallowvale.roof.RoofInformation.TILE_BEFORE_BASE_ACTION
import com.open.quester.quest.myreque.darknessofhallowvale.roof.RoofInformation.TILE_BEFORE_BASE_LADDER
import com.open.quester.quest.myreque.darknessofhallowvale.roof.RoofInformation.TILE_EIGHTH_ACTION
import com.open.quester.quest.myreque.darknessofhallowvale.roof.RoofInformation.TILE_EIGHTTEEN_ACTION
import com.open.quester.quest.myreque.darknessofhallowvale.roof.RoofInformation.TILE_ELEVEN_ACTION
import com.open.quester.quest.myreque.darknessofhallowvale.roof.RoofInformation.TILE_FIFTEEN_ACTION
import com.open.quester.quest.myreque.darknessofhallowvale.roof.RoofInformation.TILE_FIFTH_ACTION
import com.open.quester.quest.myreque.darknessofhallowvale.roof.RoofInformation.TILE_FIRST_ROOF_JUMP
import com.open.quester.quest.myreque.darknessofhallowvale.roof.RoofInformation.TILE_FOURTEEN_ACTION
import com.open.quester.quest.myreque.darknessofhallowvale.roof.RoofInformation.TILE_FOURTH_ACTION
import com.open.quester.quest.myreque.darknessofhallowvale.roof.RoofInformation.TILE_NINETEEN_ACTION
import com.open.quester.quest.myreque.darknessofhallowvale.roof.RoofInformation.TILE_NINTH_ACTION
import com.open.quester.quest.myreque.darknessofhallowvale.roof.RoofInformation.TILE_SECOND_ROOF_JUMP
import com.open.quester.quest.myreque.darknessofhallowvale.roof.RoofInformation.TILE_SEVENTEEN_ACTION
import com.open.quester.quest.myreque.darknessofhallowvale.roof.RoofInformation.TILE_SEVENTH_ACTION
import com.open.quester.quest.myreque.darknessofhallowvale.roof.RoofInformation.TILE_SIXTEEN_ACTION
import com.open.quester.quest.myreque.darknessofhallowvale.roof.RoofInformation.TILE_SIXTH_ACTION
import com.open.quester.quest.myreque.darknessofhallowvale.roof.RoofInformation.TILE_TENTH_ACTION
import com.open.quester.quest.myreque.darknessofhallowvale.roof.RoofInformation.TILE_THIRD_ROOF_JUMP_ONE
import com.open.quester.quest.myreque.darknessofhallowvale.roof.RoofInformation.TILE_THIRD_ROOF_JUMP_TWO
import com.open.quester.quest.myreque.darknessofhallowvale.roof.RoofInformation.TILE_THIRTEEN_ACTION
import com.open.quester.quest.myreque.darknessofhallowvale.roof.RoofInformation.TILE_TWELVE_ACTION
import com.open.quester.quest.myreque.darknessofhallowvale.roof.RoofInformation.TILE_TWELVE_ACTION2
import com.open.quester.quest.myreque.darknessofhallowvale.roof.RoofInformation.TILE_TWENTYEIGHT_ACTION
import com.open.quester.quest.myreque.darknessofhallowvale.roof.RoofInformation.TILE_TWENTYEIGHT_DOWN_ACTION
import com.open.quester.quest.myreque.darknessofhallowvale.roof.RoofInformation.TILE_TWENTYFIVE_ACTION
import com.open.quester.quest.myreque.darknessofhallowvale.roof.RoofInformation.TILE_TWENTYFOUR_ACTION
import com.open.quester.quest.myreque.darknessofhallowvale.roof.RoofInformation.TILE_TWENTYONE_ACTION
import com.open.quester.quest.myreque.darknessofhallowvale.roof.RoofInformation.TILE_TWENTYSEVEN_ACTION
import com.open.quester.quest.myreque.darknessofhallowvale.roof.RoofInformation.TILE_TWENTYSIX_ACTION
import com.open.quester.quest.myreque.darknessofhallowvale.roof.RoofInformation.TILE_TWENTYTHREE_ACTION
import com.open.quester.quest.myreque.darknessofhallowvale.roof.RoofInformation.TILE_TWENTYTWO_ACTION
import com.open.quester.quest.myreque.darknessofhallowvale.roof.RoofInformation.TILE_TWENTY_ACTION
import com.open.quester.quest.myreque.darknessofhallowvale.roof.RoofTopJump
import com.open.quester.quest.myreque.darknessofhallowvale.tasks.*
import org.powbot.api.Condition
import org.powbot.api.Point
import org.powbot.api.Tile
import org.powbot.api.rt4.*
import org.powbot.api.rt4.walking.model.Skill

class DarknessOfHallowvale(information: QuestInformation) : BaseQuest(information) {
    private val startQuest = QuestTaskList(
        BankStep(firstBankRequirements, BANK_BURG, information, foodRequired = true),
        SimpleConversationStep(
            NAME_VELIAF, TILE_VELIAF_BURG_PUB, CONVERSATION_VELIAF_START,
            "Talking to Veliaf", information
        )
    )

    private val repairBoat = SimpleObjectStep(TILE_NEAR_BOAT, CONVERSATION_BOAT, {
        Objects.stream(TILE_NEAR_BOAT, 5)
            .name("Boat").first()
    }, { go: GameObject -> InteractionsHelper.useItemOnInteractive(ITEM_PLANK, go) },
        { Chat.chatting() }, "Fixing boat", information
    )

    private val repairBoatChute = SimpleObjectStep(TILE_NEAR_BOAT, CONVERSATION_BOAT, {
        Objects.stream(TILE_NEAR_BOAT_CHUTE, 5)
            .name("Boat Chute").first()
    }, { go: GameObject -> InteractionsHelper.useItemOnInteractive(ITEM_PLANK, go) },
        { Chat.chatting() }, "Fixing chute", information
    )
    private val pushBoat = QuestTaskList(
        SimpleObjectStep(TILE_NEAR_BOAT_BOARD, CONVERSATION_BOAT, "Boat", "Board", { go: GameObject -> !go.valid() },
            "Boarding boat", information,
            { Varpbits.varpbit(VARP_INFORATION, VARP_SHIFT_BOAT_PUSHED, 0x1) == 1 }).also {
            it.pointVariance = Point(2, 0)
        },
        SimpleObjectStep(
            TILE_NEAR_BOAT, CONVERSATION_BOAT, "Boat", "Push",
            { Chat.chatting() }, "Pushing boat", information
        ),
    )

    private val talkToCitizen = QuestTaskList(
        ClimbRock(),
        GoDown(),
        JumpFromBoat(),
        SimpleConversationStep(
            NAME_CITIZEN, TILE_NEAR_CITIZEN,
            CONVERSATION_CITIZEN, "Talking to Citizen", information
        )
    )

    private val talkToRal = QuestTaskList(
        ClimbRock(),
        GoDown(),
        JumpFromBoat(),
        SimpleConversationStep(
            NAME_RAL, TILE_RAL, CONVERSATION_RAL, "Talking to Ral", information
        )
    )

    private val enterTheRoofs = SimpleObjectStep(TILE_BEFORE_AGILITY, arrayOf(messageFromVyre),
        { Objects.stream(TILE_BEFORE_AGILITY, 2, GameObject.Type.INTERACTIVE).name("Ladder").first() },
        { go: GameObject -> go.interact("Climb-up") }, { AREA_FIRST_ROOF.contains(Players.local()) },
        "Climbing ladder", information,
        shouldExecute = { AREA_FIRST_AREA.contains(Players.local()) })

    private val RoofTaskCourse = arrayOf(
        RoofTopJump(AREA_FIRST_ROOF, TILE_FIRST_ROOF_JUMP, NAME_FLOORBOARDS, ACTION_FLOORBOARDS),
        RoofTopJump(AREA_SECOND_ROOF, TILE_SECOND_ROOF_JUMP, NAME_FLOORBOARDS, ACTION_FLOORBOARDS),
        RoofTopJump(AREA_THIRD_ROOF, TILE_THIRD_ROOF_JUMP_ONE, NAME_WALL, ACTION_WALL),
        RoofTopJump(AREA_THIRD_ROOF, TILE_THIRD_ROOF_JUMP_TWO, NAME_FLOOR, ACTION_WALK_ACROSS),
        RoofTopJump(AREA_FOURTH_ROOF, TILE_FOURTH_ACTION, NAME_WALL, ACTION_WALL_CRAWL),
        RoofTopJump(AREA_FIFTH_ROOF, TILE_FIFTH_ACTION, NAME_WALL, ACTION_WALL),
        RoofTopJump(AREA_FIFTH_ROOF, TILE_FIFTH_ACTION, NAME_FLOOR, ACTION_WALK_ACROSS),
        RoofTopJump(AREA_SIXTH_ROOF, TILE_SIXTH_ACTION, NAME_LADDER, ACTION_CLIMB_DOWN),
        RoofTopJump(AREA_SEVENTH_ROOF, TILE_SEVENTH_ACTION, NAME_TABLE, ACTION_SEARCH),
        RoofTopJump(AREA_SEVENTH_ROOF, TILE_SEVENTH_ACTION, NAME_TRAPDOOR_TABLE, ACTION_OPEN),
        RoofTopJump(AREA_SEVENTH_ROOF, TILE_SEVENTH_ACTION, NAME_TRAPDOOR_TUNNEL, ACTION_CLIMB_INTO),
        RoofTopJump(AREA_EIGHTH_ROOF, TILE_EIGHTH_ACTION, NAME_SHELF, ACTION_CLIMB_UP),
        RoofTopJump(AREA_NINTH_ROOF, TILE_NINTH_ACTION, NAME_WALL, ACTION_WALL_CRAWL),

        RoofTopJump(AREA_TENTH_ROOF, TILE_TENTH_ACTION, NAME_FLOORBOARDS, ACTION_FLOORBOARDS),
        RoofTopJump(AREA_ELEVEN_ROOF, TILE_ELEVEN_ACTION, NAME_LADDER, ACTION_CLIMB_DOWN),
        RoofTopJump(AREA_TWELVE_ROOF, TILE_TWELVE_ACTION, NAME_POTS, ACTION_SEARCH)
        { Inventory.count(ITEM_DOOR_KEY) == 0 },
        RoofTopJump(AREA_TWELVE_ROOF, TILE_TWELVE_ACTION2, NAME_DOOR, ACTION_OPEN)
        { Inventory.count(ITEM_DOOR_KEY) == 1 },
        RoofTopJump(AREA_THIRTEEN_ROOF, TILE_THIRTEEN_ACTION, NAME_LADDER, ACTION_CLIMB_UP),
        RoofTopJump(AREA_FOURTEEN_ROOF, TILE_FOURTEEN_ACTION, NAME_FLOORBOARDS, ACTION_FLOORBOARDS),
        RoofTopJump(AREA_FIFTEEN_ROOF, TILE_FIFTEEN_ACTION, NAME_SHELF, ACTION_CLIMB_UP),
        RoofTopJump(AREA_SIXTEEN_ROOF, TILE_SIXTEEN_ACTION, NAME_LADDER, ACTION_CLIMB_UP),
        RoofTopJump(AREA_SEVENTEEN_ROOF, TILE_SEVENTEEN_ACTION, NAME_FLOORBOARDS, ACTION_FLOORBOARDS),
        RoofTopJump(AREA_EIGHTTEEN_ROOF, TILE_EIGHTTEEN_ACTION, NAME_LADDER, ACTION_CLIMB_DOWN),
        RoofTopJump(AREA_NINETEEN_ROOF, TILE_NINETEEN_ACTION, NAME_WASHING_LINE, ACTION_WALK_ACROSS),
        RoofTopJump(AREA_TWENTY_ROOF, TILE_TWENTY_ACTION, NAME_LADDER, ACTION_CLIMB_DOWN),
        RoofTopJump(AREA_TWENTYONE_ROOF, TILE_TWENTYONE_ACTION, NAME_WALL, ACTION_WALL),
        RoofTopJump(AREA_TWENTYONE_ROOF, TILE_TWENTYONE_ACTION, NAME_FLOOR, ACTION_WALK_ACROSS),
        RoofTopJump(AREA_TWENTYTWO_ROOF, TILE_TWENTYTWO_ACTION, NAME_SHELF, ACTION_CLIMB_UP),
        RoofTopJump(AREA_TWENTYTHREE_ROOF, TILE_TWENTYTHREE_ACTION, NAME_SHELF, ACTION_CLIMB_DOWN),
        RoofTopJump(AREA_TWENTYFOUR_ROOF, TILE_TWENTYFOUR_ACTION, NAME_FLOORBOARDS, ACTION_FLOORBOARDS),
        RoofTopJump(AREA_TWENTYFIVE_ROOF, TILE_TWENTYFIVE_ACTION, NAME_FLOORBOARDS, ACTION_FLOORBOARDS),
        RoofTopJump(AREA_TWENTYSIX_ROOF, TILE_TWENTYSIX_ACTION, NAME_FLOORBOARDS, ACTION_FLOORBOARDS),
        RoofTopJump(AREA_TWENTYSEVEN_ROOF, TILE_TWENTYSEVEN_ACTION, NAME_LADDER, ACTION_CLIMB_DOWN),
        RoofTopJump(AREA_TWENTYSEVEN_ROOF, TILE_TWENTYSEVEN_ACTION, NAME_LADDER, ACTION_CLIMB_UP)
        { Inventory.count(ITEM_LADDER_PIECE) == 0 },
        RoofTopJump(AREA_TWENTYEIGHT_ROOF, TILE_TWENTYEIGHT_ACTION, NAME_WALL, ACTION_SEARCH)
        { Inventory.count(ITEM_LADDER_PIECE) == 0 },
        RoofTopJump(AREA_TWENTYEIGHT_ROOF, TILE_TWENTYEIGHT_DOWN_ACTION, NAME_LADDER, ACTION_CLIMB_DOWN)
        { Inventory.count(ITEM_LADDER_PIECE) == 1 },
        enterTheRoofs,
        SimpleObjectStep(Tile.Nil, arrayOf(messageFromVyre), { Objects.nearestGameObject("Broken ladder") },
            { go: GameObject -> InteractionsHelper.useItemOnInteractive(ITEM_LADDER_PIECE, go) },
            { Chat.chatting() }, "Fixing ladder", information,
            { AREA_TWENTYSEVEN_ROOF.contains(Players.local()) && Inventory.count(ITEM_LADDER_PIECE) == 1 }
        ),
        // To get to final area
        SimpleObjectStep(
            TILE_BELOW_KNIFE, arrayOf(messageFromVyre), { Objects.stream(TILE_BELOW_KNIFE, 2).name("Ladder").first() },
            { go: GameObject -> go.interact(ACTION_CLIMB_UP) }, { Condition.wait { Players.local().floor() == 1 } },
            "Climbing ladder", information,
            { AREA_HIDEOUT_BASE.contains(Players.local()) && Inventory.count("Knife") == 0 }
        ),
        PickupItemStep(
            Tile.Nil, { GroundItems.stream().name("Knife").first() },
            { AREA_KNIFE_ROOM.contains(Players.local()) && Inventory.count("Knife") == 0 }, "Take",
            "Picking up knife", information
        ),
        SimpleObjectStep(
            Tile.Nil, arrayOf(messageFromVyre), { Objects.stream().within(AREA_KNIFE_ROOM).name("Ladder").first() },
            { go: GameObject -> go.interact(ACTION_CLIMB_DOWN) }, { Condition.wait { Players.local().floor() == 0 } },
            "Climbing down ladder", information,
            { AREA_KNIFE_ROOM.contains(Players.local()) && Inventory.count("Knife") == 1 }
        ),
        SimpleObjectStep(
            TILE_BASE_LADDER_FINAL,
            arrayOf(messageFromVyre),
            { Objects.stream(TILE_BASE_LADDER_FINAL, 2).name("Ladder").first() },
            { go: GameObject -> go.interact(ACTION_CLIMB_UP) },
            { Condition.wait { Players.local().floor() == 1 } },
            "Climbing ladder",
            information,
            { AREA_HIDEOUT_BASE.contains(Players.local()) && Inventory.count("Knife") == 1 }
        ),
        RoofTopJump(AREA_BEFORE_BASE, TILE_BEFORE_BASE_ACTION, NAME_FLOORBOARDS, ACTION_FLOORBOARDS),
        RoofTopJump(AREA_ABOVE_BASE, TILE_ABOVE_BASE_ACTION, NAME_STAIRS_DOWN, ACTION_CLIMB_DOWN),
    )

    private val handleFirstRunToBase = QuestTaskList(
        SimpleObjectStep(Tile.Nil, arrayOf(), "Trapdoor", "Climb-down", { go: GameObject -> !go.valid() },
            "Climbing down", information,
            { AREA_INSIDE_BASE_DOOR.contains(Players.local()) && Objects.nearestGameObject("Trapdoor") != GameObject.Nil }),
        SimpleObjectStep(Tile.Nil, arrayOf(), "Lumpy rug", "Open",
            { Objects.nearestGameObject("Trapdoor") != GameObject.Nil }, "Opening the rug?", information,
            { AREA_INSIDE_BASE_DOOR.contains(Players.local()) && Objects.nearestGameObject("Lumpy rug") != GameObject.Nil }),
        SimpleObjectStep(Tile.Nil, arrayOf(), "Decorated wall", "Press",
            { Objects.nearestGameObject("Lumpy rug") == GameObject.Nil }, "Pressing wall", information,
            { AREA_INSIDE_BASE_DOOR.contains(Players.local()) }),
        SimpleObjectStep(TILE_BASE_LADDER_FINAL,
            arrayOf(messageFromVyre),
            { Objects.stream().name("Wall").action("Push").nearest().first() },
            { go: GameObject -> go.interact("Push") },
            { Condition.wait { Players.local().y() < 3640 } },
            "Pushing door",
            information,
            { AREA_BASE.contains(Players.local()) && Objects.stream().id(17980).first() != GameObject.Nil }
        ),
    )

    private val enterBase = QuestTaskList(
        // Inside base area
        SimpleObjectStep(
            TILE_BASE_LADDER_FINAL,
            arrayOf(messageFromVyre),
            { Objects.stream().name("Wall").action("Push").nearest().first() },
            { go: GameObject -> InteractionsHelper.useItemOnInteractive("Knife", go) },
            { Condition.wait { Players.local().floor() == 1 } },
            "Knifing a wall",
            information,
            { AREA_BASE.contains(Players.local()) }
        ),
        *RoofTaskCourse,
        WaitStep({ true }, "Waiting for obstacle")
    )

    private val talkToVertida = SimpleConversationStep(
        NAME_VERTIDA, TILE_VERTIDA, CONVERSATION_VERTIDA, "Talking to Vertida",
        information, shouldExecute = { AREA_UNDERGROUND_BASE.contains(Players.local()) }
    )

    private val reportToVeliaf = QuestTaskList(
        CastTeleport(Magic.Spell.VARROCK_TELEPORT, { AREA_UNDERGROUND_BASE.contains(Players.local()) }),
        BankStep(talkInPubRequirement, BANK_CANIFIS, information, foodRequired = true),
        SimpleConversationStep(
            NAME_VELIAF, TILE_VELIAF_BURG_PUB, CONVERSAITON_VELIAF_SECOND,
            "Talking to Veliaf", information
        )
    )

    private val talkToDrezel = QuestTaskList(
        CastTeleport(Magic.Spell.VARROCK_TELEPORT, { AREA_UNDER_PUB.contains(Players.local()) }),
        SimpleConversationStep(
            NAME_DREZEL, TILE_DREZEL_UNDERGROUND, arrayOf("Okay, thanks."), "Talking to Drezel",
            information
        )
    )

    private val searchBushes = SimpleObjectStep(
        TILE_BUSHES, arrayOf("Okay, will do."), "Bush", "Search", { Chat.chatting() },
        "Searching bushes", information,
    )

    private val talkToRoald = SimpleConversationStep(
        NAME_KING_RONALD, TILE_KING_RONALD, CONVERSATION_ROALD, "Talking to Roald",
        information
    )
    private val talkToVeliafAgain = SimpleConversationStep(
        NAME_VELIAF, TILE_VELIAF_BURG_PUB, CONVERSAITON_VELIAF_SECOND,
        "Talking to Veliaf", information
    )

    private val returnToBase = QuestTaskList(
        BankStep(
            secondBankRequirements, BANK_BURG, information, shouldExecute =
            { AREA_UNDER_PUB.contains(Players.local()) || AREA_BURG.contains(Players.local()) }, foodRequired = true
        ),
        // Return to Halowvale
        SimpleObjectStep(TILE_NEAR_BOAT_BOARD, CONVERSATION_BOAT, "Boat", "Board", { go: GameObject -> !go.valid() },
            "Boarding boat", information,
            { AREA_BURG.contains(Players.local()) }).also {
            it.pointVariance = Point(2, 0)
        },
        ClimbRock(),
        JumpFromBoat(),
        GoDown(),

        // Enter base and talk to him
        talkToVertida,
        SimpleObjectStep(Tile.Nil, arrayOf(), "Trapdoor", "Climb-down", { go: GameObject -> !go.valid() },
            "Climbing down trapdoor", information,
            {
                AREA_INSIDE_BASE_DOOR.contains(Players.local()) && Objects.stream().name("Trapdoor").within(
                    AREA_INSIDE_BASE_DOOR
                ).first() != GameObject.Nil
            }),
        SimpleObjectStep(Tile.Nil, arrayOf(), "Lumpy rug", "Open",
            { Objects.nearestGameObject("Trapdoor") != GameObject.Nil }, "Opening the rug?", information,
            { AREA_INSIDE_BASE_DOOR.contains(Players.local()) && Objects.nearestGameObject("Trapdoor") != GameObject.Nil }),
        SimpleObjectStep(Tile.Nil, arrayOf(), "Decorated wall", "Press",
            { Objects.nearestGameObject("Lumpy rug") == GameObject.Nil }, "Pressing wall", information,
            { AREA_INSIDE_BASE_DOOR.contains(Players.local()) }),
        SimpleObjectStep(TILE_BASE_LADDER_FINAL,
            arrayOf(messageFromVyre),
            { Objects.stream().name("Wall").action("Push").nearest().first() },
            { go: GameObject -> go.interact("Push") },
            { Condition.wait { Players.local().y() < 3640 } },
            "Pushing door",
            information,
            { AREA_BASE.contains(Players.local()) && Objects.stream().id(17980).first() != GameObject.Nil }
        ),
        *RoofTaskCourse,
        WaitStep({ true }, "Waiting for obstacle")
    )

    private val talkToSaflan = QuestTaskList(
        SimpleConversationStep(
            NAME_SAFALAAN, TILE_SAAFALAAN, CONVERSATION_SAAFALAAN, "Talking to Saafalaan on wall", information,
            shouldExecute = {
                Players.local().floor() >= 2 || (Players.local()
                    .floor() == 0 && Players.local().y() >= 3312) || AREA_SAAFALAAN_WALL.contains(Players.local()) ||
                        areaSecondWall.contains(Players.local())
            }
        ),
        PassBarricade(),
        WalkToArea(areaRockySurface, areaRockySurface.randomTile, "Walking to secret switch", { true }, information),
    )

    private val drawNorth = QuestTaskList(
        WalkToExactTile(
            TILE_NORTH_WALL, "Walking to North drawing",
            { Players.local().tile() != TILE_NORTH_WALL }, information
        ),
        CombineItemStep(
            ITEM_CHARCOAL, ITEM_PAPYRUS, "Sketching", { Players.local().tile() == TILE_NORTH_WALL },
            true, arrayOf()
        )
    )
    private val drawWest = QuestTaskList(
        WalkToExactTile(
            TILE_WEST_WALL, "Walking to West drawing",
            { Players.local().tile() != TILE_WEST_WALL }, information
        ),
        CombineItemStep(
            ITEM_CHARCOAL, ITEM_PAPYRUS, "Sketching", { Players.local().tile() == TILE_WEST_WALL },
            true, arrayOf()
        )
    )
    private val drawSouth = QuestTaskList(
        WalkToExactTile(
            TILE_SOUTH_WALL, "Walking to South drawing",
            { Players.local().tile() != TILE_SOUTH_WALL }, information
        ),
        EnablePrayer(Prayer.Effect.PROTECT_FROM_MELEE),
        CombineItemStep(
            ITEM_CHARCOAL, ITEM_PAPYRUS, "Sketching", { Players.local().tile() == TILE_SOUTH_WALL },
            true, arrayOf()
        )
    )
    private val tankVanstrom = TankVanstrom(information)

    private val getSentToMine = QuestTaskList(
        WalkToExactTile(
            TILE_SOUTH_WALL, "Walking to South drawing",
            { Players.local().tile() != TILE_SOUTH_WALL && Inventory.count(ITEM_SKETCH_3) == 0 }, information
        ),
        CombineItemStep(
            ITEM_CHARCOAL,
            ITEM_PAPYRUS,
            "Sketching",
            { Players.local().tile() == TILE_SOUTH_WALL && Inventory.count(ITEM_SKETCH_3) == 0 },
            true,
            arrayOf()
        ),
        SimpleObjectStep(
            Tile(3596, 3312, 0),
            arrayOf(),
            "Wall", "Climb-down", { Players.local().floor() == 1 }, "Climbing down wall", information,
            shouldExecute = { AREA_SAAFALAAN_WALL_GROUND.contains(Players.local()) }
        ),
        SimpleConversationStep(NAME_VYREWATCH,
            Tile(3588, 3282, 1),
            CONVERSATION_SEND_ME_TO_MINE,
            "Getting sent to mine",
            information,
            shouldExecute = { AREA_SAAFALAAN_WALL.contains(Players.local()) }),
        Mine(information),
        SimpleObjectStep(TILE_BELOW_KNIFE,
            arrayOf(),
            { Objects.stream().name("Fireplace").within(TILE_BELOW_KNIFE, 4).first() },
            { go: GameObject -> InteractionsHelper.useItemOnInteractive("Knife", go) },
            { Chat.chatting() },
            "Knifing Fireplace",
            information,
            shouldExecute = { Inventory.count(ITEM_SEALED_MESSAGE) == 0 }
        ),
        SimpleObjectStep(TILE_BELOW_KNIFE, arrayOf(), { Objects.stream().within(TILE_BELOW_KNIFE, 5).first() },
            { go: GameObject ->
                if (go.id() == 18042) {
                    InteractionsHelper.useItemOnInteractive("Knife", go)
                } else {
                    go.interact("Inspect")
                }
            },
            { Chat.chatting() }, "Knifing portrait", information,
            shouldExecute = { Inventory.count(ITEM_ORNATE_KEY) == 0 }
        ),
        WaitStep(
            { true }, "Waiting for cut scene etc"
        ),
    )

    private val getPortrait = QuestTaskList(
        SimpleObjectStep(TILE_BELOW_KNIFE,
            arrayOf(),
            { Objects.stream().name("Portrait").within(TILE_BELOW_KNIFE, 8).first() },
            { go: GameObject ->
                if (go.id() == 18042) {
                    InteractionsHelper.useItemOnInteractive("Knife", go)
                } else {
                    go.interact("Inspect")
                }
            },
            { Chat.chatting() },
            "Knifing portrait",
            information,
            shouldExecute = { Inventory.count(ITEM_ORNATE_KEY) == 0 }
        ),
        SimpleConversationStep(NAME_SAFALAAN, TILE_VERTIDA, arrayOf(), "Talking to Safalaan", information,
            shouldExecute = { AREA_UNDERGROUND_BASE.contains(Players.local()) }),
        SimpleObjectStep(Tile.Nil, arrayOf(), "Trapdoor", "Climb-down", { go: GameObject -> !go.valid() },
            "Climbing down trapdoor", information,
            {
                AREA_INSIDE_BASE_DOOR.contains(Players.local()) && Objects.stream().name("Trapdoor").within(
                    AREA_INSIDE_BASE_DOOR
                ).first() != GameObject.Nil
            }),
        SimpleObjectStep(Tile.Nil, arrayOf(), "Lumpy rug", "Open",
            { Objects.nearestGameObject("Trapdoor") != GameObject.Nil }, "Opening the rug?", information,
            { AREA_INSIDE_BASE_DOOR.contains(Players.local()) && Objects.nearestGameObject("Lumpy rug") != GameObject.Nil }),
        SimpleObjectStep(Tile.Nil, arrayOf(), "Decorated wall", "Press",
            { Objects.nearestGameObject("Lumpy rug") == GameObject.Nil }, "Pressing wall", information,
            { AREA_INSIDE_BASE_DOOR.contains(Players.local()) }),
        SimpleObjectStep(TILE_BASE_LADDER_FINAL,
            arrayOf(messageFromVyre),
            { Objects.stream().name("Wall").action("Push").nearest().first() },
            { go: GameObject -> go.interact("Push") },
            { Condition.wait { Players.local().y() < 3640 } },
            "Pushing door",
            information,
            { AREA_BASE.contains(Players.local()) && Objects.stream().id(17980).first() != GameObject.Nil }
        ),
        SimpleObjectStep(
            TILE_BASE_LADDER_FINAL,
            arrayOf(messageFromVyre),
            { Objects.stream(TILE_BASE_LADDER_FINAL, 2).name("Ladder").first() },
            { go: GameObject -> go.interact(ACTION_CLIMB_UP) },
            { Condition.wait { Players.local().floor() == 1 } },
            "Climbing ladder",
            information,
            { AREA_HIDEOUT_BASE.contains(Players.local()) && Inventory.count("Knife") == 1 }
        ),
        RoofTopJump(AREA_BEFORE_BASE, TILE_BEFORE_BASE_ACTION, NAME_FLOORBOARDS, ACTION_FLOORBOARDS),
        RoofTopJump(AREA_ABOVE_BASE, TILE_ABOVE_BASE_ACTION, NAME_STAIRS_DOWN, ACTION_CLIMB_DOWN),
    )

    val unlockLab = QuestTaskList(
        // Exit base
        SimpleObjectStep(Tile(3627, 9617),
            arrayOf(),
            "Ladder",
            "Climb-up",
            { !AREA_UNDERGROUND_BASE.contains(Players.local()) },
            "Exiting base",
            information,
            shouldExecute = { AREA_UNDERGROUND_BASE.contains(Players.local()) }),
        SimpleObjectStep(TILE_BASE_LADDER_FINAL,
            arrayOf(messageFromVyre),
            { Objects.stream().name("Wall").action("Push").nearest().first() },
            { go: GameObject -> go.interact("Push") },
            { Players.local().y() > 3640 },
            "Pushing door",
            information,
            { AREA_INSIDE_BASE_DOOR.contains(Players.local()) }
        ).also { it.pointVariance = Point(0, -1) },
        SimpleObjectStep(TILE_BASE_LADDER_FINAL,
            arrayOf(messageFromVyre),
            "Stairs up",
            "Climb-up",
            { AREA_ABOVE_BASE.contains(Players.local()) },
            "Climbing stairs",
            information,
            { AREA_BASE.contains(Players.local()) }
        ),
        RoofTopJump(AREA_ABOVE_BASE, TILE_ABOVE_BASE_REVERSE_ACTION, NAME_FLOORBOARDS, ACTION_FLOORBOARDS),
        RoofTopJump(AREA_BEFORE_BASE, TILE_BEFORE_BASE_LADDER, NAME_LADDER, ACTION_CLIMB_DOWN),

        SimpleObjectStep(TILE_NEAR_TAPESTRY, arrayOf(),
            { Objects.stream().name(NAME_TAPESTRY).within(TILE_NEAR_TAPESTRY, 3).first() },
            { go: GameObject -> InteractionsHelper.useItemOnInteractive("Knife", go) },
            { go: GameObject -> !go.valid() },
            "Cutting tapestry",
            information,
            { Objects.stream().name(NAME_TAPESTRY).within(TILE_NEAR_TAPESTRY, 3).first() != GameObject.Nil }
        ),
    )

    private val enterLab = QuestTaskList(
        SimpleObjectStep(Tile.Nil, arrayOf(), { Objects.nearestGameObject(NAME_STATUE) },
            { go: GameObject -> InteractionsHelper.useItemOnInteractive(ITEM_ORNATE_KEY, go) },
            { Chat.chatting() }, "Giving the statue the key",
            information,
            { AREA_STATUE_ROOM.contains(Players.local()) }
        ),
        SimpleObjectStep(TILE_NEAR_TAPESTRY, arrayOf(),
            "Slashed tapestry",
            "Walk-through",
            { go: GameObject -> !go.valid() },
            "Walking through tapestry",
            information,
            { !AREA_STATUE_ROOM.contains(Players.local()) }
        ),
    )

    private val teleGrabBook = QuestTaskList(
        SimpleObjectStep(Tile(3642, 3306),
            arrayOf(),
            "Staircase",
            "Climb-down",
            { !AREA_STATUE_ROOM.contains(Players.local()) },
            "Climbing down stairs",
            information, { AREA_STATUE_ROOM.contains(Players.local()) && Inventory.count(ITEM_HAEM_BOOK) == 0 }),
        PickupItemStep(
            Tile(3628, 9690),
            { GroundItems.stream().name(ITEM_HAEM_BOOK).first() },
            { Inventory.count(ITEM_HAEM_BOOK) == 0 },
            "Telegrab",
            "Grabbing book",
            information,
        ),
        SimpleObjectStep(
            Tile.Nil,
            arrayOf(),
            "Staircase",
            "Climb-up",
            { !AREA_LAB.contains(Players.local()) },
            "Exiting lab",
            information,
            { AREA_LAB.contains(Players.local()) },
        ).also {
               it.pointVariance = Point(-4,-4)
        },
        SimpleConversationStep(NAME_SAFALAAN, TILE_VERTIDA, arrayOf(), "Talking to Safalaan", information,
            shouldExecute = { AREA_UNDERGROUND_BASE.contains(Players.local()) }),
        SimpleObjectStep(Tile.Nil, arrayOf(), "Trapdoor", "Climb-down", { go: GameObject -> !go.valid() },
            "Climbing down trapdoor", information,
            {
                AREA_INSIDE_BASE_DOOR.contains(Players.local()) && Objects.stream().name("Trapdoor").within(
                    AREA_INSIDE_BASE_DOOR
                ).first() != GameObject.Nil
            }),
        SimpleObjectStep(Tile.Nil, arrayOf(), "Lumpy rug", "Open",
            { Objects.nearestGameObject("Trapdoor") != GameObject.Nil }, "Opening the rug?", information,
            { AREA_INSIDE_BASE_DOOR.contains(Players.local()) && Objects.nearestGameObject("Lumpy rug") != GameObject.Nil }),
        SimpleObjectStep(Tile.Nil, arrayOf(), "Decorated wall", "Press",
            { Objects.nearestGameObject("Lumpy rug") == GameObject.Nil }, "Pressing wall", information,
            { AREA_INSIDE_BASE_DOOR.contains(Players.local()) }),
        SimpleObjectStep(TILE_BASE_LADDER_FINAL,
            arrayOf(messageFromVyre),
            { Objects.stream().name("Wall").action("Push").nearest().first() },
            { go: GameObject -> go.interact("Push") },
            { Condition.wait { Players.local().y() < 3640 } },
            "Pushing door",
            information,
            { AREA_BASE.contains(Players.local()) && Objects.stream().id(17980).first() != GameObject.Nil }
        ),
        RoofTopJump(AREA_BEFORE_BASE, TILE_BEFORE_BASE_ACTION, NAME_FLOORBOARDS, ACTION_FLOORBOARDS),
        RoofTopJump(AREA_ABOVE_BASE, TILE_ABOVE_BASE_ACTION, NAME_STAIRS_DOWN, ACTION_CLIMB_DOWN),
        SimpleObjectStep(
            TILE_BASE_LADDER_FINAL,
            arrayOf(messageFromVyre),
            { Objects.stream(TILE_BASE_LADDER_FINAL, 2).name("Ladder").first() },
            { go: GameObject -> go.interact(ACTION_CLIMB_UP) },
            { Condition.wait { Players.local().floor() == 1 } },
            "Climbing ladder",
            information
        ),
    )

    override fun addRequirements(): QuestRequirements {
        return QuestRequirements(
            allItemRequirements, listOf(
                SkillRequirement(Skill.Construction.index, 5),
                SkillRequirement(Skill.Mining.index, 20),
                SkillRequirement(Skill.Thieving.index, 22),
                SkillRequirement(Skill.Agility.index, 26),
                SkillRequirement(Skill.Crafting.index, 32),
                SkillRequirement(Skill.Magic.index, 33),
                SkillRequirement(Skill.Strength.index, 40)
            )
        )
    }

    override fun getQuestStep(stepPosition: Int): BaseQuestStep? {
        return when (stepPosition and 511) {
            0 -> startQuest.processStep()
            10 -> repairBoat
            20 -> repairBoatChute
            30 -> pushBoat.processStep()
            40, 50, 52, 54 -> talkToCitizen.processStep()
            60 -> talkToRal.processStep()
            65 -> enterBase.processStep()
            70 -> handleFirstRunToBase.processStep()
            80 -> talkToVertida
            90, 100 -> reportToVeliaf.processStep()
            110 -> talkToDrezel.processStep()
            120 -> searchBushes
            130 -> talkToDrezel.processStep()
            1435, 140, 150, 160 -> talkToRoald
            170 -> talkToVeliafAgain
            180 -> returnToBase.processStep() // TOOD Change to mine
            190, 195 -> talkToSaflan.processStep()
            200 -> drawNorth.processStep()
            210 -> drawWest.processStep()
            220 -> drawSouth.processStep()
            230 -> tankVanstrom
            240, 250 -> getSentToMine.processStep()
            260 -> getPortrait.processStep()
            270 -> unlockLab.processStep()
            280 -> enterLab.processStep()
            290, 300 -> teleGrabBook.processStep()
            310 -> reportToVeliaf.processStep()
            320 -> {
                information.complete = true
                CommonMethods.closeQuestComplete()
                null
            }
            else -> TODO("Missing ID $stepPosition, Shifted: ${stepPosition and 511}")
        }
    }
}