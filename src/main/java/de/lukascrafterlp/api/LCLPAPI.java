package de.lukascrafterlp.api;

import de.lukascrafterlp.api.util.PlayerSessionData;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.function.Supplier;

public class LCLPAPI extends JavaPlugin {

    public static final String PLUGIN_NAME = "LCLPAPI";

    private static final Logger LOGGER = LogManager.getLogger();
    private static LCLPAPI plugin;

    @Override
    public void onLoad() {
        Config.load().exceptionally(ex -> {
            LOGGER.error("Could not load config", ex);
            return null;
        });
    }

    @Override
    public void onEnable() {
        plugin = this;

        // register events
        listen(PlayerSessionData::new);
    }

    private void listen(Supplier<? extends Listener> supplier) {
        Bukkit.getPluginManager().registerEvents(supplier.get(), this);
    }

    public static LCLPAPI getPlugin() {
        return plugin;
    }
}
