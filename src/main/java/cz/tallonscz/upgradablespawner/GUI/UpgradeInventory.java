package cz.tallonscz.upgradablespawner.GUI;

import cz.tallonscz.upgradablespawner.Keys.SpawnerKeys;
import cz.tallonscz.upgradablespawner.Utilities.Economy;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UpgradeInventory {
    private Inventory inventory;

    private static Map<Player, Inventory> openedUpgradeInventory = new HashMap<>();

    public UpgradeInventory(PersistentDataContainer container){
        createInventory(container);
    }

    private void createInventory(PersistentDataContainer container){
        inventory = Bukkit.createInventory(null, 27, Component.text("Upgrade Menu"));
        inventory.setItem(10, getUpgradeAmountItem(getPersistantDataInt(container, SpawnerKeys.UPGRADESPAWNERS_SPAWNER_AMOUNT)));
        inventory.setItem(13, getUpgradeTimeItem(getPersistantDataInt(container, SpawnerKeys.UPGRADESPAWNERS_SPAWNER_TIME)));
        inventory.setItem(16, getUpgradeSizeItem(getPersistantDataInt(container, SpawnerKeys.UPGRADESPAWNERS_SPAWNER_STORAGE)));
    }

    public static void updateInventory(Inventory inventory, PersistentDataContainer container){
        inventory.clear();
        inventory.setItem(10, getUpgradeAmountItem(getPersistantDataInt(container, SpawnerKeys.UPGRADESPAWNERS_SPAWNER_AMOUNT)));
        inventory.setItem(13, getUpgradeTimeItem(getPersistantDataInt(container, SpawnerKeys.UPGRADESPAWNERS_SPAWNER_TIME)));
        inventory.setItem(16, getUpgradeSizeItem(getPersistantDataInt(container, SpawnerKeys.UPGRADESPAWNERS_SPAWNER_STORAGE)));
    }

    public Inventory getInventory() {
        return inventory;
    }

    private static ItemStack getUpgradeSizeItem(int i){
        ItemStack item = new ItemStack(Material.CHEST);
        ItemMeta meta = item.getItemMeta();

        if(i >= 54){
            meta.displayName(Component.text("SIZE "+i/9).color(TextColor.color(255, 255, 255)).decoration(TextDecoration.ITALIC, false));

            List<Component> list = new ArrayList<>();
            list.add(Component.text(""));
            list.add(Component.text("Inventory size is on maximum size.").color(TextColor.color(255, 255, 255)).decoration(TextDecoration.ITALIC, false));
            list.add(Component.text("You can put chest on South site,").color(TextColor.color(255, 255, 255)).decoration(TextDecoration.ITALIC, false));
            list.add(Component.text("and the Spawner put item there.").color(TextColor.color(255, 255, 255)).decoration(TextDecoration.ITALIC, false));


            meta.lore(list);

            item.setItemMeta(meta);
            return item;
        }

        meta.displayName(Component.text("SIZE "+i/9).color(TextColor.color(255, 255, 255)).decoration(TextDecoration.ITALIC, false));

        List<Component> list = new ArrayList<>();
        list.add(Component.text(""));
        list.add(Component.text("Inventory size is: " + i).color(TextColor.color(255, 255, 255)).decoration(TextDecoration.ITALIC, false));
        double roundedCost = Math.round(Economy.upgradeCostCalculationStorage(i/9) * 100.0) / 100.0;
        list.add(Component.text("Upgrade cost: "+ roundedCost).color(TextColor.color(255, 255, 255)).decoration(TextDecoration.ITALIC, false));


        meta.lore(list);

        item.setItemMeta(meta);
        return item;
    }

    private static ItemStack getUpgradeAmountItem(int i){
        ItemStack item = new ItemStack(Material.CHEST);
        ItemMeta meta = item.getItemMeta();

        if(i >= 5){
            meta.displayName(Component.text("AMOUNT "+i).color(TextColor.color(255, 255, 255)).decoration(TextDecoration.ITALIC, false));

            List<Component> list = new ArrayList<>();
            list.add(Component.text(""));
            list.add(Component.text("Amount is on maximum lvl.").color(TextColor.color(255, 255, 255)).decoration(TextDecoration.ITALIC, false));


            meta.lore(list);

            item.setItemMeta(meta);
            return item;
        }

        meta.displayName(Component.text("AMOUNT "+i).color(TextColor.color(255, 255, 255)).decoration(TextDecoration.ITALIC, false));

        List<Component> list = new ArrayList<>();
        list.add(Component.text(""));
        list.add(Component.text("Max number of Entities: " + i).color(TextColor.color(255, 255, 255)).decoration(TextDecoration.ITALIC, false));
        double roundedCost = Math.round(Economy.upgradeCostCalculation(i) * 100.0) / 100.0;
        list.add(Component.text("Upgrade cost: "+ roundedCost).color(TextColor.color(255, 255, 255)).decoration(TextDecoration.ITALIC, false));

        meta.lore(list);
        item.setItemMeta(meta);
        return item;
    }

    private static ItemStack getUpgradeTimeItem(int i){
        ItemStack item = new ItemStack(Material.CHEST);
        ItemMeta meta = item.getItemMeta();
        int[] position = {0,6,5,4,3,2,1};

        if(i <= 5){
            meta.displayName(Component.text("Time "+position[i/5]).color(TextColor.color(255, 255, 255)).decoration(TextDecoration.ITALIC, false));

            List<Component> list = new ArrayList<>();
            list.add(Component.text(""));
            list.add(Component.text("Time is on maximum lvl.").color(TextColor.color(255, 255, 255)).decoration(TextDecoration.ITALIC, false));


            meta.lore(list);

            item.setItemMeta(meta);
            return item;
        }



        meta.displayName(Component.text("Time "+position[i/5]).color(TextColor.color(255, 255, 255)).decoration(TextDecoration.ITALIC, false));
        List<Component> list = new ArrayList<>();
        list.add(Component.text(""));
        list.add(Component.text("Current spawning time: " + i + " s").color(TextColor.color(255, 255, 255)).decoration(TextDecoration.ITALIC, false));
        list.add(Component.text("Upgrade cost: "+ Economy.upgradeCostCalculation(position[i/5])).color(TextColor.color(255, 255, 255)).decoration(TextDecoration.ITALIC, false));

        meta.lore(list);
        item.setItemMeta(meta);
        return item;
    }

    private static int getPersistantDataInt(PersistentDataContainer container, NamespacedKey key){
        if (container.has(key)){
            return container.get(key, PersistentDataType.INTEGER);
        }
        return -1;
    }

    public static Inventory getUpgradeInventory(Player player){
        return openedUpgradeInventory.get(player);
    }

    public static void putInventoryToMap(Inventory inventory, Player player){
        openedUpgradeInventory.put(player, inventory);
    }

    public static void removeInventoryFromMap(Player player){
        openedUpgradeInventory.remove(player);
    }
}
