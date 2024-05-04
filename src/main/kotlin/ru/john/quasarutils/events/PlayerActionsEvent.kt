package ru.john.quasarutils.events

import org.bukkit.GameMode
import org.bukkit.Material
import org.bukkit.Sound
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.Action
import org.bukkit.event.entity.EntityDamageByEntityEvent
import org.bukkit.event.entity.PlayerDeathEvent
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.event.player.PlayerItemConsumeEvent
import org.bukkit.event.player.PlayerMoveEvent
import org.bukkit.inventory.EquipmentSlot
import org.bukkit.potion.PotionEffect
import org.bukkit.potion.PotionEffectType
import ru.john.quasarutils.QuasarUtils
import ru.john.quasarutils.attributes.Attribute
import ru.john.quasarutils.util.QPlayer

class PlayerActionsEvent : Listener {

    @EventHandler
    fun drinkUseHands(event: PlayerInteractEvent) {
        if(!event.player.inventory.itemInMainHand.isEmpty) return
        if(!event.player.isSneaking) return
        if(event.action != Action.RIGHT_CLICK_BLOCK) return
        if(event.hand!! != EquipmentSlot.HAND) return

        val config = QuasarUtils.playerActionsConfig!!.data()!!

        val list = event.player.getLineOfSight(null, config.blockThirstAddMaxDistance())
        var flag = false

        list.forEach {
            if(it.type == Material.WATER) {
                flag = true
            }
        }
        if(!flag) return

        val instance = QuasarUtils.instance!!
        val qPlayer: QPlayer = QuasarUtils.serviceManager!!.getPlayerDataContainerService().getPlayer(event.player)!!

        // Проверка на кулдаун
        if(qPlayer.drinkCooldown) return

        val currThirst = qPlayer.getAttribute(Attribute.THIRST)!!
        var value = config.thirstMaxValue().toDouble()
        // Лимит на максимальное количество жажды
        if(currThirst.value+config.blockThirstAdding() < value) {
            value = (currThirst.value + config.blockThirstAdding())
        }

        qPlayer.setAttribute(Attribute.THIRST, value)
        qPlayer.drinkCooldown = true

        var loc = event.player.location
        loc = loc.add(0.0, 1.0, 0.0)
        event.player.playSound(loc, Sound.valueOf(config.handDrinkWaterSound()), 1f, 1f)

        // Сбрасываем кулдаун через время в конфиге
        instance.server.scheduler.runTaskLater(instance, Runnable {
            qPlayer.drinkCooldown = false
        }, config.handDrinkWaterCooldown().toLong())
    }

    @EventHandler
    fun healStats(event: PlayerDeathEvent) {
        val qPlayer: QPlayer = QuasarUtils.serviceManager!!.getPlayerDataContainerService().getPlayer(event.player)!!
        val config = QuasarUtils.playerActionsConfig!!.data()!!

        qPlayer.setAttribute(Attribute.THIRST, config.thirstMaxValue().toDouble())
        qPlayer.setAttribute(Attribute.STAMINA, config.maxStaminaValue().toDouble())
    }

    @EventHandler
    fun drinkFromBottle(event: PlayerItemConsumeEvent) {
        val config = QuasarUtils.playerActionsConfig!!.data()!!
        val materials : ArrayList<Pair<Material, Int>> = ArrayList()
        config.itemsThirstAdding().forEach {
            try {
                val str = it.split(":")
                materials.add(Pair(Material.valueOf(str[0]), str[1].toInt()))
            } catch (ignore: Exception) {}
        }

        val pair = containsInPairs(materials, event.item.type) ?: return

        val qPlayer: QPlayer = QuasarUtils.serviceManager!!.getPlayerDataContainerService().getPlayer(event.player)!!

        val currThirst = qPlayer.getAttribute(Attribute.THIRST)!!
        var value = config.thirstMaxValue().toDouble()
        // Лимит на максимальное количество жажды
        if(currThirst.value+pair.second < value) {
            value = (currThirst.value + pair.second)
        }

        qPlayer.setAttribute(Attribute.THIRST, value)
    }

    @EventHandler
    fun detectDamage(event: EntityDamageByEntityEvent) {
        if(event.entity !is Player) return

        val service = QuasarUtils.serviceManager!!.getPlayerDataContainerService()
        val qPlayer = service.getPlayer(event.entity as Player)
        qPlayer!!.playerDamaged = true
        val instance = QuasarUtils.instance!!
        instance.server.scheduler.runTaskLater(instance, Runnable {
            qPlayer.playerDamaged = false
        }, 10L)
    }

    @EventHandler
    fun removeStamina(event: PlayerMoveEvent) {
        if((event.to.y <= event.from.y) || (event.to.y - 0.5 == event.from.y)) return

        if(event.player.gameMode == GameMode.CREATIVE || event.player.gameMode == GameMode.SPECTATOR) return
        val block = event.player.location.block.type

        val service = QuasarUtils.serviceManager!!.getPlayerDataContainerService()
        val qPlayer = service.getPlayer(event.player)!!

        val attribute = qPlayer.getAttribute(Attribute.STAMINA)!!
        val config = QuasarUtils.playerActionsConfig!!.data()!!

        if(event.player.isClimbing || event.player.isInWater || qPlayer.playerDamaged) return

        if(qPlayer.staminaCooldown) event.isCancelled = true

        if(attribute.value-config.staminaPerJump() <= 0) {
            val instance = QuasarUtils.instance!!

            event.player.addPotionEffect(
                PotionEffect(
                    PotionEffectType.SLOW,
                    config.staminaSlowEffectDuration()*20,
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
        qPlayer.setAttribute(
            Attribute.STAMINA,
            String.format(
                "%.1f", attribute.value-config.staminaPerJump()
            )
                .toDouble()
        )
    }

    private fun containsInPairs(pairs : ArrayList<Pair<Material, Int>>, material: Material) : Pair<Material, Int>? {
        pairs.forEach {
            if(it.first == material) return it
        }
        return null
    }

}