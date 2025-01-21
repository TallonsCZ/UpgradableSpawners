package cz.tallonscz.upgradablespawner.Listeners;

import cz.tallonscz.upgradablespawner.GUI.SpawnerInventory;
import cz.tallonscz.upgradablespawner.Keys.SpawnerItemKeys;
import cz.tallonscz.upgradablespawner.Spawners.SpawnerBlock;
import cz.tallonscz.upgradablespawner.Utilities.Database;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

public class PlaceSpawnerEvent implements Listener {

    Database database = new Database();

    @EventHandler
    public void spawnerPlaceEvent(BlockPlaceEvent event){
        Block targetBlock = event.getBlockAgainst();
        if (targetBlock == null){
            return;
        }
        if (SpawnerInventory.isThereSpawner(targetBlock.getLocation())){
            event.setCancelled(true);
            return;
        }
        Block block = event.getBlockPlaced();
        ItemMeta meta = event.getItemInHand().getItemMeta();
        if(!(meta.getPersistentDataContainer().has(SpawnerItemKeys.UPGRADESPAWNERS_ITEM_SPAWNERS))){
            return;
        }
        SpawnerBlock.setSpawnerBlock(block, meta);
        //Vznik spawnerInventory
        Inventory inventory = Bukkit.createInventory(null, meta.getPersistentDataContainer().get(SpawnerItemKeys.UPGRADESPAWNERS_ITEM_STORAGE, PersistentDataType.INTEGER), Component.text("Spawner Inventory"));
        SpawnerInventory.setInventory(block.getLocation(), inventory, event.getPlayer());


        SpawnerInventory.saveAllInventories();
    }
}
