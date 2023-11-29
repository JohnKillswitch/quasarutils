package ru.john.quasarutils.commands

import net.kyori.adventure.text.minimessage.MiniMessage
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.command.ConsoleCommandSender
import org.bukkit.entity.Player
import org.bukkit.plugin.java.JavaPlugin
import ru.john.quasarutils.Configuration
import ru.john.quasarutils.configs.Messages
import ru.john.quasarutils.database.MainBase
import ru.john.quasarutils.util.CacheMap
import ru.john.quasarutils.util.ChatHelper

class SetCharacterInfo(
    private val plugin: JavaPlugin,
    private val miniMessage: MiniMessage,
    private val mainBase: MainBase,
    private val cHelper: ChatHelper,
    private val cacheMap: CacheMap,
    private val messages: Configuration<Messages>
) : CommandExecutor{

    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<String>): Boolean {

        val mData = this.messages.data()

        if (args.isEmpty()) {
            cHelper.sendMessage(sender, mData!!.wrongUsage())
            return true
        }
        if (sender !is Player) {
            cHelper.sendMessage(sender, mData!!.playersOnly())
            return true
        }

        when (args[0]) {
            "create" -> createCharacter(sender, args)
            "delete" -> deleteCharacter(sender, args)
            else -> cHelper.sendMessage(sender, "!!! Нет такой команды!")
        }
        return true
    }

    private fun deleteCharacter(player: Player, args: Array<String>) {

        if (!player.hasPermission("quasarutils.delete") && args.size>1) {
            cHelper.sendMessage(player, messages.data()!!.noPerm())
            return
        }
        val target = Bukkit.getPlayerUniqueId(args[1])
        mainBase.deletePlayerData(target.toString())
        cacheMap.removePlayerInfo(target.toString())


    }

    private fun createCharacter(player: Player, args: Array<String>) {

        if (!player.hasPermission("quasarutils.create") && args.size == 3) {
            cHelper.sendMessage(player, messages.data()!!.noPerm())
            return
        }
        else cHelper.sendMessage(player, messages.data()!!.wrongUsage())

        if (!checkName(player, args[1]) || !checkSurname(player, args[2])) {
            return
        }

        if (cacheMap.isPlayerInMap(player.uniqueId.toString())) {
            cHelper.sendMessage(player, messages.data()!!.alreadyCreated())
            return
        }

        mainBase.addPlayer(player.uniqueId.toString(),args[1], args[2])
        cacheMap.addPlayerInfo(player.uniqueId.toString(),args[1], args[2])
        val consoleSender: ConsoleCommandSender = Bukkit.getConsoleSender()
        Bukkit.dispatchCommand(consoleSender, "lp user ${player.name} permission set welcome.teleport")

    }

    private fun checkName(player: Player, name: String): Boolean {
        if (name.length > 10) {
            cHelper.sendMessage(player, messages.data()!!.maxLenghtName())
            return false
        }

        if (!name.matches(Regex("[А-Яа-я]+"))) {
            cHelper.sendMessage(player, messages.data()!!.cyrrilicOnlyName())
            return false
        }
        return true
    }

    private fun checkSurname(player: Player, name: String): Boolean {
        if (name.length > 10) {
            cHelper.sendMessage(player, messages.data()!!.maxLenghtSurname())
            return false
        }

        if (!name.matches(Regex("[А-Яа-я]+"))) {
            cHelper.sendMessage(player, messages.data()!!.cyrrilicOnlySurname())
            return false
        }
        return true
    }
}
