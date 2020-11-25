package mystars.forms;

/**
 * This class manages the form for the creation of course index lessons.
 */
public class CreateLessonResponse {
	/**
	 * The lesson type: Lecture, Tutorial or Lab.
	 */
	private final String lessonType;

	/**
	 * The day of the week the lesson is held on.
	 */
	private final String day;

	/**
	 * The location that the lesson is held at.
	 */
	private final String location;

	/**
	 * The group number of the lesson the student is assigned to.
	 */
	private final String groupNo;

	/**
	 * The weeks of the semester when the lesson will be held.
	 */
	private final boolean[] weeks;

	/**
	 * The starting time of the lesson.
	 */
	private final int startPeriod;

	/**
	 * The ending time of the lesson.
	 */
	private final int endPeriod;

	/**
	 * Sets the parameters for the course index lesson.
	 * 
	 * @param lessonType  The lesson type: Lecture, Tutorial or Lab.
	 * @param day         The day of the week the lesson is held on.
	 * @param location    The location that the lesson is held at.
	 * @param groupNo     The group number of the lesson the student is assigned to.
	 * @param weeks       The weeks of the semester when the lesson will be held.
	 * @param startPeriod The starting time of the lesson.
	 * @param endPeriod   The ending time of the lesson.
	 */
	public CreateLessonResponse(String lessonType, String day, String location, String groupNo, boolean[] weeks,
			int startPeriod, int endPeriod) {
		this.lessonType = lessonType;
		this.day = day;
		this.location = location;
		this.groupNo = groupNo;
		this.weeks = weeks;
		this.startPeriod = startPeriod;
		this.endPeriod = endPeriod;
	}

	/**
	 * Returns the lesson type: Lecture, Tutorial or Lab.
	 * 
	 * @return The lesson type: Lecture, Tutorial or Lab.
	 */
	public String getLessonType() {
		return lessonType;
	}

	/**
	 * Returns the day of the week the lesson is held on.
	 * 
	 * @return Theday of the week the lesson is held on.
	 */
	public String getDay() {
		return day;
	}

	/**
	 * Returns the location that the lesson is held at.
	 * 
	 * @return The location that the lesson is held at.
	 */
	public String getLocation() {
		return location;
	}

	/**
	 * Returns the group number of the lesson the student is assigned to.
	 * 
	 * @return The group number of the lesson the student is assigned to.
	 */
	public String getGroupNo() {
		return groupNo;
	}

	/**
	 * Returns the weeks of the semester when the lesson will be held.
	 * 
	 * @return The weeks of the semester when the lesson will be held.
	 */
	public boolean[] getWeeks() {
		return weeks;
	}

	/**
	 * Returns the starting time of the lesson.
	 * 
	 * @return The starting time of the lesson.
	 */
	public int getStartPeriod() {
		return startPeriod;
	}

	/**
	 * Returns the ending time of the lesson.
	 * 
	 * @return The ending time of the lesson.
	 */
	public int getEndPeriod() {
		return endPeriod;
	}
}
