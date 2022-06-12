package de.lukascrafterlp.api.events;

import de.lukascrafterlp.api.api.EntityHelper;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ItemMergeEvent;

public class EntityListener implements Listener {

    @EventHandler
    public void onItemMerge(ItemMergeEvent e) {
        if (!EntityHelper.canMerge(e.getEntity()))
            e.setCancelled(true);
    }
}
