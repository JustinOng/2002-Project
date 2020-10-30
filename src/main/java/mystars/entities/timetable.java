package mystars.entities;
/**
 * <h1>Class: timetable</h1>
 * 
 * This timetable class stores an array of indexes for a student's registered courses.
 * 
 * @param indexes An arraylist of string elements corresponding to the courses that
 * a student has registered for. 
 */

import java.util.ArrayList;

public class timetable
{
	//An arraylist stores a dynamicaly sized collection of elements.
	//It can resize to accomodate new elements or shrink when elements are removed.
	private ArrayList<String> indexes = new ArrayList<String>();
	
	public timetable()
	{
		
	}
	
	/**
	 * This method adds a course and index to a student's timetable.
	 * 
	 * @param course The enum of the course to be added to the student's timetable.
	 * @param indexNo The index number of the course to be added to the student's timetable.
	 */
	public void addIndex(Course course, int indexNo)
	{
		indexes.add(indexNo, course);
	}
	
	/**
	 * This method removes a course's index from the student's timetable.
	 * 
	 * @param indexNo The index number of the course to be removed from the student's timetable.
	 */
	public void removeIndex(int indexNo)
	{
		indexes.remove(indexNo);
	}
}
