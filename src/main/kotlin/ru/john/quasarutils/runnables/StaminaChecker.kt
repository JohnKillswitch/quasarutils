package ru.john.quasarutils.runnables

import org.bukkit.GameMode
import org.bukkit.entity.Player
import org.bukkit.potion.PotionEffect
import org.bukkit.potion.PotionEffectType
import ru.john.quasarutils.QuasarUtils
import ru.john.quasarutils.attributes.Attribute
import ru.john.quasarutils.util.PlayerRunnable
import kotlin.math.roundToInt

class StaminaChecker(
    private val player: Player
) : PlayerRunnable(player, 0L, 20L) {
    override fun run() {
        if(!player.isOnline) cancel()
        if(player.isDead) return
        if(player.gameMode == GameMode.CREATIVE || player.gameMode == GameMode.SPECTATOR) return

        val service = QuasarUtils.serviceManager!!.getPlayerDataContainerService()
        val qPlayer = service.getPlayer(player)!!
        if(qPlayer.staminaCooldown) return

        val attribute = qPlayer.getAttribute(Attribute.STAMINA)!!
        val config = QuasarUtils.playerActionsConfig!!.data()!!

        if(player.isSprinting) {

            if((attribute.value as Double)-config.staminaPerRun() <= 0) {
                val instance = QuasarUtils.instance!!

                player.addPotionEffect(
                    PotionEffect(
                        PotionEffectType.SLOW,
                        config.staminaSlowEffectDuration(),
                        3,
                        false,
                        false,
                        false
                    )
                )

                qPlayer.setAttribute(Attribute.STAMINA, 0.0)
                qPlayer.staminaCooldown = true

                // Сбрасываем кулдаун через время в конфиге
                instance.server.scheduler.runTaskLater(instance, Runnable {
                    qPlayer.staminaCooldown = false
                }, config.staminaCooldownDuration().toLong())
                return
            }

            val minusStamina = String.format(
                "%.1f", (attribute.value)-config.staminaPerRun()
            ).toDouble()
            qPlayer.setAttribute(
                Attribute.STAMINA,
                minusStamina
            )
            return
        }

        val plusStamina = String.format(
            "%.1f", (attribute.value)+config.staminaHeal()
        ).toDouble()

        if(plusStamina >= config.maxStaminaValue()) {
            qPlayer.setAttribute(Attribute.STAMINA, config.maxStaminaValue().toDouble())
            return
        }
        qPlayer.setAttribute(Attribute.STAMINA, plusStamina)
    }

    companion object {
        val onJoin: Boolean = true
    }

}