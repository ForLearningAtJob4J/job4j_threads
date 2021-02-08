package ru.job4j;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.ArrayList;
import java.util.List;

@ThreadSafe
public class UserStorage {
    @GuardedBy("this")
    private final List<User> users = new ArrayList<>();

    private synchronized User findById(int id) {
        for (User user: users) {
            if (user.getId() == id) {
                return user;
            }
        }
        return null;
    }

    public synchronized int size() {
        return users.size();
    }

    public synchronized boolean add(User user) {
        if (findById(user.getId()) != null) {
            return false;
        }
        return users.add(new User(user.getId(), user.getAmount()));
    }

    public synchronized boolean update(User user) {
        User found = findById(user.getId());
        if (found == null) {
            return false;
        } else {
            found.setAmount(user.getAmount());
            return true;
        }
    }

    public synchronized boolean delete(User user) {
        return users.remove(findById(user.getId()));
    }

    public synchronized int getAmountById(int id) {
        User found = findById(id);
        return found == null ? 0 : found.getAmount();
    }

    public synchronized boolean transfer(int fromId, int toId, int amount) {
        User source = findById(fromId);
        User target = findById(toId);
        if (source == null || target == null || source.getAmount() < amount) {
            return false;
        }
        source.setAmount(source.getAmount() - amount);
        target.setAmount(target.getAmount() + amount);
        return true;
    }
}