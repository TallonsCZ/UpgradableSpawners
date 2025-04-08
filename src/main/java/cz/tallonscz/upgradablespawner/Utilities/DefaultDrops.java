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

    public static Collection<ItemStack> getDefaultDrops(String type){
        List<ItemStack> drops = new ArrayList<>();

        switch (type) {
            case "WOOD":
                drops.add(new ItemStack(Material.OAK_LOG, 1));
                drops.add(new ItemStack(Material.ACACIA_LOG, 1));
                drops.add(new ItemStack(Material.BIRCH_LOG, 1));
                drops.add(new ItemStack(Material.CHERRY_LOG, 1));
                drops.add(new ItemStack(Material.DARK_OAK_LOG, 1));
                drops.add(new ItemStack(Material.JUNGLE_LOG, 1));
                drops.add(new ItemStack(Material.MANGROVE_LOG, 1));
                drops.add(new ItemStack(Material.SPRUCE_LOG, 1));
                break;
            case "INGOT":
                drops.add(new ItemStack(Material.RAW_COPPER, 1));
                drops.add(new ItemStack(Material.RAW_IRON, 1));
                drops.add(new ItemStack(Material.REDSTONE, 4));
                drops.add(new ItemStack(Material.RAW_GOLD, 1));
            default:
                break;
        }
        return drops;
    }

}
