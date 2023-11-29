package ru.john.quasarutils.commands

import org.bukkit.Bukkit
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

        if(Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null) {
            PlaceholderExpansion(cacheMap).register()
        }

        return true
    }
}