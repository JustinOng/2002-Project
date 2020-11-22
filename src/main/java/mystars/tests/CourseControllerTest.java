package mystars.tests;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import mystars.entities.*;
import mystars.enums.*;
import mystars.controllers.CourseController;
import mystars.exceptions.AppException;

/**
 * <h1>Class: CourseControllerTest</h1>
 * 
 * This class performs the required testing and validation to ensure that the
 * respective controllers are able to perform their required functionality
 * correctly.
 */
class CourseControllerTest {
	/**
	 * Create a new file storage object.
	 */
	private FileStorage storage = new FileStorage("");

	/**
	 * Create a new course controller object.
	 */
	private CourseController controller = new CourseController();

	/**
	 * Create a new course controller and storage object.
	 * 
	 * @throws AppException Shows error message if the initialization fails.
	 */
	CourseControllerTest() throws AppException {
		Entity.setStorage(storage);
		controller.start();
	}

	@BeforeEach
	/**
	 * Before each test, this method wipes the storage object since no valid file
	 * was found.
	 * 
	 * @throws Exception Shows error message if the file loading fails.
	 */
	void setUp() throws Exception {
		storage.loadFromDisk();
	}

	@Test
	/**
	 * Test for the creation of a new course in a selected school.
	 * 
	 * @throws AppException Shows error message if any of the tests fail.
	 */
	void test_creation() throws AppException {
		String courseCode = "CZ0001";

		// Course controller creates the course.
		controller.createCourse("course name", courseCode, School.CSE, 1);
		Course.getCourse(courseCode);
	}

	@Test
	/**
	 * Test for the registration of a course by a student.
	 * 
	 * @throws AppException Shows error message if any of the tests fail.
	 */
	void test_registration() throws AppException {
		// Create a new student.
		Student student = new Student("name", "email@example.com", "matric no", "user", "password", Gender.Male,
				Nationality.Singaporean);

		// Course controller creates the course.
		controller.createCourse("course name", "CZ0001", School.CSE, 1);

		// Exception handling if student tries to register for course that does not
		// exist.
		assertThrows(AppException.class, () -> controller.registerCourse(student, 0),
				"registering for non existent index");

		// Create a new index for the course and register the new student for it.
		controller.createIndex("CZ0001", 1, 1);
		controller.registerCourse(student, 1);

		// Check if the following conditions are true, and throw an assertion error
		// otherwise.
		assertTrue(Index.getIndex(1).hasStudent(student), "index enrolled should contain student");
		assertTrue(student.getTimetable().getIndexes().contains(Index.getIndex(1)),
				"student's timetable should contain index 1");
	}

	@Test
	/**
	 * Test for the dropping of a course by a student.
	 * 
	 * @throws AppException Shows error message if any of the tests fail.
	 */
	void test_dropping() throws AppException {
		Student student = new Student("name", "email@example.com", "matric no", "user", "password", Gender.Male, Nationality.Singaporean);
		controller.createCourse("course name", "CZ0001", School.CSE, 1);

		// Create the new course index and register the student for it, before dropping
		// the student from that index.
		controller.createIndex("CZ0001", 1, 1);
		controller.registerCourse(student, 1);
		controller.dropCourse(student, "CZ0001");

		// Check if the following conditions are false, and throw an assertion error
		// otherwise.
		assertFalse(Index.getIndex(1).hasStudent(student), "index should no longer contain student");
		assertFalse(student.getTimetable().getIndexes().contains(Index.getIndex(1)),
				"student's timetable should no longer contain index 1");
	}

	@Test
	/**
	 * Test for the swopping of an index by a student with another student.
	 * 
	 * @throws AppException Shows error message if any of the tests fail.
	 */
	void test_swop() throws AppException {
		// Create two new students to swop index with each other.
		Student studentA = new Student("name", "email@example.com", "matric no", "userA", "password", Gender.Male,
				Nationality.Singaporean);
		Student studentB = new Student("name", "email@example.com", "matric no", "userB", "password", Gender.Male,
				Nationality.Singaporean);

		// Create the course with two separate indexes.
		controller.createCourse("course name", "CZ0001", School.CSE, 1);
		controller.createIndex("CZ0001", 1, 1);
		controller.createIndex("CZ0001", 2, 1);

		// Exception handling if the student tries to swop with themself.
		assertThrows(AppException.class, () -> controller.swopIndex(studentA, 0, studentA, 0), "swopping with self");

		// Exception handling if student does not specify the index they want to swop.
		assertThrows(AppException.class, () -> controller.swopIndex(studentA, 2, studentB, 1),
				"students must have the index they want to swop");

		// Register the students for their respective courses and swop them.
		controller.registerCourse(studentA, 1);
		controller.registerCourse(studentB, 2);
		controller.swopIndex(studentA, 1, studentB, 2);

		assertTrue(Index.getIndex(1).hasStudent(studentB), "indexA should contain studentB");
		assertTrue(Index.getIndex(2).hasStudent(studentA), "indexB should contain studentA");

		assertTrue(studentA.getTimetable().getIndexes().contains(Index.getIndex(2)),
				"studentA should have indexB in their timetable");
		assertTrue(studentB.getTimetable().getIndexes().contains(Index.getIndex(1)),
				"studentB should have indexA in their timetable");
	}

	@Test
	/**
	 * Test for the successful assigning of a student to the index if there are
	 * vaccancies. Test for the placement of a student onto the waitlist if there
	 * are no vaccancies.
	 * 
	 * @throws AppException Shows error message if any of the tests fail.
	 */
	void test_waitlist() throws AppException {
		// Create two new students. One to be successfully allocated an index, with the
		// other put on a waitlist.
		Student studentA = new Student("name", "email@example.com", "matric no", "userA", "password", Gender.Male,
				Nationality.Singaporean);
		Student studentB = new Student("name", "email@example.com", "matric no", "userB", "password", Gender.Male,
				Nationality.Singaporean);

		// Create the course index.
		controller.createCourse("course name", "CZ0001", School.CSE, 1);
		controller.createIndex("CZ0001", 1, 1);

		assertTrue(controller.registerCourse(studentA, 1), "student A should have registered successfully");
		assertFalse(controller.registerCourse(studentB, 1), "student B should have been placed on the waitlist");

		assertFalse(Index.getIndex(1).hasStudent(studentB), "student B should not be enrolled");
		List<Registration> registrations = studentB.getTimetable().getRegistrations();

		// Check if the values are equal to within a positive delta range, and throw an
		// assertion error otherwise.
		assertEquals(registrations.size(), 1, "student B should be registered for only one index");
		assertTrue(registrations.get(0).getStatus() == Registration.Status.Waitlist,
				"student B should be on the waitlist for index 1");

		// Student drops the course.
		controller.dropCourse(studentA, "CZ0001");

		assertTrue(Index.getIndex(1).hasStudent(studentB), "student B should be registered");
		registrations = studentB.getTimetable().getRegistrations();

		assertEquals(registrations.size(), 1, "student B should be registered for only one index");
		assertTrue(registrations.get(0).getStatus() == Registration.Status.Registered,
				"student B should be registered for index 1");
	}
}
