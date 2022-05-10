package com.open.quester.quest.demonslayer

import org.powbot.api.Area
import org.powbot.api.Tile

object DemonSlayerConstants {

    val CONVERSATION_KILL_DELRITH = arrayOf("Carlem","Aber","Camerinthum", "Purchai","Gabindo")

    const val NAME_DELRITH = "Delrith"
    const val NAME_WEAKENED_DELRITH = "Weakened Delrith"

    const val ITEM_BUCKET_OF_WATER ="Bucket of water"
    const val ITEM_SILVERLIGHT = "Silverlight"
    const val ITEM_BONES = "Bones"
    const val ID_BONES = 526

    const val ID_FIRST_KEY = 2400
    const val ID_SECOND_KEY = 2401
    const val ID_THIRD_KEY = 2399

    const val NAME_GYPSY = "Gypsy Aris"
    val TILE_GYPSY = Tile(3204, 3424, 0)
    val CONVERSATION_GYPSY = arrayOf(
        "The Demon Slayer Quest",
        "Yes.",
        "Ok, here you go.",
        "Okay, where is he? I'll kill him for you!",
        "So how did Wally kill Delrith?",
        "Okay, thanks. I'll do my best to stop the demon."
    )

    const val NAME_PRYSIN = "Sir Prysin"
    val TILE_PRYSIN = Tile(3203, 3472, 0)
    val CONVERSATION_PRYSIN = arrayOf(
        "Gypsy Aris said I should come and talk to you.",
        "I need to find Silverlight.",
        "He's back and unfortunately I've got to deal with him.", "So give me the keys!", "Can you give me your key?"
    )

    const val NAME_ROVIN = "Captain Rovin"
    val TILE_ROVIN = Tile(3205, 3498, 2)
    val CONVERSATION_ROVIN = arrayOf(
        "Where does the wizard live?",
        "Yes I know, but this is important.",
        "There's a demon who wants to invade this city.",
        "Yes, very.",
        "It's not them who are going to fight the demon, it's me.",
        "Sir Prysin said you would give me the key.",
        "Why did he give you one of the keys then?"
    )

    const val NAME_TRAIBORN = "Wizard Traiborn"
    val TILE_TRAIBORN = Tile(3114,3163, 1)
    val CONVERSATION_TRAIBORN = arrayOf(
        "Talk about Demon Slayer.",
        "I need to get a key given to you by Sir Prysin.",
        "Well, have you got any keys knocking around?",
        "I'll get the bones for you."
    )

    val TILE_BUCKET = Tile(3221,3497, 1)
    val TILE_SINK = Tile(3224,3495,0)
    val TILE_DRAIN = Tile(3225, 3496, 0)
    val TILE_SECOND_KEY = Tile(3226, 9897, 0)

}