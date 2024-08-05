package dqbb

import kotlinx.serialization.json.Json
import java.io.File
import java.nio.charset.Charset
import java.util.Scanner

fun main() {

    val actorsFolder = File(ClassLoader.getSystemResource("actors").file)

    if (!actorsFolder.exists() || !actorsFolder.isDirectory) {
        throw IllegalArgumentException("resources/actors folder not found")
    }

    val actorFiles = actorsFolder.listFiles { _, name -> name.endsWith(".json") }
        ?: throw IllegalArgumentException("No JSON files found in the actors folder")

    // Display the list of available actor files
    println("Available actor files:")
    actorFiles.forEachIndexed { index, file ->
        println("${index + 1}. ${file.name}")
    }

    // Prompt the user to select the files to load
    val scanner = Scanner(System.`in`)
    println("Enter the numbers of the files you want to load, separated by commas:")
    val input = scanner.nextLine()
    val selectedIndices = input.split(",").map { it.trim().toInt() - 1 }

    val actors = mutableListOf<Actor>()

    selectedIndices.forEach { index ->
        if (index in actorFiles.indices) {
            val file = actorFiles[index]
            val jsonContent = file.readText(Charset.defaultCharset())
            val decoded = Json.decodeFromString<JSONActor>(jsonContent)

            // Prompt the user for the number of instances to create
            println("How many instances of ${file.name} would you like to create?")
            val numInstances = scanner.nextLine().toIntOrNull() ?: 1

            repeat(numInstances) {
                val actor = decoded.build()
                actors.add(actor)
                println("Created instance ${it + 1} of ${file.name}: $actor")
            }
        } else {
            println("Invalid selection: ${index + 1}")
        }
    }

    // Set allegiance for each created actor instance
    actors.forEachIndexed { index, actor ->
        println("Set allegiance for ${actor.arn} (${index + 1}) (default is 0):")
        val allegiance = scanner.nextLine().toIntOrNull() ?: 0
        actor.allegiance = allegiance // Assuming Actor class has a mutable property 'allegiance'
        println("${actor.arn} (${index + 1}) set with allegiance $allegiance")
    }
3
    // Pass the actors to the BattleSystem and start it
    val battleSystem = BattleSystem(actors)

    while(battleSystem.isActive) {
        battleSystem.run()
    }

    battleSystem.trail.forEach {
        println(it.message)
    }
}
