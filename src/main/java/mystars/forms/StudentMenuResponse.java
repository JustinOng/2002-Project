package mystars.forms;

public class StudentMenuResponse {
	public enum Selected {
		Register, Drop, Change, Swop
	};
	
	private final Selected selected;
	
	public StudentMenuResponse(Selected selected) {
		this.selected = selected;
	}
	
	public Selected getSelected() {
		return selected;
	}
}
