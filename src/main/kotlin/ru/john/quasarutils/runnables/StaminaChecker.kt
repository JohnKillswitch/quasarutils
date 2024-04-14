package ru.john.quasarutils.runnables

import org.bukkit.GameMode
import org.bukkit.entity.Player
import ru.john.quasarutils.util.PlayerRunnable

class StaminaChecker(
    private val player: Player
) : PlayerRunnable(player, 0L, 20L) {
    override fun run() {
        if(!player.isOnline) cancel()
        if(player.isDead) return
        if(player.gameMode == GameMode.CREATIVE || player.gameMode == GameMode.SPECTATOR) return

        if(player.isSprinting) {

        }
    }

}