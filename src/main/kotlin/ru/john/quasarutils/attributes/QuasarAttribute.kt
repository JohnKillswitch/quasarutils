package ru.john.quasarutils.attributes

import org.bukkit.NamespacedKey
import org.bukkit.persistence.PersistentDataType
import ru.john.quasarutils.QuasarUtils

class QuasarAttribute(
    private val name: String,
    val value: Double,
    val key: NamespacedKey,
    val type: PersistentDataType<Double, Double>
) {
    companion object {

        private val thirstKey = NamespacedKey(QuasarUtils.instance!!, "thirst")

        fun getDefaultAttributes() : ArrayList<QuasarAttribute> {
            val list: ArrayList<QuasarAttribute> = ArrayList()
            // Жажда
            list.add(QuasarAttribute("thirst", 20.0, thirstKey, PersistentDataType.DOUBLE))
            return list
        }
    }
}