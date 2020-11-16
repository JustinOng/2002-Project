package mystars.entities;

import java.io.Serializable;

public class Registration implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public enum Status {
		Registered, Waitlist
	}

	private Index index;
	private Status status;

	public Registration(Index index, Status status) {
		this.index = index;
		this.status = status;
	}

	public void setRegistered() {
		status = Status.Registered;
	}

	public Index getIndex() {
		return index;
	}

	public Status getStatus() {
		return status;
	}
}
