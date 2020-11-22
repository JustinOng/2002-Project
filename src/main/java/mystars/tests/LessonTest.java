package mystars.tests;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import mystars.entities.*;
import mystars.enums.Day;
import mystars.enums.LessonType;
import mystars.exceptions.AppException;

public class LessonTest {
	@Test
	void test_clash() throws AppException {
		Lesson base = new Lesson(null, LessonType.Lecture, Day.Monday, "TR+1", "AAA",
				new boolean[] { true, true, true, true, true, true, true, true, true, true, true, true, true }, 2, 4);

		Lesson c1 = new Lesson(null, LessonType.Lecture, Day.Monday, "TR+1", "AAA",
				new boolean[] { true, true, true, true, true, true, true, true, true, true, true, true, true }, 2, 4);
		
		assertTrue(base.clashesWith(c1), "both occupy the same timeslot");
		
		Lesson c2 = new Lesson(null, LessonType.Lecture, Day.Monday, "TR+1", "AAA",
				new boolean[] { true, true, true, true, true, true, true, true, true, true, true, true, true }, 3, 4);
		
		assertTrue(base.clashesWith(c2), "c2 starts in the middle of base");
		
		Lesson c3 = new Lesson(null, LessonType.Lecture, Day.Monday, "TR+1", "AAA",
				new boolean[] { true, true, true, true, true, true, true, true, true, true, true, true, true }, 1, 3);
		
		assertTrue(base.clashesWith(c3), "base starts in the middle of c3");
		
		Lesson c4 = new Lesson(null, LessonType.Lecture, Day.Monday, "TR+1", "AAA",
				new boolean[] { true, true, true, true, true, true, true, true, true, true, true, true, true }, 4, 6);
		
		assertFalse(base.clashesWith(c4), "c4 starts immediately after base");
		
		Lesson c5 = new Lesson(null, LessonType.Lecture, Day.Monday, "TR+1", "AAA",
				new boolean[] { true, true, true, true, true, true, true, true, true, true, true, true, true }, 1, 2);

		assertFalse(base.clashesWith(c4), "base starts immediately after c5");
	}
}
