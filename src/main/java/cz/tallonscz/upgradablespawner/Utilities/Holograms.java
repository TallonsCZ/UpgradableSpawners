package cz.tallonscz.upgradablespawner.Utilities;

import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;

public class Holograms {

    public void createHologram(Location location, String... inLines){
        String[] lines;
        lines = inLines;

        for (String line : lines){
            ArmorStand stand = location.getWorld().spawn(location, ArmorStand.class);

            stand.setVisible(false);
            stand.setGravity(false);
            stand.setInvulnerable(false);

            stand.setCustomNameVisible(true);
            stand.setCustomName(Common.colorize(line));

            location.subtract(0,0.25, 0);
        }
    }

    public void deleteHologram(Location location){
        for(Entity entity: location.getWorld().getNearbyEntities(location, 3, 6, 3)){
            if(entity instanceof ArmorStand && !entity.hasGravity()){
                entity.remove();
            }
        }
    }
}
