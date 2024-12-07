package cz.tallonscz.upgradablespawner.Listeners;

import cz.tallonscz.upgradablespawner.Upgradablespawner;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;

public class ListenerRegister {

    public ListenerRegister(){
        Plugin plugin = Upgradablespawner.INSTANCE;
        PluginManager pluginManager = Upgradablespawner.INSTANCE.getServer().getPluginManager();
        pluginManager.registerEvents(new BreakSpawnerEvent(), plugin);
        pluginManager.registerEvents(new PlaceSpawnerEvent(), plugin);
        //pluginManager.registerEvents();
    }
}
