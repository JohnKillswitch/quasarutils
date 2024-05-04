package ru.john.quasarutils.diseases

import org.bukkit.entity.Player
import ru.john.quasarutils.QuasarUtils

abstract class Disease(
    private val name: String,
    private val stageList: ArrayList<Stage>
) {
    fun set(player: Player) {
    }
}