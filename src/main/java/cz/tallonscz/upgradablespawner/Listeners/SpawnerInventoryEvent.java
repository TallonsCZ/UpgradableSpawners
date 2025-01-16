package cz.tallonscz.upgradablespawner.Listeners;

import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;

public class SpawnerInventoryEvent implements Listener {

    @EventHandler
    public void onSpawnerInventoryClick(InventoryClickEvent event){
        Player player = (Player) event.getWhoClicked();
        Inventory clickedInventory = event.getClickedInventory();
        if(clickedInventory == null){return;}
        Component nameInventory = event.getView().title();
        Component upgradeInv = Component.text("Spawner Inventory");
        if(!nameInventory.equals(upgradeInv)){
            return;
        }
        if (event.getAction().equals(InventoryAction.MOVE_TO_OTHER_INVENTORY)) {
            if (clickedInventory.equals(event.getView().getBottomInventory())) {
                event.setCancelled(true);
            }
        }
        if (event.getCursor() != null && event.getCursor().getType() != Material.AIR){
            if (event.getSlotType() == InventoryType.SlotType.CONTAINER && event.getSlot() < event.getView().getTopInventory().getSize()){
                event.setCancelled(true);
                return;
            }
        }
    }

    @EventHandler
    public void onSpawnerInventoryDrag(InventoryDragEvent event) {
        Player player = (Player) event.getWhoClicked();
        Component nameInventory = event.getView().title();
        Component spawInventory = Component.text("Spawner Inventory");

        if(!nameInventory.equals(spawInventory) && !nameInventory.equals(Component.text("Upgrade Menu"))){return;}

        int topInventorySize = event.getView().getTopInventory().getSize();
        for(int slot: event.getRawSlots()){
            if (slot < topInventorySize){
                event.setCancelled(true);
                return;
            }
        }
    }
}
