package me.keyboi.bushrehab;

import me.keyboi.bushrehab.listener.BushGrowingListener;
import me.keyboi.bushrehab.listener.PlayerUseBonemealListener;
import me.keyboi.bushrehab.listener.PlayerUseWaterPotionListener;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;


public final class BushRehab extends JavaPlugin implements Listener {

    @Override
    public void onEnable() {
        // Plugin startup logic
        System.out.println("BushRehab setting up shop");
        getServer().getPluginManager().registerEvents(new PlayerUseWaterPotionListener(), this);
        getServer().getPluginManager().registerEvents(new PlayerUseBonemealListener(), this);
        getServer().getPluginManager().registerEvents(new BushGrowingListener(), this);
    }




    @Override
    public void onDisable() {
        // Plugin shutdown logic
        System.out.println("BushRehab closing shop");
    }
}
