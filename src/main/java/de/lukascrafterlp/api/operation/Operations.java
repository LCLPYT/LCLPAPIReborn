package de.lukascrafterlp.api.operation;

import org.bukkit.entity.Player;

import java.util.concurrent.TimeUnit;

public class Operations {

	public static ScheduledOperation scheduled(Runnable run, long delay) {
		if(run == null) return null;
		
		return new ScheduledOperation(delay) {
			
			@Override
			public void run() {
				run.run();
			}
		};
	}
	
	public static ScheduledOperation scheduled(Player p, PlayerOperation op, long delay) {
		if(p == null || op == null) return null;
		
		return new ScheduledOperation(delay) {
			
			@Override
			public void run() {
				op.run(p);
			}
		};
	}
	
	/**
	 * Measures the time an operation takes.
	 * 
	 * @param r The operation to run.
	 * @return The passed time the operation took to execute. In nanoseconds.
	 */
	public static long measureOperationTime(Runnable r) {
		return measureOperationTime(r, TimeUnit.NANOSECONDS);
	}
	
	public static long measureOperationTime(Runnable r, TimeUnit unit) {
		if(r == null || unit == null) return -1L;
		
		long start = System.nanoTime();
		r.run();
		long passed = System.nanoTime() - start;
		
		return unit.convert(passed, TimeUnit.NANOSECONDS);
	}
	
}
