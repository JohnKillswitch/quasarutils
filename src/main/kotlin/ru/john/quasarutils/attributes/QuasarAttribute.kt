package ru.john.quasarutils.attributes

import org.bukkit.NamespacedKey
import org.bukkit.persistence.PersistentDataType
import ru.john.quasarutils.QuasarUtils

class QuasarAttribute(
    private val name: String,
    var value: Double,
    val key: NamespacedKey,
    val type: PersistentDataType<Double, Double>
) {
    companion object {

        private val thirstKey = NamespacedKey(QuasarUtils.instance!!, "thirst")
        private val staminaKey = NamespacedKey(QuasarUtils.instance!!, "stamina")

        fun getDefaultAttributes() : ArrayList<QuasarAttribute> {
            val list: ArrayList<QuasarAttribute> = ArrayList()
            val config = QuasarUtils.playerActionsConfig!!.data()!!
            // Жажда и стамина
            list.add(QuasarAttribute("thirst", config.thirstMaxValue().toDouble(), thirstKey, PersistentDataType.DOUBLE))
            list.add(QuasarAttribute("stamina", config.maxStaminaValue().toDouble(), staminaKey, PersistentDataType.DOUBLE))
            return list
        }
    }
}