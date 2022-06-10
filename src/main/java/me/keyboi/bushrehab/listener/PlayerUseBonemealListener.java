package me.keyboi.bushrehab.listener;

import com.jeff_media.customblockdata.CustomBlockData;
import me.keyboi.bushrehab.BushRehab;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

public class PlayerUseBonemealListener implements Listener {

    BushRehab main;
    public PlayerUseBonemealListener(BushRehab main) {
        this.main = main;
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {

        if (event.getAction() == Action.RIGHT_CLICK_BLOCK) {

            Player player = event.getPlayer();
            Block clickedBlock = event.getClickedBlock();
            Location blockLocation = clickedBlock.getLocation();
            PersistentDataContainer customBlockData = new CustomBlockData(clickedBlock, main);
            if(customBlockData.get(main.keys.bushStateKey, PersistentDataType.INTEGER) == null){return;}
            if(customBlockData.get(main.keys.bushStateKey, PersistentDataType.INTEGER) == 2){
                player.sendMessage("[BushRehab]" + ChatColor.GREEN + " Give this bush some time to rehabilitate, please.");
                return;
            }
            if (clickedBlock != null && clickedBlock.getType() == Material.POTTED_DEAD_BUSH && customBlockData.get(main.keys.bushStateKey, PersistentDataType.INTEGER) != 2) {
                
                if (player.getInventory().getItemInMainHand().getType() == Material.BONE_MEAL) {
                    event.setCancelled(true);

                    if (customBlockData.get(main.keys.bushStateKey, PersistentDataType.INTEGER) == 0) {
                        customBlockData.set(main.keys.bushStateKey, PersistentDataType.INTEGER, 1);
                        removeBonemeal(player);
                    }
                    else {
                        player.sendMessage("[BushRehab]" + ChatColor.GREEN + " This bush has already been fertilized. Try giving it some water!");
                    }
                }
            }
        }
    }
    public void removeBonemeal(Player player){
        Material item = player.getInventory().getItemInMainHand().getType();
        Block clickedBlock = player.getTargetBlock(null, 4);
        int itemAmount = player.getInventory().getItemInMainHand().getAmount();

        if (item==Material.BONE_MEAL && clickedBlock.getType() == Material.POTTED_DEAD_BUSH) {
            if (itemAmount > 1) {
                player.getInventory().getItemInMainHand().setAmount(itemAmount - 1);
            } else {
                player.getInventory().setItemInMainHand(new ItemStack(Material.AIR));
            }
            player.sendMessage("[BushRehab]" + ChatColor.GREEN + "Fertilized the dead bush. It could use some water!");
        }
    }
}
