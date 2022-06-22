package me.keyboi.bushrehab.listener;

import me.keyboi.bushrehab.BushRehab;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoin implements Listener {
    BushRehab main;
    public PlayerJoin(BushRehab main) {
        this.main = main;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event){
        if(Bukkit.getOnlinePlayers().size()<2){
        main.loadTasks();}
    }
}
