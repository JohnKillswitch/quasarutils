package ru.john.quasarutils.events.virus

import org.bukkit.Bukkit
import org.bukkit.entity.Player
import org.bukkit.plugin.java.JavaPlugin
import org.bukkit.potion.PotionEffect
import org.bukkit.potion.PotionEffectType
import org.bukkit.scheduler.BukkitTask
import ru.john.quasarutils.util.CacheMap
import java.time.Instant

class ApplyStages(
    private val cacheMap: CacheMap,
    private val plugin: JavaPlugin
) {

    fun detectPlayerStage(player: Player) {
        val virus = cacheMap.getPlayerVirus(player.uniqueId.toString()) ?: return

        val stage = calculateStage(virus.infectTime)


        when(stage) {
            1 -> firstStageHandler(player)
            2 -> secondStageHandler(player)
            3 -> thirdStageHandler(player)
            4 -> fourthStageHandler(player)
        }
    }

    private fun calculateStage(infectTime: Long): Int {

        val time = Instant.now().toEpochMilli() - infectTime

        return when {

//            time > 43200000 -> 4
//            time > 32400000 -> 3
//            time > 21600000 -> 2
            time > 10*60*60*1000*4 -> 4
            time > 10*60*60*1000*3 -> 3
            time > 10*60*60*1000*2 -> 2
            else -> 1
        }
    }

    private fun firstStageHandler (player: Player) {

        val uuid = player.uniqueId.toString()

        this.cacheMap.getStageSchedulersMap(player.uniqueId.toString()) ?: startFirstStage(player)
            .also { this.cacheMap.addStageSchedulersMap(uuid, it) }
    }

    private fun startFirstStage(player: Player): BukkitTask {
        return Bukkit.getScheduler().runTaskTimer(
            this.plugin,
            Runnable {
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "screeneffect fullscreen #000000 20 60 20 nofreeze ${player.name}}")
            },
            0L,
            5 * 60 * 20L
        )
    }

    private fun secondStageHandler (player: Player) {

        this.cacheMap.removeStageSchedulersMap(player.uniqueId.toString())

        val players = getNearbyPlayers(player)

        players.forEach { it.addPotionEffect(PotionEffect(PotionEffectType.HUNGER, 60,  0)) }
    }

    private fun getNearbyPlayers(player: Player) =
        player.getNearbyEntities(30.0,30.0,30.0).filterIsInstance(Player::class.java)

    private fun thirdStageHandler (player: Player) {

        if (player.location.block.lightFromSky.toInt() != 15) return
        player.fireTicks = 60


    }

    private fun fourthStageHandler (player: Player) {
        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "mmocore admin class ${player.name} UNDEAD}")
    }
}