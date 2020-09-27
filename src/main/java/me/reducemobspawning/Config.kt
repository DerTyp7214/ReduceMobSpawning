package me.reducemobspawning

import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.command.TabCompleter
import org.bukkit.entity.EntityType
import org.bukkit.entity.Player
import org.bukkit.plugin.java.JavaPlugin
import java.util.*

class Config(private val plugin: JavaPlugin) : CommandExecutor, TabCompleter {

    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<String>): Boolean {
        if (sender is Player) {
            val configuration = plugin.config
            if (sender.isOp && args.size == 2) {
                val add = args[0] == "add"
                try {
                    val entityType = EntityType.valueOf(args[1])
                    val entities = configuration.getStringList("entities")
                    if (add) {
                        if (!entities.contains(entityType.name)) entities.add(entityType.name) else sender.sendMessage("This entity is already defined!")
                    } else {
                        if (!entities.remove(entityType.name)) sender.sendMessage("This entity is not in the list.")
                    }
                    configuration["entities"] = entities
                    plugin.saveConfig()
                    sender.sendMessage("§eList updated.")
                } catch (e: Exception) {
                    sender.sendMessage("§c" + args[1] + " §eis not a valid Entity.")
                }
            }
        }
        return false
    }

    override fun onTabComplete(sender: CommandSender, command: Command, alias: String, args: Array<String>): List<String> {
        if (sender is Player) {
            if (sender.isOp && args.size == 1) {
                val completions: MutableList<String> = ArrayList()
                if ("add".startsWith(args[0])) completions.add("add") else if ("remove".startsWith(args[0])) completions.add("remove")
                return completions
            } else if (sender.isOp && args.size == 2) {
                val name = args[1]
                val completions: MutableList<String> = ArrayList()
                for (e in EntityType.values()) {
                    if (e.isSpawnable && e.name.toLowerCase().startsWith(name.toLowerCase())) completions.add(e.name)
                }
                return completions
            }
        }
        return ArrayList()
    }
}