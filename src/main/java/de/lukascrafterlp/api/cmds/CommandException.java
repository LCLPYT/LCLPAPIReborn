package de.lukascrafterlp.api.cmds;

import net.kyori.adventure.text.Component;

import java.io.Serial;

public class CommandException extends RuntimeException {

    public static final CommandException NO_PERMISSION = new CommandException("You don't have permission to do that."),
            NOT_A_PLAYER = new CommandException("You must be a player to do that."),
            NOT_ONLINE = new CommandException("That player is not online.");

    @Serial
    private static final long serialVersionUID = -8373382680221065338L;

    public final Component errorMessage;

    public CommandException(String msg) {
        this(Component.text(msg));
    }

    public CommandException(Component msg) {
        this.errorMessage = msg;
    }
}
