package cz.tallonscz.upgradablespawner.Listeners;

import cz.tallonscz.upgradablespawner.GUI.SpawnerInventory;
import cz.tallonscz.upgradablespawner.Keys.SpawnerKeys;
import cz.tallonscz.upgradablespawner.Utilities.DefaultDrops;
import org.bukkit.Location;
import org.bukkit.block.CreatureSpawner;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.SpawnerSpawnEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataContainer;

import java.awt.*;
import java.util.Collection;
import java.util.List;

public class SpawnerCastEvent implements Listener {

    @EventHandler
    public void spawnerCastEvent(SpawnerSpawnEvent event){
        if(!(event.getSpawner().getPersistentDataContainer().has(SpawnerKeys.UPGRADESPAWNERS_SPAWNER_SPAWNERS))){
            return;
        }
        List<Player> players = event.getLocation().getWorld().getPlayers();
        Location location = event.getSpawner().getLocation();
        Inventory inventory = SpawnerInventory.getInventory(location);
        Collection<ItemStack> coll = DefaultDrops.getDefaultDrops(event.getEntityType());
        for (ItemStack item : coll) {
            inventory.addItem(item);
        }
        event.setCancelled(true);
    }

    private void sendAllPlayersMessage(List<Player> players, String mess){
        for (Player player: players) {
            player.sendMessage(mess);
        }
    }
}
