package me.reducemobspawning

import org.bukkit.configuration.file.FileConfiguration
import org.bukkit.entity.Entity
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.EntitySpawnEvent
import org.bukkit.plugin.java.JavaPlugin

class Events(private val plugin: JavaPlugin) : Listener {

    @EventHandler
    fun onSpawn(event: EntitySpawnEvent) {
        if (isEntity(event.entity, plugin.config) && (0..2).random() == 1) event.isCancelled = true
    }

    private fun isEntity(entity: Entity, configuration: FileConfiguration): Boolean {
        val entities = configuration.getStringList("entities")
        var isEntity = false
        for (e in entities) {
            if (entity.type.name == e) isEntity = true
        }
        return isEntity
    }
}