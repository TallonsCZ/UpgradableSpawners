package cz.tallonscz.upgradablespawner.GUI;

import cz.tallonscz.upgradablespawner.Utilities.Database;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class SpawnerInventory {
    private static Map<Location, Inventory> spawnerInventories = new HashMap<>();

    public SpawnerInventory(){
    }

    public static void removeInventory(Location location){
        spawnerInventories.remove(location);

        try (Connection connection = Database.getConnection()){
            PreparedStatement preparedStatement = connection
                    .prepareStatement("DELETE FROM `spawners` WHERE `position` = ?");
            preparedStatement.setString(1, location.toString());
            preparedStatement.execute();
            connection.close();
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    public static UUID getOwner(Location location){
        UUID ownerUUID = null;
        try (Connection connection = Database.getConnection()){
            PreparedStatement preparedStatement = connection
                    .prepareStatement("SELECT owner FROM `spawners` WHERE `position` = ?");
            preparedStatement.setString(1, location.toString());
            preparedStatement.execute();
            ResultSet resultSet = preparedStatement.executeQuery();

            
            if (resultSet.next()) {
                String owner = resultSet.getString("owner");
                ownerUUID = UUID.fromString(owner);
            }
            connection.close();
        } catch (SQLException e){
            e.printStackTrace();
        }
        return ownerUUID;
    }

    public static boolean isThereSpawner(Location location){
        return spawnerInventories.get(location) != null;
    }

    public static void setInventory(Location location, Inventory inventory, Player player){
        spawnerInventories.put(location, inventory);
        try (Connection connection = Database.getConnection()){
            PreparedStatement preparedStatement = connection
                    .prepareStatement("INSERT INTO `spawners` (`position`, `owner`, `world`, `x`, `y`, `z`) VALUES (?, ?, ?, ?, ?, ?)");
            preparedStatement.setString(1, location.toString());
            preparedStatement.setString(2, player.getUniqueId().toString());
            preparedStatement.setString(3, location.getWorld().getUID().toString());
            preparedStatement.setDouble(4, location.x());
            preparedStatement.setDouble(5, location.y());
            preparedStatement.setDouble(6, location.z());
            preparedStatement.execute();
            connection.close();
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    public static void setInventory(Location location, Inventory inventory, OfflinePlayer player){
        spawnerInventories.put(location, inventory);
        try (Connection connection = Database.getConnection()){
            PreparedStatement preparedStatement = connection
                    .prepareStatement("INSERT INTO `spawners` (`position`, `owner`, `world`, `x`, `y`, `z`) VALUES (?, ?, ?, ?, ?, ?)");
            preparedStatement.setString(1, location.toString());
            preparedStatement.setString(2, player.getUniqueId().toString());
            preparedStatement.setString(3, location.getWorld().getUID().toString());
            preparedStatement.setDouble(4, location.x());
            preparedStatement.setDouble(5, location.y());
            preparedStatement.setDouble(6, location.z());
            preparedStatement.execute();
            connection.close();
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    public static Inventory getInventory(Location location){
        return spawnerInventories.get(location);
    }

    public static Map<Location, Inventory> getAllInventories(){
        return spawnerInventories;
    }

    public static void loadAllInventories(){
        try(Connection connection = Database.getConnection()){
            PreparedStatement preparedStatement = connection
                    .prepareStatement("SELECT * FROM spawners");
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                String world = resultSet.getString("world");
                UUID worldUUID = UUID.fromString(world);
                World worlD = Bukkit.getWorld(worldUUID);
                double x = resultSet.getDouble("x");
                double y = resultSet.getDouble("y");
                double z = resultSet.getDouble("z");
                Location location = new Location(worlD, x, y, z);
                Inventory loadInventory = inventoryFromBase64(resultSet.getString("inventory"));
                spawnerInventories.put(location, loadInventory);
            }
            connection.close();
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    public static void saveAllInventories(){
        spawnerInventories.forEach((location, inventory) -> {
            try(Connection connection = Database.getConnection()){
                PreparedStatement preparedStatement = connection
                        .prepareStatement("UPDATE spawners SET `inventory` = ? WHERE `position` = ?");
                preparedStatement.setString(1, inventoryToBase64(inventory));
                preparedStatement.setString(2, location.toString());
                preparedStatement.executeUpdate();
                connection.close();
            }catch (SQLException e){
                e.printStackTrace();
                return;
            }
        });

            
        }

    private static String inventoryToBase64(Inventory inventory){
        try{
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            ObjectOutputStream dataOutput = new ObjectOutputStream(outputStream);

            dataOutput.writeInt(inventory.getSize());

            for (int i = 0; i < inventory.getSize(); i++){
                ItemStack item = inventory.getItem(i);
                if (item != null){
                    dataOutput.writeObject(item.serialize());
                }else{
                    dataOutput.writeObject(null);
                }

            }

            dataOutput.close();
            return Base64.getEncoder().encodeToString(outputStream.toByteArray());

        }catch (Exception e){
            e.printStackTrace();;
            return null;
        }
    }

    private static Inventory inventoryFromBase64(String data){
        try {
            ByteArrayInputStream inputStream = new ByteArrayInputStream(Base64.getDecoder().decode(data));
            ObjectInputStream dataInput = new ObjectInputStream(inputStream);

            int size = dataInput.readInt();
            Inventory inventory = Bukkit.createInventory(null, size, Component.text("Spawner Inventory"));

            for (int i = 0; i < size; i++){
                Map<String, Object> serializedItem = (Map<String, Object>) dataInput.readObject();
                if (serializedItem != null){
                    ItemStack item = ItemStack.deserialize(serializedItem);
                    inventory.setItem(i, item);
                }

            }
            dataInput.close();
            return inventory;
        } catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

}
