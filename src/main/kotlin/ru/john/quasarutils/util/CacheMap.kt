package ru.john.quasarutils.util

import com.sk89q.worldguard.util.profile.util.UUIDs
import ru.john.quasarutils.database.MainBase
import java.util.*
import kotlin.collections.HashMap

class CacheMap(
    private val mainBase: MainBase
) {
    private val cacheMap = HashMap<String, Pair<String, String>>()

    fun getPlayerInfo (uuid: String): Pair<String, String>? {

        if (this.cacheMap[uuid] != null) return cacheMap[uuid]
        return mainBase.getNameAndSurname(uuid)
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