package de.lukascrafterlp.api.operation;

import de.lukascrafterlp.api.LCLPAPI;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

public abstract class RepeatingOperation implements Runnable, SchedulerOperation {

	private BukkitTask task = null;
	private Runnable end = null;
	private final long delay;
	private final long period;
	private final Plugin plugin;
	
	public RepeatingOperation(long delay, long period) {
		this(delay, period, LCLPAPI.getPlugin());
	}
	
	public RepeatingOperation(long delay, long period, Plugin plugin) {
		this(delay, period, plugin, true);
	}
	
	public RepeatingOperation(long delay, long period, Plugin plugin, boolean instantStart) {
		this.delay = delay;
		this.period = period;
		this.plugin = plugin;
		if(instantStart) construct(delay, period, plugin);
	}

	public void construct() {
		construct(delay, period, plugin);
	}
	
	public void construct(long delay, long period, Plugin plugin) {
		if(task != null) return;
		
		task = new BukkitRunnable() {
			
			@Override
			public void run() {
				runOperation();
			}
		}.runTaskTimer(plugin, delay, period);
	}
	
	private void runOperation() {
		this.run();
	}

	public void cancelTask() {
		cancel();
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
	
	public BukkitTask getTask() {
		return task;
	}
	
	public long getDelay() {
		return delay;
	}
	
	public long getPeriod() {
		return period;
	}
	
	public Plugin getPlugin() {
		return plugin;
	}
	
	public Runnable getEnd() {
		return end;
	}
	
}
