package ru.john.quasarutils.events

import net.Indyuce.mmoitems.MMOItems
import org.bukkit.entity.Ageable
import org.bukkit.entity.Entity
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.EntityDeathEvent
import org.bukkit.inventory.ItemStack
import ru.john.quasarutils.Configuration
import ru.john.quasarutils.configs.Config


class ChangeAnimalDrop(
    private val config: Configuration<Config>
) : Listener {

    @EventHandler
    fun checkEntity(event: EntityDeathEvent) {
        val animalList = this.config.data()?.changedAnimal()
        val entity  = event.entity

        if (animalList?.contains(entity.type.name) == false || entity !is Ageable) return

        event.drops.clear()
        event.drops.add(changeDrop(entity))
    }

    private fun changeDrop (animal: Entity): ItemStack? {
        val mmoitem = MMOItems.plugin.getMMOItem(MMOItems.plugin.types["CONSUMABLE"], "${animal.type.name}_TUSHA")
        return mmoitem!!.newBuilder().build()

    }
}