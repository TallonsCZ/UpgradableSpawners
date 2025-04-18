package cz.tallonscz.upgradablespawner.Commands;

import cz.tallonscz.upgradablespawner.Items.PickupSpawnerItem;
import cz.tallonscz.upgradablespawner.Items.SpawnerItem;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.List;

public class GlobalCommand implements CommandExecutor, TabExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if(!(commandSender instanceof Player player)){
            commandSender.sendMessage("This command can be only execute by Player");
            return true;
        }
        List<String> allowedTypes = Arrays.asList("ZOMBIE", "SKELETON", "WOOD", "INGOT");
        switch (strings[0]) {
            case "getSpawner":
                if (!allowedTypes.contains(strings[1].toUpperCase())) {
                    player.sendMessage("You used invalid type. Allowed: " + String.join(", ", allowedTypes));
                    return true;
                }else{
                    return getSpawnerItem(player, strings[1]);
                }

            case "getItem":
                return getPickupItem(player);
        }
        player.sendMessage("You have just type command.");
        return true;
    }

    private boolean getPickupItem(Player player){
        PickupSpawnerItem item = new PickupSpawnerItem();
        player.getInventory().addItem(item.getItem());
        return true;
    }
    private boolean getSpawnerItem(Player player, String type){
        SpawnerItem spawnerItem = new SpawnerItem();
        ItemStack item = spawnerItem.getSpawner(type, 1);
        player.getInventory().addItem(item);
        return true;
    }


    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (args.length == 1){
            return Arrays.asList(
                    "getSpawner",
                    "getItem"
            );
        } else if (args.length == 2 && args[0].equals("getSpawner")) {
            return Arrays.asList(
                    "ZOMBIE",
                    "SKELETON",
                    "WOOD",
                    "INGOT"
            );
        }
        return List.of();
    }
}
