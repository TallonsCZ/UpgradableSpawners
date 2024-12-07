package cz.tallonscz.upgradablespawner;

import cz.tallonscz.upgradablespawner.Commands.CommandRegister;
import cz.tallonscz.upgradablespawner.Listeners.ListenerRegister;
import cz.tallonscz.upgradablespawner.Utilities.Database;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

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
            Database database = new Database();
        }catch (Exception e){
            getServer().getPluginManager().disablePlugin(this);
        }


    }

    @Override
    public void onDisable() {

    }
}
