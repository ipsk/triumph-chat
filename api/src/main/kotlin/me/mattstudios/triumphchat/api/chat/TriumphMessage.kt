package me.mattstudios.triumphchat.api.chat

import net.kyori.adventure.text.Component
import org.bukkit.entity.Player

interface TriumphMessage {

    val message: Component
    val consoleMessage: Component
    val mentionsList: List<Player>

}