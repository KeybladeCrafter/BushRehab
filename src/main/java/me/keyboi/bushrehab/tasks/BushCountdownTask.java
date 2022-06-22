package me.keyboi.bushrehab.tasks;

import me.keyboi.bushrehab.BushRehab;
import me.keyboi.bushrehab.TaskHandler;
import me.keyboi.bushrehab.listener.PlayerUseWaterPotionListener;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class BushCountdownTask extends BukkitRunnable {

    BushRehab main;
    public BushCountdownTask(BushRehab main) {
        this.main = main;
    }

    public static <K, V> K getKey(Map<K, V> map, V value)
    {
        for (Map.Entry<K, V> entry: map.entrySet())
        {
            if (value.equals(entry.getValue())) {return entry.getKey();}
        }
        return null;
    }

    @Override
    public void run() {

        if(PlayerUseWaterPotionListener.getTaskMap()==null){return;}
        List<Integer> toRemoveList = new ArrayList<>();
        for(Map.Entry<Integer,Integer> task:PlayerUseWaterPotionListener.getTaskMap().entrySet()) {
            if(task==null){return;}
            Integer key = task.getKey();
            Integer value = task.getValue();
            if (value > 0){
                value = value - 20;
                PlayerUseWaterPotionListener.getTaskMap().put(key,value);
                TaskHandler.get().set("tasks."+ key +".timeleft", value);
                TaskHandler.save();
            }else if(key ==null){
                TaskHandler.get().set("tasks."+key, null);
                TaskHandler.save();
            }
            else{
                toRemoveList.add(key);
                TaskHandler.get().set("tasks."+key, null);
                TaskHandler.save();
            }
        }
        for(Integer taskKey:toRemoveList){
            PlayerUseWaterPotionListener.getTaskMap().remove(taskKey);
        }
    }
}
