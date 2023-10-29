package ru.john.quasarutils

import net.kyori.adventure.text.minimessage.MiniMessage
import org.bukkit.Bukkit
import org.bukkit.plugin.java.JavaPlugin
import org.bukkit.scheduler.BukkitRunnable
import ru.john.quasarutils.commands.SetCharacterInfo
import ru.john.quasarutils.configs.Config
import ru.john.quasarutils.configs.Messages
import ru.john.quasarutils.database.DataSource
import ru.john.quasarutils.database.MainBase
import ru.john.quasarutils.events.JoinAndLeaveHandler
import ru.john.quasarutils.events.MoveOnPaths
import ru.john.quasarutils.util.CacheMap
import ru.john.quasarutils.util.ChatHelper
import space.arim.dazzleconf.ConfigurationOptions


class QuasarUtils : JavaPlugin() {
    override fun onEnable() {

        val miniMessage = MiniMessage.builder().build()

        dataFolder.mkdirs();

        val messages = Configuration.create(
            this,
            "messages.yml",
            Messages::class.java,
            ConfigurationOptions.defaults()
        )
        val config = Configuration.create(
            this,
            "config.yml",
            Config::class.java,
            ConfigurationOptions.defaults()
        )
        messages.reloadConfig()
        config.reloadConfig()


        val hikariDataSource = DataSource(this).create()
        val mainBase = MainBase(hikariDataSource)
        mainBase.createTables()
        val cacheMap = CacheMap(mainBase)
        val placeholderExpansion = PlaceholderExpansion(cacheMap)

        if(Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null) {
            PlaceholderExpansion(cacheMap).register()
        }

        val cHelper = ChatHelper(miniMessage)

        getCommand("char")!!.setExecutor(
            SetCharacterInfo(miniMessage, mainBase, cHelper, cacheMap, messages)
        )
        server.pluginManager.registerEvents(JoinAndLeaveHandler(cacheMap, mainBase), this)

        registerSchedulers(config, this)


    }
    private fun registerSchedulers(config: Configuration<Config>, plugin: JavaPlugin) {
        object : BukkitRunnable() {
            override fun run() {
                server.onlinePlayers.forEach { player ->
                        MoveOnPaths(config, plugin).checkBlock(player)
                    }
                }
        }.runTaskTimerAsynchronously(this, 0L, 5L)
    }


    override fun onDisable() {
        // Plugin shutdown logic


        // disable end_crystal block damage
    }
}