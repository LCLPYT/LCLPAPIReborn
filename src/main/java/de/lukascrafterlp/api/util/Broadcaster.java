package de.lukascrafterlp.api.util;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.command.CommandSender;

public class Broadcaster {

    protected final String name;

    public Broadcaster(String name) {
        this.name = name;
    }

    public Component prefixed(Component component) {
        return Component.empty().color(NamedTextColor.GRAY)
                .append(Component.text(name.concat("> "), NamedTextColor.BLUE))
                .append(component);
    }

    public Component error(Component component) {
        return prefixed(component.color(NamedTextColor.RED));
    }

    public void send(CommandSender p, Component component) {
        p.sendMessage(prefixed(component));
    }

    public void sendError(CommandSender p, Component component) {
        p.sendMessage(error(component));
    }
}
