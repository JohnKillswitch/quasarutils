package ru.john.quasarutils.events

import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.Action
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.plugin.java.JavaPlugin
import ru.john.quasarutils.Configuration
import ru.john.quasarutils.configs.Config
import ru.john.quasarutils.configs.Messages

class OpenStations(
    private val plugin: JavaPlugin,
    private val config: Configuration<Config>
) : Listener {
    @EventHandler
    fun checkBlock(event: PlayerInteractEvent) {

        val action = event.action
        if (action != Action.RIGHT_CLICK_BLOCK) return

        val player = event.player
        val clickedBlock = event.clickedBlock
        val configBlocks = this.config.data()?.stationBlocks()

        configBlocks?.get(clickedBlock?.type.toString()).takeIf { player.isSneaking }?.let {
            event.isCancelled = true
            if (it.startsWith("DM:", ignoreCase = true)) {
                scheduleCommandWithDelay(2) {
                    Bukkit.getServer().dispatchCommand(
                        Bukkit.getConsoleSender(),
                        "dm open ${it.drop(3)} ${player.name}")
                }
            }
            if (it.startsWith("MI:", ignoreCase = true)) {
                scheduleCommandWithDelay(2) {
                    Bukkit.getServer().dispatchCommand(
                        Bukkit.getConsoleSender(),
                        "mi stations open ${it.drop(3)} ${player.name}")
                }
            }
        }
    }

    private fun scheduleCommandWithDelay(delayTicks: Long, task: () -> Unit) =
        Bukkit.getServer().scheduler.runTaskLater(this.plugin, Runnable(task), delayTicks).taskId

}