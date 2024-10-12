package me.hieu.core.handler;

import lombok.Getter;
import me.hieu.core.Core;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

/**
 * Author: Le Thanh Hieu
 * Date: 01/10/2024
 */

@Getter
public class ConfigHandler {

    private File defaultConfig, langConfig;
    private FileConfiguration defaultConfigz, langConfigz;

    public ConfigHandler(){
        Core plugin = Core.getInstance();
        defaultConfig = new File(plugin.getDataFolder(), "config.yml");
        if (!defaultConfig.exists()) {
            defaultConfig.getParentFile().mkdirs();
            plugin.saveResource("config.yml", false);
        }
        defaultConfigz = new YamlConfiguration();
        try {
            defaultConfigz.load(defaultConfig);
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }
        langConfig = new File(plugin.getDataFolder(), "lang.yml");
        if (!langConfig.exists()) {
            langConfig.getParentFile().mkdirs();
            plugin.saveResource("lang.yml", false);
        }
        langConfigz = new YamlConfiguration();
        try {
            langConfigz.load(langConfig);
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }
    }

}
