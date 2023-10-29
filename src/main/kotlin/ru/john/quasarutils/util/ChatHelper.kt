package ru.john.quasarutils.util

import net.kyori.adventure.text.minimessage.MiniMessage
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver
import org.bukkit.command.CommandSender

class ChatHelper(
    private val miniMessage: MiniMessage
) {
    fun sendMessage(receiver: CommandSender, message: String) {
        receiver.sendMessage(miniMessage.deserialize(message))
    }

    fun sendMessageWithPlaceholder(receiver: CommandSender, message: String, vararg resolvers: TagResolver) {
        receiver.sendMessage(miniMessage.deserialize(message, *resolvers))
    }
}