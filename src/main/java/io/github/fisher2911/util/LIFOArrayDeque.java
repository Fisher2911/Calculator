package io.github.fisher2911.util;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayDeque;
import java.util.Collection;

public class LIFOArrayDeque<T> extends ArrayDeque<T> {

    public LIFOArrayDeque() {
    }

    public LIFOArrayDeque(int numElements) {
        super(numElements);
    }

    public LIFOArrayDeque(Collection<? extends T> c) {
        super(c);
    }

    @Override
    public T poll() {
        return this.pollLast();
    }

    @Override
    public T peek() {
        return super.peekLast();
    }

    @NotNull
    @Override
    public T pop() {
        return super.removeLast();
    }
}
