package ru.john.quasarutils.database

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import org.bukkit.plugin.java.JavaPlugin

class DataSource(
    private val plugin: JavaPlugin
) {

    fun create(): HikariDataSource {

        val hikariConfig = HikariConfig()

        setupConnection(hikariConfig)

        return HikariDataSource(hikariConfig)
    }
    private fun setupConnection(config: HikariConfig) {

        config.setDriverClassName("org.sqlite.JDBC")
        config.jdbcUrl = "jdbc:sqlite:${plugin.dataFolder.toPath().resolve("database.sqlite.db")}"
        config.username = "root"
        config.password = ""

    }
}