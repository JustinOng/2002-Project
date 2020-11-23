package mystars.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import mystars.exceptions.AppException;

/**
 * <h1>Class: Timetable</h1>
 * 
 * This class implements the student's timetable.
 */
public class Timetable implements Serializable {
	/**
	 * ID for versioning of serialized data.
	 */
	private static final long serialVersionUID = 2L;
	
	private static int maxAu = Integer.MAX_VALUE;
	private int au = 0;

	/**
	 * List of indexes added to this timetable. Registration is used to hold an
	 * extra "status" property to signify whether the user is on the waitlist or has
	 * directly registered
	 */
	private List<Registration> registrations = new ArrayList<>();

	/**
	 * Adds an index to this timetable with status Registered
	 * 
	 * @param index The index to be added.
	 * @throws AppException If the index clashes with any already added index
	 */
	public void addIndex(Index index) throws AppException {
		addIndex(index, false);
	}

	/**
	 * Adds an index to this timetable with status controlled by the
	 * {@code waitlist} parameter.
	 * 
	 * @param index    Index to be added.
	 * @param waitlist {@code true} if index should be added with status waitlist,
	 *                 {@code false} otherwise.
	 * @throws AppException If the index clashes with any already added index.
	 */
	public void addIndex(Index index, boolean waitlist) throws AppException {
		assertAddIndex(index);
		if (waitlist) {
			registrations.add(new Registration(index, Registration.Status.Waitlist));
		} else {
			registrations.add(new Registration(index, Registration.Status.Registered));
			au += index.getCourse().getAu();
		}
	}

	/**
	 * Check if {@code add} can be added to this timetable without any clash.
	 * 
	 * @param add The index to be added.
	 * @return {@code true} if {@code add} can be added without any clash, or
	 *         {@code false} otherwise.
	 */
	public boolean canAddIndex(Index add) {
		return canAddIndex(add, null);
	}

	/**
	 * Check if {@code add} can be added to this timetable without any clash after
	 * removing {@code remove}.
	 * 
	 * @param add    The index to be added.
	 * @param remove The index to be removed.
	 * @return {@code true} if {@code add} can be added without any clash, or
	 *         {@code false} otherwise.
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
	 * Check if {@code add} can be added to this timetable without any clash.
	 * 
	 * @param add The index to be added.
	 * @throws AppException If there will be a clash.
	 */
	public void assertAddIndex(Index add) throws AppException {
		assertAddIndex(add, null);
	}

	/**
	 * Check if {@code add} can be added to this timetable without any clash after
	 * removing {@code remove}.
	 * 
	 * @param add    The index to be added.
	 * @param remove The index to be removed. Can be {@code null}
	 * @throws AppException If another index of the same course of {@code add} has
	 *                      already been added.
	 * @throws AppException If adding {@code add} would result in a clash with
	 *                      another index.
	 */
	public void assertAddIndex(Index add, Index remove) throws AppException {
		int newAu = au + add.getCourse().getAu();
		
		if (remove != null) {
			newAu -= remove.getCourse().getAu();
		}
		
		if (newAu > maxAu) {
			throw new AppException(String.format("You can only register for %d AU of courses", maxAu));
		}
		
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
	 * Remove an index from this timetable.
	 * 
	 * @param index The index to be removed..
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
		au -= remove.getIndex().getCourse().getAu();
	}

	/**
	 * Returns the list of indexes added to this timetable.
	 * 
	 * @return The list of indexes added to this timetable.
	 */
	public List<Index> getIndexes() {
		List<Index> indexes = new ArrayList<>();
		for (Registration reg : registrations) {
			indexes.add(reg.getIndex());
		}
		return indexes;
	}

	/**
	 * Returns the list of courses of indexes added to this timetable
	 * 
	 * @return The list of courses of indexes added to this timetable
	 */
	public List<Course> getCourses() {
		List<Course> courses = new ArrayList<Course>();
		for (Registration reg : registrations) {
			courses.add(reg.getIndex().getCourse());
		}
		return courses;
	}

	/**
	 * Returns the list of Registrations (Index + Status) belonging to this
	 * timetable
	 * 
	 * @return The list of Registrations (Index + Status) belonging to this
	 *         timetable
	 */
	public List<Registration> getRegistrations() {
		return registrations;
	}

	/**
	 * Marks the provided index as Registered. Used to indicate that a student has
	 * been allocated an Index after being on the waitlist.
	 * 
	 * @param index The index to mark as Registered.
	 */
	public void setRegistered(Index index) {
		for (Registration reg : registrations) {
			if (reg.getIndex() == index) {
				reg.setRegistered();
				au += index.getCourse().getAu();
				return;
			}
		}
	}
	
	/**
	 * Set the maximum number of AUs a timetable can contain
	 * @param max Maximum number of AUs a timetable can contain
	 */
	public static void setMaxAu(int max) {
		maxAu = max;
	}
}
