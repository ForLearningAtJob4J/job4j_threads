package ru.job4j.cache;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicReference;

public class Cache {
    private final Map<Integer, Base> memory = new ConcurrentHashMap<>();

    public boolean add(Base model) {
        return memory.putIfAbsent(model.getId(), model) == null;
    }

    public Base update(Base model) {
        AtomicReference<Base> old = new AtomicReference<>();
        memory.computeIfPresent(model.getId(), (integer, base) -> {
            old.set(base);
            if (model.getVersion() != base.getVersion()) {
                throw new OptimisticException("Versions are not equal");
            }
            base = new Base(integer, base.getVersion() + 1);
            base.setName(model.getName());
            return base;
        });
        return old.get();
    }

    public void delete(Base model) {
        memory.remove(model.getId());
    }

    public Base get(Integer key) {
        return memory.get(key);
    }
}