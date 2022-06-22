package me.keyboi.bushrehab;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;

public class TaskHandler {

    private static File file;
    private static FileConfiguration customFile;

    public static void setup(){
        file = new File(Bukkit.getServer().getPluginManager().getPlugin("BushRehab").getDataFolder(),"tasks.yml");
        if(!file.exists()){
            try {
                file.createNewFile();

            } catch (IOException e) {
                Bukkit.getLogger().log(Level.WARNING,"[BushRehab] Couldnt create new file");
            }
        }
        customFile = YamlConfiguration.loadConfiguration(file);
    }

    public static FileConfiguration get(){
        return customFile;
    }

    public static void save(){
        try {
            customFile.save(file);
        } catch (IOException e) {
            Bukkit.getLogger().log(Level.WARNING,"[BushRehab] Couldnt save file");
        }
    }

    public static void reload(){
        customFile = YamlConfiguration.loadConfiguration(file);
    }
}
