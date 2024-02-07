package ru.john.quasarutils.events

import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerFishEvent
import ru.john.quasarutils.Configuration
import ru.john.quasarutils.configs.Config

class DisableDisabledFishing (
    private val config: Configuration<Config>
) : Listener {

    @EventHandler
    fun onBrew(event: PlayerFishEvent) {
        this.config.data()?.fishTypes()?.contains(event.caught?.type.toString()).let { event.isCancelled = true }
    }
}
