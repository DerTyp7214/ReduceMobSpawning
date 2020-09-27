package me.reducemobspawning

import org.bukkit.plugin.java.JavaPlugin

class ReduceMobSpawning : JavaPlugin() {
    override fun onEnable() {
        getCommand("spawn")?.setExecutor(Config(this))
        server.pluginManager.registerEvents(Events(this), this)
    }

    override fun onDisable() {
    }
}