package com.open.quester

enum class Varpbits
/**
 * Contains information required to get quest state
 *
 * @param questName   - Name of Quest to read
 * @param questVarbit - Varbit to get completion state
 * @param finishedValue  - final value when it is completed
 */(val questName: String, val questVarbit: Int, val finishedValue: Int, val hasCombat: Boolean = false) {
    // Name, Quest varbit, Completion Status
    // BLACK_KNIGHTS_FORTRESS("Black Knight's Fortress", 130 ,-1),
    ANIMAL_MAGNETISM("Animal Magnetism", 939, -1),
    BIOHAZARD("Biohazard", 68, -1),
    BONE_VOYAGE("Bone Voyage", 1630, -1),
    CLIENT_OF_KOUREND("Client of Kourend", 1566, 7),
    COOKS_ASSISTANT("Cook's Assistant", 29, 2),
    DADDYS_HOME("Daddys Home", 393, -1),
    DEMON_SLAYER("Demon Slayer", 222, -1),
    ENTER_THE_ABYSS("Enter the Abyss", 492, -1),
    THE_DIG_SITE("The Digsite", 131, -1),
    DORICS_QUEST("Dorics Quest", 31, 100),
    DRUIDIC_RITUAL("Druidic Ritual", 80, 4),
    ERNEST_THE_CHICKEN("Ernest the Chicken", 32, -1),
    FIGHT_ARENA("Fight Arena", 17, -1, true),
    GERTRUDES_CAT("Gertrudes Cat", 180, -1),
    FISHING_CONTEST("Fishing Contest", 11, 5),
    GOBLIN_DIPLOMACY("Goblin Diplomacy", 62, 454),
    HAND_IN_THE_SAND("Hand in the Sand", 635, -1),
    HAZEEL_CULT("Hazeel Cult", 223, -1),
    IMP_CATCHER("Imp Catcher", 160, 2),
    LOST_CITY("Lost City", 147, -1),
    NATURAL_HISTORY("Natural History", 1014, -1),
    OBSERVATORY_QUEST("Observatory Quest", 112, -1),
    NATURE_SPIRIT("Nature Spirit", 307, -1),
    IN_SEARCH_OF_THE_MYREQUE("In search of the Myreque", 387, -1),
    PLAGUE_CITY("Plague City", 165, 29),
    PRIEST_IN_PERIL("Priest in peril", 302, -1),
    PRINCE_ALI_RESCUE("Prince Ali Rescue", 273, -1),
    REGICIDE("Regicide", 328, -1),
    ROMEO_JULIET("Romeo & Juliet", 144, 100),
    RUNE_MYSTERIES("Rune Mysteries", 63, 6),
    SEA_SLUG("Sea Slug", 159, -1),
    SHEEP_SHEARER("Sheep Shearer", 179, 21),
    TEMPLE_OF_THE_EYE("Temple of the eye", 3405, -1),
    THE_KNIGHTS_SWORD("The Knights Sword", 122, -1),
    THE_RESTLESS_GHOST("The Restless Ghost", 107, 5),
    TREE_GNOME_VILLAGE("Tree Gnome Village", 111, -1),
    QUEST_UNDERGROUND_PASS("Underground Pass", 161, -1),
    VAMPYRE_SLAYER("Vampyre Slayer", 178, -1),
    WATERFALL("Waterfall", 65, -1),
    WITCHS_HOUSE("Witch's House", 226, -1, true),
    WITCHS_POTION("Witch's Potion", 67, 3, true),
    X_MARKS_THE_SPOT("X Marks The Spot", 2111, 49807368);

}