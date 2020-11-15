package mystars.forms;

public class IndexManagementResponse {
	public enum Selected {
		CreateLesson, ListStudents
	};

	private final Selected selected;
	private final String index;

	public IndexManagementResponse(String index, Selected selected) {
		this.index = index;
		this.selected = selected;
	}
	
	public String getIndex() {
		return index;
	}

	public Selected getSelected() {
		return selected;
	}
}
