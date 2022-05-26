package de.lukascrafterlp.api.api;

import de.lukascrafterlp.api.hooks.JoinHook;
import net.kyori.adventure.text.ComponentLike;

import javax.annotation.Nullable;

public class GlobalTablist {

    private static volatile ComponentLike header = null, footer = null;
    private static JoinHook joinHook = null;

    @Nullable
    public static ComponentLike header() {
        return header;
    }

    public static void header(@Nullable ComponentLike component) {
        header = component;
        update();
    }

    @Nullable
    public static ComponentLike footer() {
        return footer;
    }

    public static void footer(@Nullable ComponentLike component) {
        footer = component;
        update();
    }

    private static void update() {
        boolean shouldListen = header != null || footer != null;

        if (shouldListen) {
            if (joinHook != null) return;

            joinHook = JoinHook.EVENT.register(p -> {
                ComponentLike header = header(), footer = footer();

                if (header != null)
                    p.sendPlayerListHeader(header);
                if (footer != null)
                    p.sendPlayerListFooter(footer);
            });
        } else if (joinHook != null)
            JoinHook.EVENT.unregister(joinHook);
    }
}
