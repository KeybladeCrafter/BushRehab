package me.keyboi.bushrehab;

import me.keyboi.bushrehab.listener.PlayerUseBonemealListener;
import me.keyboi.bushrehab.listener.PlayerUseWaterPotionListener;
import me.keyboi.bushrehab.tasks.ChangeBushData;
import org.bukkit.*;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitTask;


public final class BushRehab extends JavaPlugin implements Listener {

    public final Keys keys;

    public BushRehab() {
        keys = new Keys(this);
    }
    @Override
    public void onEnable() {
        Bukkit.getConsoleSender().sendMessage("_/\\_ BushRehab opening up shop _/\\_");
        registerEvents();
        BukkitTask task = new ChangeBushData(this).runTaskTimer(this,0,0);

    }


    @Override
    public void onDisable () {
        Bukkit.getConsoleSender().sendMessage("_/\\_ BushRehab closing up shop _/\\_");

    }

    private void registerEvents() {
        PluginManager pm = getServer().getPluginManager();
        pm.registerEvents(new PlayerUseBonemealListener(this), this);
        pm.registerEvents(new PlayerUseWaterPotionListener(this), this);
    }
}