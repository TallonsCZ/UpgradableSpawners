package cz.tallonscz.upgradablespawner.Keys;

import cz.tallonscz.upgradablespawner.Upgradablespawner;
import org.bukkit.NamespacedKey;
import org.bukkit.plugin.Plugin;

public class SpawnerKeys {
    static Plugin plugin = Upgradablespawner.INSTANCE;

    public static final NamespacedKey UPGRADESPAWNERS_SPAWNERS = new NamespacedKey(plugin, "UPGRADESPAWNERS_SPAWNERS");
}
