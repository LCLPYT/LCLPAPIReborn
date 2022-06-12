package de.lukascrafterlp.api.cmds;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import javax.annotation.Nonnull;
import java.util.UUID;

public class Arguments {

    private Arguments() {
        throw new IllegalStateException("Arguments is a singleton");
    }

    @Nonnull
    public static Player getPlayer(String name) {
        Player p = Bukkit.getPlayer(name);
        if (p == null) throw CommandException.NOT_ONLINE_NAME.apply(name);
        return p;
    }

    @Nonnull
    public static Player getPlayer(UUID uuid) {
        Player p = Bukkit.getPlayer(uuid);
        if (p == null) throw new CommandException("There is no player with UUID \"%s\" online.".formatted(uuid));
        return p;
    }

    public static int getInt(String s) {
        return getInt(s, 10);
    }

    public static int getInt(String s, int radix) {
        try {
            return Integer.parseInt(s, radix);
        } catch (Exception e) {
            throw new CommandException("Argument \"%s\" is not a valid integer.".formatted(s));
        }
    }

    public static long getLong(String s) {
        return getLong(s, 10);
    }

    public static long getLong(String s, int radix) {
        try {
            return Long.parseLong(s, radix);
        } catch (Exception e) {
            throw new CommandException("Argument \"%s\" is not a valid integer.".formatted(s));
        }
    }

    public static float getFloat(String s) {
        try {
            return Float.parseFloat(s);
        } catch (Exception e) {
            throw new CommandException("Argument \"%s\" is not a valid number.".formatted(s));
        }
    }

    public static double getDouble(String s) {
        try {
            return Double.parseDouble(s);
        } catch (Exception e) {
            throw new CommandException("Argument \"%s\" is not a valid number.".formatted(s));
        }
    }

    public static boolean getBoolean(String s) {
        if (s == null || s.isBlank())
            throw new CommandException("Argument \"%s\" is not a boolean value.".formatted(s));

        return Boolean.parseBoolean(s);
    }
}
