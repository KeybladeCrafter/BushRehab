package me.keyboi.bushrehab.listener;

import com.jeff_media.customblockdata.events.CustomBlockDataRemoveEvent;
import me.keyboi.bushrehab.BushRehab;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.persistence.PersistentDataType;

public class BlockDataRemoveListener implements Listener {
    BushRehab main;
    public BlockDataRemoveListener(BushRehab main) {
        this.main = main;
    }

    @EventHandler
    public void onBlockDataRemove(CustomBlockDataRemoveEvent event){
        if(event.getCustomBlockData().has(main.keys.bushStateKey, PersistentDataType.INTEGER)) {
            System.out.println("CustomBlockData data removed=================================");
        }
    }
}
