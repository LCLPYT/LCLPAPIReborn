package de.lukascrafterlp.api.events;

import de.lukascrafterlp.api.events.event.PlayerFireExtinguishEvent;
import org.bukkit.block.Block;
import org.bukkit.block.data.type.Fire;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class BlockListener implements Listener {

    @EventHandler
    public void onHitBlock(PlayerInteractEvent e) {
        if (e.getAction() != Action.LEFT_CLICK_BLOCK) return;

        Block b = e.getClickedBlock();
        if (b == null || !(b.getBlockData() instanceof Fire)) return;

        if (!new PlayerFireExtinguishEvent(e.getPlayer(), b, e.getBlockFace()).callEvent()) {
            e.setCancelled(true);
        }
    }
}
