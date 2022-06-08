package me.keyboi.bushrehab;

import me.keyboi.bushrehab.listener.BushGrowingListener;
import me.keyboi.bushrehab.listener.PlayerUseBonemealListener;
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
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionType;


public final class BushRehab extends JavaPlugin implements Listener {
    private NamespacedKey bushStateKey;

    @Override
    public void onEnable() {
        Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "BushRehab opening up shop");
        getServer().getPluginManager().registerEvents(new PlayerUseWaterPotionListener(), this);
        bushStateKey = new NamespacedKey(this, "bushstate");

        // getServer().getPluginManager().registerEvents(new PlayerUseBonemealListener(), this);
        //  getServer().getPluginManager().registerEvents(new BushGrowingListener(), this);
    }
    @EventHandler
    public void changeBushState(PlayerInteractEvent event) {
        final Block block = event.getClickedBlock();
        assert block != null;
        Player player = event.getPlayer();
        PotionMeta meta = (PotionMeta) player.getInventory().getItemInMainHand().getItemMeta();
        PotionType potiontype = meta.getBasePotionData().getType();

        final PersistentDataContainer customBlockData = new CustomBlockData(block, this);
        
        if (event.getAction() == Action.RIGHT_CLICK_BLOCK) {
            player.sendMessage("right clicked");
            
            if (block.getType() == Material.POTTED_DEAD_BUSH && potiontype == PotionType.WATER) {
                player.sendMessage("clicking bush with water");
                
                if (customBlockData.isEmpty()) {
                    customBlockData.set(bushStateKey, PersistentDataType.INTEGER, 1);
                    player.sendMessage("bushstate is empty");
                }
                else if (!customBlockData.isEmpty()){
                    player.sendMessage("bushstate isnt empty");
                }
            }
        }

    }
        @Override
        public void onDisable () {
            Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "BushRehab closing up shop");
        }
}
