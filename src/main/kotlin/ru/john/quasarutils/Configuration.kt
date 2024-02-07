package ru.john.quasarutils

import net.bytebuddy.implementation.bytecode.Throw
import org.bukkit.plugin.Plugin
import space.arim.dazzleconf.ConfigurationOptions
import space.arim.dazzleconf.error.ConfigFormatSyntaxException
import space.arim.dazzleconf.error.InvalidConfigException
import space.arim.dazzleconf.ext.snakeyaml.CommentMode
import space.arim.dazzleconf.ext.snakeyaml.SnakeYamlConfigurationFactory
import space.arim.dazzleconf.ext.snakeyaml.SnakeYamlOptions
import space.arim.dazzleconf.helper.ConfigurationHelper
import java.io.IOException
import java.nio.file.Path
import kotlin.concurrent.Volatile

class Configuration<C> private constructor(
    private val configHelper: ConfigurationHelper<C>
) {
    @Volatile
    private var configData: C? = null
    fun reloadConfig() {
        try {
            configData = configHelper.reloadConfigData()
        } catch (ex: IOException) {
            Throw.valueOf(ex.toString())
        } catch (ex: ConfigFormatSyntaxException) {
            loadDefault()
        } catch (ex: InvalidConfigException) {
            loadDefault()
        }
    }

    private fun loadDefault() {
        configData = configHelper.factory.loadDefaults()
    }

    fun data(): C? {
        if (configData == null) {
            reloadConfig()
        }
        return configData
    }

    companion object {
        fun <C> create(
            plugin: Plugin,
            fileName: String?,
            configClass: Class<C>?,
            options: ConfigurationOptions?
        ): Configuration<C> {
            return create(plugin.dataFolder.toPath(), fileName, configClass, options)
        }

        fun <C> create(
            configFolder: Path?,
            fileName: String?,
            configClass: Class<C>?,
            options: ConfigurationOptions?
        ): Configuration<C> {
            val yamlOptions = SnakeYamlOptions.Builder()
                .commentMode(CommentMode.alternativeWriter())
                .build()
            val configFactory = SnakeYamlConfigurationFactory.create(
                configClass,
                options,  // change this if desired
                yamlOptions
            )
            return Configuration(ConfigurationHelper(configFolder, fileName, configFactory))
        }
    }
}
