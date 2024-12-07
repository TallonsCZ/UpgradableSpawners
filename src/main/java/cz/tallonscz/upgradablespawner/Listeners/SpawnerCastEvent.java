package cz.tallonscz.upgradablespawner.Listeners;

import cz.tallonscz.upgradablespawner.Keys.SpawnerKeys;
import org.bukkit.block.CreatureSpawner;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.SpawnerSpawnEvent;
import org.bukkit.persistence.PersistentDataContainer;

public class SpawnerCastEvent implements Listener {

    @EventHandler
    public void spawnerCastEvent(SpawnerSpawnEvent event){
        if(!(event.getSpawner().getPersistentDataContainer().has(SpawnerKeys.UPGRADESPAWNERS_SPAWNER_SPAWNERS))){
            return;
        }
        event.setCancelled(true);
        CreatureSpawner spawner = event.getSpawner();
        PersistentDataContainer container = spawner.getPersistentDataContainer();


    }
}
