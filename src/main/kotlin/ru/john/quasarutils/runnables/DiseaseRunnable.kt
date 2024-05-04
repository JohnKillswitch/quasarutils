package ru.john.quasarutils.runnables

import org.bukkit.entity.Player
import ru.john.quasarutils.diseases.Disease
import ru.john.quasarutils.util.PlayerRunnable

abstract class DiseaseRunnable(
    private val pl: Player,
    private val disease: Disease,
    val d: Long,
    val per: Long
) : PlayerRunnable(pl, d, per) {

    companion object {
        val onJoin: Boolean = false
    }
}