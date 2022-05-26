package de.lukascrafterlp.api.hooks;

import org.bukkit.entity.Player;

public interface JoinHook {

    HookInvoker<JoinHook> EVENT = new HookInvoker<>(hooks -> p -> hooks.forEach(l -> l.onJoin(p)));

    void onJoin(Player p);
}
