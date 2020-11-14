package mystars.entities;

import java.io.Serializable;
import java.util.ArrayList;

import mystars.exceptions.AppException;

/**
 * <h1>Class: Timetable</h1>
 *
 *
 * This timetable class constructs a timetable by adding and removing indexes 
 * after confirming that they can be added/removed.
 *
 */
public class Timetable implements Serializable {
	/**
	 * Serialization of Timetable ID
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * List of indexes
	 */
	private ArrayList<Index> indexes = new ArrayList<Index>();

	/**
	 * Adds index object to array of indexes if index is not yet registered or there are no clashes.
	 * 
	 * @param index The instance of the index object to be added to list of indexes
	 */
	public void addIndex(Index index) throws AppException {
		assertAddIndex(index);
		indexes.add(index);
	}

	/**
	 * Checks if index can be added.
	 * 
	 * @param add index to be added.
	 * @return true if index can be added, false if otherwise.
	 */
	public boolean canAddIndex(Index add) {
		return canAddIndex(add, null);
	}

	/**
	 * Checks if index can be added after comparing with a second index object
	 * 
	 * @param add index to be added.
	 * @param remove index to be compared with.
	 * @return true if index can be added, false if otherwise.
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
	 * Confirms addition of index to list of indexes if there is no index to compare with.
	 * 
	 * @param add index to be added.
	 */
	public void assertAddIndex(Index add) throws AppException {
		assertAddIndex(add, null);
	}
	
	/**
	 * Confirms addition of index to list of indexes after comparing with second index.
	 * 
	 * @param add index to be added.
	 * @param remove index to be compared with.
	 * @throws AppException If currently iterated index belongs to the same course as index to be added.
	 * @throws AppException If currently iterated index clashes with index to be added.
	 */
	public void assertAddIndex(Index add, Index remove) throws AppException {
		for (Index i : indexes) {
			// check if iteration matches remove
			if (i == remove) continue;
			// checks if current index belongs to the same course as add index.
			if (i.belongsToSameCourse(add)) {
				throw new AppException(String.format("Already registered for %s in %s", i, i.getCourse()));
			}
			// checks if current index clashes with add index
			if (i.clashesWith(add)) {
				throw new AppException(String.format("Clashes with %s", i.toString()));
			}
		}
	}

	/**
	 * Removes index from list of indexes.
	 * 
	 * @param remove index to be removed.
	 */
	public void removeIndex(Index index) {
		indexes.remove(index);
	}

	/**
	 * Gets list of index objects.
	 * 
	 * @return indexes This returns the array of indexes.
	 */
	public ArrayList<Index> getIndexes() {
		return indexes;
	}

	/**
	 * Adds indexs to list of course objects and returns it.
	 * 
	 * @return courses This returns the array of courses.
	 */
	public ArrayList<Course> getCourses() {
		ArrayList<Course> courses = new ArrayList<Course>();
		for (Index i : indexes) {
			courses.add(i.getCourse());
		}

		return courses;
	}
}
