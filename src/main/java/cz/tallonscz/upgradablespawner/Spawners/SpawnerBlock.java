package cz.tallonscz.upgradablespawner.Spawners;

import cz.tallonscz.upgradablespawner.GUI.SpawnerInventory;
import cz.tallonscz.upgradablespawner.Keys.SpawnerItemKeys;
import cz.tallonscz.upgradablespawner.Keys.SpawnerKeys;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.CreatureSpawner;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.UUID;

public class SpawnerBlock {
    public static void setSpawnerBlock(Block block, ItemMeta meta){
        if (block.getType() != Material.SPAWNER){
            return;
        }
        BlockState state = block.getState();
        if(state instanceof CreatureSpawner spawner){
            try {
                spawner.setSpawnedType(EntityType.valueOf(SpawnerItem.getStringPersistantDataFromItem(meta, SpawnerItemKeys.UPGRADESPAWNERS_ITEM_TYPE)));
                setPersistantData(SpawnerKeys.UPGRADESPAWNERS_SPAWNER_SPAWNERS, spawner, true);
                setPersistantData(SpawnerKeys.UPGRADESPAWNERS_SPAWNER_TYPE, spawner, SpawnerItem.getStringPersistantDataFromItem(meta, SpawnerItemKeys.UPGRADESPAWNERS_ITEM_TYPE));
                setPersistantData(SpawnerKeys.UPGRADESPAWNERS_SPAWNER_LEVEL, spawner, SpawnerItem.getIntPersistantDataFromItem(meta, SpawnerItemKeys.UPGRADESPAWNERS_ITEM_LEVEL));
                setPersistantData(SpawnerKeys.UPGRADESPAWNERS_SPAWNER_STORAGE, spawner, SpawnerItem.getIntPersistantDataFromItem(meta, SpawnerItemKeys.UPGRADESPAWNERS_ITEM_STORAGE));
                setPersistantData(SpawnerKeys.UPGRADESPAWNERS_SPAWNER_TIME, spawner, SpawnerItem.getIntPersistantDataFromItem(meta, SpawnerItemKeys.UPGRADESPAWNERS_ITEM_TIME));
                setPersistantData(SpawnerKeys.UPGRADESPAWNERS_SPAWNER_AMOUNT, spawner, SpawnerItem.getIntPersistantDataFromItem(meta, SpawnerItemKeys.UPGRADESPAWNERS_ITEM_AMOUNT));

                spawner.setSpawnCount(spawner.getPersistentDataContainer().get(SpawnerKeys.UPGRADESPAWNERS_SPAWNER_AMOUNT, PersistentDataType.INTEGER));
                int delay = spawner.getPersistentDataContainer().get(SpawnerKeys.UPGRADESPAWNERS_SPAWNER_TIME, PersistentDataType.INTEGER) * 20;

                spawner.update();

                spawner.setMinSpawnDelay(delay);
                spawner.setMaxSpawnDelay(delay);
                spawner.setDelay(delay);

                spawner.update();
            } catch (NullPointerException e) {
                throw new NullPointerException();
            }
        }
    }



    private static void setPersistantData(NamespacedKey key, CreatureSpawner spawner, int number){
        PersistentDataContainer container = spawner.getPersistentDataContainer();
        container.set(key, PersistentDataType.INTEGER, number);
    }
    private static void setPersistantData(NamespacedKey key, CreatureSpawner spawner, boolean bool){
        PersistentDataContainer container = spawner.getPersistentDataContainer();
        container.set(key, PersistentDataType.BOOLEAN, bool);
    }
    private static void setPersistantData(NamespacedKey key, CreatureSpawner spawner, String string){
        PersistentDataContainer container = spawner.getPersistentDataContainer();
        container.set(key, PersistentDataType.STRING, string);
    }

    public static int changeAmount(CreatureSpawner spawner){
        PersistentDataContainer container = spawner.getPersistentDataContainer();
        int currentAmount = container.get(SpawnerKeys.UPGRADESPAWNERS_SPAWNER_AMOUNT, PersistentDataType.INTEGER);
        int newAmount = currentAmount+1;
        if (newAmount > 5){
            return 1;
        }
        container.set(SpawnerKeys.UPGRADESPAWNERS_SPAWNER_AMOUNT, PersistentDataType.INTEGER, newAmount);
        spawner.update();
        return 0;
    }

    public static int changeTime(CreatureSpawner spawner){
        PersistentDataContainer container = spawner.getPersistentDataContainer();
        int currentTime = container.get(SpawnerKeys.UPGRADESPAWNERS_SPAWNER_TIME, PersistentDataType.INTEGER);
        int newTime = currentTime - 5;
        if (newTime < 5){
            return 1;
        }
        container.set(SpawnerKeys.UPGRADESPAWNERS_SPAWNER_TIME, PersistentDataType.INTEGER, newTime);
        spawner.setDelay(newTime*20);
        spawner.setMinSpawnDelay(newTime*20);
        spawner.setMaxSpawnDelay(newTime*20);
        spawner.update();
        return 0;
    }

    public static int changeInventory(CreatureSpawner spawner){
        PersistentDataContainer container = spawner.getPersistentDataContainer();
        int currentSize = container.get(SpawnerKeys.UPGRADESPAWNERS_SPAWNER_STORAGE, PersistentDataType.INTEGER);
        int newSize = currentSize+9;
        if (newSize > 54){
            return 1;
        }
        Inventory newInventory = Bukkit.createInventory(null, newSize, Component.text("Spawner Inventory"));
        Location spawnerLocation = spawner.getLocation();
        Inventory oldInventory = SpawnerInventory.getInventory(spawnerLocation);
        UUID getOwnerUUID = SpawnerInventory.getOwner(spawnerLocation);
        if (getOwnerUUID == null){
            return 1;
        }
        oldInventory.forEach((itemStack -> {
            if(itemStack != null && itemStack.getAmount() > 0){
                newInventory.addItem(itemStack);
            }
        }));
        SpawnerInventory.removeInventory(spawnerLocation);
        SpawnerInventory.setInventory(spawnerLocation, newInventory, Bukkit.getOfflinePlayer(getOwnerUUID));
        container.set(SpawnerKeys.UPGRADESPAWNERS_SPAWNER_STORAGE, PersistentDataType.INTEGER, newSize);
        spawner.update();
        return 0;
    }
}
