package de.lukascrafterlp.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
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
    private static final Logger logger = LogManager.getLogger();
    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    @NotNull
    protected static File getConfigFile() {
        return new File(LCLPAPI.getPlugin().getDataFolder(), "lclpapi.json");
    }

    private static void onChange() {
        // implement behaviour here
    }

    public static CompletableFuture<Void> load() {
        return CompletableFuture.runAsync(() -> {
            File configFile = getConfigFile();

            if (!configFile.exists()) {
                config = new Config(); // default config
                save(); // do not chain save, this can be done separately
                return;
            }

            try (JsonReader reader = new JsonReader(new FileReader(configFile))) {
                config = gson.fromJson(reader, Config.class);
                if (config == null) config = new Config(); // default config
            } catch (Exception e) {
                logger.error("Could not load config", e);
                config = new Config(); // default config
            }

            onChange();
        });
    }

    public static CompletableFuture<Void> save() {
        return CompletableFuture.runAsync(() -> {
            if (config == null) throw new IllegalStateException("Tried to save null config");

            File configFile = getConfigFile();

            if (!configFile.getParentFile().exists() && !configFile.getParentFile().mkdirs()) {
                logger.error("Could not create config directory.");
                return;
            }

            try (JsonWriter writer = gson.newJsonWriter(new FileWriter(configFile))) {
                JsonElement json = gson.toJsonTree(config);
                gson.toJson(json, writer);
            } catch (Exception e) {
                logger.error("Could not write config file", e);
            }
        });
    }

    public static void dispatchHandledSave() {
        save().exceptionally(ex -> {
            logger.error("Failed to save config", ex);
            return null;
        });
    }

    /* - */

    public static boolean isNoTrample() {
        return config.gameplay.noTrample;
    }
}
