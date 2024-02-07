package ru.john.quasarutils.commands

import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.plugin.java.JavaPlugin
import ru.john.quasarutils.PlaceholderExpansion
import ru.john.quasarutils.util.CacheMap

class MainCommand(
    private val plugin: JavaPlugin,
    private val expansion: PlaceholderExpansion,
    private val cacheMap: CacheMap,
) : CommandExecutor {

    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<String>): Boolean {
        if (args.isEmpty()) return false

        if (!sender.hasPermission("quasarutils.spawn")) return true

        if (args[0] == "spawn") onSpawnCommand(args)


        return true
    }

    private fun onSpawnCommand(args: Array<String>) {
        if (args.size < 2) return

        val player = Bukkit.getPlayer(args[1])



        val posList = ArrayList<Location>()

        posList.add(Location(Bukkit.getWorld("world"), 657.5, 81.0, -515.5, 0f, 0f))
        posList.add(Location(Bukkit.getWorld("world"), -412.5, 87.0, -451.5, 0f, 0f))
        posList.add(Location(Bukkit.getWorld("world"), -484.5, 82.0, 308.5, 0f, 0f))
        posList.add(Location(Bukkit.getWorld("world"), -125.5, 96.0, -38.5, 0f, 0f))
        posList.add(Location(Bukkit.getWorld("world"), 557.5, 99.0, 422.5, 0f, 0f))


        player?.teleport(posList.random())
    }
}