package ru.john.quasarutils.events

import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.inventory.BrewEvent
import org.bukkit.inventory.*
import ru.john.quasarutils.Configuration
import ru.john.quasarutils.configs.Config

class DisableRecipes(
    private val config: Configuration<Config>
) : Listener {

//    init {
//        println("DisableRecipes initialized")
//    }
//
//    @EventHandler
//    fun onBrew(event: BrewEvent) {
//        println("DisableRecipes test")
//        Bukkit.getOnlinePlayers().forEach { player ->
//            player.sendMessage("test")
//        }
//        val brewerInventory = event.contents
//
//        if (shouldRemoveRecipe(brewerInventory)) {
//            event.isCancelled = true
//        }
//    }

//    private fun shouldRemoveRecipe(brewerInventory: BrewerInventory): Boolean =
//        brewerInventory.ingredient?.type == Material.NETHER_WART ||
//                brewerInventory.ingredient?.type == Material.FERMENTED_SPIDER_EYE
    //        brewerInventory.any { item ->
//            item.type == Material.NETHER_WART || item.type == Material.FERMENTED_SPIDER_EYE
//        }
    fun disableRecipes() {

        val furnaceCrafts = this.config.data()?.furnaceTypes()
        val benchCrafts = this.config.data()?.benchTypes()

        furnaceCrafts?.forEach { type ->
            Bukkit.recipeIterator().forEachRemaining {recipe ->
                if (recipe is CookingRecipe<*> && recipe.inputChoice.itemStack.type.toString() == type) {
                    Bukkit.removeRecipe(recipe.key)
                }
            }
        }
        benchCrafts?.forEach { type ->
            Bukkit.recipeIterator().forEachRemaining {recipe ->
                if (recipe is ShapedRecipe && recipe.result.type.toString() == type) {
                    Bukkit.removeRecipe(recipe.key)
                }
            }
        }
        benchCrafts?.forEach { type ->
            Bukkit.recipeIterator().forEachRemaining {recipe ->
                if (recipe is ShapelessRecipe && recipe.result.type.toString() == type) {
                    Bukkit.removeRecipe(recipe.key)
                }
            }
        }
    }

}