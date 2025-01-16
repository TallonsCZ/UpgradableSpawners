package cz.tallonscz.upgradablespawner.Listeners;

import cz.tallonscz.upgradablespawner.GUI.UpgradeInventory;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

public class UpgradeInventoryEvent implements Listener {

    @EventHandler
    public void playerClickUpgradeInventoryEvent(InventoryClickEvent event){
        Player player = (Player) event.getWhoClicked();
        Inventory clickedInventory = event.getClickedInventory();
        if(clickedInventory == null){return;}
        Component playerInventory = player.getInventory().getType().defaultTitle();
        Component nameInventory = event.getView().title();
        Component upgradeInv = Component.text("Upgrade Menu");
        if(!nameInventory.equals(upgradeInv)){
            return;
        }
        if(event.getAction().equals(InventoryAction.MOVE_TO_OTHER_INVENTORY)){
            event.setCancelled(true);
            return;
        }
        if(!(clickedInventory.equals(event.getView().getTopInventory()))){
            return;
        }
        event.setCancelled(true);

    }

}
