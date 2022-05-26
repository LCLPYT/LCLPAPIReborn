package de.lukascrafterlp.api.api;

import net.minecraft.network.protocol.Packet;
import org.bukkit.craftbukkit.v1_18_R2.entity.CraftPlayer;
import org.bukkit.entity.Player;

public class PacketHelper {

    public static void sendPacket(Packet<?> packet, Player p) {
        ((CraftPlayer) p).getHandle().connection.send(packet);
    }
}
