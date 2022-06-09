package me.keyboi.bushrehab;

import me.keyboi.bushrehab.listener.PlayerUseWaterPotionListener;
import org.bukkit.*;
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
    private Player player;

    public BushRehab() {
        keys = new Keys(this);
    }

    @Override
    public void onEnable() {
        Bukkit.getConsoleSender().sendMessage("_/\\_ BushRehab opening up shop _/\\_");

        registerEvents();
        caster(player);

    }

    @Override
    public void onDisable () {

        Bukkit.getConsoleSender().sendMessage("_/\\_ BushRehab closing up shop _/\\_");

    }

    private void registerEvents() {
        PluginManager pm = getServer().getPluginManager();

        pm.registerEvents(new PlayerUseWaterPotionListener(this), this);

    }
    public void caster(Player caster) {
        if (caster.getTargetBlock(null, 4).getType() == Material.POTTED_DEAD_BUSH) {
            Location targetLoc = caster.getTargetBlock(null, 4).getLocation();
            Block targetBlock = targetLoc.getBlock();
            PersistentDataContainer customExistBlockData = new CustomBlockData(targetBlock, this);

            if (customExistBlockData.get(this.keys.bushStateKey,PersistentDataType.INTEGER) == null) {
                customExistBlockData.set(this.keys.bushStateKey, PersistentDataType.INTEGER, 0);
            }
        }
    }

}