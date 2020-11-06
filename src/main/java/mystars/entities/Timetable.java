package mystars.entities;

import java.util.ArrayList;

import mystars.exceptions.AppException;

public class Timetable {
	private ArrayList<Index> indexes = new ArrayList<Index>();

	public void addIndex(Index index) throws AppException {
		assertAddIndex(index);
		indexes.add(index);
	}

	public boolean canAddIndex(Index add) {
		return canAddIndex(add, null);
	}

	public boolean canAddIndex(Index add, Index remove) {
		try {
			assertAddIndex(add, remove);
			return true;
		} catch (AppException e) {
			return false;
		}
	}
	
	public void assertAddIndex(Index add) throws AppException {
		assertAddIndex(add, null);
	}
	
	public void assertAddIndex(Index add, Index remove) throws AppException {
		for (Index i : indexes) {
			if (i == remove) continue;
				
			if (i.belongsToSameCourse(add)) {
				throw new AppException(String.format("Already registered for %s in %s", i, i.getCourse()));
			}

			if (i.belongsToSameCourse(add)) {
				throw new AppException(String.format("Clashes with %s", i.toString()));
			}
		}
	}

	public void removeIndex(Index index) {
		indexes.remove(index);
	}

	public ArrayList<Index> getIndexes() {
		return indexes;
	}

	public ArrayList<Course> getCourses() {
		ArrayList<Course> courses = new ArrayList<Course>();
		for (Index i : indexes) {
			courses.add(i.getCourse());
		}

		return courses;
	}
}
