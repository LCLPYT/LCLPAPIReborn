package de.lukascrafterlp.api.util;

import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class PlayerSessionDataTest {

    @Test
    void setAndGet() {
        final UUID uuid = UUID.randomUUID();
        final PlayerSessionData.DataType<Integer> MY_INT_STORAGE = PlayerSessionData.createDataType();

        final int expected = new Random().nextInt();
        MY_INT_STORAGE.set(uuid, expected);

        final int actual = MY_INT_STORAGE.get(uuid).orElseThrow();
        assertEquals(expected, actual);
    }

    @Test
    void dropData() throws ReflectiveOperationException {
        final PlayerSessionData.DataType<String> MY_STRING_STORAGE = PlayerSessionData.createDataType();

        final var keys = Stream.generate(UUID::randomUUID).limit(5).toList();

        // fill with random strings
        final Random random = new Random();
        keys.forEach(key -> {
            final byte[] bytes = new byte[random.nextInt(10) + 5];
            random.nextBytes(bytes);

            MY_STRING_STORAGE.set(key, new String(bytes, StandardCharsets.UTF_8));
        });

        final UUID droppedKey = keys.get(random.nextInt(keys.size()));
        PlayerSessionData.dropDataOf(droppedKey);
        Optional<String> value = MY_STRING_STORAGE.get(droppedKey);

        assertTrue(value.isEmpty());

        assertMapSize(keys);
    }

    @SuppressWarnings("unchecked")
    private void assertMapSize(List<UUID> keys) throws ReflectiveOperationException {
        Field f = PlayerSessionData.class.getDeclaredField("sessionData");
        f.setAccessible(true);
        var map = (Map<UUID, Map<Integer, Object>>) f.get(null);
        assertEquals(keys.size() - 1, map.size());
    }

    @Test
    void emptyData() {
        final PlayerSessionData.DataType<String> MY_STRING_STORAGE = PlayerSessionData.createDataType();
        Optional<String> value = MY_STRING_STORAGE.get(UUID.randomUUID());
        assertTrue(value.isEmpty());
    }
}