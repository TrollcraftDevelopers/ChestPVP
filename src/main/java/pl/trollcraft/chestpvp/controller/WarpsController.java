package pl.trollcraft.chestpvp.controller;

import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;
import pl.trollcraft.chestpvp.model.Warp;
import pl.trollcraft.chestpvp.provider.ConfigProvider;
import pl.trollcraft.chestpvp.provider.HelpProvider;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class WarpsController {

    private final ConfigProvider configProvider;
    private final List<Warp> warps;

    public WarpsController (ConfigProvider configProvider) {
        this.configProvider = configProvider;
        warps = new ArrayList<>();
    }

    public void register(Warp warp) {
        warps.add(warp);
    }

    public void save(Warp warp) {
        String name = warp.getName();
        Location loc = warp.getLocation();

        configProvider.write("warps." + name + ".loc", HelpProvider.Locations.locToString(loc));
        configProvider.save();
    }

    public void remove(Warp warp) {
        String name = warp.getName();
        warps.remove(warp);

        configProvider.delete("warps." + name);
        configProvider.save();
    }

    public Optional<Warp> find(String name) {
        return warps.stream()
                .filter(warp -> warp.getName().equalsIgnoreCase(name))
                .findFirst();
    }

    public void load() {
        ConfigurationSection section = configProvider.read("warps", ConfigurationSection.class);
        section.getKeys(false).forEach( warpName -> {
            Location loc = HelpProvider.Locations.locFromString(
                    configProvider.read("warps." + warpName + ".loc"));

            register(new Warp(warpName, loc));
        } );
    }

}
