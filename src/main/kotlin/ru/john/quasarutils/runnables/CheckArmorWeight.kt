package ru.john.quasarutils.runnables

import me.angeschossen.lands.api.utils.PlayerUtils
import org.bukkit.Location
import org.bukkit.Material
import org.bukkit.Sound
import org.bukkit.block.Block
import org.bukkit.entity.Player
import org.bukkit.scheduler.BukkitRunnable
import ru.john.quasarutils.Configuration
import ru.john.quasarutils.QuasarUtils
import ru.john.quasarutils.configs.PlayerTasksConfig
import ru.john.quasarutils.util.ArmorUtils

/*
Раннейбл для проверки тяжести брони,
при совпадении всех условий - ломает блок под игроком
 */
class CheckArmorWeight(
    private val player: Player,
    private val config: Configuration<PlayerTasksConfig> = QuasarUtils.playerTasksConfig!!
) : BukkitRunnable() {
    override fun run() {
        if(!player.isOnline) cancel()
        if(!ArmorUtils.playerArmorIsHeavy(player)) return

        val location = player.location
        val locUnderPlayer: Location = location.subtract(0.0, 1.0, 0.0)
        val locWorld = location.world
        val blockUnderPlayer: Block = locWorld.getBlockAt(locUnderPlayer)
        val blockMaterial: Material = blockUnderPlayer.type

        if(config?.data()?.notAllowedForWeightArmor()!!.contains(blockMaterial.name)) {
            blockUnderPlayer.type = Material.AIR
            player.playSound(blockUnderPlayer.location, Sound.BLOCK_STONE_BREAK, 1f, 0f)
        }
    }

}