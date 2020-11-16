package mystars.entities;

import java.io.Serializable;

/**
 * Represents one index added to a timetable. Tracks current status of this
 * relationship
 *
 */
public class Registration implements Serializable {
	/**
	 * ID for versioning of serialized data.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Possible states of this Registration
	 */
	public enum Status {
		Registered, Waitlist
	}

	/**
	 * Index this Registration is for
	 */
	private Index index;

	/**
	 * Status of this Registration
	 */
	private Status status;

	/**
	 * Creates new instance of Registration
	 * 
	 * @param index  Index of this Registration
	 * @param status Status of this Registration
	 */
	public Registration(Index index, Status status) {
		this.index = index;
		this.status = status;
	}

	/**
	 * Mark this registration as Registered
	 */
	public void setRegistered() {
		status = Status.Registered;
	}

	/**
	 * Retrieves index of this relationship
	 * 
	 * @return index of this relationship
	 */
	public Index getIndex() {
		return index;
	}

	/**
	 * * Retrieves status of this relationship
	 * 
	 * @return status of this relationship
	 */
	public Status getStatus() {
		return status;
	}
}
