package de.lukascrafterlp.api.events.event;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

public class ConfigChangedEvent extends Event {

    private static final HandlerList handlers = new HandlerList();

    public final String name;
    public final Object config;

    public ConfigChangedEvent(String name, Object config) {
        super(true);
        this.name = name;
        this.config = config;
    }

    @Override
    public @NotNull HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }
}
