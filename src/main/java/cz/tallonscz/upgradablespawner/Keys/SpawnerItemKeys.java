package cz.tallonscz.upgradablespawner.Keys;

import cz.tallonscz.upgradablespawner.Upgradablespawner;
import org.bukkit.NamespacedKey;
import org.bukkit.plugin.Plugin;

public class SpawnerItemKeys {
    static Plugin plugin = Upgradablespawner.INSTANCE;

    public static final NamespacedKey UPGRADESPAWNERS_ITEM_LEVEL = new NamespacedKey(plugin, "UPGRADESPAWNERS_ITEM_LEVEL");
    public static final NamespacedKey UPGRADESPAWNERS_ITEM_TYPE = new NamespacedKey(plugin, "UPGRADESPAWNERS_ITEM_TYPE");
    public static final NamespacedKey UPGRADESPAWNERS_ITEM_SPAWNERS = new NamespacedKey(plugin, "UPGRADESPAWNERS_ITEM_SPAWNERS");
}
