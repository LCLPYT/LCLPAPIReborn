package de.lukascrafterlp.api.api;

import de.lukascrafterlp.api.operation.ScheduledOperation;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.BoundingBox;

import java.util.ArrayList;
import java.util.List;

public class Blood {

    /**
     *
     * @param loc the location to spawn the blood
     * @param amount the amount of blood items to spawn
     */
    public static void spawnBlood(Location loc, int amount) {
        List<Item> items = new ArrayList<>();

        for(int a = 0; a < amount; a++) {
            Item i = loc.getWorld().dropItemNaturally(loc, new ItemStack(Material.RED_DYE));
            i.setPickupDelay(1200);
            i.addScoreboardTag("nomerge");
            items.add(i);
        }

        new ScheduledOperation(7L) {

            @Override
            public void run() {
                for(Item i : items) i.remove();
            }
        };
    }

    public static void bleed(Entity en, int amount) {
        BoundingBox bb = en.getBoundingBox();
        Location l = en.getLocation().add(-(bb.getWidthX() / 2D), bb.getHeight() / 2D, -(bb.getWidthZ() / 2D));

        spawnBlood(l, amount);
    }
}
