package ru.john.quasarutils.events

import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerFishEvent
import ru.john.quasarutils.Configuration
import ru.john.quasarutils.QuasarUtils
import ru.john.quasarutils.configs.Config

class DisableDisabledFishing : Listener {

    @EventHandler
    fun onBrew(event: PlayerFishEvent) {
        QuasarUtils.defaultConfig!!
            .data()?.fishTypes()?.contains(event.caught?.type.toString()).let { event.isCancelled = true }
    }
}
