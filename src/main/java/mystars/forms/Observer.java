package mystars.forms;

import java.util.ArrayList;

public abstract class Observer {
	protected ArrayList<IUserInterfaceObserver> observers = new ArrayList<IUserInterfaceObserver>();

	public void addObserver(IUserInterfaceObserver observer) {
		this.observers.add(observer);
	}
}
