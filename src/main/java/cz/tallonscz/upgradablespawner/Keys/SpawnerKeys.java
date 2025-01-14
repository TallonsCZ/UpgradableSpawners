package cz.tallonscz.upgradablespawner.Keys;

import cz.tallonscz.upgradablespawner.Upgradablespawner;
import org.bukkit.NamespacedKey;
import org.bukkit.plugin.Plugin;

public class SpawnerKeys {
    static Plugin plugin = Upgradablespawner.INSTANCE;

    public static final NamespacedKey UPGRADESPAWNERS_SPAWNER_SPAWNERS = new NamespacedKey(plugin, "UPGRADESPAWNERS_SPAWNER_SPAWNERS");
    public static final NamespacedKey UPGRADESPAWNERS_SPAWNER_LEVEL = new NamespacedKey(plugin, "UPGRADESPAWNERS_SPAWNER_LEVEL");
    public static final NamespacedKey UPGRADESPAWNERS_SPAWNER_TIME = new NamespacedKey(plugin, "UPGRADESPAWNERS_SPAWNER_TIME");
    public static final NamespacedKey UPGRADESPAWNERS_SPAWNER_LOOT = new NamespacedKey(plugin, "UPGRADESPAWNERS_SPAWNER_LOOT");
    public static final NamespacedKey UPGRADESPAWNERS_SPAWNER_AMOUNT = new NamespacedKey(plugin, "UPGRADESPAWNERS_SPAWNER_AMOUNT");
    public static final NamespacedKey UPGRADESPAWNERS_SPAWNER_TYPE = new NamespacedKey(plugin, "UPGRADESPAWNERS_SPAWNER_TYPE");
    public static final NamespacedKey UPGRADESPAWNERS_SPAWNER_STORAGE = new NamespacedKey(plugin, "UPGRADESPAWNERS_SPAWNER_STORAGE");
}
