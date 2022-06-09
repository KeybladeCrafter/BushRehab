package me.keyboi.bushrehab;

import org.bukkit.NamespacedKey;

public class Keys {

    public final NamespacedKey bushStateKey;

    public Keys(BushRehab main) {
        bushStateKey = new NamespacedKey(main, "bush_state");
    }

}
