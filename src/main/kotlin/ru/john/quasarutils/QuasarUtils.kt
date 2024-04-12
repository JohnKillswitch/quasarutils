package ru.john.quasarutils

import me.angeschossen.lands.api.LandsIntegration
import net.kyori.adventure.text.minimessage.MiniMessage
import org.bukkit.Bukkit
import org.bukkit.plugin.java.JavaPlugin
import org.bukkit.scheduler.BukkitRunnable
import org.reflections.Reflections
import ru.john.quasarutils.commands.MainCommand
import ru.john.quasarutils.commands.SetCharacterInfo
import ru.john.quasarutils.configs.Config
import ru.john.quasarutils.configs.Messages
import ru.john.quasarutils.configs.PlayerTasksConfig
import ru.john.quasarutils.crafts.ShaplessCrafts
import ru.john.quasarutils.database.DataSource
import ru.john.quasarutils.database.CharBase
import ru.john.quasarutils.events.*
import ru.john.quasarutils.services.ServiceLoader
import ru.john.quasarutils.util.CacheMap
import ru.john.quasarutils.util.ChatHelper
import space.arim.dazzleconf.ConfigurationOptions


class QuasarUtils : JavaPlugin() {

    companion object {
        var instance: JavaPlugin? = null
        var playerTasksConfig: Configuration<PlayerTasksConfig>? = null
        var reflections: Reflections = Reflections("ru.john")
        var serviceManager: ServiceLoader? = null
        var cacheMap: CacheMap? = null
        var charBase: CharBase? = null
        var defaultConfig: Configuration<Config>? = null
    }

    override fun onEnable() {
        instance = this

        Bukkit.resetRecipes()

        val miniMessage = MiniMessage.builder().build()

        dataFolder.mkdirs();

        val messages = Configuration.create(
            this,
            "messages.yml",
            Messages::class.java,
            ConfigurationOptions.defaults()
        )

        defaultConfig = Configuration.create(
            this,
            "config.yml",
            Config::class.java,
            ConfigurationOptions.defaults()
        )

        playerTasksConfig = Configuration.create(
            this,
            "playerTasksConfig.yml",
            PlayerTasksConfig::class.java,
            ConfigurationOptions.defaults()
        )

        messages.reloadConfig()
        defaultConfig!!.reloadConfig()
        playerTasksConfig!!.reloadConfig()


        val hikariDataSource = DataSource(this).create()
        charBase = CharBase(hikariDataSource)
        charBase!!.createCharacterTable()
        cacheMap = CacheMap(charBase!!)
        val placeholderExpansion = PlaceholderExpansion(cacheMap!!)
        val landsApi: LandsIntegration = LandsIntegration.of(this)

        if(Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null) {
            PlaceholderExpansion(cacheMap!!).register()
        }


        val cHelper = ChatHelper(miniMessage)

        getCommand("char")!!.setExecutor(
            SetCharacterInfo(this, miniMessage, charBase!!, cHelper, cacheMap!!, messages)
        )

        getCommand("quasarutils")!!.setExecutor(
            MainCommand(this, placeholderExpansion, cacheMap!!)
        )



        val disableRecipes = DisableRecipes(defaultConfig!!)
        disableRecipes.disableRecipes()

        ShaplessCrafts(this).createRecipes()

        Bukkit.updateRecipes()
        Bukkit.updateResources()

        serviceManager = ServiceLoader()

        registerSchedulers(defaultConfig!!, this, cacheMap!!, landsApi)
    }
    private fun registerSchedulers(config: Configuration<Config>, plugin: JavaPlugin, cacheMap: CacheMap, landsApi: LandsIntegration) {
        object : BukkitRunnable() {
            override fun run() {
                server.onlinePlayers.forEach { player ->
                    MoveOnPaths(config, plugin).checkBlock(player)
                }
            }
        }.runTaskTimerAsynchronously(this, 0L, 5L)
        object : BukkitRunnable() {
            override fun run() {
                server.onlinePlayers.forEach { player ->
                    CheckBedLay(landsApi,plugin).checkPlayerLay(player)
                }
            }
        }.runTaskTimerAsynchronously(this, 0L, 20L)
    }


    override fun onDisable() {
        // Plugin shutdown logic


        // disable end_crystal block damage
    }
}