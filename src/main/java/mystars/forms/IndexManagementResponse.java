package mystars.forms;

/**
 * This class manages the the form for the management of course indexes with the
 * relevant options.
 */
public class IndexManagementResponse {
	/**
	 * Enum containing the options for managing course indexes.
	 */
	public enum Selected {
		CreateLesson, ListStudents, ListLessons
	};

	/**
	 * The selected object.
	 */
	private final Selected selected;

	/**
	 * The course index.
	 */
	private final String index;

	/**
	 * Sets the course index and the selected object.
	 * 
	 * @param index    The course index.
	 * @param selected The selected object.
	 */
	public IndexManagementResponse(String index, Selected selected) {
		this.index = index;
		this.selected = selected;
	}

	/**
	 * Returns the course index.
	 * 
	 * @return The course index.
	 */
	public String getIndex() {
		return index;
	}

	/**
	 * Returns the selected object.
	 * 
	 * @return The selected object.
	 */
	public Selected getSelected() {
		return selected;
	}
}
