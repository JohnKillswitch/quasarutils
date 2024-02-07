package ru.john.quasarutils.util

import net.kyori.adventure.text.Component
import net.kyori.adventure.text.minimessage.MiniMessage
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

class ChatHelper(
    private val miniMessage: MiniMessage
) {
    fun sendMessage(receiver: CommandSender, message: String) {
        receiver.sendMessage(message)

    }


//    fun sendMessageWithPlaceholder(receiver: CommandSender, message: String, vararg resolvers: TagResolver) {
//        receiver.sendMessage(miniMessage.deserialize(message, *resolvers))
//    }
}