package ru.john.quasarutils.util

import io.lumine.mythic.lib.api.item.NBTItem
import org.bukkit.entity.Player

class ArmorUtils {
    companion object {
        fun playerArmorIsHeavy(player: Player): Boolean {
            if (!player.isUnderWater) return false

            val heavyArmorPieces = player.inventory.armorContents
                .filterNotNull()
                .count { item ->
                    NBTItem.get(item).tags.contains("MMOITEMS_DISPLAYED_TYPE") &&
                            NBTItem.get(item)["MMOITEMS_DISPLAYED_TYPE"].toString().filterNot { it == '"' } == "&fТяжёлая броня" }

            return heavyArmorPieces > 0
        }
    }
}