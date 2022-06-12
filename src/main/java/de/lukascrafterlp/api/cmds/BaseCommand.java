package de.lukascrafterlp.api.cmds;

import de.lukascrafterlp.api.LCLPAPI;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;
import java.util.Objects;

public abstract class BaseCommand implements CommandExecutor {

    public final String name;

    public BaseCommand(String name) {
        this.name = Objects.requireNonNull(name);
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!command.getName().equals(name)) return false;

        try {
            return execute(sender, command, label, args);
        } catch (CommandException e) {
            handleError(sender, e);
            return true;
        }
    }

    protected abstract boolean execute(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) throws CommandException;

    protected void handleError(CommandSender sender, CommandException error) {
        LCLPAPI.BROADCASTER.sendError(sender, error.errorMessage);
    }

    protected void usage(String explanation) {
        throw new CommandException("Usage: /%s %s".formatted(name, explanation));
    }

    protected void requireOp(CommandSender sender) {
        if (!sender.isOp()) throw CommandException.NO_PERMISSION;
    }

    @Nonnull
    protected Player requirePlayer(CommandSender sender) {
        if (!(sender instanceof Player p)) throw CommandException.NOT_A_PLAYER;
        return p;
    }
}
