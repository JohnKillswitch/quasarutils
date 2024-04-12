package ru.john.quasarutils.events

import org.bukkit.Material
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerItemConsumeEvent
import org.bukkit.plugin.java.JavaPlugin
import ru.john.quasarutils.Configuration
import ru.john.quasarutils.configs.Config

class PreventEatingGapple : Listener {

    @EventHandler
    fun checkFood(event: PlayerItemConsumeEvent) {

        val item = event.item

        if (item.type != Material.GOLDEN_APPLE && item.type != Material.ENCHANTED_GOLDEN_APPLE) return
        

        event.isCancelled = true
    }
}