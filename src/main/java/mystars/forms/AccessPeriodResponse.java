package mystars.forms;

import java.time.LocalDateTime;

/**
 * <h1>Class: AccessPeriodResponse</h1>
 * 
 * This class holds the information for the MySTARS access period starting and ending date and time.
 */
public class AccessPeriodResponse {
	/**
	 * The starting date and time of the access period.
	 */
	private final LocalDateTime start;
	
	/**
	 * The ending date and time for the access period.
	 */
	private final LocalDateTime end;

	/**
	 * Sets the access period starting and ending date and time.
	 * @param start The access period starting date and time.
	 * @param end	The access period ending date and time.
	 */
	public AccessPeriodResponse(LocalDateTime start, LocalDateTime end) {
		this.start = start;
		this.end = end;
	}

	/**
	 * Returns the access period starting date and time.
	 * 
	 * @return The access period starting date and time.
	 */
	public LocalDateTime getStart() {
		return start;
	}

	/**
	 * Returns the access period ending date and time.
	 * 
	 * @return The access period ending date and time.
	 */
	public LocalDateTime getEnd() {
		return end;
	}
}
