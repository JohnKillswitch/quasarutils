package ru.john.quasarutils.util

import org.bukkit.entity.Player
import org.bukkit.persistence.PersistentDataType
import ru.john.quasarutils.attributes.Attribute
import ru.john.quasarutils.attributes.QAttributeInstance

/*
Объект-обертка, отвечающий за хранение различных данных
игрока в удобном ООП виде.
 */
class QPlayer(
    private val player: Player
) {
    val attributes: ArrayList<QAttributeInstance> = ArrayList()
    var drinkCooldown: Boolean = false
    var staminaCooldown: Boolean = false
    var playerDamaged: Boolean = false

    init {
        Attribute.values().forEach { attribute ->
            val value = player.persistentDataContainer.get(attribute.key, PersistentDataType.DOUBLE)
            if(value == null) {
                player.persistentDataContainer.set(attribute.key, PersistentDataType.DOUBLE, attribute.defaultValue)
            } else {
                attributes.add(QAttributeInstance(attribute, value))
            }
        }
    }

    fun setAttribute(attribute: Attribute, value: Double) {
        val instance = Attribute.getAttribute(attribute, this)!!
        instance.value = value
        player.persistentDataContainer.set(attribute.key, PersistentDataType.DOUBLE, value)
    }

    fun getAttribute(attribute: Attribute): QAttributeInstance? {
        return Attribute.getAttribute(attribute, this)
    }
}