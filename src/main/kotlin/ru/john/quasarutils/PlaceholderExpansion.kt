package ru.john.quasarutils

import me.clip.placeholderapi.expansion.PlaceholderExpansion
import org.bukkit.OfflinePlayer
import org.bukkit.entity.Player
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
            "name" -> return cacheMap.getPlayerInfo(player?.uniqueId.toString())?.first!!
            "surname" -> return cacheMap.getPlayerInfo(player?.uniqueId.toString())?.second!!
        }
        return "some_error"

    }
}