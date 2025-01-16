package cz.tallonscz.upgradablespawner.GUI;

import cz.tallonscz.upgradablespawner.Keys.SpawnerKeys;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.ArrayList;
import java.util.List;

public class UpgradeInventory {
    private Inventory inventory;

    public UpgradeInventory(PersistentDataContainer container){
        createInventory(container);
    }

    private void createInventory(PersistentDataContainer container){
        inventory = Bukkit.createInventory(null, 27, Component.text("Upgrade Menu"));
        inventory.setItem(10, getUpgradeAmountItem(getPersistantDataInt(container, SpawnerKeys.UPGRADESPAWNERS_SPAWNER_AMOUNT)));
        //inventory.setItem(10, getUpgradeTimeItem(getPersistantDataInt(container, SpawnerKeys.UPGRADESPAWNERS_SPAWNER_TIME)));
        //inventory.setItem(10, getUpgradeSizeItem(getPersistantDataInt(container, SpawnerKeys.UPGRADESPAWNERS_SPAWNER_STORAGE)));
    }

    public Inventory getInventory() {
        return inventory;
    }

    private ItemStack getUpgradeSizeItem(int i){
        ItemStack item = new ItemStack(Material.CHEST);
        ItemMeta meta = item.getItemMeta();

        meta.displayName(Component.text("SIZE "+i));

        List<Component> list = new ArrayList<>();
        list.add(Component.text(""));
        list.add(Component.text("Inventory size is: " + i));
        list.add(Component.text("Upgrade cost: xx"));

        meta.lore(list);
        return item;
    }

    private ItemStack getUpgradeAmountItem(int i){
        ItemStack item = new ItemStack(Material.CHEST);
        ItemMeta meta = item.getItemMeta();

        meta.displayName(Component.text("COUNT "+i));

        List<Component> list = new ArrayList<>();
        list.add(Component.text(""));
        list.add(Component.text("Number of Entities: " + i));
        list.add(Component.text("Upgrade cost: xx"));

        meta.lore(list);
        return item;
    }

    private ItemStack getUpgradeTimeItem(int i){
        ItemStack item = new ItemStack(Material.CHEST);
        ItemMeta meta = item.getItemMeta();

        meta.displayName(Component.text("Time: "+i+" s"));

        List<Component> list = new ArrayList<>();
        list.add(Component.text(""));
        list.add(Component.text("Current spawning time: " + i+ " s"));
        list.add(Component.text("Upgrade cost: xx"));

        meta.lore(list);
        return item;
    }

    private int getPersistantDataInt(PersistentDataContainer container,NamespacedKey key){
        if (container.has(key)){
            return container.get(key, PersistentDataType.INTEGER);
        }
        return -1;
    }
}
