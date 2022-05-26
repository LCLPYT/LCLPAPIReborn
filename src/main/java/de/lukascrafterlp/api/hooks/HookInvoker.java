package de.lukascrafterlp.api.hooks;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;

public class HookInvoker<T> {

    protected List<T> hooks = new ArrayList<>();
    protected final Function<List<T>, T> invokerFactory;
    protected T invoker = null;  // is non-null after instantiation

    public HookInvoker(Function<List<T>, T> invokerFactory) {
        this.invokerFactory = Objects.requireNonNull(invokerFactory);
        updateInvoker();
    }

    protected void updateInvoker() {
        this.invoker = Objects.requireNonNull(invokerFactory.apply(hooks));
    }

    public T register(T t) {
        Objects.requireNonNull(t);
        hooks.add(t);
        updateInvoker();
        return t;
    }

    public void unregister(T t) {
        hooks.remove(t);
    }

    public T invoker() {
        return invoker;
    }
}
