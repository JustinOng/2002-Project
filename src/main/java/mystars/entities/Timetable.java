package mystars.entities;

import java.io.Serializable;
import java.util.ArrayList;

import mystars.exceptions.AppException;

/**
 * <h1>Class: Timetable</h1>
 * 
 * This Timetable class implements the student's timetable.
 */
public class Timetable implements Serializable {
	/**
	 * ID for versioning of serialized data.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * List of indexes added to this timetable
	 */
	private ArrayList<Index> indexes = new ArrayList<Index>();

	/**
	 * Adds an index to this timetable
	 * 
	 * @param index The index to be added.
	 * @throws AppException if the index clashes with any already added index
	 */
	public void addIndex(Index index) throws AppException {
		assertAddIndex(index);
		indexes.add(index);
	}

	/**
	 * Check if {@code add} can be added to this timetable without any clash
	 * 
	 * @param add Index to be added
	 * @return {@code true} if {@code add} can be added without any clash
	 * @return {@code false} otherwise
	 */
	public boolean canAddIndex(Index add) {
		return canAddIndex(add, null);
	}

	/**
	 * Check if {@code add} can be added to this timetable without any clash after
	 * removing {@code remove}
	 * 
	 * @param add    Index to be added
	 * @param remove Index to be removed
	 * @return {@code true} if {@code add} can be added without any clash
	 * @return {@code false} otherwise
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
	 * Check if {@code add} can be added to this timetable without any clash
	 * 
	 * @param add Index to be added
	 * @throws AppException if there will be a clash
	 */
	public void assertAddIndex(Index add) throws AppException {
		assertAddIndex(add, null);
	}

	/**
	 * Check if {@code add} can be added to this timetable without any clash after
	 * removing {@code remove}
	 * 
	 * @param add    Index to be added
	 * @param remove Index to be removed
	 * @throws AppException if another index of the same course of {@code add} has
	 *                      already been added
	 * @throws AppException if adding {@code add} would result in a clash with
	 *                      another index
	 */
	public void assertAddIndex(Index add, Index remove) throws AppException {
		for (Index i : indexes) {
			if (i == remove)
				continue;

			if (i.belongsToSameCourse(add)) {
				throw new AppException(String.format("Already registered for %s in %s", i, i.getCourse()));
			}

			if (i.clashesWith(add)) {
				throw new AppException(String.format("Clashes with %s", i.toString()));
			}
		}
	}

	/**
	 * Remove an index from this timetable
	 * 
	 * @param index Index to be removed.
	 */
	public void removeIndex(Index index) {
		indexes.remove(index);
	}

	/**
	 * Returns a list of indexes added to this timetable
	 * 
	 * @return a list of indexes added to this timetable
	 */
	public ArrayList<Index> getIndexes() {
		return indexes;
	}

	/**
	 * Returns a list of courses of indexes added to this timetable
	 * 
	 * @return a list of courses of indexes added to this timetable
	 */
	public ArrayList<Course> getCourses() {
		ArrayList<Course> courses = new ArrayList<Course>();
		for (Index i : indexes) {
			courses.add(i.getCourse());
		}
		return courses;
	}
}
