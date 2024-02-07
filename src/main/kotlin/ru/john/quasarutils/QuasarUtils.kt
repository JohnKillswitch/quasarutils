package ru.john.quasarutils

import net.kyori.adventure.text.minimessage.MiniMessage
import org.bukkit.Bukkit
import org.bukkit.plugin.java.JavaPlugin
import org.bukkit.scheduler.BukkitRunnable
import ru.john.quasarutils.commands.InfectCommands
import ru.john.quasarutils.commands.MainCommand
import ru.john.quasarutils.commands.SetCharacterInfo
import ru.john.quasarutils.configs.Config
import ru.john.quasarutils.configs.Messages
import ru.john.quasarutils.crafts.ShaplessCrafts
import ru.john.quasarutils.database.DataSource
import ru.john.quasarutils.database.MainBase
import ru.john.quasarutils.events.*
import ru.john.quasarutils.events.virus.ApplyStages
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
        mainBase.createVirusTable()
        mainBase.createCharacterTable()
        val cacheMap = CacheMap(mainBase)
        val placeholderExpansion = PlaceholderExpansion(cacheMap)

        if(Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null) {
            PlaceholderExpansion(cacheMap).register()
        }


        val cHelper = ChatHelper(miniMessage)

        getCommand("char")!!.setExecutor(
            SetCharacterInfo(this, miniMessage, mainBase, cHelper, cacheMap, messages)
        )

        getCommand("quasarutils")!!.setExecutor(
            MainCommand(this, placeholderExpansion, cacheMap)
        )

        val disableRecipes = DisableRecipes(config)
        disableRecipes.disableRecipes()

        ShaplessCrafts(this).createRecipes()

        server.pluginManager.registerEvents(JoinAndLeaveHandler(cacheMap, mainBase), this)
        server.pluginManager.registerEvents(OpenStations(this, config), this)
        server.pluginManager.registerEvents(DisableDisabledFishing(config), this)
        server.pluginManager.registerEvents(DisableBreed(), this)
        server.pluginManager.registerEvents(ChangeAnimalDrop(config),this)

        registerSchedulers(config, this, cacheMap)


    }
    private fun registerSchedulers(config: Configuration<Config>, plugin: JavaPlugin, cacheMap: CacheMap) {
        object : BukkitRunnable() {
            override fun run() {
                server.onlinePlayers.forEach { player ->
                        MoveOnPaths(config, plugin).checkBlock(player)
                    }
                }
        }.runTaskTimerAsynchronously(this, 0L, 5L)

//        object : BukkitRunnable() {
//            override fun run() {
//                server.onlinePlayers.forEach { player ->
//                    ApplyStages(cacheMap, plugin).detectPlayerStage(player)
//                }
//            }
//        }.runTaskTimer(this, 0L, 20L)
    }


    override fun onDisable() {
        // Plugin shutdown logic


        // disable end_crystal block damage
    }
}