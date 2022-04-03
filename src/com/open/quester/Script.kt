package com.open.quester

import com.google.common.eventbus.Subscribe
import com.open.quester.common.base.BaseQuest
import com.open.quester.helpers.SystemMessageManager
import com.open.quester.models.QuestInformation
import com.open.quester.models.QuestRunnerState
import com.open.quester.models.SetupResult
import com.open.quester.quest.daddyshome.DaddysHome
import com.open.quester.quest.doricsquest.DoricsQuest
import com.open.quester.quest.druidicritual.DruidicRitual
import com.open.quester.quest.entertheabyss.EnterTheAbyss
import com.open.quester.quest.ernestthechicken.ErnestTheChicken
import com.open.quester.quest.gertrudescat.GertrudesCat
import com.open.quester.quest.hazeelcult.HazeelCult
import com.open.quester.quest.lostcity.LostCity
import com.open.quester.quest.naturalhistory.NaturalHistory
import com.open.quester.quest.plaguecity.PlagueCity
import com.open.quester.quest.romeoandjuliet.RomeoAndJuliet
import com.open.quester.quest.runemysteries.RuneMysteries
import com.open.quester.quest.sheepshearer.SheepShearer
import com.open.quester.quest.templeoftheeye.TempleOfTheEye
import com.open.quester.quest.theknightssword.TheKnightsSword
import com.open.quester.quest.vampyreslayer.VampyreSlayer
import com.open.quester.quest.witchespotion.WitchesPotion
import com.open.quester.quest.xmarksthespot.XMarksTheSpot
import com.open.quester.tasks.GrandExchangeTask
import com.open.quester.tasks.SetupTask
import org.powbot.api.event.MessageEvent
import org.powbot.api.rt4.Equipment
import org.powbot.api.rt4.Item
import org.powbot.api.rt4.Magic
import org.powbot.api.script.*
import org.powbot.api.script.paint.Paint
import org.powbot.api.script.paint.PaintBuilder
import org.powbot.mobile.script.ScriptManager
import org.powbot.mobile.service.ScriptUploader
import org.powbot.quests.quest.witcheshouse.WitchesHouse
import java.util.logging.Logger

@ScriptManifest(
    name = "Open Quester",
    description = "Finishes Quests",
    version = "1.0.7",
    markdownFileName = "openquester.md",
    category = ScriptCategory.Quests,
)
@ScriptConfiguration.List(
    [
        ScriptConfiguration(
            "Quest Name", "Name of the quest you want to run", OptionType.STRING,
            "Dorics Quest",
            ["Dorics Quest", "Druidic Ritual", "Enter the Abyss", "Ernest the Chicken", "Gertrudes Cat", "Lost City",
                "Hazeel Cult", "Natural History", "Plague City", "Romeo & Juliet", "Rune Mysteries",
                "Sheep Shearer", "Temple of the eye", "The Knights Sword", "Vampyre Slayer", "Witch's House",
                "Witch's Potion", "X Marks The Spot"]
        ),
        ScriptConfiguration(
            "Food", "Food you wish to eat if required", OptionType.STRING,
            "",
            ["",
                "Shrimps", "Cooked chicken", "Cooked meat", "Sardine", "Bread", "Herring",
                "Mackerel", "Choc-ice", "Trout", "Cod", "Pike", "Roast beast meat", "Pineapple punch",
                "Salmon", "Tuna", "Jug of wine", "Rainbow fish", "Stew", "Cake", "Meat pie",
                "Lobster", "Bass", "Plain pizza", "Swordfish", "Potato with butter", "Apple pie",
                "Chocolate cake", "Tangled toads' legs", "Potato with cheese", "Meat pizza",
                "Monkfish", "Anchovy pizza", "Cooked karambwan", "Curry", "Ugthanki kebab", "Mushroom potato",
                "Shark", "Sea turtle", "Pineapple pizza", "Summer pie", "Manta ray", "Tuna potato", "Dark crab",
                "Anglerfish"
            ]
        ),
        ScriptConfiguration(
            "Spell",
            "Spell you wish to cast(MUST have staff or wand equipped regardless if it banks)",
            OptionType.STRING,
            allowedValues = arrayOf(
                "",
                "WIND_STRIKE",
                "WATER_STRIKE",
                "FIRE_STRIKE",
            ),
        ),
        ScriptConfiguration(
            "HasRequirements", "Click here if you are mid quest or know you have requirements",
            OptionType.BOOLEAN
        ),
    ]
)
class Script : AbstractScript() {
    private val logger = Logger.getLogger(this.javaClass.simpleName)
    private var questInformation: QuestInformation? = null
    private var quest: BaseQuest? = null
    private var state = QuestRunnerState.SETUP
    private val setupTask: SetupTask by lazy {
        SetupTask(quest!!.questRequirements.itemRequirements, quest!!.questRequirements.skillRequirements)
    }
    private val grandExchangeTask: GrandExchangeTask by lazy {
        val itemsToBuy = quest!!.questRequirements.itemRequirements.filter { it.chosenRequirement == null }.toList()
        GrandExchangeTask(itemsToBuy)
    }

    override fun onStart() {
        addPaint()
        setupConfiguration()
    }

    override fun poll() {
        val currentQuest = quest
        val information = questInformation

        if (currentQuest == null || information == null) {
            logger.info("No quests found stopping")
            ScriptManager.stop()
            return
        }

        when (state) {
            QuestRunnerState.SETUP -> {
                when (setupTask.complete()) {
                    SetupResult.INCOMPLETE -> state = QuestRunnerState.GRAND_EXCHANGE
                    SetupResult.COMPLETE -> state = QuestRunnerState.QUESTING
                    SetupResult.FAILURE -> ScriptManager.stop()
                    else -> {}
                }
            }
            QuestRunnerState.GRAND_EXCHANGE -> {
                when (grandExchangeTask.complete()) {
                    SetupResult.UNKNOWN -> {}// DO Nothing
                    SetupResult.INCOMPLETE -> {} // TODO Might not need state
                    SetupResult.COMPLETE -> state = QuestRunnerState.QUESTING
                    SetupResult.FAILURE -> ScriptManager.stop()
                }
            }
            QuestRunnerState.QUESTING -> {
                if (information.complete) {
                    logger.info("Completed quest :)")
                    ScriptManager.stop()
                    return
                } else {
                    currentQuest.run()
                }
            }
            QuestRunnerState.COMPLETE -> TODO()
        }
    }

    private fun getQuest(questInformation: QuestInformation): BaseQuest {
        return when (questInformation.questVarbits) {
            Varpbits.ANIMAL_MAGNETISM -> TODO()
            Varpbits.BIOHAZARD -> TODO()
            Varpbits.BONE_VOYAGE -> TODO()
            Varpbits.CLIENT_OF_KOUREND -> TODO()
            Varpbits.COOKS_ASSISTANT -> TODO()
            Varpbits.DADDYS_HOME -> DaddysHome(questInformation)
            Varpbits.DEMON_SLAYER -> TODO()
            Varpbits.THE_DIG_SITE -> TODO()
            Varpbits.DORICS_QUEST -> DoricsQuest(questInformation)
            Varpbits.DRUIDIC_RITUAL -> DruidicRitual(questInformation)
            Varpbits.ENTER_THE_ABYSS -> EnterTheAbyss(questInformation)
            Varpbits.ERNEST_THE_CHICKEN -> ErnestTheChicken(questInformation)
            Varpbits.FIGHT_ARENA -> TODO()
            Varpbits.GERTRUDES_CAT -> GertrudesCat(questInformation)
            Varpbits.FISHING_CONTEST -> TODO()
            Varpbits.GOBLIN_DIPLOMACY -> TODO()
            Varpbits.HAND_IN_THE_SAND -> TODO()
            Varpbits.HAZEEL_CULT -> HazeelCult(questInformation)
            Varpbits.IMP_CATCHER -> TODO()
            Varpbits.LOST_CITY -> LostCity(questInformation)
            Varpbits.NATURAL_HISTORY -> NaturalHistory(questInformation)
            Varpbits.OBSERVATORY_QUEST -> TODO()
            Varpbits.PLAGUE_CITY -> PlagueCity(questInformation)
            Varpbits.PRIEST_IN_PERIL -> TODO()
            Varpbits.REGICIDE -> TODO()
            Varpbits.ROMEO_JULIET -> RomeoAndJuliet(questInformation)
            Varpbits.RUNE_MYSTERIES -> RuneMysteries(questInformation)
            Varpbits.SEA_SLUG -> TODO()
            Varpbits.SHEEP_SHEARER -> SheepShearer(questInformation)
            Varpbits.TEMPLE_OF_THE_EYE -> TempleOfTheEye(questInformation)
            Varpbits.THE_KNIGHTS_SWORD -> TheKnightsSword(questInformation)
            Varpbits.THE_RESTLESS_GHOST -> TODO()
            Varpbits.TREE_GNOME_VILLAGE -> TODO()
            Varpbits.QUEST_UNDERGROUND_PASS -> TODO()
            Varpbits.VAMPYRE_SLAYER -> VampyreSlayer(questInformation)
            Varpbits.WATERFALL -> TODO()
            Varpbits.WITCHS_HOUSE -> WitchesHouse(questInformation)
            Varpbits.WITCHS_POTION -> WitchesPotion(questInformation)
            Varpbits.X_MARKS_THE_SPOT -> XMarksTheSpot(questInformation)
        }
    }

    private fun setupConfiguration() {
        val questName = getOption<String>("Quest Name")
        val food = getOption<String>("Food")
        val hasRequirements = getOption<Boolean>("HasRequirements")
        val information = Varpbits.values().first { it.questName == questName }
        val spellText = getOption<String>("Spell")
        updateQuestConfiguration(food, hasRequirements, information, spellText, questName)
    }

    private fun updateQuestConfiguration(
        foodName: String, hasRequirements: Boolean, varpbits: Varpbits,
        spellText: String?, questName: String
    ) {
        val weapon = Equipment.itemAt(Equipment.Slot.MAIN_HAND)

        val spell =
            if (questName == Varpbits.LOST_CITY.questName) {
                Magic.Spell.valueOf(getOption("Spell"))
            } else if (weapon == Item.Nil || spellText.isNullOrEmpty()) {
                null
            } else {
                val firstItemName = weapon.name()
                if (firstItemName.contains("wand", true) || firstItemName.contains("staff", true)) {
                    Magic.Spell.valueOf(getOption("Spell"))
                } else {
                    null
                }
            }

        // TODO Get half names etc for food
        questInformation = QuestInformation(varpbits, arrayOf(foodName), weapon, spell)
        quest = getQuest(questInformation!!)
        quest!!.setup()
        if (hasRequirements) {
            state = QuestRunnerState.QUESTING
        }
    }

    private fun addPaint() {
        val p: Paint = PaintBuilder.newBuilder()
            .addString("Quest name:") { questInformation?.questVarbits?.name }
            .addString("Last status:") { questInformation?.currentQuestStatus }
            .y(45)
            .x(40)
            .build()
        addPaint(p)
    }

    @Subscribe
    fun messaged(messageEvent: MessageEvent) {
        val currentQuest = quest ?: return
        currentQuest.handleMessage(messageEvent)
        SystemMessageManager.messageRecieved(messageEvent)
    }
}

fun main(args: Array<String>) {
    ScriptUploader().uploadAndStart("Open Quester", "", "127.0.0.1:5625", true, false)
}
