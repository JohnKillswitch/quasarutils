package ru.john.quasarutils

import me.angeschossen.lands.api.LandsIntegration
import net.kyori.adventure.text.minimessage.MiniMessage
import org.bukkit.Bukkit
import org.bukkit.plugin.java.JavaPlugin
import org.bukkit.scheduler.BukkitRunnable
import ru.john.quasarutils.commands.MainCommand
import ru.john.quasarutils.commands.SetCharacterInfo
import ru.john.quasarutils.configs.Config
import ru.john.quasarutils.configs.Messages
import ru.john.quasarutils.crafts.ShaplessCrafts
import ru.john.quasarutils.database.DataSource
import ru.john.quasarutils.database.CharBase
import ru.john.quasarutils.events.*
import ru.john.quasarutils.util.CacheMap
import ru.john.quasarutils.util.ChatHelper
import space.arim.dazzleconf.ConfigurationOptions


class QuasarUtils : JavaPlugin() {

    override fun onEnable() {

        Bukkit.resetRecipes()

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
        val charBase = CharBase(hikariDataSource)
        charBase.createCharacterTable()
        val cacheMap = CacheMap(charBase)
        val placeholderExpansion = PlaceholderExpansion(cacheMap)
        val landsApi: LandsIntegration = LandsIntegration.of(this)

        if(Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null) {
            PlaceholderExpansion(cacheMap).register()
        }


        val cHelper = ChatHelper(miniMessage)

        getCommand("char")!!.setExecutor(
            SetCharacterInfo(this, miniMessage, charBase, cHelper, cacheMap, messages)
        )

        getCommand("quasarutils")!!.setExecutor(
            MainCommand(this, placeholderExpansion, cacheMap)
        )



        val disableRecipes = DisableRecipes(config)
        disableRecipes.disableRecipes()

        ShaplessCrafts(this).createRecipes()

        Bukkit.updateRecipes()
        Bukkit.updateResources()

        server.pluginManager.registerEvents(JoinAndLeaveHandler(cacheMap, charBase), this)
        server.pluginManager.registerEvents(OpenStations(this, config), this)
        server.pluginManager.registerEvents(DisableDisabledFishing(config), this)
        server.pluginManager.registerEvents(DisableBreed(), this)
        server.pluginManager.registerEvents(ChangeAnimalDrop(config),this)
        server.pluginManager.registerEvents(PreventHardArmorSwim(config, this), this)
        //server.pluginManager.registerEvents(PreventNotFarmerCrops(config, this), this)
        server.pluginManager.registerEvents(PreventEatingGapple(config, this), this)

        registerSchedulers(config, this, cacheMap, landsApi)


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