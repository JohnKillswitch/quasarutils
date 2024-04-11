package ru.john.quasarutils

import me.clip.placeholderapi.expansion.PlaceholderExpansion
import org.bukkit.Bukkit
import org.bukkit.OfflinePlayer
import ru.john.quasarutils.util.CacheMap


class PlaceholderExpansion(
    private val cacheMap: CacheMap
) : PlaceholderExpansion() {
    override fun getAuthor(): String {
        return "KeizuMoon"
    }

    override fun getIdentifier(): String {
        return "quasarutils"
    }

    override fun getVersion(): String {
        return "1.0.0"
    }

    override fun onRequest(player: OfflinePlayer, params: String): String {
        when (params) {
            "name" -> return cacheMap.getPlayerInfo(player.uniqueId.toString())?.first!!.replaceFirstChar { char -> char.uppercase() }
            "surname" -> return cacheMap.getPlayerInfo(player.uniqueId.toString())?.second!!.replaceFirstChar { char -> char.uppercase() }
        }
        return "some_error"

    }
}