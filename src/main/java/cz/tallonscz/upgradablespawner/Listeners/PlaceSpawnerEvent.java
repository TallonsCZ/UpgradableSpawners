package cz.tallonscz.upgradablespawner.Listeners;

import cz.tallonscz.upgradablespawner.GUI.SpawnerInventory;
import cz.tallonscz.upgradablespawner.Items.SpawnerItem;
import cz.tallonscz.upgradablespawner.Keys.SpawnerItemKeys;
import cz.tallonscz.upgradablespawner.Spawners.SpawnerBlock;
import cz.tallonscz.upgradablespawner.Utilities.Database;
import cz.tallonscz.upgradablespawner.Utilities.Holograms;
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
        Holograms holo = new Holograms();
        holo.createHologram(block.getLocation().subtract(0, 1, 0).add(0.5, 0, 0.5), SpawnerItem.getStringPersistantDataFromItem(meta, SpawnerItemKeys.UPGRADESPAWNERS_ITEM_TYPE) + " Spawner");


        SpawnerInventory.saveAllInventories();
    }
}
