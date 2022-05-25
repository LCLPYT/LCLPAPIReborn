package de.lukascrafterlp.api.cmds;

import de.lukascrafterlp.api.LCLPAPI;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

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
}
