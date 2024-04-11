package ru.john.quasarutils.events

import org.bukkit.Material
import org.bukkit.block.Block
import org.bukkit.entity.Item
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.BlockBreakEvent
import org.bukkit.event.block.BlockDropItemEvent
import org.bukkit.event.player.PlayerHarvestBlockEvent
import org.bukkit.inventory.ItemStack
import org.bukkit.plugin.java.JavaPlugin
import ru.john.quasarutils.Configuration
import ru.john.quasarutils.configs.Config

class PreventNotFarmerCrops(
    private val config: Configuration<Config>,
    private val plugin: JavaPlugin
) : Listener {

    @EventHandler
    fun checkBlock(event: BlockDropItemEvent) {

        val player = event.player
        val block = event.block
        val items = event.items


        if (player.hasPermission("farmer.harvest")) return

        changeDrop(block, items, player)
    }

    private fun changeDrop(block: Block, items: List<Item>, player: Player ) {


        player.sendMessage("${items.size}")
        items.drop(items.size)
        player.sendMessage("${items.size}")


        when(block.blockData.material) {

            Material.MELON -> player.inventory.addItem(ItemStack(Material.MELON_SLICE, 1))
            Material.WHEAT_SEEDS -> player.inventory.addItem(listOf(ItemStack(Material.WHEAT_SEEDS, 1), ItemStack(Material.WHEAT, 1)).random())
            Material.CARROTS -> player.inventory.addItem(ItemStack(Material.CARROT, 1))
            Material.POTATOES -> player.inventory.addItem(ItemStack(Material.POTATO, 1))
            Material.BEETROOTS -> player.inventory.addItem(ItemStack(Material.BEETROOT, 1))
            else -> return

        }

    }

}