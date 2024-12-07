package cz.tallonscz.upgradablespawner;

import cz.tallonscz.upgradablespawner.Commands.CommandRegister;
import cz.tallonscz.upgradablespawner.Listeners.ListenerRegister;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public final class Upgradablespawner extends JavaPlugin {

    public static Plugin INSTANCE;
    public static Upgradablespawner CLASSINSTANCE;
    @Override
    public void onEnable() {
        INSTANCE = this;
        CLASSINSTANCE = this;

        //Registering commands
        CommandRegister commandRegister = new CommandRegister();
        ListenerRegister listenerRegister = new ListenerRegister();
        //getServer().getPluginManager().registerEvents();
    }

    @Override
    public void onDisable() {

    }
}
