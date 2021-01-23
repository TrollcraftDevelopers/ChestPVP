package pl.trollcraft.chestpvp.provider;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;

public class HelpProvider {

    public static class Locations {

        public static Location locFromString(String str) {
            String[] str2loc = str.split(":");
            org.bukkit.Location loc = new org.bukkit.Location((World) Bukkit.getWorlds().get(0), 0.0D, 0.0D, 0.0D, 0.0F, 0.0F);
            loc.setWorld(Bukkit.getWorld(str2loc[0]));
            loc.setX(Double.parseDouble(str2loc[1]));
            loc.setY(Double.parseDouble(str2loc[2]));
            loc.setZ(Double.parseDouble(str2loc[3]));
            loc.setYaw(Float.parseFloat(str2loc[4]));
            loc.setPitch(Float.parseFloat(str2loc[5]));
            return loc;
        }

        public static String locToString(Location location) {
            double x = location.getX();
            double y = location.getY();
            double z = location.getZ();
            float yaw = location.getYaw();
            float pitch = location.getPitch();

            return x + ":" + y + ":" + z + ":" + yaw + ":" + pitch; }
    }

}
