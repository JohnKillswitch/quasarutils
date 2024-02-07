package ru.john.quasarutils.crafts

import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.NamespacedKey
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.RecipeChoice.MaterialChoice
import org.bukkit.inventory.ShapelessRecipe
import org.bukkit.plugin.java.JavaPlugin


class ShaplessCrafts (
    private val plugin: JavaPlugin
) {
    fun createRecipes () {
        val key = NamespacedKey(this.plugin, "string_Craft")
        val stringRecipe = ShapelessRecipe(key, ItemStack(Material.STRING))
        val woolChoice = MaterialChoice(Material.BLACK_WOOL, Material.BLUE_WOOL, Material.BROWN_WOOL,Material.CYAN_WOOL,
            Material.GRAY_WOOL, Material.GREEN_WOOL, Material.LIGHT_BLUE_WOOL, Material.LIGHT_GRAY_WOOL, Material.LIME_WOOL, Material.WHITE_WOOL,
            Material.MAGENTA_WOOL, Material.ORANGE_WOOL, Material.PINK_WOOL, Material.RED_WOOL, Material.PURPLE_WOOL, Material.YELLOW_WOOL)


        stringRecipe.addIngredient(woolChoice)

        Bukkit.addRecipe(stringRecipe);
    }
}