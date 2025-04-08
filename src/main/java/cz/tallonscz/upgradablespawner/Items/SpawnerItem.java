package cz.tallonscz.upgradablespawner.Items;

import cz.tallonscz.upgradablespawner.Keys.SpawnerItemKeys;
import cz.tallonscz.upgradablespawner.Keys.SpawnerKeys;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.CreatureSpawner;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.ArrayList;
import java.util.List;

public class SpawnerItem {

    private ItemStack item = new ItemStack(Material.SPAWNER);

    public SpawnerItem(){

    }

    //CREATING DEFAULT SPAWNER ITEM
    public ItemStack getSpawner(EntityType entityType, int amount){
        item.setAmount(amount);
        ItemMeta meta = item.getItemMeta();

        meta.displayName(Component.text(entityType.name() + " SPAWNER").color(NamedTextColor.GOLD));
        setPersistantData(meta, SpawnerItemKeys.UPGRADESPAWNERS_ITEM_SPAWNERS, true);
        setPersistantData(meta, SpawnerItemKeys.UPGRADESPAWNERS_ITEM_LEVEL, 0);
        setPersistantData(meta, SpawnerItemKeys.UPGRADESPAWNERS_ITEM_TYPE, entityType.name());
        setPersistantData(meta, SpawnerItemKeys.UPGRADESPAWNERS_ITEM_STORAGE, 9);
        setPersistantData(meta, SpawnerItemKeys.UPGRADESPAWNERS_ITEM_TIME, 30);
        setPersistantData(meta, SpawnerItemKeys.UPGRADESPAWNERS_ITEM_AMOUNT, 1);
        setPersistantData(meta, SpawnerItemKeys.UPGRADESPAWNERS_ITEM_PLAYER, false);
        item.setItemMeta(meta);
        return item;
    }

    //CREATING SPAWNER ITEM FROM BLOCK
    public ItemStack getSpawner(Block block){
        item.setAmount(1);
        ItemMeta meta = item.getItemMeta();
        EntityType entityType = EntityType.valueOf(getPersistantDataStringFromBlock(block, SpawnerKeys.UPGRADESPAWNERS_SPAWNER_TYPE));

        meta.displayName(Component.text(entityType.name() + " SPAWNER").color(NamedTextColor.GOLD));

        setPersistantData(meta, SpawnerItemKeys.UPGRADESPAWNERS_ITEM_SPAWNERS, true);
        setPersistantData(meta, SpawnerItemKeys.UPGRADESPAWNERS_ITEM_LEVEL, getPersistantDataIntFromBlock(block, SpawnerKeys.UPGRADESPAWNERS_SPAWNER_LEVEL));
        setPersistantData(meta, SpawnerItemKeys.UPGRADESPAWNERS_ITEM_TYPE, entityType.name());
        setPersistantData(meta, SpawnerItemKeys.UPGRADESPAWNERS_ITEM_STORAGE, getPersistantDataIntFromBlock(block, SpawnerKeys.UPGRADESPAWNERS_SPAWNER_STORAGE));
        setPersistantData(meta, SpawnerItemKeys.UPGRADESPAWNERS_ITEM_AMOUNT, getPersistantDataIntFromBlock(block, SpawnerKeys.UPGRADESPAWNERS_SPAWNER_AMOUNT));
        setPersistantData(meta, SpawnerItemKeys.UPGRADESPAWNERS_ITEM_TIME, getPersistantDataIntFromBlock(block, SpawnerKeys.UPGRADESPAWNERS_SPAWNER_TIME));
        setPersistantData(meta, SpawnerItemKeys.UPGRADESPAWNERS_ITEM_PLAYER, true);
        List<Component> list = new ArrayList<>();
        Component sepLine = Component.text("");
        Component fLine = Component.text("Time: " + getPersistantDataIntFromBlock(block, SpawnerKeys.UPGRADESPAWNERS_SPAWNER_TIME)).color(TextColor.color(255, 255, 255)).decoration(TextDecoration.ITALIC, false);
        Component sLine = Component.text("Amount: " + getPersistantDataIntFromBlock(block, SpawnerKeys.UPGRADESPAWNERS_SPAWNER_AMOUNT)).color(TextColor.color(255, 255, 255)).decoration(TextDecoration.ITALIC, false);
        Component pLine = Component.text("Storage: " + getPersistantDataIntFromBlock(block, SpawnerKeys.UPGRADESPAWNERS_SPAWNER_STORAGE)).color(TextColor.color(255, 255, 255)).decoration(TextDecoration.ITALIC, false);
        list.add(sepLine);
        list.add(fLine);
        list.add(sLine);
        list.add(pLine);
        meta.lore(list);

        item.setItemMeta(meta);

        return item;
    }

    //CREATING SPAWNER ITEM FROM BLOCK TO RESPAWN
    public ItemStack getSpawnerForRespawn(Block block){
        item.setAmount(1);
        ItemMeta meta = item.getItemMeta();
        EntityType entityType = EntityType.valueOf(getPersistantDataStringFromBlock(block, SpawnerKeys.UPGRADESPAWNERS_SPAWNER_TYPE));

        meta.displayName(Component.text(entityType.name() + "SPAWNER").color(NamedTextColor.GOLD));

        setPersistantData(meta, SpawnerItemKeys.UPGRADESPAWNERS_ITEM_SPAWNERS, true);
        setPersistantData(meta, SpawnerItemKeys.UPGRADESPAWNERS_ITEM_LEVEL, getPersistantDataIntFromBlock(block, SpawnerKeys.UPGRADESPAWNERS_SPAWNER_LEVEL));
        setPersistantData(meta, SpawnerItemKeys.UPGRADESPAWNERS_ITEM_TYPE, entityType.name());
        setPersistantData(meta, SpawnerItemKeys.UPGRADESPAWNERS_ITEM_STORAGE, getPersistantDataIntFromBlock(block, SpawnerKeys.UPGRADESPAWNERS_SPAWNER_STORAGE));
        setPersistantData(meta, SpawnerItemKeys.UPGRADESPAWNERS_ITEM_AMOUNT, getPersistantDataIntFromBlock(block, SpawnerKeys.UPGRADESPAWNERS_SPAWNER_AMOUNT));
        setPersistantData(meta, SpawnerItemKeys.UPGRADESPAWNERS_ITEM_TIME, getPersistantDataIntFromBlock(block, SpawnerKeys.UPGRADESPAWNERS_SPAWNER_TIME));
        setPersistantData(meta, SpawnerItemKeys.UPGRADESPAWNERS_ITEM_PLAYER, false);
        List<Component> list = new ArrayList<>();
        Component sepLine = Component.text("");
        Component fLine = Component.text("Time: " + getPersistantDataIntFromBlock(block, SpawnerKeys.UPGRADESPAWNERS_SPAWNER_TIME)).color(TextColor.color(255, 255, 255)).decoration(TextDecoration.ITALIC, false);
        Component sLine = Component.text("Amount: " + getPersistantDataIntFromBlock(block, SpawnerKeys.UPGRADESPAWNERS_SPAWNER_AMOUNT)).color(TextColor.color(255, 255, 255)).decoration(TextDecoration.ITALIC, false);
        Component pLine = Component.text("Storage: " + getPersistantDataIntFromBlock(block, SpawnerKeys.UPGRADESPAWNERS_SPAWNER_STORAGE)).color(TextColor.color(255, 255, 255)).decoration(TextDecoration.ITALIC, false);
        list.add(sepLine);
        list.add(fLine);
        list.add(sLine);
        list.add(pLine);
        meta.lore(list);

        item.setItemMeta(meta);

        return item;
    }

    private int getPersistantDataIntFromBlock(Block block, NamespacedKey key){
        BlockState state = block.getState();
        if(state instanceof CreatureSpawner spawner){
            PersistentDataContainer container = spawner.getPersistentDataContainer();
            if (container.has(key)){
                return container.get(key, PersistentDataType.INTEGER);
            }
        }
        return -1;
    }

    private String getPersistantDataStringFromBlock(Block block, NamespacedKey key){
        BlockState state = block.getState();
        if(state instanceof CreatureSpawner spawner){
            PersistentDataContainer container = spawner.getPersistentDataContainer();
            if (container.has(key)){
                return container.get(key, PersistentDataType.STRING);
            }
        }
        return "-1";
    }

    private Boolean getPersistantDataBoolFromBlock(Block block, NamespacedKey key){
        BlockState state = block.getState();
        if(state instanceof CreatureSpawner spawner){
            PersistentDataContainer container = spawner.getPersistentDataContainer();
            if (container.has(key)){
                return container.get(key, PersistentDataType.BOOLEAN);
            }
        }
        return false;
    }

    private void setPersistantData(ItemMeta meta ,NamespacedKey key, int number){
        PersistentDataContainer container = meta.getPersistentDataContainer();
        container.set(key, PersistentDataType.INTEGER, number);
    }

    private void setPersistantData(ItemMeta meta ,NamespacedKey key, String string){
        PersistentDataContainer container = meta.getPersistentDataContainer();
        container.set(key, PersistentDataType.STRING, string);
    }

    private void setPersistantData(ItemMeta meta ,NamespacedKey key, boolean bool){
        PersistentDataContainer container = meta.getPersistentDataContainer();
        container.set(key, PersistentDataType.BOOLEAN, bool);
    }

    public static String getStringPersistantDataFromItem(ItemMeta meta, NamespacedKey key){
        if (!(meta.getPersistentDataContainer().has(key))){
            return "-1";
        }
        return meta.getPersistentDataContainer().get(key, PersistentDataType.STRING);
    }

    public static int getIntPersistantDataFromItem(ItemMeta meta, NamespacedKey key){
        if (!(meta.getPersistentDataContainer().has(key))){
            return -1;
        }
        return meta.getPersistentDataContainer().get(key, PersistentDataType.INTEGER);
    }

    public static boolean getBoolPersistantDataFromItem(ItemMeta meta, NamespacedKey key){
        if (!(meta.getPersistentDataContainer().has(key))){
            return false;
        }
        return meta.getPersistentDataContainer().get(key, PersistentDataType.BOOLEAN);
    }

}
