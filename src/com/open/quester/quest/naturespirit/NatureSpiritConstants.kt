package com.open.quester.quest.naturespirit

import org.powbot.api.Area
import org.powbot.api.Tile

object NatureSpiritConstants {

    const val ITEM_SILVER_SICKLE = "Silver sickle"
    const val ITEM_GHOSTSPEAK_AMULET = "Ghostspeak amulet"

    const val ITEM_WASHING_BOWL = "Washing bowl"
    const val ITEM_MIRROR = "Mirror"
    const val ITEM_JOURNAL = "Journal"
    const val ITEM_SPELL = "Druidic spell"
    const val ITEM_USED_SPELL = "A used spell"
    const val ITEM_POUCH = "Druid pouch"
    const val ID_POUCH = 2957
    const val ID_POUCH_WITH = 2958
    val TILE_PUCH = Tile(3443, 9741)
    const val ITEM_BLESSED_SICKLE = "Silver sickle (b)"
    const val ITEM_SPELL_CARD = "A used spell or druidic spell"
    const val ITEM_FUNGUS = "Mort myre fungus"

    const val NAME_DREZEL = "Drezel"
    val TILE_DREZEL = Tile(3439, 9896)
    val CONVERSATION_DREZEL = arrayOf("Well, what is it, I may be able to help?", "Yes.")

    const val NAME_GROTTO = "Grotto"
    val TILE_GROTTO = Tile(3440, 3337)
    val CONVERSATION_GROTTO = arrayOf("How long have you been a ghost?", "Ok, thanks.", "I'd better get going.")
    val MISC_FILLIMAN = arrayOf("Ok, thanks.")

    val TILE_INSIDE_GROTTO = Tile(3441, 9734)
    val TILE_LOTS_LOGS = Tile(3413, 3360, 0)

    const val NAME_GROTTO_TREE = "Grotto tree"
    val ACTION_SEARCH_GROTTO = "Search"

    const val NAME_FILLMAN = "Filliman Tarlock"
    val TILE_FILLMAN = Tile(3440, 3336)
    val CONVERSATION_FILLIMAN = arrayOf("How long have you been a ghost?")
    val CONVERSATION_GET_SPELL = arrayOf("How can I help?")
    val CONVERSATION_FILLIMANCAST = arrayOf("I think I've solved the puzzle!")

    const val NAME_NATURE_SPIRIT = "Nature Spirit"
    val TILE_NATURE_SPIRIT = Tile(3441, 9738)
    val CONVERSATION_NATURE_SPIRIT = arrayOf("Ok, thanks.")

    val TILE_WASHING_BOWL = Tile(3437, 3337)

    const val NAME_STONE = "Stone"
    val TILE_STONE = Tile(3439, 3336)

    val NAME_FUNGUS_ON_LOG = "Fungi on log"
    val TILE_SINGLE = Tile(3425, 3332)
    val ANIMATION_BLOOM = 811

    val AREA_OUTSIDE_GROTTO = Area(Tile(3433, 3344), Tile(3449, 3331))
    val AREA_INSIDE_GROTTO = Area(Tile(3434, 9747), Tile(3449, 9731))

    val TILE_MUSHROOM = Tile(3439, 3336)
    val TILE_SPELL = Tile(3441, 3336)
    val TILE_ORANGE_STONE = Tile(3440, 3335)
    val TILE_ALTAR = Tile(3442, 9741)
}