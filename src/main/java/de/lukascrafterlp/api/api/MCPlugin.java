package de.lukascrafterlp.api.api;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.PluginCommand;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.function.Function;
import java.util.function.Supplier;

public class MCPlugin extends JavaPlugin {

    protected void command(String name, Function<String, CommandExecutor> factory) {
        final PluginCommand command = getCommand(name);
        if (command == null) throw new IllegalStateException("Plugin command '%s' was not registered in plugin.yml".formatted(name));

        command.setExecutor(factory.apply(name));
    }

    protected void listen(Supplier<? extends Listener> supplier) {
        Bukkit.getPluginManager().registerEvents(supplier.get(), this);
    }
}
