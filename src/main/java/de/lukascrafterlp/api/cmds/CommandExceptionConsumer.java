package de.lukascrafterlp.api.cmds;

import org.bukkit.command.CommandSender;

@FunctionalInterface
public interface CommandExceptionConsumer {

    void apply(CommandSender sender, CommandException exception);
}
