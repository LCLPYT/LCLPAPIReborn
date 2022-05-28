package de.lukascrafterlp.api;

import de.lukascrafterlp.api.api.MCPlugin;
import de.lukascrafterlp.api.cmds.CommandResourcepack;
import de.lukascrafterlp.api.events.EntityListener;
import de.lukascrafterlp.api.events.PlayerListener;
import de.lukascrafterlp.api.resource.ResourcepackManager;
import de.lukascrafterlp.api.util.Broadcaster;
import de.lukascrafterlp.api.util.PlayerSessionData;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.bukkit.NamespacedKey;

public class LCLPAPI extends MCPlugin {

    public static final String PLUGIN_NAME = "LCLPAPI";
    public static final Broadcaster BROADCASTER = new Broadcaster(PLUGIN_NAME);

    private static final Logger LOGGER = LogManager.getLogger();
    private static LCLPAPI plugin;

    public LCLPAPI() {
        plugin = this;
    }

    public static LCLPAPI getPlugin() {
        return plugin;
    }

    @Override
    public void onLoad() {
        // load configs
        Config.load().exceptionally(ex -> {
            LOGGER.error("Could not load config", ex);
            return null;
        });
    }

    @Override
    public void onEnable() {
        // events
        listen(new PlayerSessionData());
        listen(new ResourcepackManager.Listener());
        listen(new PlayerListener());
        listen(new EntityListener());

        ResourcepackManager.load().exceptionally(ex -> {
            LOGGER.error("Could not load config", ex);
            return null;
        });

        // commands
        command("resourcepack", CommandResourcepack::new);
    }

    public static NamespacedKey identifier(String path) {
        return new NamespacedKey(plugin, path);
    }
}
