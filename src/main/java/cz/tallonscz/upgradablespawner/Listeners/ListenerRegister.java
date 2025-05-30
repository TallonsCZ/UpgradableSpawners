package cz.tallonscz.upgradablespawner.Listeners;

import cz.tallonscz.upgradablespawner.Upgradablespawner;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;

public class ListenerRegister {

    public static void registerListener(Plugin plugin){
        PluginManager pluginManager = Upgradablespawner.INSTANCE.getServer().getPluginManager();
        pluginManager.registerEvents(new BreakSpawnerEvent(), plugin);
        pluginManager.registerEvents(new PlaceSpawnerEvent(), plugin);
        pluginManager.registerEvents(new PlayerEvents(), plugin);
        pluginManager.registerEvents(new SpawnerCastEvent(), plugin);
        pluginManager.registerEvents(new UpgradeInventoryEvent(), plugin);
        pluginManager.registerEvents(new SpawnerInventoryEvent(), plugin);
    }
}
