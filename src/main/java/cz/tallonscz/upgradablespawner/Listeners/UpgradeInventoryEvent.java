package cz.tallonscz.upgradablespawner.Listeners;

import cz.tallonscz.upgradablespawner.GUI.UpgradeInventory;
import cz.tallonscz.upgradablespawner.Keys.SpawnerKeys;
import cz.tallonscz.upgradablespawner.Spawners.SpawnerBlock;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.CreatureSpawner;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

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

        PersistentDataContainer dataContainer = player.getPersistentDataContainer();
        String blockLocationString = dataContainer.get(SpawnerKeys.UPGRADESPAWNERS_PLAYER_LASTSPAWNER, PersistentDataType.STRING);
        if (blockLocationString == null) {
            player.sendMessage("Spawner block not found!");
            return;
        }

        Location location = deserializeLocation(blockLocationString);
        Block block = location.getBlock();

        if (block.getType() != Material.SPAWNER){
            return;
        }
        BlockState state = block.getState();
        if(!(state instanceof CreatureSpawner spawner)){
            return;
        }
        event.setCancelled(true);
        switch (event.getSlot()){
            case 10:
                if (SpawnerBlock.changeAmount(spawner) == 1){
                    player.sendMessage(Component.text("[UpgradeSpawner] Spawner is on max upgrade value!"));
                }
                UpgradeInventory.updateInventory(UpgradeInventory.getUpgradeInventory(player), spawner.getPersistentDataContainer());
                break;
            case 12:
                if (SpawnerBlock.changeTime(spawner)==1){
                    player.sendMessage(Component.text("[UpgradeSpawner] Spawner is on max upgrade value!"));
                }
                UpgradeInventory.updateInventory(UpgradeInventory.getUpgradeInventory(player), spawner.getPersistentDataContainer());
                break;
            case 14:
                break;
            default:
                break;
        }
    }

    private Location deserializeLocation(String locationString) {
        String[] parts = locationString.split(";");

        World world = Bukkit.getWorld(parts[0]);
        double x = Double.parseDouble(parts[1]);
        double y = Double.parseDouble(parts[2]);
        double z = Double.parseDouble(parts[3]);

        return new Location(world, x, y, z);
    }

}
