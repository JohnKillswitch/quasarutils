package ru.john.quasarutils.runnables

import org.bukkit.GameMode
import org.bukkit.entity.Player
import org.bukkit.potion.PotionEffect
import org.bukkit.potion.PotionEffectType
import ru.john.quasarutils.QuasarUtils
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
        val wrappedObject = service.getPlayerWrappedObject(player)!!
        if(wrappedObject.staminaCooldown) return

        val attribute = wrappedObject.getAttribute("stamina")!!
        val config = QuasarUtils.playerActionsConfig!!.data()!!

        if(player.isSprinting) {

            if(attribute.value-config.staminaPerRun() <= 0) {
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
                wrappedObject.setAttribute("stamina", 0.0)
                wrappedObject.staminaCooldown = true

                // Сбрасываем кулдаун через время в конфиге
                instance.server.scheduler.runTaskLater(instance, Runnable {
                    wrappedObject.staminaCooldown = false
                }, config.staminaCooldownDuration().toLong())
                return
            }

            val minusStamina = String.format(
                "%.1f", attribute.value-config.staminaPerRun()
            ).toDouble()
            wrappedObject.setAttribute(
                "stamina",
                minusStamina
            )
            return
        }

        val plusStamina = String.format(
            "%.1f", attribute.value+config.staminaHeal()
        ).toDouble()

        if(plusStamina >= config.maxStaminaValue()) {
            wrappedObject.setAttribute("stamina", config.maxStaminaValue().toDouble())
            return
        }
        wrappedObject.setAttribute("stamina", plusStamina)
    }

}