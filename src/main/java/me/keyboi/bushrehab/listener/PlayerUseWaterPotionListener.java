package me.keyboi.bushrehab.listener;

import me.keyboi.bushrehab.BushRehab;
import me.keyboi.bushrehab.CustomBlockData;
import me.keyboi.bushrehab.ItemSerializer;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.Tag;
import org.bukkit.block.Block;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.type.Sapling;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityPotionEffectEvent;
import org.bukkit.event.inventory.InventoryInteractEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionType;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class PlayerUseWaterPotionListener implements Listener {

     private NamespacedKey bushStateKey;

    @EventHandler
    public void onPlayerDrinkWater(PlayerItemConsumeEvent e){
        Player p = e.getPlayer();
        PotionMeta meta = (PotionMeta) p.getInventory().getItemInMainHand().getItemMeta();
        if (meta != null && meta.getBasePotionData().getType() == PotionType.WATER) {
             e.setCancelled(true);
        }
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        Block clickedBlock = event.getClickedBlock();

        if (event.getAction() == Action.RIGHT_CLICK_BLOCK) {
            assert clickedBlock != null;

            if (clickedBlock.getType() == Material.POTTED_DEAD_BUSH) {
                Material item = player.getInventory().getItemInMainHand().getType();

                if (item == Material.POTION) {
                    PotionMeta meta = (PotionMeta) player.getInventory().getItemInMainHand().getItemMeta();

                    if (meta != null) {
                        PotionType potiontype = meta.getBasePotionData().getType();

                        if (clickedBlock.getType() == Material.POTTED_DEAD_BUSH && potiontype == PotionType.WATER) {
                            event.setCancelled(true);
                            emptyWaterbottle(player);
                            player.getPlayer();
                            player.sendMessage("Watering dead bush");

                        }
                    }
                }
            }
        }
    }
   @EventHandler
   public void onClickPottedDeadBush(InventoryInteractEvent event){
       Block clickedBlock = event.getWhoClicked().getTargetBlock(null,4);
       PotionMeta meta = (PotionMeta) event.getWhoClicked().getInventory().getItemInMainHand().getItemMeta();
       PotionType potiontype = meta.getBasePotionData().getType();

       if (clickedBlock.getType() == Material.POTTED_DEAD_BUSH && potiontype == PotionType.WATER){
           event.setCancelled(true);
       }

   }

   public void emptyWaterbottle(Player player){
        Material item = player.getPlayer().getInventory().getItemInMainHand().getType();
        PotionMeta meta = (PotionMeta) player.getInventory().getItemInMainHand().getItemMeta();
        PotionType potiontype = meta.getBasePotionData().getType();
        Block clickedBlock = player.getTargetBlock(null,4);
        if(potiontype == PotionType.WATER && clickedBlock.getType() == Material.POTTED_DEAD_BUSH){
            player.getInventory().setItemInMainHand(new ItemStack(Material.GLASS_BOTTLE));

        }
   }
        /*if (action == Action.RIGHT_CLICK_BLOCK) {
            Material item = p.getInventory().getItemInMainHand().getType();

            if(item == Material.POTION) {
                PotionMeta meta = (PotionMeta) p.getInventory().getItemInMainHand().getItemMeta();
                final Block clickedBlock = event.getClickedBlock();


                if (meta != null && meta.getBasePotionData().getType() == PotionType.WATER && clickedBlock != null) {

                    if (clickedBlock.getType() == Material.POTTED_DEAD_BUSH) {
                        ItemStack itemStack = new ItemStack(Material.POTTED_DEAD_BUSH);
                        final PersistentDataContainer customBlockData = new CustomBlockData(Material.POTTED_DEAD_BUSH,this);
                        ItemMeta itemMeta = itemStack.getItemMeta();

                        if(!itemMeta.getPersistentDataContainer().has(bushGrowthStageKey, PersistentDataType.INTEGER)){
                            System.out.println("You should fertilize before watering");
                        }
                        else if (itemMeta.getPersistentDataContainer().get(bushGrowthStageKey,PersistentDataType.INTEGER) == 1 ){
                            itemMeta.getPersistentDataContainer().set(bushGrowthStageKey, PersistentDataType.INTEGER, 2);
                            itemStack.setItemMeta(itemMeta);
                            System.out.println("Watering the dead bush!");
                        }
                        else if(itemMeta.getPersistentDataContainer().get(bushGrowthStageKey,PersistentDataType.INTEGER) > 1 ){
                            System.out.println("The bush needs a full day to absorb nutrients");
                        }
                    }
                    else{
                        return;
                    }

                }
                else{
                    return;
                }
            }
            else{
                return;
            }
        }
        else{
            return;
        }*/

}
