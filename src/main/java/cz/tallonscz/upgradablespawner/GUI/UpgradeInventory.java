package cz.tallonscz.upgradablespawner.GUI;

import org.bukkit.Bukkit;
import org.bukkit.inventory.Inventory;

public class UpgradeInventory {

    private Inventory inventory;

    private UpgradeInventory(){

    }

    private void createInventory(){
        inventory = Bukkit.createInventory(null, 27);

    }

    public Inventory getInventory() {
        return inventory;
    }
}
