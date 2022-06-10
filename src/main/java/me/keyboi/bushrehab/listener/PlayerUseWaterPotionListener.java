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
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.potion.PotionType;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Random;

public class PlayerUseWaterPotionListener implements Listener {

    BushRehab main;
    public PlayerUseWaterPotionListener(BushRehab main) {
        this.main = main;
    }
    private final Material[] saplings = new Material[]{Material.POTTED_ACACIA_SAPLING,Material.POTTED_BIRCH_SAPLING,Material.POTTED_DARK_OAK_SAPLING,Material.POTTED_JUNGLE_SAPLING,Material.POTTED_SPRUCE_SAPLING,Material.POTTED_OAK_SAPLING};

    @EventHandler
    public void onPlayerDrinkWater(PlayerItemConsumeEvent e){
        Player p = e.getPlayer();
        PotionMeta meta = (PotionMeta) p.getInventory().getItemInMainHand().getItemMeta();
        Material targetBlock = p.getTargetBlock(null,5).getType();

        if (targetBlock == Material.POTTED_DEAD_BUSH && meta.getBasePotionData().getType() == PotionType.WATER) {
             e.setCancelled(true);
             p.sendMessage("no drinking while watering the bush");
        }
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

                if (player.getInventory().getItemInMainHand().getType() == Material.POTION) {
                    PotionMeta potionMeta = (PotionMeta) player.getInventory().getItemInMainHand().getItemMeta();

                    if (potionMeta.getBasePotionData().getType() == PotionType.WATER) {

                        player.sendMessage("clicking bush at " + blocklocation.getX() + " " + blocklocation.getY() + " " + blocklocation.getZ() + " with water");

                        event.setCancelled(true);
                        if (customBlockData.get(main.keys.bushStateKey, PersistentDataType.INTEGER) == 1) {
                            customBlockData.set(main.keys.bushStateKey, PersistentDataType.INTEGER, 2);
                            emptyWaterbottle(player);
                            player.sendMessage("bush at " + blocklocation.getX() + " " + blocklocation.getY() + " " + blocklocation.getZ() + " set to " + customBlockData.get(main.keys.bushStateKey, PersistentDataType.INTEGER));
                            new BukkitRunnable(){
                                @Override
                                public void run() {
                                    Random r = new Random();
                                    int index = r.nextInt(saplings.length);
                                    Material item = saplings[index];
                                    clickedBlock.setType(item);
                                    player.sendMessage("The previously dead bush at "+ blocklocation.getX() + " " + blocklocation.getY() + " " + blocklocation.getZ() +" has grown into a sapling!");
                                    customBlockData.remove(main.keys.bushStateKey);
                                }
                            }.runTaskLater(main,100);
                        } else {
                            player.sendMessage("bush at " + blocklocation.getX() + " " + blocklocation.getY() + " " + blocklocation.getZ() + " needs to be fertilized first!");
                        }
                       /* if (customBlockData.get(main.keys.bushStateKey, PersistentDataType.INTEGER) == 0) {
                            customBlockData.set(main.keys.bushStateKey, PersistentDataType.INTEGER, 1);
                            player.sendMessage("bush at " + blocklocation.getX() + " " + blocklocation.getY() + " " + blocklocation.getZ() + " set to " + customBlockData.get(main.keys.bushStateKey, PersistentDataType.INTEGER));

                        } else {
                            player.sendMessage("bush at " + blocklocation.getX() + " " + blocklocation.getY() + " " + blocklocation.getZ() + " has already been watered! It needs fertilizer);
                        }*/

                    }
                }
            }
        }
    }
   public void emptyWaterbottle(Player player){
        Material item = player.getInventory().getItemInMainHand().getType();
            if(item==Material.POTION) {
                PotionMeta meta = (PotionMeta) player.getInventory().getItemInMainHand().getItemMeta();
                PotionType potiontype = meta.getBasePotionData().getType();
                Block clickedBlock = player.getTargetBlock(null, 4);

                if (potiontype == PotionType.WATER && clickedBlock.getType() == Material.POTTED_DEAD_BUSH) {
                    player.getInventory().setItemInMainHand(new ItemStack(Material.GLASS_BOTTLE));
                    player.sendMessage("poured water on dead bush");
            }
        }
   }
}
