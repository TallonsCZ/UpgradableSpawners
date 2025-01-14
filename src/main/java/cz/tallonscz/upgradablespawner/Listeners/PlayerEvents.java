package cz.tallonscz.upgradablespawner.Listeners;

import cz.tallonscz.upgradablespawner.GUI.SpawnerInventory;
import cz.tallonscz.upgradablespawner.Keys.SpawnerKeys;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.CreatureSpawner;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;

public class PlayerEvents implements Listener {

    @EventHandler
    public void playerOpenSpawnerInventory(PlayerInteractEvent event){
        Block block = event.getClickedBlock();
        assert block != null;
        if(!(event.getAction().isRightClick())){
            return;
        }
        if(block.getType() != Material.SPAWNER){
            return;
        }
        if (!(block.getState() instanceof CreatureSpawner spawner)){
            return;
        }
        if (!(spawner.getPersistentDataContainer().has(SpawnerKeys.UPGRADESPAWNERS_SPAWNER_SPAWNERS))){
            return;
        }
        Location location = block.getLocation();
        Inventory inv = SpawnerInventory.getInventory(location);
        event.getPlayer().openInventory(inv);
    }


}
