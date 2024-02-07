package ru.john.quasarutils.events

import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.EntityBreedEvent

class DisableBreed : Listener {

    @EventHandler
    fun checkBreeder(event: EntityBreedEvent) {
        val player = event.breeder ?: return
        if (!player.hasPermission("farmer.breed")) event.isCancelled = true
    }
}