package me.keyboi.bushrehab.listener;

import com.jeff_media.customblockdata.events.CustomBlockDataRemoveEvent;
import me.keyboi.bushrehab.BushRehab;
import me.keyboi.bushrehab.TaskHandler;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class BlockBrokenListener implements Listener {

    BushRehab main;
    public BlockBrokenListener(BushRehab main) {
        this.main = main;
    }

    @EventHandler
    public void onBlockBreakEarly(CustomBlockDataRemoveEvent event) {
        Location blockLocation = event.getBlock().getLocation();
        onBlockDataChange(blockLocation);
    }
    public static void onBlockDataChange(Location blockLocation) {
        if (TaskHandler.get().getConfigurationSection("tasks") == null) {
            return;
        }
        for (String task : TaskHandler.get().getConfigurationSection("tasks").getKeys(false)) {
            if (blockLocation.getBlockX() != TaskHandler.get().getInt("tasks." + task + ".LocX")) {
                continue;
            }
            if (blockLocation.getBlockY() != TaskHandler.get().getInt("tasks." + task + ".LocY")) {
                continue;
            }
            if (blockLocation.getBlockZ() != TaskHandler.get().getInt("tasks." + task + ".LocZ")) {
                continue;
            }
            int taskInt = Integer.parseInt(task);
            Bukkit.getScheduler().cancelTask(taskInt);
            PlayerUseWaterPotionListener.getTaskMap().remove(taskInt);
            if (!PlayerUseWaterPotionListener.getTaskMap().containsKey(taskInt)) {
                if (TaskHandler.get().getConfigurationSection("tasks").contains(task)) {
                    TaskHandler.get().set("tasks." + task, null);
                    TaskHandler.save();
                }

            }
        }
    }
}
