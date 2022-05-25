package de.lukascrafterlp.api.util;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;

public class PlayerSessionData implements Listener {

    private static final Map<UUID, Map<Integer, Object>> sessionData = new HashMap<>();
    private static final AtomicInteger nextContainerId = new AtomicInteger(0);

    /**
     * Registers a new data type which serves as a comfortability layer to access session data.
     *
     * @param <T> The data type of the stored data.
     * @return The newly created DataType.
     */
    public static <T> DataType<T> createDataType() {
        return createDataType(DataType::new);
    }

    /**
     * Registers a new data type which serves as a comfortability layer to access session data.
     *
     * @param typeFactory Specifies a factory that will be used to create the data type object.
     * @param <T> The data type of the stored data.
     * @param <U> The class of the newly created DataType class.
     * @return The newly created DataType class.
     */
    public static <T, U extends DataType<T>> DataType<T> createDataType(Function<Integer, U> typeFactory) {
        final int id = getUniqueDataTypeId();
        return typeFactory.apply(id);
    }

    /**
     * Gets a unique container id which can be used to access session data.
     * An id corresponds to a set of data, saved in the session data map.
     * @return A unique container id.
     */
    public static int getUniqueDataTypeId() {
        return nextContainerId.getAndIncrement();
    }

    @SuppressWarnings("unchecked")
    public static <T> Optional<T> getData(int dataTypeId, UUID uuid) {
        return Optional.ofNullable(sessionData.get(uuid))
                .map(sessionData -> (T) sessionData.get(dataTypeId));
    }

    public static void setData(int dataTypeId, UUID uuid, @Nullable Object value) {
        final var dataMap = sessionData.computeIfAbsent(uuid, ignored -> new HashMap<>());
        if (value == null) {
            dataMap.remove(dataTypeId);
            return;
        }

        dataMap.put(dataTypeId, value);
    }

    public static void dropDataOf(UUID uuid) {
        sessionData.remove(uuid);
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent e) {
        dropDataOf(e.getPlayer().getUniqueId());
    }

    public static class DataType<T> {
        private final int id;

        private DataType(int id) {
            this.id = id;
        }

        public Optional<T> get(Player p) {
            return get(p.getUniqueId());
        }

        public Optional<T> get(UUID uuid) {
            return getData(this.id, uuid);
        }

        public void set(Player p, @Nullable T value) {
            set(p.getUniqueId(), value);
        }

        public void set(UUID uuid, @Nullable T value) {
            setData(this.id, uuid, value);
        }

        public void remove(Player p) {
            remove(p.getUniqueId());
        }

        public void remove(UUID uuid) {
            set(uuid, null);
        }

        public boolean has(Player p) {
            return has(p.getUniqueId());
        }

        public boolean has(UUID uuid) {
            return get(uuid).isPresent();
        }
    }
}
