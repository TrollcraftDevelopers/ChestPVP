package pl.trollcraft.chestpvp;

import org.bukkit.plugin.java.JavaPlugin;
import pl.trollcraft.chestpvp.controller.WarpsController;
import pl.trollcraft.chestpvp.provider.ConfigProvider;
import pl.trollcraft.chestpvp.service.DeathService;

import java.util.logging.Level;

public class ChestPVPPlugin extends JavaPlugin {

    private ConfigProvider warpsConfigProvider;

    @Override
    public void onLoad() {
        getLogger().log(Level.INFO, "Preparing configuration providers...");
        warpsConfigProvider = new ConfigProvider(this, "settings/warps.yml");
    }

    @Override
    public void onEnable() {

        getServer().getPluginManager().registerEvents(new DeathService(this), this);

        getLogger().log(Level.INFO, "Loading warps...");
        new WarpsController(warpsConfigProvider).load();
    }
}
