package de.lukascrafterlp.api.cmds;

import de.lukascrafterlp.api.LCLPAPI;
import de.lukascrafterlp.api.resource.ResourcepackManager;
import net.kyori.adventure.text.Component;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class CommandResourcepack extends BaseCommand implements BaseTabCompleter {

    public CommandResourcepack(String name) {
        super(name);
    }

    @NotNull
    @Override
    public String getName() {
        return name;
    }

    @Override
    public boolean execute(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) throws CommandException {
        if (args.length == 0) {
            usage(sender);
            return true;
        }
        if (args.length == 1) {
            var p = requirePlayer(sender);
            var url = getPack(args[0], p);

            ResourcepackManager.sendResourcepackRequest(p, url);
            return true;
        }
        if (args.length == 2) {
            requireOp(sender);

            var p = Arguments.getPlayer(args[1]);
            var pack = getPack(args[0], p);

            ResourcepackManager.sendResourcepackRequest(p, pack);
            LCLPAPI.BROADCASTER.send(sender, Component.text("Sending resourcepack request to %s".formatted(p.getName())));
            return true;
        }

        return false;
    }

    private ResourcepackManager.Pack getPack(String rp, CommandSender p) {
        final ResourcepackManager.Pack pack = ResourcepackManager.getPack(rp);
        if (pack == null)
            throw new CommandException("Resourcepack '%s' isn't configured.".formatted(rp));

        return pack;
    }

    private void usage(CommandSender sender) {
        if (sender.isOp()) usage("<pack> [player]");
        else usage("<pack>");
    }

    @Override
    public @Nullable List<String> tabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (args.length == 1)
            return new ArrayList<>(ResourcepackManager.getResourcepacks());

        return null;
    }
}
