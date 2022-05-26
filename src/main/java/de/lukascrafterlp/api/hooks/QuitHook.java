package de.lukascrafterlp.api.hooks;

import org.bukkit.entity.Player;

public interface QuitHook {

    HookInvoker<QuitHook> EVENT = new HookInvoker<>(hooks -> p -> hooks.forEach(l -> l.onQuit(p)));

    void onQuit(Player p);
}
