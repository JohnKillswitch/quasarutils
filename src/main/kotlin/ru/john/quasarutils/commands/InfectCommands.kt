package ru.john.quasarutils.commands

import org.bukkit.Bukkit
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import org.bukkit.plugin.java.JavaPlugin
import ru.john.quasarutils.database.MainBase
import ru.john.quasarutils.util.CacheMap
import java.time.Instant
import java.util.UUID

class InfectCommands(
    private val plugin: JavaPlugin,
    private val cacheMap: CacheMap,
    private  val mainBase: MainBase
) : CommandExecutor {

    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<String>): Boolean {

        if (args.isEmpty()) return true

        if (args.size<2) return true

        val subCommand = args[0]


        val player = Bukkit.getPlayer(args[1])

        if (subCommand == "add") player?.let { addInfect(it) }
        else if (subCommand == "remove") player?.let { removeInfect(it) }


        return true
    }

    private fun addInfect(player: Player) {

        val uuid = player.uniqueId.toString()

        this.cacheMap.addPlayerVirus(uuid)
        this.mainBase.insertInfectionData(uuid, Instant.now().toEpochMilli())




    }
    private fun removeInfect(player: Player) {

        val uuid = player.uniqueId.toString()

        this.cacheMap.removePlayerVirus(uuid)
        this.mainBase.deleteInfectionDataByUUID(uuid)
        this.cacheMap.removeStageSchedulersMap(uuid)

    }
}