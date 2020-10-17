package mystars.forms;

public class CreateIndexResponse {
	private final int number;
	private final int maxEnrolled;
	
	public CreateIndexResponse(int number, int maxEnrolled) {
		this.number = number;
		this.maxEnrolled = maxEnrolled;
	}
	
	public int getNumber() {
		return number;
	}
	
	public int getMaxEnrolled() {
		return maxEnrolled;
	}
}
