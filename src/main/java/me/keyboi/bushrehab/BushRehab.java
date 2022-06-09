package me.keyboi.bushrehab;

import me.keyboi.bushrehab.listener.PlayerUseWaterPotionListener;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionData;
import org.bukkit.potion.PotionType;


public final class BushRehab extends JavaPlugin implements Listener {

    public final Keys keys;

    public BushRehab() {
        keys = new Keys(this);
    }

    @Override
    public void onEnable() {
        Bukkit.getConsoleSender().sendMessage("_/\\_ BushRehab opening up shop _/\\_");

        registerEvents();

    }

    @Override
    public void onDisable () {

        Bukkit.getConsoleSender().sendMessage("_/\\_ BushRehab closing up shop _/\\_");

    }

    private void registerEvents() {
        PluginManager pm = getServer().getPluginManager();

        pm.registerEvents(new PlayerUseWaterPotionListener(this), this);

    }

}
