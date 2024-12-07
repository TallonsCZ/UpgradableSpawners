package cz.tallonscz.upgradablespawner.Commands;

import cz.tallonscz.upgradablespawner.Upgradablespawner;

public class CommandRegister {



    public CommandRegister(){
        Upgradablespawner plugin = Upgradablespawner.CLASSINSTANCE;

        plugin.getCommand("UpgradeSpawner").setExecutor(new GlobalCommand());
    }

}
