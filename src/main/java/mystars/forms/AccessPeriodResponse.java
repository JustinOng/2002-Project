package mystars.forms;

import java.time.LocalDateTime;

public class AccessPeriodResponse {
	private final LocalDateTime start;
	private final LocalDateTime end;

	public AccessPeriodResponse(LocalDateTime start, LocalDateTime end) {
		this.start = start;
		this.end = end;
	}

	public LocalDateTime getStart() {
		return start;
	}

	public LocalDateTime getEnd() {
		return end;
	}
}
