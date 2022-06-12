package de.lukascrafterlp.api.operation;

public interface SchedulerOperation {

	void cancel();
	
	void onCancel();
	
	void setOnCancel(Runnable run);
}
