package me.keyboi.bushrehab;

import org.bukkit.NamespacedKey;

public class Keys {

    public NamespacedKey bushStateKey;

    public Keys(BushRehab main) {
        bushStateKey = new NamespacedKey(main, "bushstate");
    }

}