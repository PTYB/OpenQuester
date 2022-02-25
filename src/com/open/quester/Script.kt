package com.open.quester

import com.open.quester.common.base.BaseQuest
import com.open.quester.models.QuestInformation
import com.open.quester.models.QuestRunnerState
import com.open.quester.models.SetupResult
import com.open.quester.quest.romeoandjuliet.RomeoAndJuliet
import com.open.quester.quest.runemysteries.RuneMysteries
import com.open.quester.quest.sheepshearer.SheepShearer
import com.open.quester.quest.witchespotion.WitchesPotion
import com.open.quester.quest.xmarksthespot.XMarksTheSpot
import com.open.quester.tasks.GrandExchangeTask
import com.open.quester.tasks.SetupTask
import org.powbot.api.rt4.Equipment
import org.powbot.api.rt4.Item
import org.powbot.api.rt4.Magic
import org.powbot.api.script.*
import org.powbot.api.script.paint.Paint
import org.powbot.api.script.paint.PaintBuilder
import org.powbot.mobile.script.ScriptManager
import org.powbot.mobile.service.ScriptUploader
import java.util.logging.Logger

@ScriptManifest(
    name = "Open Quester",
    description = "Finishes Quests",
    version = "1.0.0",
    markdownFileName = "openquester.md",
    category = ScriptCategory.Quests,
)
@ScriptConfiguration.List(
    [
        ScriptConfiguration(
            "Quest Name", "Name of the quest you want to run", OptionType.STRING,
            "Romeo & Juliet",
            ["Romeo & Juliet", "Rune Mysteries", "Sheep Shearer", "Witch's Potion", "X Marks The Spot"]
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
                "WIND_STRIKE"
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
            Varpbits.DEMON_SLAYER -> TODO()
            Varpbits.THE_DIG_SITE -> TODO()
            Varpbits.DORICS_QUEST -> TODO()
            Varpbits.DRUIDIC_RITUAL -> TODO()
            Varpbits.ERNEST_THE_CHICKEN -> TODO()
            Varpbits.FIGHT_ARENA -> TODO()
            Varpbits.GERTRUDES_CAT -> TODO()
            Varpbits.FISHING_CONTEST -> TODO()
            Varpbits.GOBLIN_DIPLOMACY -> TODO()
            Varpbits.HAND_IN_THE_SAND -> TODO()
            Varpbits.HAZEEL_CULT -> TODO()
            Varpbits.IMP_CATCHER -> TODO()
            Varpbits.LOST_CITY -> TODO()
            Varpbits.NATURAL_HISTORY -> TODO()
            Varpbits.OBSERVATORY_QUEST -> TODO()
            Varpbits.PLAGUE_CITY -> TODO()
            Varpbits.PRIEST_IN_PERIL -> TODO()
            Varpbits.REGICIDE -> TODO()
            Varpbits.ROMEO_JULIET -> return RomeoAndJuliet(questInformation)
            Varpbits.RUNE_MYSTERIES -> return RuneMysteries(questInformation)
            Varpbits.SEA_SLUG -> TODO()
            Varpbits.SHEEP_SHEARER -> return SheepShearer(questInformation)
            Varpbits.THE_KNIGHTS_SWORD -> TODO()
            Varpbits.THE_RESTLESS_GHOST -> TODO()
            Varpbits.TREE_GNOME_VILLAGE -> TODO()
            Varpbits.QUEST_UNDERGROUND_PASS -> TODO()
            Varpbits.VAMPYRE_SLAYER -> TODO()
            Varpbits.WATERFALL -> TODO()
            Varpbits.WITCHS_HOUSE -> TODO()
            Varpbits.WITCHS_POTION -> return WitchesPotion(questInformation)
            Varpbits.X_MARKS_THE_SPOT -> return XMarksTheSpot(questInformation)
        }
    }

    private fun setupConfiguration() {
        val questName = getOption<String>("Quest Name")
        val food = getOption<String>("Food")
        val hasRequirements = getOption<Boolean>("HasRequirements")
        val information = Varpbits.values().first { it.questName == questName }
        val spellText = getOption<String>("Spell")
        updateQuestConfiguration(food, hasRequirements, information, spellText)
    }

    fun updateQuestConfiguration(
        foodName: String, hasRequirements: Boolean, varpbits: Varpbits, spellText: String?
    ) {
        val weapon = Equipment.itemAt(Equipment.Slot.MAIN_HAND)

        val spell = if (weapon == Item.Nil || spellText.isNullOrEmpty()) {
            null
        } else {
            val firstItemName = weapon.name()
            if (firstItemName.contains("wand", true) || firstItemName.contains("staff", true)) {
                Magic.Spell.valueOf(getOption<String>("Spell"))
            } else {
                null
            }
        }

        val eatAction = when {
            foodName.isNullOrEmpty() -> null
            foodName == "Jug of wine" -> "Drink"
            else -> "Eat"
        }

        // TODO Get half names etc for food
        questInformation =
            QuestInformation(varpbits, arrayOf(foodName), weapon, spell, 5, eatAction, hasRequirements)
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
}

fun main(args: Array<String>) {
    ScriptUploader().uploadAndStart("Open Quester", "", "emulator-5554", true, false)
}
