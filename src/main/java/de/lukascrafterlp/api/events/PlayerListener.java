package de.lukascrafterlp.api.events;

import de.lukascrafterlp.api.hooks.JoinHook;
import de.lukascrafterlp.api.hooks.QuitHook;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerListener implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        JoinHook.EVENT.invoker().onJoin(e.getPlayer());
        
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent e) {
        QuitHook.EVENT.invoker().onQuit(e.getPlayer());
    }
}
