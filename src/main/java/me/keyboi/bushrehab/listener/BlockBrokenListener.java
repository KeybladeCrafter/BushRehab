package me.keyboi.bushrehab.listener;

import com.jeff_media.customblockdata.events.CustomBlockDataRemoveEvent;
import me.keyboi.bushrehab.BushRehab;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import static me.keyboi.bushrehab.listener.PlayerUseWaterPotionListener.taskId;


public class BlockBrokenListener implements Listener {

    BushRehab main;
    public BlockBrokenListener(BushRehab main) {
        this.main = main;
    }

    @EventHandler
    public void onBlockBreakEarly(CustomBlockDataRemoveEvent event) {

        Bukkit.getScheduler().cancelTask(taskId);
    }

}
