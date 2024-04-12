package ru.john.quasarutils.events

import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.inventory.BrewEvent
import org.bukkit.inventory.*
import ru.john.quasarutils.Configuration
import ru.john.quasarutils.QuasarUtils
import ru.john.quasarutils.configs.Config

class DisableRecipes {

    fun disableRecipes() {

        val config = QuasarUtils.defaultConfig!!

        val furnaceCrafts = config.data()?.furnaceTypes()
        val benchCrafts = config.data()?.benchTypes()

        furnaceCrafts?.forEach { type ->
            Bukkit.recipeIterator().forEachRemaining {recipe ->
                if (recipe != null && recipe is CookingRecipe<*> && recipe.inputChoice.itemStack.type.toString() == type) {
                    Bukkit.removeRecipe(recipe.key)
                }
            }
        }
        benchCrafts?.forEach { type ->
            Bukkit.recipeIterator().forEachRemaining {recipe ->
                if (recipe != null && recipe is ShapedRecipe && recipe.result.type.toString() == type) {
                    Bukkit.removeRecipe(recipe.key)
                }
            }
        }
        benchCrafts?.forEach { type ->
            Bukkit.recipeIterator().forEachRemaining {recipe ->
                if (recipe != null && recipe is ShapelessRecipe && recipe.result.type.toString() == type) {
                    Bukkit.removeRecipe(recipe.key)
                }
            }
        }
    }

}