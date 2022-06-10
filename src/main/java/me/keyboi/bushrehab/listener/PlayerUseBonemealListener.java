package me.keyboi.bushrehab.listener;

import com.jeff_media.customblockdata.CustomBlockData;
import me.keyboi.bushrehab.BushRehab;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.potion.PotionType;

public class PlayerUseBonemealListener {

    BushRehab main;
    public PlayerUseBonemealListener(BushRehab main) {
        this.main = main;
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {

        if (event.getAction() == Action.RIGHT_CLICK_BLOCK) {
            Player player = event.getPlayer();
            Block clickedBlock = event.getClickedBlock();
            player.sendMessage("right clicking");

            if (clickedBlock != null && clickedBlock.getType() == Material.POTTED_DEAD_BUSH) {
                Location blocklocation = clickedBlock.getLocation();
                player.sendMessage("a dead bush");

                if (player.getInventory().getItemInMainHand().getType() == Material.BONE_MEAL) {
                    player.sendMessage("holding bonemeal");
                    event.setCancelled(true);
                    PersistentDataContainer customBlockData = new CustomBlockData(clickedBlock, main);
                    player.sendMessage("clicking bush at " + blocklocation.getX() + " " + blocklocation.getY() + " " + blocklocation.getZ() + " with bonemeal");

                    if (customBlockData.get(main.keys.bushStateKey, PersistentDataType.INTEGER) == 1) {
                        customBlockData.set(main.keys.bushStateKey, PersistentDataType.INTEGER, 2);
                        player.sendMessage("bush at " + blocklocation.getX() + " " + blocklocation.getY() + " " + blocklocation.getZ() + " set to " + customBlockData.get(main.keys.bushStateKey, PersistentDataType.INTEGER));
                    } else if(customBlockData.get(main.keys.bushStateKey, PersistentDataType.INTEGER) != 1 ) {
                        player.sendMessage("bush at " + blocklocation.getX() + " " + blocklocation.getY() + " " + blocklocation.getZ() + " holds value of: " + customBlockData.get(main.keys.bushStateKey, PersistentDataType.INTEGER));
                    }
                }
            }
        }
    }



}
