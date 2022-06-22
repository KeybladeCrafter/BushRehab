package me.keyboi.bushrehab.listener;

import com.jeff_media.customblockdata.CustomBlockData;
import me.keyboi.bushrehab.BushRehab;
import me.keyboi.bushrehab.TaskHandler;
import me.keyboi.bushrehab.tasks.BushCountdownTask;
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
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionType;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class PlayerUseWaterPotionListener implements Listener {

    BushRehab main;
    public PlayerUseWaterPotionListener(BushRehab main) {this.main = main;}
    private Location bushLoc;
    public int taskId;
    public int delayTimeSec = BushRehab.getPlugin(BushRehab.class).getConfig().getInt("Growth Time");
    public int delayTimeTick=delayTimeSec*20;
    static Map<Integer,Integer> taskTime = new HashMap();
    public static Map<Integer, Integer> getTaskMap(){
        return taskTime;
    }
    public static final Material[] saplings = new Material[]{Material.POTTED_ACACIA_SAPLING,Material.POTTED_BIRCH_SAPLING,Material.POTTED_DARK_OAK_SAPLING,Material.POTTED_JUNGLE_SAPLING,Material.POTTED_SPRUCE_SAPLING,Material.POTTED_OAK_SAPLING};

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        if (event.getAction() == Action.RIGHT_CLICK_BLOCK) {
            Player taskPlayer = event.getPlayer();
            Block clickedBlock = event.getClickedBlock();
            Location blockLocation = clickedBlock.getLocation();
            bushLoc = blockLocation;
            PersistentDataContainer customBlockData = new CustomBlockData(clickedBlock, main);

            if(clickedBlock.getType().name().contains("POTTED") && clickedBlock.getType()!=Material.POTTED_DEAD_BUSH){
                event.setCancelled(true);
                Collection<ItemStack> drops = clickedBlock.getDrops();
                clickedBlock.setType(Material.FLOWER_POT);
                for(ItemStack droppedItem: drops) {
                    if(droppedItem.getType() != Material.FLOWER_POT){
                        taskPlayer.getWorld().dropItem(blockLocation, droppedItem);
                    }
                }
            }
            else if(event.getClickedBlock().getType() == Material.POTTED_DEAD_BUSH) {
                ItemStack item = event.getPlayer().getInventory().getItemInMainHand();
                if (item.getType() == null) {
                    if (customBlockData.has(main.keys.bushStateKey, PersistentDataType.INTEGER)) {
                        customBlockData.remove(main.keys.bushStateKey);
                        event.getPlayer().sendMessage("[BushRehab]" + ChatColor.GREEN + "You removed the bush too soon!");
                    }
                }else if (item.getType() == Material.POTION) {
                    PotionMeta meta = (PotionMeta) event.getPlayer().getInventory().getItemInMainHand().getItemMeta();
                    PotionType potiontype = meta.getBasePotionData().getType();
                    if (potiontype != PotionType.WATER) {
                        if (customBlockData.has(main.keys.bushStateKey, PersistentDataType.INTEGER)) {
                            customBlockData.remove(main.keys.bushStateKey);
                            event.getPlayer().sendMessage("[BushRehab]" + ChatColor.GREEN + "You removed the bush too soon!");
                        }
                    } else{
                        if (customBlockData.get(main.keys.bushStateKey, PersistentDataType.INTEGER) == null){
                            event.setCancelled(true);
                            taskPlayer.sendMessage("[BushRehab]" + ChatColor.GREEN + " This bush needs nutrients. You should fertilize first!");
                        }else if (customBlockData.get(main.keys.bushStateKey, PersistentDataType.INTEGER) == 1) {
                            event.setCancelled(true);
                            taskPlayer.sendMessage("[BushRehab]" + ChatColor.GREEN + "This bush has been given nutrients and water. Give it some time to rehabilitate!");
                        }else if (customBlockData.get(main.keys.bushStateKey, PersistentDataType.INTEGER) == 0) {
                            event.setCancelled(true);
                            emptyWaterbottle(taskPlayer);
                            customBlockData.set(main.keys.bushStateKey, PersistentDataType.INTEGER, 1);
                            delayGrowthTask(clickedBlock,delayTimeTick);
                            bushSave();
                        }
                    }
                } else if (item.getType() != Material.BONE_MEAL) {
                    if (customBlockData.has(main.keys.bushStateKey, PersistentDataType.INTEGER)) {
                        customBlockData.remove(main.keys.bushStateKey);
                        event.getPlayer().sendMessage("[BushRehab]" + ChatColor.GREEN + "You removed the bush too soon!");
                    }
                }

            }
        }
    }

   public void emptyWaterbottle(Player player){
        Material item = player.getInventory().getItemInMainHand().getType();
        if(item!=Material.POTION) {return;}
        PotionMeta meta = (PotionMeta) player.getInventory().getItemInMainHand().getItemMeta();
        assert meta != null;
        PotionType potiontype = meta.getBasePotionData().getType();
        Block clickedBlock = player.getTargetBlock(null, 4);
        if (potiontype == PotionType.WATER && clickedBlock.getType() == Material.POTTED_DEAD_BUSH) {
            player.getInventory().setItemInMainHand(new ItemStack(Material.GLASS_BOTTLE));
            player.sendMessage("[BushRehab]" + ChatColor.GREEN + " Watering the fertilized bush");
        }
   }

    public void delayGrowthTask( Block clickedBlock, Integer delayTimeTick) {
        PersistentDataContainer customBlockData = new CustomBlockData(clickedBlock, JavaPlugin.getPlugin(BushRehab.class));
        taskId = Bukkit.getServer().getScheduler().runTaskLater(JavaPlugin.getPlugin(BushRehab.class), new Runnable() {
            @Override
            public void run() {
                Random r = new Random();
                int index = r.nextInt(saplings.length);
                Material item = saplings[index];
                clickedBlock.setType(item);
                customBlockData.remove(JavaPlugin.getPlugin(BushRehab.class).keys.bushStateKey);
            }
        },delayTimeTick).getTaskId();
    }
    private void bushSave(){
        getTaskMap().put(taskId,delayTimeTick);
        TaskHandler.get().set("tasks."+ BushCountdownTask.getKey(getTaskMap(),delayTimeTick)+".world",bushLoc.getWorld().getName());
        TaskHandler.get().set("tasks."+ BushCountdownTask.getKey(getTaskMap(),delayTimeTick)+".LocX",bushLoc.getX());
        TaskHandler.get().set("tasks."+ BushCountdownTask.getKey(getTaskMap(),delayTimeTick)+".LocY",bushLoc.getY());
        TaskHandler.get().set("tasks."+ BushCountdownTask.getKey(getTaskMap(),delayTimeTick) +".LocZ",bushLoc.getZ());
        TaskHandler.get().set("tasks."+ BushCountdownTask.getKey(getTaskMap(),delayTimeTick) +".timeleft", delayTimeTick);
        TaskHandler.save();
    }
}
