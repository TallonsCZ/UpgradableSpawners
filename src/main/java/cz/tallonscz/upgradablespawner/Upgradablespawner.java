package cz.tallonscz.upgradablespawner;

import cz.tallonscz.upgradablespawner.Commands.CommandRegister;
import cz.tallonscz.upgradablespawner.GUI.SpawnerInventory;
import cz.tallonscz.upgradablespawner.Listeners.ListenerRegister;
import cz.tallonscz.upgradablespawner.Utilities.Database;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public final class Upgradablespawner extends JavaPlugin {
    private static Economy econ = null;
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

        if (!setupEconomy() ) {
            getLogger().severe(String.format("[%s] - Disabled due to no Vault dependency found!", getDescription().getName()));
            getServer().getPluginManager().disablePlugin(this);
            return;
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

    private boolean setupEconomy() {
        if (getServer().getPluginManager().getPlugin("Vault") == null) {
            return false;
        }
        RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) {
            return false;
        }
        econ = rsp.getProvider();
        return econ != null;
    }

    public static Economy getEconomy() {
        return econ;
    }
}
