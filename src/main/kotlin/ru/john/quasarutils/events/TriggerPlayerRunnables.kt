package ru.john.quasarutils.events

import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.event.player.PlayerQuitEvent
import ru.john.quasarutils.QuasarUtils
import ru.john.quasarutils.services.types.PlayerRunnablesService


class TriggerPlayerRunnables : Listener {

    @EventHandler
    fun registerPlayer(event: PlayerJoinEvent) {
        var service = QuasarUtils.serviceManager!!.getPlayerDataContainerService()
        service.addPlayer(event.player)
        PlayerRunnablesService(event.player)
    }

    @EventHandler
    fun stopRunnables(event: PlayerQuitEvent) {
        val serviceManager = QuasarUtils.serviceManager
        serviceManager!!.removePlayerRunnablesService(event.player)
    }
}