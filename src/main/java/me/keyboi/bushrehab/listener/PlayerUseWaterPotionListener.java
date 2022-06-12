package me.keyboi.bushrehab.listener;

import com.jeff_media.customblockdata.CustomBlockData;
import me.keyboi.bushrehab.BushRehab;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
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

import java.util.Collection;
import java.util.Random;

public class PlayerUseWaterPotionListener implements Listener {

    BushRehab main;
    public PlayerUseWaterPotionListener(BushRehab main) {
        this.main = main;
    }
    public static final Material[] saplings = new Material[]{Material.POTTED_ACACIA_SAPLING,Material.POTTED_BIRCH_SAPLING,Material.POTTED_DARK_OAK_SAPLING,Material.POTTED_JUNGLE_SAPLING,Material.POTTED_SPRUCE_SAPLING,Material.POTTED_OAK_SAPLING};
    public static final String[] saplingnames = new String[]{"acacia","birch","dark oak","jungle","spruce","oak"};

    @EventHandler
    public void onPlayerDrinkWater(PlayerItemConsumeEvent e){
        Player p = e.getPlayer();
        PotionMeta meta = (PotionMeta) p.getInventory().getItemInMainHand().getItemMeta();
        Material targetBlock = p.getTargetBlock(null,5).getType();

        if (targetBlock == Material.POTTED_DEAD_BUSH && meta.getBasePotionData().getType() == PotionType.WATER) {
            p.sendMessage("You cant drink water and a pour it");
             e.setCancelled(true);
        }
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {

        if (event.getAction() == Action.RIGHT_CLICK_BLOCK) {
            Player player = event.getPlayer();
            Block clickedBlock = event.getClickedBlock();
            Location blockLocation = clickedBlock.getLocation();
            PersistentDataContainer customBlockData = new CustomBlockData(clickedBlock, main);

            if(clickedBlock.getType().name().contains("POTTED") && clickedBlock.getType()!=Material.POTTED_DEAD_BUSH){
                event.setCancelled(true);
                Collection<ItemStack> drops = clickedBlock.getDrops();
                clickedBlock.setType(Material.FLOWER_POT);
                for(ItemStack droppedItem: drops) {
                    if(droppedItem.getType() != Material.FLOWER_POT){
                        player.getWorld().dropItem(blockLocation, droppedItem);
                    }
                }
            }

            if (clickedBlock.getType() == Material.POTTED_DEAD_BUSH && customBlockData.get(main.keys.bushStateKey, PersistentDataType.INTEGER) != 2) {

                if (player.getInventory().getItemInMainHand().getType() == Material.POTION) {
                    PotionMeta potionMeta = (PotionMeta) player.getInventory().getItemInMainHand().getItemMeta();

                    if (potionMeta.getBasePotionData().getType() == PotionType.WATER) {
                        event.setCancelled(true);

                        if (customBlockData.get(main.keys.bushStateKey, PersistentDataType.INTEGER) == 1) {
                            emptyWaterbottle(player);
                            customBlockData.set(main.keys.bushStateKey, PersistentDataType.INTEGER, 2);
                            delayGrowthTask(player,clickedBlock);
                        } else {
                            player.sendMessage("[BushRehab]" + ChatColor.GREEN + " This bush needs nutrients. You should fertilize first!");
                        }
                    }
                }
            }
        }
    }
   public void emptyWaterbottle(Player player){
        Material item = player.getInventory().getItemInMainHand().getType();
            if(item==Material.POTION) {
                PotionMeta meta = (PotionMeta) player.getInventory().getItemInMainHand().getItemMeta();
                assert meta != null;
                PotionType potiontype = meta.getBasePotionData().getType();
                Block clickedBlock = player.getTargetBlock(null, 4);

                if (potiontype == PotionType.WATER && clickedBlock.getType() == Material.POTTED_DEAD_BUSH) {
                    player.getInventory().setItemInMainHand(new ItemStack(Material.GLASS_BOTTLE));
                    player.sendMessage("[BushRehab]" + ChatColor.GREEN + " Watering the fertilized bush");
            }
        }
   }
    public static int taskId;
    public void delayGrowthTask(Player player, Block clickedBlock) {

        Location blockLocation = clickedBlock.getLocation();
        PersistentDataContainer customBlockData = new CustomBlockData(clickedBlock, main);
        taskId = Bukkit.getServer().getScheduler().runTaskLater(main, () -> {
            Random r = new Random();
            int index = r.nextInt(saplings.length);
            Material item = saplings[index];
            String name = saplingnames[index];
            clickedBlock.setType(item);
            player.sendMessage("[BushRehab]" + ChatColor.GOLD + " The previously dead bush at " + ChatColor.WHITE + blockLocation.getBlockX() + " " + blockLocation.getBlockY() + " " + blockLocation.getBlockZ() + ChatColor.GREEN + " has grown into a " + ChatColor.WHITE + name + ChatColor.GREEN + " sapling!");
            customBlockData.remove(main.keys.bushStateKey);

        },100L).getTaskId();
    }
}
