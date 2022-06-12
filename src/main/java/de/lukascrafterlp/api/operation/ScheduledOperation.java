package de.lukascrafterlp.api.operation;

import de.lukascrafterlp.api.LCLPAPI;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

public abstract class ScheduledOperation implements Runnable, SchedulerOperation {

	private BukkitTask task = null;
	private Runnable end = null;
	
	public ScheduledOperation(long delay) {
		construct(delay, LCLPAPI.getPlugin());
	}
	
	public ScheduledOperation(long delay, Plugin plugin) {
		construct(delay, plugin);
	}

	private void construct(long delay, Plugin plugin) {
		task = new BukkitRunnable() {
			
			@Override
			public void run() {
				runOperation();
			}
		}.runTaskLater(plugin, delay);
	}
	
	private void runOperation() {
		this.run();
	}
	
	@Override
	public void cancel() {
		if(task != null) {
			task.cancel();
			onCancel();
		}
		task = null;
	}
	
	@Override
	public void onCancel() {
		if(end != null) end.run();
	}
	
	@Override
	public void setOnCancel(Runnable run) {
		end = run;
	}
	
}
