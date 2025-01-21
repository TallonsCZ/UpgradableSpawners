package cz.tallonscz.upgradablespawner.Listeners;

import cz.tallonscz.upgradablespawner.GUI.SpawnerInventory;
import cz.tallonscz.upgradablespawner.Keys.SpawnerKeys;
import cz.tallonscz.upgradablespawner.Spawners.SpawnerItem;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.CreatureSpawner;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

public class BreakSpawnerEvent implements Listener {

    @EventHandler
    public void breakSpawnerEvent(BlockBreakEvent event){
        Block breakBlock = event.getBlock();
        Player player = event.getPlayer();
        if(breakBlock.getType() != Material.SPAWNER){
            return;
        }
        if (!(breakBlock.getState() instanceof CreatureSpawner spawner)){
            return;
        }
        if (!(spawner.getPersistentDataContainer().has(SpawnerKeys.UPGRADESPAWNERS_SPAWNER_SPAWNERS))){
            return;
        }
        SpawnerItem item = new SpawnerItem();
        ItemStack getItem = item.getSpawner(breakBlock);
        SpawnerInventory.removeInventory(breakBlock.getLocation());
        player.getInventory().addItem(getItem);

    }
}