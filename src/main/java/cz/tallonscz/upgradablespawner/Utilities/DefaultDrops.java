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
            default:
                break;
        }
        return drops;
    }

}
