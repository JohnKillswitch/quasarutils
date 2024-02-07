package ru.john.quasarutils.util

import com.sk89q.worldguard.util.profile.util.UUIDs
import jdk.jfr.Timestamp
import org.bukkit.OfflinePlayer
import org.bukkit.scheduler.BukkitTask
import ru.john.quasarutils.database.MainBase
import java.time.Instant
import java.util.*
import kotlin.collections.HashMap

class CacheMap(
    private val mainBase: MainBase
) {
    private val cacheMap = HashMap<String, Pair<String, String>>()
    private val virusCacheMap = HashMap<String, PlayerVirus?>()
    private val firstStageSchedulersMap = HashMap<String, BukkitTask>()

    fun getPlayerVirus(uuid: String): PlayerVirus? {
        if (this.virusCacheMap.containsKey(uuid)) return virusCacheMap[uuid]
        return null

    }

    fun addPlayerVirus(uuid: String) {
        this.mainBase.getPlayerVirus(uuid)?.let { this.virusCacheMap.put(uuid, it) }
    }

    fun removePlayerVirus(uuid: String) {
        this.virusCacheMap.remove(uuid)
    }

    fun getStageSchedulersMap(uuid: String) =
        this.firstStageSchedulersMap[uuid]

    fun addStageSchedulersMap(uuid: String, task: BukkitTask) {
        this.firstStageSchedulersMap.putIfAbsent(uuid, task)
    }
    fun removeStageSchedulersMap(uuid: String) {
        this.firstStageSchedulersMap[uuid]?.cancel()
        this.firstStageSchedulersMap.remove(uuid)
    }


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