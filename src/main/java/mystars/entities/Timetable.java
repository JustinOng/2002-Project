package mystars.entities;

import java.util.ArrayList;

import mystars.exceptions.*;

public class Timetable {
	private ArrayList<Index> indexes = new ArrayList<Index>();

	public void addIndex(Index index) throws CourseAlreadyAddedException, IndexClashException {
		for (Index i : indexes) {
			if (i.belongsToSameCourse(index)) {
				throw new CourseAlreadyAddedException();
			}

			if (i.clashesWith(index)) {
				throw new IndexClashException();
			}
		}
		
		indexes.add(index);
	}

	public void removeIndex(int indexNo) {
		indexes.remove(indexNo);
	}
}
