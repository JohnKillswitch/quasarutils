package ru.john.quasarutils.util

import org.bukkit.entity.Player
import org.bukkit.scheduler.BukkitRunnable

abstract class PlayerRunnable(
    private val player: Player,
    val delay: Long,
    val period: Long
) : BukkitRunnable() {
}