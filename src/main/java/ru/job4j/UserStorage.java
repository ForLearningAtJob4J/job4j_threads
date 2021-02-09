package ru.job4j;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.*;

@ThreadSafe
public class UserStorage {
    @GuardedBy("this")
    private final Map<Integer, User> users = new HashMap<>();

    public synchronized int size() {
        return users.size();
    }

    public synchronized boolean add(User user) {
        return users.putIfAbsent(user.getId(), new User(user.getId(), user.getAmount())) != user;
    }

    public synchronized boolean update(User user) {
        User found = users.get(user.getId());
        if (found == null) {
            return false;
        } else {
            found.setAmount(user.getAmount());
            return true;
        }
    }

    public synchronized boolean delete(User user) {
        return users.remove(user.getId()) != user;
    }

    public synchronized int getAmountById(int id) {
        User found = users.get(id);
        return found == null ? 0 : found.getAmount();
    }

    public synchronized boolean transfer(int fromId, int toId, int amount) {
        User source = users.get(fromId);
        User target = users.get(toId);
        if (source == null || target == null || source.getAmount() < amount) {
            return false;
        }
        source.setAmount(source.getAmount() - amount);
        target.setAmount(target.getAmount() + amount);
        return true;
    }
}