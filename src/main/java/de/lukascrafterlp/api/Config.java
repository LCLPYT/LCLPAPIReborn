package de.lukascrafterlp.api;

import de.lukascrafterlp.api.util.ConfigHelper;

import java.util.concurrent.CompletableFuture;

public class Config {

    public final Gameplay gameplay = new Gameplay();

    public static class Gameplay {
        public boolean noTrample = false,
                pads = false,
                elevator = false,
                plates = false,
                noFallPads = true;

        public float plateUpBoost = 1F,
                plateMultiplier = 2F;
    }

    // IO logic

    private static Config config = null;
    public static final String CONFIG_NAME = "lclpapi";

    public static CompletableFuture<Void> load() {
        return ConfigHelper.load(CONFIG_NAME, Config.class, Config::new).thenAccept(conf -> {
            config = conf;
            onChange();
        });
    }

    public static CompletableFuture<Void> save() {
        return ConfigHelper.save(CONFIG_NAME, config);
    }

    public static void onChange() {
        // implement behaviour here
    }

    /* - */

    public static boolean isNoTrample() {
        return config.gameplay.noTrample;
    }
}
