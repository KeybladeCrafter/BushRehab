package me.keyboi.bushrehab;

import com.jeff_media.customblockdata.CustomBlockData;
import me.keyboi.bushrehab.listener.BlockBrokenListener;
import me.keyboi.bushrehab.listener.PlayerJoin;
import me.keyboi.bushrehab.listener.PlayerUseBonemealListener;
import me.keyboi.bushrehab.listener.PlayerUseWaterPotionListener;
import me.keyboi.bushrehab.tasks.BushCountdownTask;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.event.Listener;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Random;
import static me.keyboi.bushrehab.listener.PlayerUseWaterPotionListener.saplings;

public final class BushRehab extends JavaPlugin implements Listener {

    public final Keys keys;
    public BushRehab() {
        keys = new Keys(this);
    }public int newTaskId;
    @Override
    public void onEnable() {
        Bukkit.getConsoleSender().sendMessage("_/\\_ BushRehab opening up shop _/\\_");
        registerEvents();
        new BushCountdownTask(this).runTaskTimer(this, 0, 20L);

        CustomBlockData.registerListener(this);

        saveDefaultConfig();
        getConfig().addDefault("Growth Time", 30);
        getConfig().options().copyDefaults(true);
        saveConfig();

        TaskHandler.setup();
        TaskHandler.get().options().copyDefaults(true);
        TaskHandler.get().addDefault("tasks.",null);
        TaskHandler.save();
    }
    @Override
    public void onDisable () {
        Bukkit.getConsoleSender().sendMessage("_/\\_ BushRehab closing up shop _/\\_");
    }
    private void registerEvents() {
        PluginManager pm = getServer().getPluginManager();
        pm.registerEvents(new PlayerUseBonemealListener(this), this);
        pm.registerEvents(new PlayerUseWaterPotionListener(this), this);
        pm.registerEvents(new BlockBrokenListener(this), this);
        pm.registerEvents(new PlayerJoin(this), this);
    }
    public void loadTasks() {
        if(TaskHandler.get().getConfigurationSection("tasks")==null){return;}

        for (String task : TaskHandler.get().getConfigurationSection("tasks").getKeys(false)) {
            int timeLeft = TaskHandler.get().getInt("tasks."+task+".timeleft");
            String worldname = TaskHandler.get().getString("tasks."+task+".world");
            World world = Bukkit.getServer().getWorld(worldname);
            int blockX = TaskHandler.get().getInt("tasks."+task+".LocX");
            int blockY = TaskHandler.get().getInt("tasks."+task+".LocY");
            int blockZ = TaskHandler.get().getInt("tasks."+task+".LocZ");
            Block block = world.getBlockAt(blockX, blockY, blockZ);
            System.out.println("Loaded task "+task);
            if (world.getBlockAt(blockX, blockY, blockZ).getType() != Material.POTTED_DEAD_BUSH) {return;}
            PersistentDataContainer customBlockData = new CustomBlockData(block, JavaPlugin.getPlugin(BushRehab.class));
            if (customBlockData.get(JavaPlugin.getPlugin(BushRehab.class).keys.bushStateKey, PersistentDataType.INTEGER) != 1){continue;}
            if (customBlockData.get(JavaPlugin.getPlugin(BushRehab.class).keys.bushStateKey, PersistentDataType.INTEGER) == 1) {
                newTaskId = Bukkit.getServer().getScheduler().runTaskLater(JavaPlugin.getPlugin(BushRehab.class), () -> {
                    Random r = new Random();
                    int index = r.nextInt(saplings.length);
                    Material item = saplings[index];
                    block.setType(item);
                    customBlockData.remove(JavaPlugin.getPlugin(BushRehab.class).keys.bushStateKey);
                    }, timeLeft).getTaskId();
                TaskHandler.get().set("tasks."+task,null);
                TaskHandler.get().set("tasks."+newTaskId+".world",worldname);
                TaskHandler.get().set("tasks."+newTaskId+".LocX",blockX);
                TaskHandler.get().set("tasks."+newTaskId+".LocY",blockY);
                TaskHandler.get().set("tasks."+newTaskId+".LocZ",blockZ);
                PlayerUseWaterPotionListener.getTaskMap().put(newTaskId,timeLeft);
            }
        }
    }
}