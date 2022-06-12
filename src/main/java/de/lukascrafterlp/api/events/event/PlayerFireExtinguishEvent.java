package de.lukascrafterlp.api.events.event;

import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

public class PlayerFireExtinguishEvent extends Event implements Cancellable{

	private static final HandlerList handlers = new HandlerList();
	
	@Override
	public @NotNull HandlerList getHandlers() {
	    return handlers;
	}

	public static HandlerList getHandlerList() {
	    return handlers;
	}

	private final Player player;
	private final Block block;
	private final BlockFace at;
	private boolean cancelled = false;
	
	public PlayerFireExtinguishEvent(Player p, Block b, BlockFace affectedBlockFace) {
		this.player = p;
		this.block = b;
		this.at = affectedBlockFace;
	}
	
	/**
	 * 
	 * @return the player who extinguishes the fire
	 */
	public Player getPlayer() {
		return player;
	}
	
	/**
	 * 
	 * @return the blockface the player clicked at aka the blockface which the fire sticks to
	 */
	public BlockFace getAffectedBlockFace() {
		return at;
	}
	
	/**
	 * 
	 * @return the fire block
	 */
	public Block getBlock() {
		return block;
	}
	
	@Override
	public boolean isCancelled() {
		return cancelled;
	}

	@Override
	public void setCancelled(boolean cancel) {
		this.cancelled = cancel;
	}
}
