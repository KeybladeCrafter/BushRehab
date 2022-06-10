package me.keyboi.bushrehab.listener;

import com.jeff_media.customblockdata.CustomBlockData;
import me.keyboi.bushrehab.BushRehab;
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
            PersistentDataContainer customBlockData = new CustomBlockData(clickedBlock, main);
            if(customBlockData.get(main.keys.bushStateKey, PersistentDataType.INTEGER) == 2){
                player.sendMessage("Give this bush some time to rehabilitate, please.");
                return;
            }
            if (clickedBlock != null && clickedBlock.getType() == Material.POTTED_DEAD_BUSH && customBlockData.get(main.keys.bushStateKey, PersistentDataType.INTEGER) != 2) {
                Location blocklocation = clickedBlock.getLocation();

                if (player.getInventory().getItemInMainHand().getType() == Material.BONE_MEAL) {

                    player.sendMessage("clicking bush at " + blocklocation.getX() + " " + blocklocation.getY() + " " + blocklocation.getZ() + " with bonemeal");
                    event.setCancelled(true);

                    if (customBlockData.get(main.keys.bushStateKey, PersistentDataType.INTEGER) == 0) {
                        customBlockData.set(main.keys.bushStateKey, PersistentDataType.INTEGER, 1);
                        player.sendMessage("bush at " + blocklocation.getX() + " " + blocklocation.getY() + " " + blocklocation.getZ() + " set to " + customBlockData.get(main.keys.bushStateKey, PersistentDataType.INTEGER));
                        removeBonemeal(player);
                    }
                    else {
                        player.sendMessage("bush at " + blocklocation.getX() + " " + blocklocation.getY() + " " + blocklocation.getZ() + " has already been fertilized. Try giving it some water!");
                    }

                    /*if (customBlockData.get(main.keys.bushStateKey, PersistentDataType.INTEGER) == 1) {
                        customBlockData.set(main.keys.bushStateKey, PersistentDataType.INTEGER, 2);
                        removeBonemeal(player);
                        player.sendMessage("bush at " + blocklocation.getX() + " " + blocklocation.getY() + " " + blocklocation.getZ() + " set to " + customBlockData.get(main.keys.bushStateKey, PersistentDataType.INTEGER));
                        new BukkitRunnable(){
                            @Override
                            public void run() {
                                clickedBlock.setType(item);
                                player.sendMessage("dead bush at "+ blocklocation.getX() + " " + blocklocation.getY() + " " + blocklocation.getZ() +" has grown!");
                                customBlockData.remove(main.keys.bushStateKey);
                            }
                        }.runTaskLater(main,100);
                    } else {
                        player.sendMessage("bush at " + blocklocation.getX() + " " + blocklocation.getY() + " " + blocklocation.getZ() + " holds value of: " + customBlockData.get(main.keys.bushStateKey, PersistentDataType.INTEGER));
                    }*/
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
                player.sendMessage("Fertilized dead bush. Give it time to absorb nutrients!");
            }
        }
    }
}
