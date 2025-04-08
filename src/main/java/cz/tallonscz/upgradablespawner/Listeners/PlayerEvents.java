package cz.tallonscz.upgradablespawner.Listeners;

import cz.tallonscz.upgradablespawner.GUI.SpawnerInventory;
import cz.tallonscz.upgradablespawner.GUI.UpgradeInventory;
import cz.tallonscz.upgradablespawner.Items.PickupSpawnerItem;
import cz.tallonscz.upgradablespawner.Items.SpawnerItem;
import cz.tallonscz.upgradablespawner.Keys.SpawnerKeys;
import cz.tallonscz.upgradablespawner.Spawners.SpawnerBlock;
import cz.tallonscz.upgradablespawner.Upgradablespawner;
import cz.tallonscz.upgradablespawner.Utilities.Holograms;
import net.kyori.adventure.text.Component;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.CreatureSpawner;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.Map;

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
        PickupSpawnerItem pickupItem = new PickupSpawnerItem();
        if (event.getPlayer().getInventory().getItemInMainHand().equals(pickupItem.getItem()) || event.getPlayer().getInventory().getItemInOffHand().equals(pickupItem.getItem())){
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
        if (Boolean.FALSE.equals(spawner.getPersistentDataContainer().get(SpawnerKeys.UPGRADESPAWNER_SPAWNER_PLAYER, PersistentDataType.BOOLEAN))){
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
        if(block == null){
            return;
        }
        if(!(event.getAction().isRightClick())){
            return;
        }
        PickupSpawnerItem pickupItem = new PickupSpawnerItem();
        if (event.getPlayer().getInventory().getItemInMainHand().equals(pickupItem.getItem()) || event.getPlayer().getInventory().getItemInOffHand().equals(pickupItem.getItem())){
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
        if (Boolean.FALSE.equals(spawner.getPersistentDataContainer().get(SpawnerKeys.UPGRADESPAWNER_SPAWNER_PLAYER, PersistentDataType.BOOLEAN))){
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

    @EventHandler
    public void playerPickSpawnerItem(PlayerInteractEvent event){
        Block block = event.getClickedBlock();
        if(block == null){
            return;
        }
        if(!(event.getAction().isRightClick())){
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
        if (Boolean.TRUE.equals(spawner.getPersistentDataContainer().get(SpawnerKeys.UPGRADESPAWNER_SPAWNER_PLAYER, PersistentDataType.BOOLEAN))){
            return;
        }
        Player player = event.getPlayer();
        PickupSpawnerItem pickupItem = new PickupSpawnerItem();
        if (!(player.getInventory().getItemInMainHand().isSimilar(pickupItem.getItem()) || player.getInventory().getItemInOffHand().isSimilar(pickupItem.getItem()))){
            return;
        }
        if((spawner.getPersistentDataContainer().has(SpawnerKeys.UPGRADESPAWNERS_ACTIVE_PICKUP))){
            if(spawner.getPersistentDataContainer().get(SpawnerKeys.UPGRADESPAWNERS_ACTIVE_PICKUP, PersistentDataType.BOOLEAN) == true){
                player.sendMessage("This action is already started..");
                return;
            }
        }

        //Create function to get item from player inventory
        spawner.getPersistentDataContainer().set(SpawnerKeys.UPGRADESPAWNERS_ACTIVE_PICKUP, PersistentDataType.BOOLEAN, true);
        spawner.update();
        if (player.getInventory().getItemInMainHand().isSimilar(pickupItem.getItem())) {
            player.getInventory().getItemInMainHand().subtract();
        } else if (player.getInventory().getItemInOffHand().isSimilar(pickupItem.getItem())) {
            player.getInventory().getItemInOffHand().subtract();
        } else {
            return;
        }
        final Holograms holos = new Holograms();
        new BukkitRunnable(){
            int timeLeft = 30;

            @Override
            public void run() {
                holos.deleteHologram(block.getLocation());
                if(timeLeft <= 0){
                    SpawnerItem item = new SpawnerItem();
                    player.getWorld().dropItem(block.getLocation().subtract(0,1,0).add(0.5,0,0.5), item.getSpawner(block));
                    placeSpawner(block, player.getWorld(), item.getSpawnerForRespawn(block));
                    respawningBlock.put(player.getLocation(), block);
                    block.breakNaturally(false);
                    cancel();
                }
                holos.createHologram(block.getLocation().subtract(0, 1, 0).add(0.5, 0, 0.5), "Zbývající čas", String.valueOf(timeLeft));
                timeLeft--;
            }
        }.runTaskTimer(Upgradablespawner.INSTANCE, 0, 20);
        holos.deleteHologram(block.getLocation());

    }

    static public Map<Location, Block> respawningBlock = new HashMap<>();

    private void placeSpawner(Block block, World world, ItemStack itemStack){
        Block newBlock = world.getBlockAt(block.getLocation());
        new BukkitRunnable(){
            @Override
            public void run(){
                final Holograms holos = new Holograms();
                holos.deleteHologram(block.getLocation());
            }
        }.runTaskLater(Upgradablespawner.INSTANCE, 1);
        new BukkitRunnable(){
            int cooldown = 60;
            @Override
            public void run() {
                newBlock.setType(Material.SPAWNER);
                SpawnerBlock.setSpawnerBlock(newBlock, itemStack.getItemMeta());
                respawningBlock.remove(block.getLocation());
            }
        }.runTaskLater(Upgradablespawner.INSTANCE, 20*43200);
    }
}


