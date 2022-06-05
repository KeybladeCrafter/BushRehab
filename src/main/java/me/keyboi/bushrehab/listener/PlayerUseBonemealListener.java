package me.keyboi.bushrehab.listener;

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
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionType;

import java.io.IOException;

public class PlayerUseBonemealListener implements Listener {

    public NamespacedKey bushGrowthStageKey;

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event){
        Player p = event.getPlayer();
        Action action= event.getAction();
        bushGrowthStageKey = new NamespacedKey((Plugin) this, "bushgrowthstage");


        if (action == Action.RIGHT_CLICK_BLOCK) {
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
                            itemMeta.getPersistentDataContainer().set(bushGrowthStageKey, PersistentDataType.INTEGER, 1);
                            itemStack.setItemMeta(itemMeta);

                            System.out.println("Fertilizing the dead bush");
                        }
                        else if (itemMeta.getPersistentDataContainer().get(bushGrowthStageKey,PersistentDataType.INTEGER) == 1 ){
                            System.out.println("This bush has already been fertilized. Give it some water!");
                        }
                        else if(itemMeta.getPersistentDataContainer().get(bushGrowthStageKey,PersistentDataType.INTEGER) > 1 ){
                            System.out.println("The bush needs a full day to absorb nutrients");
                        }
                    }

                }
            }
        }
    }
}
