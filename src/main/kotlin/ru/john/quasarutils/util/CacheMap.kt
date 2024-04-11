package ru.john.quasarutils.util

import org.bukkit.scheduler.BukkitTask
import ru.john.quasarutils.database.CharBase
import kotlin.collections.HashMap

class CacheMap(
    private val charBase: CharBase
) {
    private val cacheMap = HashMap<String, Pair<String, String>>()
    private val firstStageSchedulersMap = HashMap<String, BukkitTask>()

    fun getPlayerInfo (uuid: String): Pair<String, String>? {

        if (this.cacheMap[uuid] != null) return cacheMap[uuid]
        return charBase.getNameAndSurname(uuid)
    }

    fun addPlayerInfo (uuid: String, name: String, surname: String) {
        val pair = Pair(name, surname)
        this.cacheMap[uuid] = pair
    }

    fun isPlayerInMap (uuid: String): Boolean {
        return this.cacheMap.containsKey(uuid)
    }

    fun removePlayerInfo (uuid: String) {
        this.cacheMap.remove(uuid)
    }
}