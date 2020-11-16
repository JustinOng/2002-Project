package mystars.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

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
	 * List of indexes added to this timetable. Registration is used to hold an
	 * extra "status" property to signify whether the user is on the waitlist or has
	 * directly registered
	 */
	private List<Registration> registrations = new ArrayList<>();

	/**
	 * Adds an index to this timetable with status Registered
	 * 
	 * @param index Index to be added.
	 * @throws AppException if the index clashes with any already added index
	 */
	public void addIndex(Index index) throws AppException {
		addIndex(index, false);
	}

	/**
	 * Adds an index to this timetable with status controlled by the
	 * {@code waitlist} parameter
	 * 
	 * @param index    Index to be added
	 * @param waitlist {@code true} if index should be added with status waitlist,
	 *                 {@code false} otherwise
	 * @throws AppException if the index clashes with any already added index
	 */
	public void addIndex(Index index, boolean waitlist) throws AppException {
		assertAddIndex(index);
		if (waitlist) {
			registrations.add(new Registration(index, Registration.Status.Waitlist));
		} else {
			registrations.add(new Registration(index, Registration.Status.Registered));
		}
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
		for (Registration reg : registrations) {
			Index i = reg.getIndex();
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
		Registration remove = null;
		for (Registration reg : registrations) {
			if (reg.getIndex() == index) {
				remove = reg;
				break;
			}
		}

		if (remove == null) {
			return;
		}

		registrations.remove(remove);
	}

	/**
	 * Returns a list of indexes added to this timetable
	 * 
	 * @return a list of indexes added to this timetable
	 */
	public List<Index> getIndexes() {
		List<Index> indexes = new ArrayList<>();
		for (Registration reg : registrations) {
			indexes.add(reg.getIndex());
		}

		return indexes;
	}

	/**
	 * Returns a list of courses of indexes added to this timetable
	 * 
	 * @return a list of courses of indexes added to this timetable
	 */
	public List<Course> getCourses() {
		List<Course> courses = new ArrayList<Course>();
		for (Registration reg : registrations) {
			courses.add(reg.getIndex().getCourse());
		}
		return courses;
	}

	/**
	 * Returns a list of Registrations (Index + Status) belonging to this timetable
	 * 
	 * @return a list of Registrations (Index + Status) belonging to this timetable
	 */
	public List<Registration> getRegistrations() {
		return registrations;
	}

	/**
	 * Marks the provided index as Registered. Used to indicate that a student has
	 * been allocated an Index after being on the waitlist
	 * 
	 * @param index Index to mark as Registered
	 */
	public void setRegistered(Index index) {
		for (Registration reg : registrations) {
			if (reg.getIndex() == index) {
				reg.setRegistered();
				return;
			}
		}
	}
}
