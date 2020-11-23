package mystars.tests;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import mystars.entities.*;
import mystars.enums.School;
import mystars.exceptions.AppException;

public class TimetableTest {
	@Test
	void test_timetable() throws AppException {
		Timetable timetable = new Timetable();
		timetable.setMaxAu(21);

		FileStorage storage = new FileStorage("");
		Entity.setStorage(storage);

		Course c1 = new Course("c1", "c1", School.CSE, 20);
		Index i1 = c1.createIndex(1, 1);
		timetable.addIndex(i1);

		Course c2 = new Course("c2", "c2", School.CSE, 1);
		Index i2 = c2.createIndex(2, 1);
		timetable.addIndex(i2);

		Course c3 = new Course("c3", "c3", School.CSE, 1);
		Index i3 = c3.createIndex(3, 1);

		assertFalse(timetable.canAddIndex(i3), "cannot add i3 because exceed max AUs");
		assertTrue(timetable.canAddIndex(i3, i1), "should be able to add i3 after removing i1");
		assertThrows(AppException.class, () -> timetable.addIndex(i3));

		timetable.removeIndex(i1);

		timetable.addIndex(i3);
	}
}
