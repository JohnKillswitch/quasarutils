package ru.john.quasarutils

import com.destroystokyo.paper.util.SneakyThrow
import org.bukkit.plugin.Plugin
import org.slf4j.Logger
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
    private val logger: Logger,
    private val configHelper: ConfigurationHelper<C>
) {
    @Volatile
    private var configData: C? = null
    fun reloadConfig() {
        try {
            configData = configHelper.reloadConfigData()
        } catch (ex: IOException) {
            SneakyThrow.sneaky(ex)
        } catch (ex: ConfigFormatSyntaxException) {
            loadDefault()
            logger.error(
                "The yaml syntax in your configuration is invalid. "
                        + "Check your YAML syntax with a tool such as https://yaml-online-parser.appspot.com/", ex
            )
        } catch (ex: InvalidConfigException) {
            loadDefault()
            logger.error(
                ("One of the values in your configuration is not valid. "
                        + "Check to make sure you have specified the right data types."), ex
            )
        }
    }

    private fun loadDefault() {
        logger.error("Failed to load configuration! Loading defaults!")
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
            return create(plugin.slF4JLogger, plugin.dataFolder.toPath(), fileName, configClass, options)
        }

        fun <C> create(
            logger: Logger,
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
            return Configuration(logger, ConfigurationHelper(configFolder, fileName, configFactory))
        }
    }
}
