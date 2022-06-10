package me.keyboi.bushrehab;

import com.jeff_media.customblockdata.CustomBlockData;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Collection;

public class ChangeBushData extends BukkitRunnable {

    BushRehab main;
    public ChangeBushData(BushRehab main) {
        this.main = main;
    }
    //private Keys keys;

    @Override
    public void run(){
        Collection<? extends Player> players = Bukkit.getOnlinePlayers();

        for (Player player : players) {
            if (player.getTargetBlock(null, 4).getType() == Material.POTTED_DEAD_BUSH) {
                Location targetLoc = player.getTargetBlock(null, 4).getLocation();
                Block targetBlock = targetLoc.getBlock();
                PersistentDataContainer customExistBlockData = new CustomBlockData(targetBlock, main);

                if (customExistBlockData.get(main.keys.bushStateKey, PersistentDataType.INTEGER) == null) {
                    customExistBlockData.set(main.keys.bushStateKey, PersistentDataType.INTEGER, 0);
                    player.sendMessage("block set to 0");
                } else {
                    player.sendMessage("block not set to 0");
                }
            }
        }
    }
    /* public void changeBushData(Player player, Material material){
        if (player.getTargetBlock(null, 4).getType() == material) {
            Location targetLoc = player.getTargetBlock(null, 4).getLocation();
            Block targetBlock = targetLoc.getBlock();
            PersistentDataContainer customExistBlockData = new CustomBlockData(targetBlock, main);

            if (customExistBlockData.get(this.keys.bushStateKey, PersistentDataType.INTEGER) == null) {
                customExistBlockData.set(this.keys.bushStateKey, PersistentDataType.INTEGER, 0);
                player.sendMessage("block set to 0");
            } else {
                player.sendMessage("block not set to 0");
            }
        }
    }*/
}
