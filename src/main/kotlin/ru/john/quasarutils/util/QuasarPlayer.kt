package ru.john.quasarutils.util

import org.bukkit.entity.Player
import org.bukkit.persistence.PersistentDataContainer
import org.bukkit.persistence.PersistentDataType
import ru.john.quasarutils.attributes.QuasarAttribute
import java.util.UUID

/*
Объект-обертка, отвечающий за хранение различных данных
игрока в удобном ООП виде.
 */
class QuasarPlayer(
    private val uuid: UUID
) {
    private val attributes: ArrayList<QuasarAttribute> = ArrayList()

    init { if(attributes.isEmpty()) QuasarAttribute.getDefaultAttributes() }

    constructor(uuid: UUID, attr: ArrayList<QuasarAttribute>) : this(uuid) {
        attributes.addAll(attr)
    }

    fun getAttributes() : ArrayList<QuasarAttribute> {
        val clone = ArrayList<QuasarAttribute>()
        clone.addAll(attributes)
        return clone
    }

    companion object {
        fun format(player: Player) : QuasarPlayer {
            val pDC: PersistentDataContainer = player.persistentDataContainer
            val attr: ArrayList<QuasarAttribute> = ArrayList()

            QuasarAttribute.getDefaultAttributes().forEach {
                val value = pDC.get(it.key, it.type)
                if(value == null) {
                    pDC.set(it.key, it.type, it.value)
                    attr.add(it)
                } else {
                    attr.add(QuasarAttribute(it.key.namespace, value, it.key, it.type))
                }
            }
            return QuasarPlayer(player.uniqueId, attr)
        }
    }
}