package mystars.entities;

import java.io.Serializable;

/**
 * <h1>Class: Registration</h1>
 * 
 * This class represents one index added to a timetable and tracks the current
 * status of this relationship.
 */
public class Registration implements Serializable {
	/**
	 * ID for versioning of serialized data.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Possible states of this Registration.
	 */
	public enum Status {
		Registered, Waitlist
	}

	/**
	 * The index this Registration is for.
	 */
	private Index index;

	/**
	 * The status of this Registration.
	 */
	private Status status;

	/**
	 * Creates a new instance of Registration.
	 * 
	 * @param index  The index of this Registration
	 * @param status The sStatus of this Registration
	 */
	public Registration(Index index, Status status) {
		this.index = index;
		this.status = status;
	}

	/**
	 * Mark this registration as Registered.
	 */
	public void setRegistered() {
		status = Status.Registered;
	}

	/**
	 * Retrieves the index of this relationship.
	 * 
	 * @return The index of this relationship.
	 */
	public Index getIndex() {
		return index;
	}

	/**
	 * * Retrieves the status of this relationship.
	 * 
	 * @return The status of this relationship.
	 */
	public Status getStatus() {
		return status;
	}
}
