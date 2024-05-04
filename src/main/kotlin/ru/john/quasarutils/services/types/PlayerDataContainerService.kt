package ru.john.quasarutils.services.types

import org.bukkit.entity.Player
import ru.john.quasarutils.services.QuasarService
import ru.john.quasarutils.services.ServiceType
import ru.john.quasarutils.util.QPlayer
import java.util.UUID

/*
Сервис для хранения оберток игроков и их UUID
соответственно.
 */
class PlayerDataContainerService : QuasarService("pdc") {
    private val dataContainer: ArrayList<Pair<UUID, QPlayer>> = ArrayList()

    fun addPlayer(player: Player) {
        val qInstance = QPlayer(player)
        dataContainer.add(Pair(player.uniqueId, qInstance))
    }

    fun getPlayer(player: Player) : QPlayer? {
        var findVal: Pair<UUID, QPlayer>? = null
        dataContainer.forEach{element ->
            if(element.first == player.uniqueId) findVal = element
        }
        if(findVal == null) return null
        return findVal!!.second
    }

    fun checkPlayer(player: Player) : Boolean {
        return getPlayer(player) != null
    }

    fun removePlayer(player: Player) {
        var findVal: Pair<UUID, QPlayer>? = null
        dataContainer.forEach{element ->
            if(element.first == player.uniqueId) findVal = element
        }
        if(findVal == null) return
        dataContainer.remove(findVal!!)
    }

    companion object {
        var type = ServiceType.STATIC
    }
}