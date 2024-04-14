package ru.john.quasarutils.services.types

import org.bukkit.entity.Player
import ru.john.quasarutils.QuasarUtils.Companion.instance
import ru.john.quasarutils.QuasarUtils.Companion.reflections
import ru.john.quasarutils.QuasarUtils.Companion.serviceManager
import ru.john.quasarutils.services.QuasarService
import ru.john.quasarutils.services.ServiceType
import ru.john.quasarutils.util.PlayerRunnable

/*
Динамический сервис, необходимый для создания потоков,
постоянно отслеживающих игрока(с разным функционалом).
Ищет все классы, наследующие PlayerRunnable и запускает их.
Вызывается, когда игрок заходит на сервер
*/
class PlayerRunnablesService(player: Player) : QuasarService(
    "pr_" + player.uniqueId
) {
    private val runnables = ArrayList<Int>()

    init {
        val objects = reflections.getSubTypesOf(
            PlayerRunnable::class.java
        )
        val instance = instance
        objects.forEach {
            val runnable: PlayerRunnable
            try {
                runnable = it.getDeclaredConstructor(Player::class.java).newInstance(player)
                runnable.runTaskTimer(instance!!, runnable.delay, runnable.period)
                runnables.add(runnable.taskId)
            } catch (e: Exception) {
                throw RuntimeException(e)
            }
        }
        serviceManager!!.dynamicServices.add(this)
    }

    companion object {
        var type = ServiceType.DYNAMIC
    }
}