package cz.tallonscz.upgradablespawner.Listeners;

import cz.tallonscz.upgradablespawner.GUI.UpgradeInventory;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

public class UpgradeInventoryEvent implements Listener {

    @EventHandler
    public void playerClickUpgradeInventoryEvent(InventoryClickEvent event){
        //Gets variables
        UpgradeInventory upgradeInventory = new UpgradeInventory();
        Inventory upgradeInv = upgradeInventory.getInventory();
        Inventory inv = event.getClickedInventory();
        Player player = (Player) event.getWhoClicked();

        if(inv != upgradeInv){
            return;
        }



    }

}
