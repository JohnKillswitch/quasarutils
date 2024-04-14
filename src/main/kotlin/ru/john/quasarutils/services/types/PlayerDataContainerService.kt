package ru.john.quasarutils.services.types

import org.bukkit.entity.Player
import ru.john.quasarutils.services.QuasarService
import ru.john.quasarutils.services.ServiceType
import ru.john.quasarutils.util.QuasarPlayer
import java.util.UUID

/*
Сервис для хранения оберток игроков и их UUID
соответственно.
 */
class PlayerDataContainerService : QuasarService("pdc") {
    private val dataContainer: ArrayList<Pair<UUID, QuasarPlayer>> = ArrayList()

    fun addPlayer(player: Player) {
        val wrapped = QuasarPlayer.format(player)
        dataContainer.add(Pair(player.uniqueId, wrapped))
    }

    fun checkPlayer(player: Player) : Boolean {
        return (getPlayerWrappedObject(player) != null)
    }

    fun getPlayerWrappedObject(player: Player) : QuasarPlayer? {
        dataContainer.forEach {
            if(it.first == player.uniqueId) {
                return it.second
            }
        }
        return null
    }

    fun getPlayerWrappedObject(uniqueId: UUID) : QuasarPlayer? {
        dataContainer.forEach {
            if(it.first == uniqueId) {
                return it.second
            }
        }
        return null
    }

    companion object {
        var type = ServiceType.STATIC
    }
}