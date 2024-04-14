package ru.john.quasarutils.runnables

import org.bukkit.GameMode
import org.bukkit.block.Biome
import org.bukkit.entity.Player
import ru.john.quasarutils.QuasarUtils
import ru.john.quasarutils.util.PlayerRunnable
import kotlin.math.roundToInt

class MinusThirstPerValue(
    private val player: Player
) : PlayerRunnable(player, 0L, 20L) {

    private var time: Int = QuasarUtils.playerActionsConfig!!.data()!!.minusThirstPerValue()
    private var desertModificator: Boolean = false
    private var runModificator: Boolean = false
    private var foodModificator: Int = player.foodLevel

    override fun run() {
        if(!player.isOnline) cancel()
        if(player.isDead) return
        if(player.gameMode == GameMode.CREATIVE || player.gameMode == GameMode.SPECTATOR) return

        if(foodModificator > player.foodLevel) {
            foodModificator = player.foodLevel
        }

        if(player.location.block.biome == Biome.DESERT && !desertModificator) {
            val modificator = QuasarUtils.playerActionsConfig!!.data()!!.desertThirstModificator()
            time = (time / modificator).roundToInt()
            desertModificator = true
        }

        if(player.isSprinting && !runModificator) {
            val modificator = QuasarUtils.playerActionsConfig!!.data()!!.runThirstModificator()
            time = (time / modificator).roundToInt()
            runModificator = true
        }

        if(foodModificator < player.foodLevel) {
            val modificator = QuasarUtils.playerActionsConfig!!.data()!!.eatThirstModificator()
            time = (time / modificator).roundToInt()
            foodModificator = player.foodLevel
        }

        val service = QuasarUtils.serviceManager!!.getPlayerDataContainerService()
        val wrappedObject = service.getPlayerWrappedObject(player)!!
        val currThirst = wrappedObject.getAttribute("thirst")

        if(currThirst!!.value <= 0) {
            player.damage(1.0)
            return
        }

        if(time <= 0) {
            wrappedObject.setAttribute("thirst", currThirst.value-1)
            time = QuasarUtils.playerActionsConfig!!.data()!!.minusThirstPerValue()
            desertModificator = false
            runModificator = false
            return
        }

        time -= 1
    }
}