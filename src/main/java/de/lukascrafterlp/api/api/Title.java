package de.lukascrafterlp.api.api;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.ComponentLike;
import net.kyori.adventure.title.Title.Times;
import net.kyori.adventure.title.TitlePart;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import static net.kyori.adventure.title.Title.DEFAULT_TIMES;
import static net.kyori.adventure.title.Title.Times.times;
import static net.kyori.adventure.util.Ticks.duration;

public class Title {

    public static void defaultTimes(Player p) {
        sendTimes(p, DEFAULT_TIMES);
    }

    public static void sendTimes(Player p, Times times) {
        p.sendTitlePart(TitlePart.TIMES, times);
    }

    public static void sendActionBar(Player p, Component component) {
        sendActionBar(p, component, DEFAULT_TIMES);
    }

    public static void sendActionBar(Player p, Component component, int in, int stay, int out) {
        Times times = times(duration(in), duration(stay), duration(out));
        sendActionBar(p, component, times);
    }

    public static void sendActionBar(Player p, Component component, Times times) {
        sendTimes(p, times);
        p.sendActionBar(component);
    }

    public static void sendTitleOnly(Player p, Component component) {
        sendTitleOnly(p, component, DEFAULT_TIMES);
    }

    public static void sendTitleOnly(Player p, Component component, int in, int stay, int out) {
        Times times = times(duration(in), duration(stay), duration(out));
        sendTitleOnly(p, component, times);
    }

    public static void sendTitleOnly(Player p, Component component, Times times) {
        sendTimes(p, times);
        p.sendTitlePart(TitlePart.TITLE, component);
    }

    public static void sendSubtitleOnly(Player p, Component component) {
        sendTitleOnly(p, component, DEFAULT_TIMES);
    }

    public static void sendSubtitleOnly(Player p, Component component, int in, int stay, int out) {
        Times times = times(duration(in), duration(stay), duration(out));
        sendSubtitleOnly(p, component, times);
    }

    public static void sendSubtitleOnly(Player p, Component component, Times times) {
        sendTimes(p, times);
        p.sendTitlePart(TitlePart.SUBTITLE, component);
    }

    /**
     * @deprecated Use {@link Player#sendPlayerListHeaderAndFooter(ComponentLike, ComponentLike)}
     * @param p The player to send the header and footer to.
     * @param header The header.
     * @param footer The footer.
     */
    @Deprecated
    public static void sendTablistHeaderFooter(Player p, ComponentLike header, ComponentLike footer) {
        p.sendPlayerListHeader(header);
        p.sendPlayerListFooter(footer);
    }

    public static void sendPublicTitle(net.kyori.adventure.title.Title title) {
        Bukkit.getOnlinePlayers().forEach(player -> player.showTitle(title));
    }

    public static void sendPublicTitleOnly(Component component) {
        Bukkit.getOnlinePlayers().forEach(player -> sendTitleOnly(player, component));
    }

    public static void sendPublicTitleOnly(Component component, int in, int stay, int out) {
        sendPublicTitleOnly(component, times(duration(in), duration(stay), duration(out)));
    }

    public static void sendPublicTitleOnly(Component component, Times times) {
        Bukkit.getOnlinePlayers().forEach(player -> sendTitleOnly(player, component, times));
    }

    public static void sendPublicSubtitleOnly(Component component) {
        Bukkit.getOnlinePlayers().forEach(player -> sendSubtitleOnly(player, component));
    }

    public static void sendPublicSubtitleOnly(Component component, int in, int stay, int out) {
        sendPublicSubtitleOnly(component, times(duration(in), duration(stay), duration(out)));
    }

    public static void sendPublicSubtitleOnly(Component component, Times times) {
        Bukkit.getOnlinePlayers().forEach(player -> sendSubtitleOnly(player, component, times));
    }

    public static void sendPublicActionBar(Component component) {
        Bukkit.getOnlinePlayers().forEach(player -> sendActionBar(player, component));
    }

    public static void sendPublicActionBar(Component component, int in, int stay, int out) {
        sendPublicActionBar(component, times(duration(in), duration(stay), duration(out)));
    }

    public static void sendPublicActionBar(Component component, Times times) {
        Bukkit.getOnlinePlayers().forEach(player -> sendActionBar(player, component, times));
    }
}
