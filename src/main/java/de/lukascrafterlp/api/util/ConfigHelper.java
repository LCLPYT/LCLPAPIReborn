package de.lukascrafterlp.api.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import de.lukascrafterlp.api.LCLPAPI;
import de.lukascrafterlp.api.events.event.ConfigChangedEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.bukkit.Bukkit;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;

public class ConfigHelper {

    private static final Logger logger = LogManager.getLogger();
    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    @NotNull
    public static File getConfigFile(String name) {
        return new File(LCLPAPI.getPlugin().getDataFolder(), "%s.json".formatted(name));
    }

    public static <T> CompletableFuture<T> load(String name, Class<T> type, Supplier<T> defaultConfig) {
        return CompletableFuture.supplyAsync(() -> {
            final File configFile = getConfigFile(name);

            if (!configFile.exists()) {
                T config = defaultConfig.get(); // default config
                save(name, config); // do not chain save, this can be done separately
                return config;
            }

            T config;
            try (JsonReader reader = new JsonReader(new FileReader(configFile))) {
                config = gson.fromJson(reader, type);
                if (config == null) config = defaultConfig.get(); // default config
            } catch (Exception e) {
                logger.error("Could not load config", e);
                config = defaultConfig.get(); // default config
            }

            Bukkit.getPluginManager().callEvent(new ConfigChangedEvent(name, config));
            return config;
        });
    }

    public static <T> CompletableFuture<Void> save(String name, T config) {
        return CompletableFuture.runAsync(() -> {
            if (config == null) throw new IllegalStateException("Tried to save null config");

            final File configFile = getConfigFile(name);

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

    public static void dispatchHandledSave(String name, Object obj) {
        save(name, obj).exceptionally(ex -> {
            logger.error("Failed to save config", ex);
            return null;
        });
    }
}
