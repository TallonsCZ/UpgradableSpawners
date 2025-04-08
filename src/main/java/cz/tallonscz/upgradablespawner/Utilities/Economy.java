package cz.tallonscz.upgradablespawner.Utilities;

import cz.tallonscz.upgradablespawner.Upgradablespawner;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

public class Economy {

    private static double MULTIPLICATOR = 2.5;
    private static double MULTIPLICATOR_STORAGE = 3.5;
    private static final net.milkbowl.vault.economy.Economy eco = Upgradablespawner.getEconomy();

    public static double getPlayerEconomy(Player player){
        return Upgradablespawner.getEconomy().getBalance(player);
    }

    public static boolean hasPlayerAnoughtMoney(Player player, double value){
        double currentMoney = eco.getBalance(player);
        if (currentMoney >= value){
            return true;
        }else{
            return false;
        }
    }

    public static boolean removeMoneyFromPlayer(Player player, double value){
        if (!hasPlayerAnoughtMoney(player, value)){
            return false;
        }
        eco.withdrawPlayer(player, value);
        return true;
    }

    public static double upgradeCostCalculation(int lvl){
        return 1000 * Math.pow(MULTIPLICATOR, lvl-1);
    }

    public static double upgradeCostCalculationStorage(int lvl){
        return 1000 * Math.pow(MULTIPLICATOR_STORAGE, lvl-1);
    }
}
