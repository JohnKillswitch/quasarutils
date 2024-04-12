package ru.john.quasarutils.events

import dev.geco.gsit.api.GSitAPI
import dev.geco.gsit.api.event.PlayerPoseEvent
import me.angeschossen.lands.api.LandsIntegration
import me.angeschossen.lands.api.land.LandWorld
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.attribute.Attribute
import org.bukkit.entity.Player
import org.bukkit.entity.Pose
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.plugin.java.JavaPlugin
import org.bukkit.potion.PotionEffect
import org.bukkit.potion.PotionEffectType
import kotlin.random.Random


class CheckBedLay(
    private val landsApi: LandsIntegration,
    private val plugin: JavaPlugin
) {

    fun checkPlayerLay(player: Player) {

        val pose = GSitAPI.getPose(player) ?: return

        if (pose.pose != Pose.SLEEPING) return

        val landsWorld = landsApi.getWorld(player.world) ?: return

        landsWorld.getLandByChunk(player.chunk.x,player.chunk.z) ?: return

        if (!pose.seat.block.blockData.material.toString().contains("bed",true)) return

        if (Random.nextInt(1, 11) <= 2)
        Bukkit.getScheduler().runTask(this.plugin, Runnable {
            if (player.health < player.getAttribute(Attribute.GENERIC_MAX_HEALTH)!!.value) player.health+=1
        })

    }


}