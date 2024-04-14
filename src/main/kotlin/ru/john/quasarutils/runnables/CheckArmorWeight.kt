package ru.john.quasarutils.runnables

import org.bukkit.Location
import org.bukkit.Material
import org.bukkit.Sound
import org.bukkit.block.Block
import org.bukkit.entity.Player
import ru.john.quasarutils.Configuration
import ru.john.quasarutils.QuasarUtils
import ru.john.quasarutils.configs.PlayerActionsConfig
import ru.john.quasarutils.util.ArmorUtils
import ru.john.quasarutils.util.PlayerRunnable
import java.util.Random

/*
Раннейбл для проверки тяжести брони,
при совпадении всех условий - ломает блок под игроком
 */
class CheckArmorWeight(
    private val player: Player,
) : PlayerRunnable(player, 0L, 20L) {
    override fun run() {
        if(!player.isOnline) cancel()
        if(player.isDead) return
        if(!ArmorUtils.playerArmorIsHeavy(player)) return

        val config: Configuration<PlayerActionsConfig> = QuasarUtils.playerActionsConfig!!
        val location = player.location
        val locUnderPlayer: Location = location.subtract(0.0, 1.0, 0.0)
        val locWorld = location.world
        val blockUnderPlayer: Block = locWorld.getBlockAt(locUnderPlayer)
        val blockMaterial: Material = blockUnderPlayer.type

        config?.data()?.notAllowedForWeightArmor()!!.forEach {
            val blockStr = it.split(":")
            if(blockStr.size != 2) return

            if(blockStr[0].contains(blockMaterial.name)) {
                val chance = Random().nextDouble()
                if(chance >= config.data()?.blockBreakChance()!!) return

                blockUnderPlayer.type = Material.AIR
                player.velocity = player.velocity.multiply(-1)
                player.velocity = player.velocity.setY(-0.5)
                try {
                    player.playSound(blockUnderPlayer.location, Sound.valueOf(blockStr[1]), 1f, 0f)
                } catch (ignore: Exception) {
                    return
                }
            }
        }
    }

}