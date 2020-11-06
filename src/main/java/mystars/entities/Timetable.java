package mystars.entities;

import java.util.ArrayList;

import mystars.exceptions.AppException;

public class Timetable {
	private ArrayList<Index> indexes = new ArrayList<Index>();

	public void addIndex(Index index) throws AppException {
		for (Index i : indexes) {
			if (i.belongsToSameCourse(index)) {
				throw new AppException(String.format("Already registered for %s in %s", i, index.getCourse()));
			}

			if (i.clashesWith(index)) {
				throw new AppException(String.format("Clashes with %s", i.toString()));
			}
		}

		indexes.add(index);
	}

	public void removeIndex(int indexNo) {
		indexes.remove(indexNo);
	}

	public ArrayList<Course> getCourses() {
		ArrayList<Course> courses = new ArrayList<Course>();
		for (Index i : indexes) {
			courses.add(i.getCourse());
		}

		return courses;
	}
}
