package mystars.tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import mystars.entities.*;
import mystars.enums.*;
import mystars.controllers.CourseController;
import mystars.exceptions.AppException;

class CourseControllerTest {
	private FileStorage storage = new FileStorage("");
	private CourseController controller = new CourseController();

	CourseControllerTest() throws AppException {
		Entity.setStorage(storage);
	}

	@BeforeEach
	void setUp() throws Exception {
		// this wipes the storage since no valid file found
		storage.loadFromDisk();
	}

	@Test
	void test_creation() throws AppException {
		String courseCode = "CZ0001";
		controller.createCourse("course name", courseCode, School.CSE);
		Course.getCourse(courseCode);
	}

	@Test
	void test_registration() throws AppException {
		Student student = new Student("name", "matric no", "user", "password", Gender.Male, Nationality.Singaporean);
		controller.createCourse("course name", "CZ0001", School.CSE);

		assertThrows(AppException.class, () -> controller.registerCourse(student, "CE0001", 0),
				"registering for non existent index");

		controller.createIndex("CZ0001", 1, 1);
		controller.registerCourse(student, 1);

		assertTrue(Index.getIndex(1).hasStudent(student), "index enrolled should contain student");
		assertTrue(student.getTimetable().getIndexes().contains(Index.getIndex(1)),
				"student's timetable should contain student");
	}

	@Test
	void test_swop() throws AppException {
		Student studentA = new Student("name", "matric no", "userA", "password", Gender.Male, Nationality.Singaporean);
		Student studentB = new Student("name", "matric no", "userB", "password", Gender.Male, Nationality.Singaporean);

		controller.createCourse("course name", "CZ0001", School.CSE);
		controller.createIndex("CZ0001", 1, 1);
		controller.createIndex("CZ0001", 2, 1);

		assertThrows(AppException.class, () -> controller.swopIndex(studentA, 0, studentA, 0), "swopping with self");
		assertThrows(AppException.class, () -> controller.swopIndex(studentA, 2, studentB, 1),
				"students must have the index they want to swop");

		controller.registerCourse(studentA, "CZ0001", 1);
		controller.registerCourse(studentB, "CZ0001", 2);

		controller.swopIndex(studentA, 1, studentB, 2);

		assertTrue(Index.getIndex(1).hasStudent(studentB), "indexA should contain studentB");
		assertTrue(Index.getIndex(2).hasStudent(studentA), "indexB should contain studentA");

		assertTrue(studentA.getTimetable().getIndexes().contains(Index.getIndex(2)),
				"studentA should have indexB in their timetable");
		assertTrue(studentB.getTimetable().getIndexes().contains(Index.getIndex(1)),
				"studentB should have indexA in their timetable");
	}
}
