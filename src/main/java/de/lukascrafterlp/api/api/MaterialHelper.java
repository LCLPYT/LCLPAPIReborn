package de.lukascrafterlp.api.api;

import org.bukkit.Material;

public class MaterialHelper {

    public static boolean isMaterial(Material mat, Material... materials) {
        for (Material m : materials)
            if (m.equals(mat))
                return true;

        return false;
    }
}
