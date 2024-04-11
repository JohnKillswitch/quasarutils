package ru.john.quasarutils.events

import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerLoginEvent
import org.bukkit.event.player.PlayerQuitEvent
import ru.john.quasarutils.database.CharBase
import ru.john.quasarutils.util.CacheMap
import java.sql.SQLException

class JoinAndLeaveHandler(
    private val cacheMap: CacheMap,
    private val mainBase: CharBase
) : Listener {
    @EventHandler
    @Throws(SQLException::class)
    private fun onServerJoin(event: PlayerLoginEvent) {
        val pair = mainBase.getNameAndSurname(event.player.uniqueId.toString())
        if (pair.first != "") cacheMap.addPlayerInfo(event.player.uniqueId.toString(), pair.first, pair.second)

    }

    @EventHandler
    private fun onServerLeave(event: PlayerQuitEvent) {

        val uuid = event.player.uniqueId.toString()

        cacheMap.removePlayerInfo(uuid)
    }
}