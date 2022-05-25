package de.lukascrafterlp.api.resource;

import de.lukascrafterlp.api.LCLPAPI;
import de.lukascrafterlp.api.util.ConfigHelper;
import de.lukascrafterlp.api.util.PlayerSessionData;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerResourcePackStatusEvent;

import javax.annotation.Nullable;
import java.util.*;
import java.util.concurrent.CompletableFuture;

public class ResourcepackManager {

    @Nullable
    private final Map<String, Pack> resourcepacks = new HashMap<>();

    @Override
    public String toString() {
        return "ResourcepackManager{resourcepacks=%s}".formatted(resourcepacks);
    }

    /* static */

    private static final String CONFIG_NAME = "resourcepacks";
    private static final PlayerSessionData.DataType<String> REQUESTING = PlayerSessionData.createDataType();
    private static final PlayerSessionData.DataType<String> INSTALLED = PlayerSessionData.createDataType();
    @Nullable
    private static ResourcepackManager INSTANCE = null;

    public static CompletableFuture<Void> load() {
        return ConfigHelper.load(CONFIG_NAME, ResourcepackManager.class, ResourcepackManager::new)
                .thenAccept(cfg -> INSTANCE = cfg);
    }

    public static void sendResourcepackRequest(Player p, Pack pack) {
        sendResourcepackRequest(p, pack, false);
    }

    public static void sendResourcepackRequest(Player p, Pack pack, boolean force) {
        sendResourcepackRequest(p, pack, force, null);
    }

    public static void sendResourcepackRequest(Player p, Pack pack, boolean force, Component prompt) {
        LCLPAPI.BROADCASTER.send(p, Component.text("Making resourcepack request for ")
                .append(Component.text(pack.url, NamedTextColor.DARK_AQUA)));

        ResourcepackManager.setRequesting(p, pack.url);
        pack.sendTo(p, force, prompt);
    }

    public static boolean isRequesting(Player p) {
        return REQUESTING.has(p);
    }

    public static void setRequesting(Player p, String url) {
        REQUESTING.set(p, url);
    }

    public static void stopRequesting(Player p) {
        REQUESTING.remove(p);
    }

    public static String getRequested(Player p) {
        return REQUESTING.get(p).orElse(null);
    }

    public static boolean existResourcepack(String s) {
        final var packs = getPacks();
        if (packs == null) return false;
        return packs.containsKey(s);
    }

    @Nullable
    public static Map<String, Pack> getPacks() {
        return Optional.ofNullable(INSTANCE)
                .map(x -> x.resourcepacks)
                .orElse(null);
    }

    @Nullable
    public static Pack getPack(String s) {
        var packs = getPacks();
        if (packs == null) return null;

        return packs.get(s);
    }

    public static List<String> getResourcepacks() {
        var packs = getPacks();
        return packs != null ? new ArrayList<>(packs.keySet()) : new ArrayList<>();
    }

    @Nullable
    public static String getInstalledResourcepack(Player p) {
        return INSTALLED.get(p).orElse(null);
    }

    public static void setInstalledIfCustom(Player p) {
        String requested = getRequested(p);
        if(requested != null)
            INSTALLED.set(p, requested);
    }

    public static class Listener implements org.bukkit.event.Listener {

        @EventHandler
        public void onResoucepackStatus(PlayerResourcePackStatusEvent e) {
            final Player p = e.getPlayer();
            if (!ResourcepackManager.isRequesting(p)) return;

            if (e.getStatus() == PlayerResourcePackStatusEvent.Status.DECLINED) {
                LCLPAPI.BROADCASTER.send(p,
                        Component.text("You have declined the pending resource request.", NamedTextColor.RED));

                ResourcepackManager.stopRequesting(p);
            } else if (e.getStatus() == PlayerResourcePackStatusEvent.Status.ACCEPTED) {
                LCLPAPI.BROADCASTER.send(p,
                        Component.text("You have accepted the resourcepack request, downloading and installing the pack...", NamedTextColor.AQUA));
            } else if (e.getStatus() == PlayerResourcePackStatusEvent.Status.FAILED_DOWNLOAD) {
                LCLPAPI.BROADCASTER.send(p,
                        Component.text("Failed to download the resoucepack.", NamedTextColor.RED));

                ResourcepackManager.stopRequesting(p);
            } else if (e.getStatus() == PlayerResourcePackStatusEvent.Status.SUCCESSFULLY_LOADED) {
                LCLPAPI.BROADCASTER.send(p,
                        Component.text("Successfully installed the resourcepack."));

                ResourcepackManager.setInstalledIfCustom(p);
                ResourcepackManager.stopRequesting(p);
            }
        }
    }

    public static class Pack {
        public final String url, sha1;

        public Pack(String url, String sha1) {
            this.url = url;
            this.sha1 = sha1;
        }

        public void sendTo(Player p) {
            sendTo(p, false);
        }

        public void sendTo(Player p, boolean force) {
            sendTo(p, force, null);
        }

        public void sendTo(Player p, boolean force, Component prompt) {
            p.setResourcePack(url, sha1, force, prompt);
        }

        @Override
        public String toString() {
            return "Pack{url='%s', sha1='%s'}".formatted(url, sha1);
        }
    }
}
