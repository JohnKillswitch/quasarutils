package ru.john.quasarutils.events

import io.lumine.mythic.lib.api.item.NBTItem
import io.papermc.paper.event.player.PlayerInventorySlotChangeEvent
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.inventory.InventoryType
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.event.player.PlayerMoveEvent
import org.bukkit.inventory.ItemStack
import org.bukkit.plugin.java.JavaPlugin
import org.bukkit.util.Vector
import ru.john.quasarutils.Configuration
import ru.john.quasarutils.configs.Config
import ru.john.quasarutils.util.ArmorUtils
import java.util.*
import kotlin.collections.HashMap

val LEATHER_ARMOR_MATERIALS = listOf(Material.LEATHER_HELMET, Material.LEATHER_CHESTPLATE, Material.LEATHER_LEGGINGS, Material.LEATHER_BOOTS)
val MMOITEMS_DISPLAYED_TAG = "MMOITEMS_DISPLAYED_TYPE"
val HEAVY_ARMOR_DISPLAY_NAME = "&fТяжёлая броня"
val NEGATIVE_VELOCITY = Vector(0f, -0.175f, 0f)

class PreventHardArmorSwim : Listener {

    @EventHandler
    fun checkPlayerArmor(event: PlayerMoveEvent) {
        val armorIsHeavy: Boolean = ArmorUtils.playerArmorIsHeavy(event.player)
        if (armorIsHeavy && event.to.y > event.from.y) event.player.velocity = NEGATIVE_VELOCITY
    }


    @EventHandler
    fun preventSwapArmor(event: InventoryClickEvent) {

        val inventory = event.clickedInventory
        val item = event.currentItem

        if (inventory?.type != InventoryType.PLAYER) return

        val player = inventory.holder as Player

        if (checkConditions(player, item)) return

        event.isCancelled = true
    }

    @EventHandler
    fun preventRcArmorSwap(event: PlayerInteractEvent) {

        val player = event.player
        val item = event.item

        if (checkConditions(player, item)) return

        event.isCancelled = true
    }

    private fun checkConditions(player: Player, item: ItemStack?): Boolean =
        (!player.isUnderWater || !LEATHER_ARMOR_MATERIALS.contains(item?.type))

}