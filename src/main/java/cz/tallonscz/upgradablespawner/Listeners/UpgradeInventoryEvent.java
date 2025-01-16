package cz.tallonscz.upgradablespawner.Listeners;

import cz.tallonscz.upgradablespawner.GUI.UpgradeInventory;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

public class UpgradeInventoryEvent implements Listener {

    @EventHandler
    public void playerClickUpgradeInventoryEvent(InventoryClickEvent event){
        Player player = (Player) event.getWhoClicked();
        Inventory clickedInventory = event.getClickedInventory();
        if(clickedInventory == null){return;}

        Component nameInventory = event.getView().title();
        Component upgradeInv = Component.text("Upgrade Menu");
        player.sendMessage(nameInventory + " " + upgradeInv);
        if(!nameInventory.equals(upgradeInv)){
            return;
        }
        event.setCancelled(true);

    }

}
