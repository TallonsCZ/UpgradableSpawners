package cz.tallonscz.upgradablespawner.Listeners;

import cz.tallonscz.upgradablespawner.Keys.SpawnerItemKeys;
import cz.tallonscz.upgradablespawner.Spawners.SpawnerBlock;
import cz.tallonscz.upgradablespawner.Utilities.Database;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.meta.ItemMeta;

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
        try (Connection connection = database.hikariDataSource.getConnection()){
            PreparedStatement preparedStatement = connection
                    .prepareStatement("INSERT INTO `spawners` (`position`, `owner`) VALUES (?, ?)");
            preparedStatement.setString(1, block.getLocation().toString());
            preparedStatement.setString(2, event.getPlayer().getUniqueId().toString());
            preparedStatement.execute();
        } catch (SQLException e){
            e.printStackTrace();
        }
    }
}
