package pl.trollcraft.chestpvp.provider;

import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.io.IOException;

public class ConfigProvider {

    private final File file;
    private final YamlConfiguration conf;

    public ConfigProvider(Plugin plugin, String fileName) {

        file = new File(plugin.getDataFolder() + File.separator + fileName);
        if (!file.exists())
            plugin.saveResource(fileName, false);
        conf = new YamlConfiguration();

        try {
            conf.load(file);
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
            throw new IllegalStateException("Failed to load file " + fileName);
        }

    }

    public String read(String key) {
        return conf.getString(key);
    }

    public<T> T read(String key, Class<T> clazz) {
        Object o = conf.get(key);
        return clazz.cast(o);
    }

    public boolean exists(String key) {
        return conf.contains(key);
    }

    public void write(String key, Object o) {
        conf.set(key, o);
    }

    public void delete(String key) {
        conf.set(key, null);
    }

    public boolean save() {

        try {
            conf.save(file);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

        return true;

    }
}
