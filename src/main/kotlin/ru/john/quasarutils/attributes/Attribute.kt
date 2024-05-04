package ru.john.quasarutils.attributes

import org.bukkit.NamespacedKey
import org.bukkit.entity.Player
import ru.john.quasarutils.QuasarUtils
import ru.john.quasarutils.util.QPlayer

enum class Attribute(val key: NamespacedKey, val defaultValue: Double) {
    STAMINA(
        NamespacedKey(QuasarUtils.instance!!, "stamina"),
        QuasarUtils.playerActionsConfig!!.data()!!.maxStaminaValue().toDouble()
    ),
    THIRST(
        NamespacedKey(QuasarUtils.instance!!, "thirst"),
        QuasarUtils.playerActionsConfig!!.data()!!.thirstMaxValue().toDouble()
    );

    companion object {
        fun getDefaultAttributes() : ArrayList<QAttributeInstance> {
            val list: ArrayList<QAttributeInstance> = ArrayList()
            Attribute.values().forEach { attribute ->
                list.add(QAttributeInstance(attribute))
            }
            return list
        }

        fun getAttribute(attribute: Attribute, player: QPlayer) : QAttributeInstance? {
            player.attributes.forEach { attr: QAttributeInstance ->
                if(attr.attribute.key == attribute.key) return attr
            }
            return null
        }
    }

}