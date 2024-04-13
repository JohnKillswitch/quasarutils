package ru.john.quasarutils.runnables

import org.bukkit.entity.Player
import ru.john.quasarutils.QuasarUtils
import ru.john.quasarutils.util.PlayerRunnable

/* штучка, которая просто выводит кастомные аттрибуты
является просто тестом, в скором времени будет удален
*/
class CheckPDC(
    private val player: Player
) : PlayerRunnable(player, 200L, 0L) {

    override fun run() {
        if(!player.isOnline) cancel()

        val service = QuasarUtils.serviceManager!!.getPlayerDataContainerService()
        val wrappedPlayer = service.getPlayerWrappedObject(player)
        player.sendMessage(wrappedPlayer!!.getAttributes()[0].key.key)
        player.sendMessage(wrappedPlayer!!.getAttributes()[0].value.toString())
    }
}