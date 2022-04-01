package org.powbot.quests.quest.naturalhistory

import org.powbot.api.Tile

object NaturalHistoryConstants {
    val TILE_ORLANDO_SMITH = Tile(1757, 4956, 0)
    const val NAME_ORLANDO_SMITH = "Orlando Smith"
    val CONVERSATION_ORLANDO_SMITH = arrayOf("Sure thing.")
    val CONVERSATION_FINISH_ORLANDO_SMITH = arrayOf("Sure thing.")

    const val NAME_PLAQUE = "Plaque"
    const val ACTION_PLAQUE = "Study"

    const val WIDGET_ANSWER = 533
    const val COMPONENT_QUESTION = 28
    const val COMPONENT_CLOSE = 32

    val LIZARD_SHIFT_COUNT = 8
    val TILE_LIZARD = Tile(1743, 4977, 0)
    val ANSWER_LIZARD = arrayOf(
        Question("How does a lizard regulate body heat?", "Sunlight"),
        Question("Who discovered how to kill lizards?", "The Slayer Masters"),
        Question("How many eyes does a lizard have?", "Three"),
        Question("What order do lizards belong to?", "Squamata"),
        Question("What happens when a lizard becomes cold?", "It becomes sleepy"),
        Question("Lizard skin is made of the same substance as?", "Hair"),
    )

    val TORTOISE_SHIFT_COUNT = 18
    val TILE_TORTOISE = Tile(1753, 4977, 0)
    val ANSWER_TORTOISE = arrayOf(
        Question("What is the name of the oldest tortoise ever recorded?", "Mibbiwocket"),
        Question("What is a tortoise's favourite food?", "Vegetables"),
        Question("Name the explorer who discovered the world's oldest tortoise.", "Admiral Bake"),
        Question("How does the tortoise protect itself?", "Hard shell"),
        Question("If a tortoise had twenty rings on its shell, how old would it be?", "Twenty years"),
        Question("Which race breeds tortoises for battle?", "Gnomes"),
    )

    val DRAGON_SHIFT_COUNT = 2
    val TILE_DRAGON = Tile(1768, 4977, 0)
    val ANSWER_DRAGON = arrayOf(
        Question("What is considered a delicacy by dragons?", "Runite"),
        Question("What is the best defence against a dragon's attack?", "Anti dragon-breath shield"),
        Question("How long do dragons live?", "Unknown"),
        Question("Which of these is not a type of dragon?", "Elemental"),
        Question("What is the favoured territory of a dragon?", "Old battle sites"),
        Question("Approximately how many feet tall do dragons stand?", "Twelve"),
    )

    val WYVERN_SHIFT_COUNT = 20
    val TILE_WYVERN = Tile(1778, 4977)
    val ANSWER_WYVERN = arrayOf(
        Question("How did the wyverns die out?", "Climate change"),
        Question("How many legs does a wyvern have?", "Two"),
        Question("Where have wyvern bones been found?", "Asgarnia"),
        Question("Which genus does the wyvern theoretically belong to?", "Reptiles"),
        Question("What are the wyverns' closest relations?", "Dragons"),
        Question("What is the ambient temperature of wyvern bones?", "Below room temperature"),
    )

    val SNAIL_SHIFT_COUNT = 6
    val TILE_SNAIL = Tile(1776,4962,0)
    val ANSWER_SNAIL = arrayOf(
        Question("What is special about the shell of the giant Morytanian snail?", "It is resistant to acid"),
        Question("How do Morytanian snails capture their prey?", "Spitting acid"),
        Question("Which of these is a snail byproduct?", "Fireproof oil"),
        Question("What does 'Achatina Acidia' mean?", "Acid-spitting snail"),
        Question("How do snails move?", "Contracting and stretching"),
        Question("What is the 'trapdoor', which snails use to cover the entrance to their shells called?", "An operculum"),
    )

    val SNAKE_SHIFT_COUNT = 12
    val TILE_SNAKE = Tile(1783,4962,0)
    val ANSWER_SNAKE = arrayOf(
        Question("What is snake venom adapted from?", "Stomach acid"),
        Question("Aside from their noses, what do snakes use to smell?", "Tongue"),
        Question("If a snake sticks its tongue out at you, what is it doing?", "Seeing how you smell"),
        Question("If some snakes use venom to kill their prey, what do other snakes use?", "Constriction"),
        Question("Lizards and snakes belong to the same order - what is it?", "Squamata"),
        Question("Which habitat do snakes prefer?", "Anywhere"),
    )

    val SEA_SLUGS_SHIFT_COUNT = 22
    val TILE_SEA_SLUGS = Tile(1781,4959,0)
    val ANSWER_SEA_SLUGS = arrayOf(
        Question("We assume that sea slugs have a stinging organ on their soft skin - what is it called?", "Nematocysts"),
        Question("Why has the museum never examined a live sea slug?", "The researchers keep vanishing"),
        Question("What do we think the sea slug feeds upon?", "Seaweed"),
        Question("What are the two fangs presumed to be used for?", "Defense or display"),
        Question("Off of which coastline would you find sea slugs?", "Ardougne"),
        Question("In what way are sea slugs similar to snails?", "They have a hard shell"),
    )

    val MONKEY_SHIFT_COUNT = 10
    val TILE_MONKEY = Tile(1774,4958,0)
    val ANSWER_MONKEY = arrayOf(
        Question("Which type of primates do monkeys belong to?", "Simian"),
        Question("Which have the lighter colour: Karamjan or Harmless monkeys?", "Harmless"),
        Question("Monkeys love bananas. What else do they like to eat?", "Bitternuts"),
        Question("There are two known families of monkeys. One is Karamjan, the other is...?", "Harmless"),
        Question("What colour mohawk do Karamjan monkeys have?", "Red"),
        Question("What have Karamjan monkeys taken a deep dislike to?", "Seaweed"),
    )

    val KALPHITE_QUEEN_SHIFT_COUNT = 26
    val TILE_KALPHITE_QUEEN = Tile(1761,4938,0)
    val ANSWER_KALPHITE_QUEEN = arrayOf(
        Question("Kalphites are ruled by a...?", "Pasha"),
        Question("What is the lowest caste in kalphite society?", "Worker"),
        Question("What are the armoured plates on a kalphite called?", "Lamellae"),
        Question("Are kalphites carnivores, herbivores or omnivores?", "Carnivores"),
        Question("What are kalphites assumed to have evolved from?", "Scarab beetles"),
        Question("Name the prominent figure in kalphite mythology?", "Scabaras"),
    )

    val TERRORBIRD_SHIFT_COUNT = 24
    val TILE_TERRORBIRD = Tile(1756,4940,0)
    val ANSWER_TERRORBIRD = arrayOf(
        Question("What is a terrorbird's preferred food?", "Anything"),
        Question("Who use terrorbirds as mounts?", "Gnomes"),
        Question("Where do terrorbirds get most of their water?", "Eating plants"),
        Question("How many claws do terrorbirds have?", "Four"),
        Question("What do terrorbirds eat to aid digestion?", "Stones"),
        Question("How many teeth do terrorbirds have?", "0"),
    )

    val PENGUIN_SHIFT_COUNT = 4
    val TILE_PENGUIN = Tile(1742,4958,0)
    val ANSWER_PENGUIN = arrayOf(
        Question("Which sense do penguins rely on when hunting?", "Sight"),
        Question("Which skill seems unusual for the penguins to possess?", "Planning"),
        Question("How do penguins keep warm?", "A layer of fat"),
        Question("What is the preferred climate for penguins?", "Cold"),
        Question("Describe the behaviour of penguins?", "Social"),
        Question("When do penguins fast?", "During breeding"),
    )

    val MOLE_SHIFT_COUNT = 14
    val TILE_MOLE = Tile(1736,4958,0)
    val ANSWER_MOLE = arrayOf(
        Question("What habitat do moles prefer?", "Subterranean"),
        Question("Why are moles considered to be an agricultural pest?", "They dig holes"),
        Question("Who discovered giant moles?", "Wyson the Gardener"),
        Question("What would you call a group of young moles?", "A labour"),
        Question("What is a mole's favourite food?", "Insects and other invertebrates"),
        Question("Which family do moles belong to?", "The Talpidae family"),
    )

    val CAMEL_SHIFT_COUNT = 16
    val TILE_CAMEL = Tile(1737,4962,0)
    val ANSWER_CAMEL = arrayOf(
        Question("What is produced by feeding chilli to a camel?", "Toxic dung"),
        Question("If an ugthanki has one, how many does a bactrian have?", "Two"),
        Question("Camels: herbivore, carnivore or omnivore?", "Omnivore"),
        Question("What is the usual mood for a camel?", "Annoyed"),
        Question("Where would you find an ugthanki?", "Al Kharid"),
        Question("Which camel byproduct is known to be very nutritious?", "Milk"),
    )

    val LEECH_SHIFT_COUNT = 28
    val TILE_LEECH = Tile(1743,4962,0)
    val ANSWER_LEECH = arrayOf(
        Question("What is the favoured habitat of leeches?", "Water"),
        Question("What shape is the inside of a leech's mouth?", "'Y'-shaped"),
        Question("Which of these is not eaten by leeches?", "Apples"),
        Question("What contributed to the giant growth of Morytanian leeches?", "Environment"),
        Question("What is special about Morytanian leeches?", "They attack by jumping"),
        Question("How does a leech change when it feeds?", "It doubles in size"),
    )
}

data class Question(val question: String, val answer: String)