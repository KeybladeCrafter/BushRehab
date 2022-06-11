package me.keyboi.bushrehab.tasks;

import com.jeff_media.customblockdata.CustomBlockData;
import me.keyboi.bushrehab.BushRehab;
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
    @Override
    public void run(){
        Collection<? extends Player> players = Bukkit.getOnlinePlayers();

        for (Player player : players) {
            if (player.getTargetBlock(null, 5).getType() == Material.POTTED_DEAD_BUSH) {
                Location targetLoc = player.getTargetBlock(null, 4).getLocation();
                Block targetBlock = targetLoc.getBlock();
                PersistentDataContainer customExistBlockData = new CustomBlockData(targetBlock, main);

                if (customExistBlockData.get(main.keys.bushStateKey, PersistentDataType.INTEGER) == null) {
                    customExistBlockData.set(main.keys.bushStateKey, PersistentDataType.INTEGER, 0);
                }
            }
        }
    }
}
