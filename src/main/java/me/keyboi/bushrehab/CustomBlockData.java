package me.keyboi.bushrehab;

import org.bukkit.NamespacedKey;
import org.bukkit.persistence.PersistentDataAdapterContext;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.Set;

public class CustomBlockData implements PersistentDataContainer {
    public CustomBlockData(Object block, Object plugin) {


    }

    @Override
    public <T, Z> void set(NamespacedKey key, PersistentDataType<T, Z> type, Z value) {

    }

    @Override
    public <T, Z> boolean has(NamespacedKey key, PersistentDataType<T, Z> type) {
        return false;
    }

    @Override
    public <T, Z> Z get(NamespacedKey key, PersistentDataType<T, Z> type) {
        return null;
    }

    @Override
    public <T, Z> Z getOrDefault(NamespacedKey key, PersistentDataType<T, Z> type, Z defaultValue) {
        return null;
    }

    @Override
    public Set<NamespacedKey> getKeys() {
        return null;
    }

    @Override
    public void remove(NamespacedKey key) {

    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public PersistentDataAdapterContext getAdapterContext() {
        return null;
    }
}
