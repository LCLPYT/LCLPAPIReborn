package de.lukascrafterlp.api.api;

import de.lukascrafterlp.api.LCLPAPI;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Item;
import org.bukkit.persistence.PersistentDataType;

public class EntityHelper {

    public static final NamespacedKey ID_NOMERGE = LCLPAPI.identifier("nomerge");

    public static void blockMerge(Item i) {
        i.getPersistentDataContainer().set(ID_NOMERGE, PersistentDataType.BYTE, (byte) 1);
    }

    public static void unblockMerge(Item i) {
        i.getPersistentDataContainer().remove(ID_NOMERGE);
    }

    public static boolean canMerge(Item i) {
        return !i.getPersistentDataContainer().has(ID_NOMERGE);
    }
}
