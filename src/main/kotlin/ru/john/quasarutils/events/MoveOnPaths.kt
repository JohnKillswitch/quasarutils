package ru.john.quasarutils.events

import org.bukkit.Bukkit
import org.bukkit.entity.Player
import org.bukkit.event.Listener
import org.bukkit.plugin.java.JavaPlugin
import org.bukkit.potion.PotionEffect
import org.bukkit.potion.PotionEffectType
import ru.john.quasarutils.Configuration
import ru.john.quasarutils.configs.Config

class MoveOnPaths(
    private val config: Configuration<Config>,
    private val plugin: JavaPlugin
) : Listener {


    fun checkBlock(player: Player) {
        val block = player.location.block
        val feetBlock = block.location.add(0.0, -1.0, 0.0).block
        val blockTypes = config.data()!!.blockTypes()
        val duration = config.data()!!.duration()
        val speedEffect = PotionEffect(
            PotionEffectType.SPEED,
            (duration * 30).toInt(),
            0,
            false,
            false,
            false)

        if (!blockTypes.contains(block.type.toString()) && !blockTypes.contains(feetBlock.type.toString())) return

        Bukkit.getScheduler().runTask(this.plugin, Runnable { player.addPotionEffect(speedEffect) })

    }
}