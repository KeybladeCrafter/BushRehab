package me.keyboi.bushrehab.listener;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.Plugin;

import java.util.Random;

public class BushGrowingListener implements Listener{
    public NamespacedKey bushGrowthStageKey;

    @EventHandler
            public void onBlockFed(BlockEvent e) {

        Material[] saplings = new Material[]{Material.POTTED_ACACIA_SAPLING,Material.POTTED_BIRCH_SAPLING,Material.POTTED_DARK_OAK_SAPLING,Material.POTTED_JUNGLE_SAPLING,Material.POTTED_SPRUCE_SAPLING,Material.POTTED_OAK_SAPLING};
        Random r = new Random();
        int index = r.nextInt(saplings.length);
        Material item = saplings[index];


        ItemStack itemStack = new ItemStack(Material.POTTED_DEAD_BUSH);
        ItemMeta itemMeta = itemStack.getItemMeta();
        final PersistentDataContainer dataContainer = itemMeta.getPersistentDataContainer();
        if (dataContainer.get(bushGrowthStageKey,PersistentDataType.INTEGER) == 2){
            Block fedBlock = (Block) dataContainer;

            Bukkit.getScheduler().runTaskLater((Plugin) this, new Runnable() {
                @Override
                public void run() {
                    fedBlock.setType(item);

                }
            }, 24000L);
        }
    }
}
