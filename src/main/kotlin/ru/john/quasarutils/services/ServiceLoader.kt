package ru.john.quasarutils.services

import org.bukkit.entity.Player
import ru.john.quasarutils.QuasarUtils
import ru.john.quasarutils.services.types.EventService
import ru.john.quasarutils.services.types.PlayerDataContainerService
import ru.john.quasarutils.services.types.PlayerRunnablesService

/*
ServiceLoader - менеджер сервисов, который отвечает за
запуск статичных сервисов во время старта сервера,
а также хранение всех сервисов.

 */
class ServiceLoader(
    private val services: ArrayList<QuasarService> = ArrayList(),
    val dynamicServices: ArrayList<QuasarService> = ArrayList()
) {

    init {
        val serviceImpl = QuasarUtils.reflections.getSubTypesOf(QuasarService::class.java)
        serviceImpl.forEach {

            val typeField = it.getDeclaredField("type")
            typeField.isAccessible = true

            if(typeField.get(it).equals(ServiceType.STATIC)) {

                val service = it.getDeclaredConstructor().newInstance()
                services.add(service)
            }
        }
    }

    private fun getService(name: String, dynamic: Boolean): QuasarService? {
        var list: ArrayList<QuasarService> = services
        if (dynamic) list = dynamicServices

        list.forEach {
            if(it.name == name) return it
        }
        return null
    }

    fun getEventService() : EventService {
        return getService("event", false) as EventService
    }

    fun getPlayerDataContainerService() : PlayerDataContainerService {
        return getService("pdc", false) as PlayerDataContainerService
    }

    fun getPlayerRunnablesService(player: Player) : PlayerRunnablesService {
        return getService("pr_" + player.uniqueId, true) as PlayerRunnablesService
    }

    fun removePlayerRunnablesService(player: Player) {
        dynamicServices.remove(getPlayerRunnablesService(player))
    }
}