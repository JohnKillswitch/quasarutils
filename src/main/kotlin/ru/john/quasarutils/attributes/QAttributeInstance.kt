package ru.john.quasarutils.attributes

import org.bukkit.NamespacedKey

class QAttributeInstance {

    val attribute: Attribute

    var value: Double
    constructor(attribute: Attribute, value: Double) {
        this.attribute = attribute
        this.value = value
    }

    constructor(attribute: Attribute) {
        this.attribute = attribute
        this.value = attribute.defaultValue
    }
}
    
//    companion object {
//
//        private val thirstKey = NamespacedKey(QuasarUtils.instance!!, "thirst")
//        private val staminaKey = NamespacedKey(QuasarUtils.instance!!, "stamina")
//
//        fun<P, C> getDefaultAttributes() : ArrayList<QuasarAttribute<P, C>> {
//            val list: ArrayList<QuasarAttribute<P, C>> = ArrayList()
//            val config = QuasarUtils.playerActionsConfig!!.data()!!
//
//            list.add(DAttribute("thirst", config.thirstMaxValue().toDouble(), thirstKey) as QuasarAttribute<P, C>)
//            list.add(DAttribute("stamina", config.maxStaminaValue().toDouble(), staminaKey) as QuasarAttribute<P, C>)
//
//            return list
//        }
//
//        fun<P, C> format(clazz: Any) : QuasarAttribute<P, C>? {
//            if(clazz is QuasarAttribute<*, *>) return clazz as QuasarAttribute<P, C>
//            return null
//        }
//    }
// }