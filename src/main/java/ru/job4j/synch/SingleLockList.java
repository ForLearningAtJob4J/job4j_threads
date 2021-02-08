package ru.job4j.synch;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.Collections;
import java.util.Iterator;
import java.util.Objects;

@ThreadSafe
public class SingleLockList<T> implements Iterable<T> {
    @GuardedBy("this")
    SimpleArray<T> array = new SimpleArray<>();

    public synchronized void add(T value) {
        array.add(value);
    }

    public synchronized T get(int index) {
        return array.get(index);
    }

    @Override
    public synchronized Iterator<T> iterator() {
        return copy(this.array).iterator();
    }

    private SimpleArray<T> copy(SimpleArray<T> array) {
        SimpleArray<T> copy = new SimpleArray<>();
        for (T el : array) {
            copy.add(el);
        }
        return copy;
    }
}
