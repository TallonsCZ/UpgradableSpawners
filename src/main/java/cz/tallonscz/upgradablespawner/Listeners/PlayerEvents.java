package cz.tallonscz.upgradablespawner.Listeners;

import cz.tallonscz.upgradablespawner.GUI.SpawnerInventory;
import cz.tallonscz.upgradablespawner.GUI.UpgradeInventory;
import cz.tallonscz.upgradablespawner.Keys.SpawnerKeys;
import net.kyori.adventure.text.Component;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.CreatureSpawner;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

public class PlayerEvents implements Listener {

    @EventHandler
    public void playerOpenSpawnerInventory(PlayerInteractEvent event){
        Block block = event.getClickedBlock();
        if(block == null){
            return;
        }
        if(!(event.getAction().isRightClick())){
            return;
        }
        if(event.getPlayer().isSneaking()){
            return;
        }
        if(block.getType() != Material.SPAWNER){
            return;
        }
        if (!(block.getState() instanceof CreatureSpawner spawner)){
            return;
        }
        if (!(spawner.getPersistentDataContainer().has(SpawnerKeys.UPGRADESPAWNERS_SPAWNER_SPAWNERS))){
            return;
        }
        Player player = event.getPlayer();
        Material itemInHand = player.getInventory().getItemInMainHand().getType();
        if (itemInHand.toString().endsWith("_SPAWN_EGG")) {
            event.setCancelled(true);
            return;
        }

        Location location = block.getLocation();
        Inventory inv = SpawnerInventory.getInventory(location);
        event.getPlayer().openInventory(inv);

    }

    @EventHandler
    public void playerOpenUpgradeInventory(PlayerInteractEvent event){
        Block block = event.getClickedBlock();
        assert block != null;
        if(!(event.getAction().isRightClick())){
            return;
        }
        if(!(event.getPlayer().isSneaking())){
            return;
        }
        if(block.getType() != Material.SPAWNER){
            return;
        }
        if (!(block.getState() instanceof CreatureSpawner spawner)){
            return;
        }
        if (!(spawner.getPersistentDataContainer().has(SpawnerKeys.UPGRADESPAWNERS_SPAWNER_SPAWNERS))){
            return;
        }
        Player player = event.getPlayer();
        Material itemInHand = player.getInventory().getItemInMainHand().getType();
        System.out.println(itemInHand.toString().endsWith("_SPAWN_EGG"));
        if (itemInHand.toString().endsWith("_SPAWN_EGG")) {
            event.setCancelled(true);
            return;
        }

        UpgradeInventory upgradeInventory = new UpgradeInventory(spawner.getPersistentDataContainer());
        Inventory upgInv = upgradeInventory.getInventory();
        player.openInventory(upgInv);
        UpgradeInventory.putInventoryToMap(upgInv, player);
        event.setCancelled(true);

        PersistentDataContainer container = player.getPersistentDataContainer();
        container.set(SpawnerKeys.UPGRADESPAWNERS_PLAYER_LASTSPAWNER, PersistentDataType.STRING, serializeLocation(block.getLocation()));
    }
    private String serializeLocation(Location location) {
        return location.getWorld().getName() + ";" + location.getX() + ";" + location.getY() + ";" + location.getZ();
    }

    @EventHandler
    public void playerCloseInventory(InventoryCloseEvent event){
        Player player = (Player) event.getPlayer();
        Inventory clickedInventory = event.getInventory();
        if(clickedInventory == null){return;}
        Component nameInventory = event.getView().title();
        Component upgradeInv = Component.text("Upgrade Menu");
        if(!nameInventory.equals(upgradeInv)){
            return;
        }
        UpgradeInventory.removeInventoryFromMap(player);
    }

}


