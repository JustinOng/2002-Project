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
		Student.setStorage("student", storage);
		Course.setStorage("course", storage);
		Index.setStorage("index", storage);
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
		assertTrue(student.getTimetable().getIndexes().contains(Index.getIndex(1)), "student's timetable should contain student");
	}
}
