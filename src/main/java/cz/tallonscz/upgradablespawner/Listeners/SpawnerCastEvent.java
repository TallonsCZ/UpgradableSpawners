package cz.tallonscz.upgradablespawner.Listeners;

import cz.tallonscz.upgradablespawner.GUI.SpawnerInventory;
import cz.tallonscz.upgradablespawner.Items.SpawnerItem;
import cz.tallonscz.upgradablespawner.Keys.SpawnerKeys;
import cz.tallonscz.upgradablespawner.Spawners.SpawnerBlock;
import cz.tallonscz.upgradablespawner.Utilities.DefaultDrops;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.SpawnerSpawnEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Random;

public class SpawnerCastEvent implements Listener {

    @EventHandler
    public void spawnerCastEvent(SpawnerSpawnEvent event){
        if(!(event.getSpawner().getPersistentDataContainer().has(SpawnerKeys.UPGRADESPAWNERS_SPAWNER_SPAWNERS))){
            return;
        }
        List<Player> players = event.getLocation().getWorld().getPlayers();
        Location location = event.getSpawner().getLocation();
        Inventory inventory = SpawnerInventory.getInventory(location);
        EntityType type = event.getEntityType();

        Block nextBlock = event.getSpawner().getBlock().getLocation().subtract(0, 0, -1).getBlock();
        if(Objects.equals(event.getSpawner().getPersistentDataContainer().get(SpawnerKeys.UPGRADESPAWNERS_SPAWNER_STORAGE, PersistentDataType.INTEGER), 54)){
            if (nextBlock.getType().equals(Material.CHEST)){
                if (nextBlock.getState() instanceof Chest chest){
                    if(chest.getBlockInventory().firstEmpty() != -1){
                        inventory = chest.getBlockInventory();
                    }
                }
            }
        }



        int amount = event.getSpawner().getPersistentDataContainer().get(SpawnerKeys.UPGRADESPAWNERS_SPAWNER_AMOUNT, PersistentDataType.INTEGER);
        if(type.equals(EntityType.CHEST_BOAT)){
            Collection<ItemStack> coll = DefaultDrops.getDefaultDrops(SpawnerItem.getPersistantDataStringFromBlock(event.getSpawner().getBlock(), SpawnerKeys.UPGRADESPAWNERS_SPAWNER_TYPE));
            if(Objects.equals(SpawnerItem.getPersistantDataStringFromBlock(event.getSpawner().getBlock(), SpawnerKeys.UPGRADESPAWNERS_SPAWNER_TYPE), "INGOT")){
                int chance;
                int size = coll.size();
                int number = 0;
                Random random = new Random(System.currentTimeMillis());
                for (ItemStack item : coll) {
                    chance = 0;
                    number++;
                    chance = random.nextInt(99)+1;
                    if(number == size){
                        int newAmount = item.getAmount() * amount;
                        item.setAmount(newAmount);
                        inventory.addItem(item);
                        break;
                    }
                    if(chance > 50){
                        int newAmount = item.getAmount() * amount;
                        item.setAmount(newAmount);
                        inventory.addItem(item);
                        break;
                    }else{
                        continue;
                    }

                }
            }else{
                int number = 0;
                int chance = 0;
                Random random = new Random(System.currentTimeMillis());
                chance = random.nextInt(coll.size());
                for (ItemStack item : coll) {
                    number++;
                    if(number == chance){
                        int newAmount = item.getAmount() * amount;
                        item.setAmount(newAmount);
                        inventory.addItem(item);
                        break;
                    }
                }
            }


        }else{
            Collection<ItemStack> coll = DefaultDrops.getDefaultDrops(type);
            for (int i = 0; i < amount; i++){
                for (ItemStack item : coll) {
                    inventory.addItem(item);
                }
            }
        }
        event.setCancelled(true);
    }

    private void sendAllPlayersMessage(List<Player> players, String mess){
        for (Player player: players) {
            player.sendMessage(mess);
        }
    }
}
