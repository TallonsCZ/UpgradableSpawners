package cz.tallonscz.upgradablespawner;

import cz.tallonscz.upgradablespawner.Commands.CommandRegister;
import cz.tallonscz.upgradablespawner.GUI.SpawnerInventory;
import cz.tallonscz.upgradablespawner.Listeners.ListenerRegister;
import cz.tallonscz.upgradablespawner.Utilities.Database;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import javax.xml.crypto.Data;
import java.io.File;

public final class Upgradablespawner extends JavaPlugin {

    public static Plugin INSTANCE;
    public static Upgradablespawner CLASSINSTANCE;
    @Override
    public void onEnable() {
        INSTANCE = this;
        CLASSINSTANCE = this;

        if(!getDataFolder().exists()){
            getDataFolder().mkdirs();
        }

        File mysql = new File(getDataFolder(), "mysql.yml");

        if(!mysql.exists()){
            saveResource("mysql.yml", false);
        }

        //Registering commands
        CommandRegister commandRegister = new CommandRegister();
        ListenerRegister listenerRegister = new ListenerRegister();
        //getServer().getPluginManager().registerEvents();



        try{
            Database.initializeDatabase();
        }catch (Exception e){
            getServer().getPluginManager().disablePlugin(this);
        }

        SpawnerInventory.loadAllInventories();
    }

    @Override
    public void onDisable() {
        if (SpawnerInventory.getAllInventories().isEmpty()) {
            System.err.println("Spawner inventories are already empty during onDisable!");
        } else {
            SpawnerInventory.saveAllInventories();
            System.out.println("Spawner inventories saved successfully!");
        }
        Database.close();
    }
}
