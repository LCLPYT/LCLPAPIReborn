package de.lukascrafterlp.api.api;

import de.lukascrafterlp.api.LCLPAPI;
import org.bukkit.scheduler.BukkitRunnable;

public class SyncHelper {

	public static void sync(final Runnable r) {
		if(r == null) return;
		
		new BukkitRunnable() {
			
			@Override
			public void run() {
				r.run();
			}
		}.runTask(LCLPAPI.getPlugin());
	}
	
	
	public static void async(final Runnable r) {
		if(r == null) return;
		
		new BukkitRunnable() {
			
			@Override
			public void run() {
				r.run();
			}
		}.runTaskAsynchronously(LCLPAPI.getPlugin());
	}
	
}
