package ru.john.quasarutils.events

import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerLoginEvent
import org.bukkit.event.player.PlayerQuitEvent
import ru.john.quasarutils.database.MainBase
import ru.john.quasarutils.util.CacheMap
import java.sql.SQLException

class JoinAndLeaveHandler(
    private val cacheMap: CacheMap,
    private val mainBase: MainBase
) : Listener {
    @EventHandler
    @Throws(SQLException::class)
    private fun onServerJoin(event: PlayerLoginEvent) {
        val pair = mainBase.getNameAndSurname(event.player.uniqueId.toString())
        if (pair.first != "") cacheMap.addPlayerInfo(event.player.uniqueId.toString(), pair.first, pair.second)

        //this.cacheMap.addPlayerVirus(this.mainBase.getPlayerVirus(event.player.uniqueId.toString())?.owner?.uniqueId.toString())

        this.cacheMap.addPlayerVirus(event.player.uniqueId.toString())

    }

    @EventHandler
    private fun onServerLeave(event: PlayerQuitEvent) {

        val uuid = event.player.uniqueId.toString()

        cacheMap.removePlayerInfo(uuid)

        this.cacheMap.removePlayerVirus(uuid)
        this.cacheMap.removeStageSchedulersMap(uuid)
    }
}