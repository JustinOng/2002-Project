package mystars.entities;

import java.io.Serializable;

import mystars.enums.*;
import mystars.exceptions.AppException;

/**
 * This class represents a Lesson. Eg: A Tutorial held on Monday between
 * 0830-0930.
 */
public class Lesson implements Serializable {
	/**
	 * ID for versioning of serialized data.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * The index that the lesson belongs to.
	 */
	private Index index;

	/**
	 * The type of lesson for this instance (i.e. lecture/tutorial/lab).
	 */
	private LessonType lessonType;

	/**
	 * The day of the week the lesson is conducted.
	 */
	private Day day;

	/**
	 * The location where the lesson is conducted.
	 */
	private String location;

	/**
	 * The group number assigned to the particular lesson (applicable for tutorials
	 * and labs).
	 */
	private String groupNo;

	/**
	 * Specific weeks the lesson will be conducted. boolean[13] representing whether
	 * the lesson is held in that particular week eg week[0] == false signifies that
	 * this lesson is not held in week 1
	 */
	private boolean[] weeks;

	/**
	 * Starting time of the Lesson, from 0-31 where 0 represents 0800 and 31
	 * represents 2330 respectively. Must be smaller than {@code endPeriod} (ie the
	 * lesson must start before it can end)
	 */
	private int startPeriod;

	/**
	 * Ending time for the lesson. See description for {@code startPeriod}. Must be
	 * larger than {@code startPeriod}
	 */
	private int endPeriod;

	/**
	 * Creates a new Lesson.
	 * 
	 * @param index       The Lesson's index.
	 * @param lessonType  The Lesson's Type.
	 * @param day         The Lesson's day.
	 * @param groupNo     The Lesson's group number.
	 * @param location    The Lesson's location.
	 * @param weeks       The Lesson's week.
	 * @param startPeriod The Lesson's start period.
	 * @param endPeriod   The Lesson's end period.
	 * @throws AppException if invalid parameters are provided
	 */
	public Lesson(Index index, LessonType lessonType, Day day, String location, String groupNo, boolean[] weeks,
			int startPeriod, int endPeriod) throws AppException {

		if (groupNo.isBlank()) {
			throw new AppException("Group number cannot be blank");
		}

		if (location.isBlank()) {
			throw new AppException("Location cannot be blank");
		}

		if (weeks.length != 13) {
			throw new AppException("Weeks must have length 13");
		}

		this.index = index;
		this.lessonType = lessonType;
		this.day = day;
		this.location = location;
		this.groupNo = groupNo;
		this.weeks = weeks;
		this.startPeriod = startPeriod;
		this.endPeriod = endPeriod;
	}

	/**
	 * Check for clashes between the current instance and {@code lesson}.
	 * 
	 * @param lesson Another lesson to compare against.
	 * @return {@code true} If there are clashes, or {@code false} otherwise
	 */
	public boolean clashesWith(Lesson lesson) {
		// loop through every week.
		for (int week = 0; week < weeks.length; week++) {
			// compare both lessons' week
			if (this.weeks[week] && lesson.weeks[week]) {
				// if both lessons occur on the same week, compare the day.
				if (this.day == lesson.day) {
					// if both lessons occur on the same week & day, compare the period they are
					// conducted.
					if ((this.startPeriod >= lesson.startPeriod && this.startPeriod < lesson.endPeriod)
							|| (lesson.startPeriod >= this.startPeriod && lesson.startPeriod < this.endPeriod)) {
						// if both lessons occur on the same week & day & overlaps in period, return
						// true.
						return true;
					}
				}
			}
		}

		// if there is no conclusive clashes, return false.
		return false;
	}

	/**
	 * Retrieves the group number of this lesson.
	 * 
	 * @return The group number of this lesson.
	 */
	public String getGroupNo() {
		return groupNo;
	}

	/**
	 * Retrieves the Index that this lesson belongs to.
	 * 
	 * @return The Index that this lesson belongs to.
	 */
	public Index getIndex() {
		return index;
	}

	/**
	 * Retrieves the lesson type of this lesson.
	 * 
	 * @return The lesson type of this lesson.
	 */
	public LessonType getLessonType() {
		return lessonType;
	}

	/**
	 * Retrieves the location where this lesson will be held.
	 * 
	 * @return The location where this lesson will be held.
	 */
	public String getLocation() {
		return location;
	}

	/**
	 * Retrieves the day on which this lesson will be held
	 * 
	 * @return The day on which this lesson will be held
	 */
	public Day getDay() {
		return day;
	}

	/**
	 * Retrieves time of this lesson as 24h time string eg "0800-0930"
	 * 
	 * @return Time of this lesson as 24h time string
	 */
	public String getTimeString() {
		return periodToString(startPeriod) + "-" + periodToString(endPeriod);
	}

	/**
	 * Converts numerical period index into 24h time string, where 0 is 0800 and 31
	 * is 2330
	 * 
	 * @param i Period index
	 * @return 24h time string representation of {@code i}
	 */
	private String periodToString(int i) {
		int hour = 8 + (i / 2);
		int minute = i % 2 == 1 ? 30 : 0;
		return String.format("%02d:%02d", hour, minute);
	}
}
