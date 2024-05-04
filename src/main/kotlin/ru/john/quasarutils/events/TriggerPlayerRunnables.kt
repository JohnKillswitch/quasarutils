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
        if(!service.checkPlayer(event.player)) service.addPlayer(event.player)
        PlayerRunnablesService(event.player)
    }

    @EventHandler
    fun unregisterPlayer(event: PlayerQuitEvent) {
        val serviceManager = QuasarUtils.serviceManager

        serviceManager!!.getPlayerDataContainerService().removePlayer(event.player)
        serviceManager.removePlayerRunnablesService(event.player)
    }
}