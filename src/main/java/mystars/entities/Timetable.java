package mystars.entities;

import java.io.Serializable;
import java.util.ArrayList;

import mystars.exceptions.AppException;

/**
 * <h1>Class: Timetable</h1>
 * 
 * This class implements the student's timetable.
 */
public class Timetable implements Serializable {
	/**
	 * Serialization of the course ID.
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * The arraylist storing the indexes of courses the student is registered for.
	 */
	private ArrayList<Index> indexes = new ArrayList<Index>();

	/**
	 * Add a course index to the student's registered course.
	 * 
	 * @param index
	 * @throws AppException
	 */
	public void addIndex(Index index) throws AppException {
		assertAddIndex(index);
		indexes.add(index);
	}

	/**
	 * Check if the index can be added to a student's registered course.
	 * 
	 * @param add 	The course index to be checked for adding.
	 * @return		Boolean value indicating if the course index was successfully added.
	 */
	public boolean canAddIndex(Index add) {
		return canAddIndex(add, null);
	}

	/**
	 * Check if a student's current course index can be swopped with another index of the same course.
	 * 
	 * @param add		The course index to be checked for adding.
	 * @param remove	The course index to be checked removing.
	 * @return			Boolean value indicating if the index swop was successful.
	 */
	public boolean canAddIndex(Index add, Index remove) {
		try {
			assertAddIndex(add, remove);
			return true;
		} catch (AppException e) {
			return false;
		}
	}
	
	/**
	 * Add a course index to the student's registered course.
	 * 
	 * @param add The course index to be added.
	 * @throws AppException
	 */
	public void assertAddIndex(Index add) throws AppException {
		assertAddIndex(add, null);
	}
	
	/**
	 * Swop a student's course index for another index in the same course.
	 * 
	 * @param add		The course index to be added.
	 * @param remove	The course index to be removed.
	 * @throws AppException
	 */
	public void assertAddIndex(Index add, Index remove) throws AppException {
		for (Index i : indexes) {
			if (i == remove) continue;
				
			if (i.belongsToSameCourse(add)) {
				throw new AppException(String.format("Already registered for %s in %s", i, i.getCourse()));
			}

			if (i.clashesWith(add)) {
				throw new AppException(String.format("Clashes with %s", i.toString()));
			}
		}
	}

	/**
	 * Remove an index from the student's registered course.
	 * 
	 * @param index The target course index to be removed.
	 */
	public void removeIndex(Index index) {
		indexes.remove(index);
	}

	/**
	 * Returns the indexes arraylist of the student's registered course indexes.
	 * 
	 * @return Arraylist of the student's registered course indexes.
	 */
	public ArrayList<Index> getIndexes() {
		return indexes;
	}

	/**
	 * Return an arraylist of the student's registered courses.
	 * 
	 * @return Arraylist of the student's registered courses.
	 */
	public ArrayList<Course> getCourses() {
		ArrayList<Course> courses = new ArrayList<Course>();
		for (Index i : indexes) {
			courses.add(i.getCourse());
		}
		return courses;
	}
}
