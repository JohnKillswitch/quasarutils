package ru.john.quasarutils.services.types

import org.bukkit.event.Listener
import ru.john.quasarutils.QuasarUtils
import ru.john.quasarutils.services.QuasarService
import ru.john.quasarutils.services.ServiceType

class EventService : QuasarService("event") {

    init {
        val events = QuasarUtils.reflections.getSubTypesOf(Listener::class.java)
        val instance = QuasarUtils.instance!!
        events.forEach {
            instance.logger.info(String.format("Ивент [%s] зарегистрирован.", it.simpleName))
            val listener = it.getDeclaredConstructor().newInstance()
            instance.server.pluginManager.registerEvents(listener, instance)
        }
    }
    companion object {
        var type = ServiceType.STATIC
    }
}