package ru.john.quasarutils.events

import org.bukkit.Material
import org.bukkit.Sound
import org.bukkit.block.Block
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.Action
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.event.player.PlayerItemConsumeEvent
import org.bukkit.inventory.EquipmentSlot
import org.bukkit.scheduler.BukkitRunnable
import ru.john.quasarutils.QuasarUtils
import ru.john.quasarutils.util.QuasarPlayer

class WaterDrinkEvent : Listener {

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
        val wrappedObject = QuasarUtils.serviceManager!!
            .getPlayerDataContainerService().getPlayerWrappedObject(event.player)!!
        // Проверка на кулдаун
        if(wrappedObject.drinkCooldown) return

        val currThirst = wrappedObject.getAttribute("thirst")!!
        var value = config.thirstMaxValue().toDouble()
        // Лимит на максимальное количество жажды
        if(currThirst.value+config.blockThirstAdding() < value) {
            value = (currThirst.value + config.blockThirstAdding())
        }
        wrappedObject.setAttribute("thirst", value)
        wrappedObject.drinkCooldown = true

        var loc = event.player.location
        loc = loc.add(0.0, 1.0, 0.0)
        event.player.playSound(loc, Sound.valueOf(config.handDrinkWaterSound()), 1f, 1f)

        // Сбрасываем кулдаун через время в конфиге
        instance.server.scheduler.runTaskLater(instance, Runnable {
            wrappedObject.drinkCooldown = false
        }, config.handDrinkWaterCooldown().toLong())
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

        val wrappedObject = QuasarUtils.serviceManager!!
                .getPlayerDataContainerService().getPlayerWrappedObject(event.player)!!

        val currThirst = wrappedObject.getAttribute("thirst")!!
        var value = config.thirstMaxValue().toDouble()
        // Лимит на максимальное количество жажды
        if(currThirst.value+pair.second < value) {
            value = (currThirst.value + pair.second)
        }

        wrappedObject.setAttribute("thirst", value)
    }

    private fun containsInPairs(pairs : ArrayList<Pair<Material, Int>>, material: Material) : Pair<Material, Int>? {
        pairs.forEach {
            if(it.first == material) return it
        }
        return null
    }

}