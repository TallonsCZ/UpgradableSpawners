package cz.tallonscz.upgradablespawner.Utilities;

import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class DefaultDrops {

    public static Collection<ItemStack> getDefaultDrops(EntityType type){
        List<ItemStack> drops = new ArrayList<>();

        switch (type) {
            case ZOMBIE:
                drops.add(new ItemStack(Material.ROTTEN_FLESH, 2));
                break;
            case SKELETON:
                drops.add(new ItemStack(Material.BONE, 2));
                drops.add(new ItemStack(Material.ARROW, 1));
            case SPIDER:
                drops.add(new ItemStack(Material.STRING, 2));
                drops.add(new ItemStack(Material.SPIDER_EYE, 1));
            default:
                break;
        }
        return drops;
    }

    public static Collection<ItemStack> getDefaultDropsCustom(String type){
        List<ItemStack> drops = new ArrayList<>();

        switch (type) {
            case "WOOD":
                drops.add(new ItemStack(Material.OAK_LOG, 1));
                break;
            case "INGOT":
                drops.add(new ItemStack(Material.RAW_COPPER, 1));
            default:
                break;
        }
        return drops;
    }

}
