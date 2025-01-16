package cz.tallonscz.upgradablespawner.Spawners;

import cz.tallonscz.upgradablespawner.Keys.SpawnerItemKeys;
import cz.tallonscz.upgradablespawner.Keys.SpawnerKeys;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.CreatureSpawner;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

public class SpawnerBlock {
    public static void setSpawnerBlock(Block block, ItemMeta meta){
        if (block.getType() != Material.SPAWNER){
            return;
        }
        BlockState state = block.getState();
        if(state instanceof CreatureSpawner spawner){
            spawner.setSpawnedType(EntityType.valueOf(SpawnerItem.getStringPersistantDataFromItem(meta, SpawnerItemKeys.UPGRADESPAWNERS_ITEM_TYPE)));
            setPersistantData(SpawnerKeys.UPGRADESPAWNERS_SPAWNER_SPAWNERS, spawner, true);
            setPersistantData(SpawnerKeys.UPGRADESPAWNERS_SPAWNER_TYPE, spawner, SpawnerItem.getStringPersistantDataFromItem(meta, SpawnerItemKeys.UPGRADESPAWNERS_ITEM_TYPE));
            setPersistantData(SpawnerKeys.UPGRADESPAWNERS_SPAWNER_LEVEL, spawner, SpawnerItem.getIntPersistantDataFromItem(meta, SpawnerItemKeys.UPGRADESPAWNERS_ITEM_LEVEL));
            setPersistantData(SpawnerKeys.UPGRADESPAWNERS_SPAWNER_STORAGE, spawner, SpawnerItem.getIntPersistantDataFromItem(meta, SpawnerItemKeys.UPGRADESPAWNERS_ITEM_STORAGE));
            setPersistantData(SpawnerKeys.UPGRADESPAWNERS_SPAWNER_TIME, spawner, SpawnerItem.getIntPersistantDataFromItem(meta, SpawnerItemKeys.UPGRADESPAWNERS_ITEM_TIME));
            setPersistantData(SpawnerKeys.UPGRADESPAWNERS_SPAWNER_AMOUNT, spawner, SpawnerItem.getIntPersistantDataFromItem(meta, SpawnerItemKeys.UPGRADESPAWNERS_ITEM_AMOUNT));

            spawner.update();

            spawner.setSpawnCount(spawner.getPersistentDataContainer().get(SpawnerKeys.UPGRADESPAWNERS_SPAWNER_AMOUNT, PersistentDataType.INTEGER));
            spawner.setDelay(spawner.getPersistentDataContainer().get(SpawnerKeys.UPGRADESPAWNERS_SPAWNER_TIME, PersistentDataType.INTEGER));

            spawner.update();
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
}
