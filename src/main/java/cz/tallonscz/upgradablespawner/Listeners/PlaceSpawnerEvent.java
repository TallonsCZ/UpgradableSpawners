package cz.tallonscz.upgradablespawner.Listeners;

import cz.tallonscz.upgradablespawner.Keys.SpawnerItemKeys;
import cz.tallonscz.upgradablespawner.Spawners.SpawnerBlock;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.meta.ItemMeta;

public class PlaceSpawnerEvent implements Listener {

    @EventHandler
    public void spawnerPlaceEvent(BlockPlaceEvent event){
        Block block = event.getBlockPlaced();
        ItemMeta meta = event.getItemInHand().getItemMeta();
        if(!(meta.getPersistentDataContainer().has(SpawnerItemKeys.UPGRADESPAWNERS_ITEM_SPAWNERS))){
            return;
        }
        SpawnerBlock.setSpawnerBlock(block, meta);
    }
}
