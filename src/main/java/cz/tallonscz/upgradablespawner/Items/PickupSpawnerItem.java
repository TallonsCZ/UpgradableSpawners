package cz.tallonscz.upgradablespawner.Items;

import cz.tallonscz.upgradablespawner.Keys.SpawnerItemKeys;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

public class PickupSpawnerItem {
    private ItemStack item;

    PickupSpawnerItem(){
         item = new ItemStack(Material.STICK);
         ItemMeta itemMeta = item.getItemMeta();
         itemMeta.getPersistentDataContainer().set(SpawnerItemKeys.UPGRADESPAWNERS_ITEM_PICKUP_ITEM, PersistentDataType.BOOLEAN, true);
         itemMeta.displayName(Component.text("Spawner Picker"));
         item.setItemMeta(itemMeta);
    }

    public ItemStack getItem(){
        return item;
    }
}
