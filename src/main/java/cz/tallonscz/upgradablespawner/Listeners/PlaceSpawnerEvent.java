package cz.tallonscz.upgradablespawner.Listeners;

import cz.tallonscz.upgradablespawner.GUI.SpawnerInventory;
import cz.tallonscz.upgradablespawner.Keys.SpawnerItemKeys;
import cz.tallonscz.upgradablespawner.Spawners.SpawnerBlock;
import cz.tallonscz.upgradablespawner.Utilities.Database;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class PlaceSpawnerEvent implements Listener {

    Database database = new Database();

    @EventHandler
    public void spawnerPlaceEvent(BlockPlaceEvent event){
        Block block = event.getBlockPlaced();
        ItemMeta meta = event.getItemInHand().getItemMeta();
        if(!(meta.getPersistentDataContainer().has(SpawnerItemKeys.UPGRADESPAWNERS_ITEM_SPAWNERS))){
            return;
        }
        SpawnerBlock.setSpawnerBlock(block, meta);

        //Vznik spawnerInventory
        Inventory inventory = Bukkit.createInventory(null, meta.getPersistentDataContainer().get(SpawnerItemKeys.UPGRADESPAWNERS_ITEM_STORAGE, PersistentDataType.INTEGER), Component.text("Spawner Inventory"));
        SpawnerInventory.setInventory(block.getLocation(), inventory);

        try (Connection connection = Database.getConnection()){
            PreparedStatement preparedStatement = connection
                    .prepareStatement("INSERT INTO `spawners` (`position`, `owner`, `world`, `x`, `y`, `z`) VALUES (?, ?, ?, ?, ?, ?)");
            preparedStatement.setString(1, block.getLocation().toString());
            preparedStatement.setString(2, event.getPlayer().getUniqueId().toString());
            preparedStatement.setString(3, block.getLocation().getWorld().getUID().toString());
            preparedStatement.setDouble(4, block.getLocation().x());
            preparedStatement.setDouble(5, block.getLocation().y());
            preparedStatement.setDouble(6, block.getLocation().z());
            preparedStatement.execute();
            connection.close();
        } catch (SQLException e){
            e.printStackTrace();
        }
        SpawnerInventory.saveAllInventories();
    }
}
