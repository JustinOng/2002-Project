package mystars.entities;

import java.io.Serializable;

import mystars.enums.*;

/**
 * <h1>Class: Lesson</h1>
 *
 *
 * This lesson class defines the blueprint of a lesson property. It also checks
 * instances where two objects of a different lesson clashes.
 *
 */

public class Lesson implements Serializable {
	/**
	 * Serialization of the course ID
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
	 * The group number assigned to the particular lesson (applicable for tutorials & labs).
	 */
	private String groupNo;

	/**
	 * The specific weeks the lesson will be conducted.
	 */
	private boolean[] weeks;

	/**
	 * The commencement time for the lesson.
	 */
	private int startPeriod;

	/**
	 * The conclusion time for the lesson.
	 */
	private int endPeriod;

	/**
	 * Constructor for Lesson. This class is responsible for the creation of a lesson
	 * object.
	 * 
	 * @param index      	The Lesson's index.
	 * @param lessonType 	The Lesson's Type.
	 * @param day     		The Lesson's day.
	 * @param groupNo   	The Lesson's group number.
	 * @param location     	The Lesson's location.
	 * @param weeks    		The Lesson's week.
	 * @param startPeriod	The Lesson's start period.
	 * @param endPeriod	   	The Lesson's end period.
	 */
	public Lesson(Index index, LessonType lessonType, Day day, String groupNo, String location, boolean[] weeks,
			int startPeriod, int endPeriod) {
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
	 * Check for clashes between lessons
	 * 
	 * @return true if there are clashes and false if there are none
	 */
	public boolean clashesWith(Lesson l) {
		// loop through every week.
		for (int week = 0; week < weeks.length; week++) {
			// compare both lessons' week
			if (this.weeks[week] && l.weeks[week]) {
				// if both lessons occur on the same week, compare the day.
				if (this.day == l.day) {
					// if both lessons occur on the same week & day, compare the period they are conducted.
					if (
							(this.startPeriod >= l.startPeriod && this.startPeriod <= l.endPeriod) ||
							(l.startPeriod >= this.startPeriod && l.startPeriod <= this.endPeriod)
					) {
						// if both lessons occur on the same week & day & overlaps in period, return true.		
						return true;
					}
				}
			}
		}
		
		// if there is no conclusive clashes, return false.
		return false;
	}
}
