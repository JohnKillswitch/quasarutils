package ru.john.quasarutils.events

import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent
import ru.john.quasarutils.QuasarUtils
import ru.john.quasarutils.runnables.CheckArmorWeight


class StartPlayerRunnables() : Listener {
    @EventHandler
    fun startRunnables(event: PlayerJoinEvent) {
        val instance = QuasarUtils.instance!!
        CheckArmorWeight(event.player).runTaskTimer(instance, 20L, 0L)
    }
}