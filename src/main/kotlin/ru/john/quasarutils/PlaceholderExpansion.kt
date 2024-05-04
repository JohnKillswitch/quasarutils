package ru.john.quasarutils

import me.clip.placeholderapi.expansion.PlaceholderExpansion
import org.bukkit.Bukkit
import org.bukkit.OfflinePlayer
import org.bukkit.entity.Player
import ru.john.quasarutils.attributes.Attribute
import ru.john.quasarutils.util.CacheMap


class PlaceholderExpansion : PlaceholderExpansion() {
    override fun getAuthor(): String {
        return "KeizuMoon, Waind"
    }

    override fun getIdentifier(): String {
        return "quasarutils"
    }

    override fun getVersion(): String {
        return "1.0.0"
    }
    
    override fun persist() : Boolean {
        return true
    }

    override fun onPlaceholderRequest(player: Player?, params: String): String {
        val cacheMap = QuasarUtils.cacheMap
        when (params) {
            "name" -> return cacheMap!!.getPlayerInfo(player!!.uniqueId.toString())?.first!!.replaceFirstChar { char -> char.uppercase() }
            "surname" -> return cacheMap!!.getPlayerInfo(player!!.uniqueId.toString())?.second!!.replaceFirstChar { char -> char.uppercase() }
            "thirst" -> {
                val service = QuasarUtils.serviceManager!!.getPlayerDataContainerService()
                return service.getPlayer(player!!)!!.getAttribute(Attribute.THIRST)!!.value.toString()
            }
            "maxThirst" -> {
                val config = QuasarUtils.playerActionsConfig!!.data()!!
                return config.thirstMaxValue().toString()
            }
            "stamina" -> {
                val service = QuasarUtils.serviceManager!!.getPlayerDataContainerService()
                return service.getPlayer(player!!)!!.getAttribute(Attribute.STAMINA)!!.value.toString()
            }
            "maxStamina" -> {
                val config = QuasarUtils.playerActionsConfig!!.data()!!
                return config.maxStaminaValue().toString()
            }
        }
        return "some_error"

    }
}