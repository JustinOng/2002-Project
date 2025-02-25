package mystars.controllers;

import mystars.enums.*;

import java.util.ArrayList;
import java.util.List;

import mystars.entities.*;
import mystars.exceptions.AppException;

/**
 * This courseController class manages the creation of course, course index and
 * lesson. It also manages the registration of course, dropping of course and
 * swopping of index.
 */

public class CourseController {
	/**
	 * Initializes the course controller object.
	 */
	public void start() {
		Index.registerObserver((Index index, Student student) -> {
			student.getTimetable().setRegistered(index);
		}, Index.Event.AllocatedWaitlist);
	}

	/**
	 * Creates a new course.
	 * 
	 * @param name       The course's name.
	 * @param courseCode The course's code.
	 * @param school     The course's school.
	 * @param au         The number of AUs this course is worth.
	 * @throws AppException if another course identified by {@code courseCode}
	 *                      already exists
	 * @throws AppException if invalid parameters are passed to the constructor of
	 *                      Course
	 */
	public void createCourse(String name, String courseCode, School school, int au) throws AppException {
		Course course = new Course(name, courseCode, school, au);
		course.markPersistent();
	}

	/**
	 * Creates a new index for the course identified by {@code courseCode}.
	 * 
	 * @param courseCode  Identifier of Course
	 * @param indexNo     Index number of the new index
	 * @param maxEnrolled The maximum number of students enrolled in the new index
	 * @throws AppException if no course identified by {@code courseCode} is found
	 * @throws AppException if invalid parameters are passed to the constructor of
	 *                      Index
	 */
	public void createIndex(String courseCode, int indexNo, int maxEnrolled) throws AppException {
		Course c = Course.getCourse(courseCode);
		c.createIndex(indexNo, maxEnrolled);
	}

	/**
	 * Returns a list of all courses
	 * 
	 * @return list of all courses
	 */
	public List<Course> getAllCourses() {
		return Course.getAllCourses();
	}

	/**
	 * Returns a list of all indexes belonging to the course identified by
	 * {@code courseCode}
	 * 
	 * @param courseCode Identifier of Course
	 * @return list of indexes
	 * @throws AppException if no course identified by {@code courseCode} is found
	 */
	public List<Index> getAllIndexes(String courseCode) throws AppException {
		Course course = Course.getCourse(courseCode);
		return course.getIndexes();
	}

	/**
	 * Retrieves Index given indexNo
	 * 
	 * @param indexNo Index number of the index to retrieve
	 * @return Index
	 * @throws AppException If no index identified by {@code indexNo} is found
	 */
	public Index getIndex(int indexNo) throws AppException {
		return Index.getIndex(indexNo);
	}

	/**
	 * Creates a new lesson on the index and course identified by {@code indexNo}
	 * and {@code courseCode} respectively
	 * 
	 * @param courseCode  Identifier of Course
	 * @param indexNo     Identifier of Index
	 * @param lessonType  Lesson type of the new lesson
	 * @param day         Day that the new Lesson is held on
	 * @param location    Location where the Lesson is held
	 * @param groupNo     Group number of the lesson eg SEP1
	 * @param week        boolean[13] representing whether the lesson is held in
	 *                    that particular week eg week[0] == false signifies that
	 *                    this lesson is not held in week 1
	 * @param startPeriod Start period of the Lesson, from 0-31 where 0 represents
	 *                    0800 and 31 represents 2330 respectively. Must be smaller
	 *                    than {@code endPeriod} (ie the lesson must start before it
	 *                    can end)
	 * @param endPeriod   End period of the Lesson, see description in
	 *                    {@code startPeriod}. Must be greater than
	 *                    {@code startPeriod}.
	 * @throws AppException if no course identified by {@code courseCode} is found
	 * @throws AppException if no index identified by {@code indexNo} is found
	 * @throws AppException if invalid parameters are passed to the constructor of
	 *                      Lesson
	 */
	public void createLesson(String courseCode, int indexNo, LessonType lessonType, Day day, String location,
			String groupNo, boolean[] week, int startPeriod, int endPeriod) throws AppException {
		Course c = Course.getCourse(courseCode);
		c.createLesson(indexNo, lessonType, day, location, groupNo, week, startPeriod, endPeriod);
	}

	/**
	 * Register a student for a particular index
	 * 
	 * @param student Student to register
	 * @param indexNo Identifier of Index
	 * @return {@code true} if student was successfully registered, or {@code false}
	 *         if student was placed on the waitlist
	 * @throws AppException if no index identified by {@code indexNo} is found
	 * @throws AppException if {@code student} has already been registered for the
	 *                      index identified by {@code indexNo}
	 * @throws AppException if the index identified by {@code indexNo} clashes with
	 *                      other indexes that {@code student} is already registered
	 *                      for
	 */
	public boolean registerCourse(Student student, int indexNo) throws AppException {
		Index index = Index.getIndex(indexNo);
		student.getTimetable().assertAddIndex(index);

		boolean registered = index.getCourse().register(student, indexNo);
		student.getTimetable().addIndex(index, !registered);

		return registered;
	}

	/**
	 * Drop a student from a particular course
	 * 
	 * @param student    Student to drop
	 * @param courseCode Identifier of Course
	 * @throws AppException if no course identified by {@code courseCode} is found
	 * @throws AppException If {@code student} is not registered for the course
	 *                      identified by {@code courseCode}
	 * 
	 */
	public void dropCourse(Student student, String courseCode) throws AppException {
		Course course = Course.getCourse(courseCode);
		Index index = course.drop(student);
		student.getTimetable().removeIndex(index);
	}

	/**
	 * Asserts that {@code student} can change index to {@code targetIndexNo}
	 * 
	 * @param courseCode    Identifier of Course
	 * @param student       Student to change index
	 * @param targetIndexNo The index number that the student wants to change to
	 * @throws AppException if invalid parameters are passed, see
	 *                      {@link Course#assertChangeIndex(Student, int)}
	 * @throws AppException if changing indexes will result in a timetable clash
	 */
	public void assertChangeIndex(String courseCode, Student student, int targetIndexNo) throws AppException {
		Course course = Course.getCourse(courseCode);
		Index curIndex = course.getStudentIndex(student);
		Index newIndex = Index.getIndex(targetIndexNo);

		course.assertChangeIndex(student, targetIndexNo);

		student.getTimetable().assertAddIndex(newIndex, curIndex);
	}

	/**
	 * This method changes the student's index from the current index to the
	 * targeted index of targetIndexNo
	 * 
	 * @param courseCode    Identifier of Course
	 * @param student       Student to change index
	 * @param targetIndexNo The index number that the student wants to change to
	 * @throws AppException if invalid parameters are passed, see
	 *                      {@link #assertChangeIndex(String, Student, int)}
	 * 
	 */
	public void changeIndex(String courseCode, Student student, int targetIndexNo) throws AppException {
		Course course = Course.getCourse(courseCode);
		Index curIndex = course.getStudentIndex(student);
		Index newIndex = Index.getIndex(targetIndexNo);

		assertChangeIndex(courseCode, student, targetIndexNo);

		course.changeIndex(student, targetIndexNo);
		student.getTimetable().removeIndex(curIndex);
		student.getTimetable().addIndex(newIndex);
	}

	/**
	 * Asserts whether {@code studentA} and {@code studentB} can swop index
	 * 
	 * @param studentA Student A
	 * @param indexNoA Identifier of Index which Student A would like to swap
	 * @param studentB Student B
	 * @param indexNoB Identifier of Index which Student B would like to swap
	 * @throws AppException if invalid parameters are passed in, see
	 *                      {@link Course#assertSwopIndex(Student, int, Student, int)}
	 * @throws AppException if swopping the index will result in a timetable clash
	 */
	public void assertSwopIndex(Student studentA, int indexNoA, Student studentB, int indexNoB) throws AppException {
		Index indexA = Index.getIndex(indexNoA);
		Index indexB = Index.getIndex(indexNoB);

		indexA.getCourse().assertSwopIndex(studentA, indexNoA, studentB, indexNoB);
		studentA.getTimetable().assertAddIndex(indexB, indexA);
		studentB.getTimetable().assertAddIndex(indexA, indexB);
	}

	/**
	 * This method allow the student to swop the course index with another student.
	 * 
	 * @param studentA Student A
	 * @param indexNoA Identifier of Index which Student A would like to swap
	 * @param studentB Student B
	 * @param indexNoB Identifier of Index which Student B would like to swap
	 * @throws AppException if invalid parameters are passed in, see
	 *                      {@link Course#assertSwopIndex(Student, int, Student, int)}
	 * 
	 */
	public void swopIndex(Student studentA, int indexNoA, Student studentB, int indexNoB) throws AppException {
		Index indexA = Index.getIndex(indexNoA);
		Index indexB = Index.getIndex(indexNoB);

		indexA.getCourse().swopIndex(studentA, indexNoA, studentB, indexNoB);

		studentA.getTimetable().removeIndex(indexA);
		studentB.getTimetable().removeIndex(indexB);
		studentA.getTimetable().addIndex(indexB);
		studentB.getTimetable().addIndex(indexA);
	}

	/**
	 * Returns the indexes that {@code student} is enrolled in
	 * 
	 * @param student target to retrieve indexes of
	 * @return list of indexes that {@code student} is enrolled in
	 */
	public List<Index> getStudentIndexes(Student student) {
		return student.getTimetable().getIndexes();
	}

	/**
	 * Returns the indexes that {@code student} is enrolled in, along with
	 * registration status (Registered or Waitlisted)
	 * 
	 * @param student target to retrieve registrations of
	 * @return list of registrations belonging to {@code student}
	 */
	public List<Registration> getStudentRegistrations(Student student) {
		List<Registration> regs = new ArrayList<Registration>();

		for (Registration reg : student.getTimetable().getRegistrations()) {
			regs.add(reg);
		}

		return regs;
	}

	/**
	 * Returns the indexes belonging to the course identified by {@code courseCode}
	 * 
	 * @param courseCode Identifier of Course
	 * @return list of indexes belonging to the course identified by
	 *         {@code courseCode}
	 * @throws AppException if no course identified by {@code courseCode} is found
	 */
	public List<Index> getCourseIndexes(String courseCode) throws AppException {
		return Course.getCourse(courseCode).getIndexes();
	}

	/**
	 * Returns the students enrolled in the course identified by {@code courseCode}
	 * 
	 * @param courseCode Identifier of Course
	 * @return list of students enrolled in the course identified by
	 *         {@code courseCode}
	 * @throws AppException if no course identified by {@code courseCode} is found
	 */
	public List<Student> getStudentsByCourse(String courseCode) throws AppException {
		return Course.getCourse(courseCode).getStudentList();
	}

	/**
	 * Returns the students enrolled in the index identified by {@code indexNo}
	 * 
	 * @param indexNo Identifier of Index
	 * @return list of students enrolled in the index identified by {@code indexNo}
	 * @throws AppException if no index identified by {@code indexNo} is found
	 */
	public List<Student> getStudentsByIndex(int indexNo) throws AppException {
		return Index.getIndex(indexNo).getStudentList();
	}

	/**
	 * Returns the number of vacancies in the index identified by {@code indexNo}
	 * 
	 * @param indexNo Identifier of Index
	 * @return number of vacancies in the index identified by {@code indexNo}
	 * @throws AppException if no index identified by {@code indexNo} is found
	 */
	public int getVacancies(int indexNo) throws AppException {
		return Index.getIndex(indexNo).getVacancies();
	}

	/**
	 * Returns the maximum number of students enrolled in the index identified by
	 * {@code indexNo}
	 * 
	 * @param indexNo Identifier of Index
	 * @return maximum number of students enrolled in the index identified by
	 *         {@code indexNo}
	 * @throws AppException if no index identified by {@code indexNo} is found
	 */
	public int getMaxEnrolled(int indexNo) throws AppException {
		return Index.getIndex(indexNo).getMaxEnrolled();
	}

	/**
	 * Register a listener for a particular event on an Index
	 * 
	 * @param callback Event callback that is triggered when an event is called
	 * @param evt      Event
	 */
	public void registerIndexObserver(Index.IndexCallback callback, Index.Event evt) {
		Index.registerObserver(callback, evt);
	}

	/**
	 * Set the maximum number of AUs a timetable can contain
	 * 
	 * @param max Maximum number of AUs a timetable can contain
	 */
	public void setMaxAUs(int max) {
		Timetable.setMaxAu(max);
	}
}
