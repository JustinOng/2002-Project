package mystars.controllers;

import mystars.enums.*;

import java.util.ArrayList;
import java.util.List;

import mystars.entities.*;
import mystars.exceptions.AppException;

/**
 * <h1>Class: courseController</h1>
 * 
 * This courseController class manages the creation of course, course index and
 * lesson. In addition, it manages registration of course and dropping of
 * course. It also manages the changing of index and swopping of index.
 * 
 */

public class CourseController {
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
	 * @throws AppException If courseCode already exist.
	 */

	public void createCourse(String name, String courseCode, School school) throws AppException {
		new Course(name, courseCode, school);
	}

	/**
	 * Creates a new index for the course identified by {@code courseCode}.
	 * 
	 * @param courseCode  Identifier of Course
	 * @param indexNo     Index number of the new index
	 * @param maxEnrolled The maximum number of students enrolled in the new index
	 * @throws AppException if no course identified by {@code courseCode} is found
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
	 * Creates a new lesson on the index and course identified by {@code indexNo}
	 * and {@code courseCode} respectively
	 * 
	 * @param courseCode  Identifier of Course
	 * @param indexNo     Identifier of Index
	 * @param lessonType  Lesson type of the new lesson
	 * @param day         Day that the new Lesson is held on
	 * @param Location    Location where the Lesson is held
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
	 * @return {@code true} if student was successfully registered
	 * @return {@code false} if student was placed on the waitlist
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
	 * This method changes the student's index from the current index to the
	 * targeted index of targetIndexNo
	 * 
	 * @param courseCode    Identifier of Course
	 * @param student       Student to change index
	 * @param targetIndexNo The index number that the student wants to change to
	 * @throws AppException if no course identified by {@code courseCode}
	 * @throws AppException if student is not enrolled in the course identified by
	 *                      {@code courseCode}
	 * @throws AppException if no index identified by {@code targetIndexNo} is found
	 * @throws AppException if the index identified by {@code targetIndexNo} has no
	 *                      more vacancies
	 * @throws AppException if adding the index identified by {@code targetIndexNo}
	 *                      after removing the current index would result in a clash
	 * 
	 */
	public void changeIndex(String courseCode, Student student, int targetIndexNo) throws AppException {
		Course course = Course.getCourse(courseCode);
		Index curIndex = course.getStudentIndex(student);
		Index newIndex = Index.getIndex(targetIndexNo);

		student.getTimetable().assertAddIndex(newIndex, curIndex);
		course.changeIndex(student, targetIndexNo);
		student.getTimetable().removeIndex(curIndex);
		student.getTimetable().addIndex(newIndex);
	}

	/**
	 * This method allow the student to swop the course index with another student.
	 * 
	 * @param studentA Student A
	 * @param indexNoA Identifier of Index which Student A would like to swap
	 * @param studentB Student B
	 * @param indexNoB Identifier of Index which Student B would like to swap
	 * @throws AppException if {@code studentA} is the same as {@code studentB} or
	 *                      {@code indexNoA} is the same as {@code indexNoB}
	 * @throws AppException if {@code indexNoA} or {@code indexNoB} do not identify
	 *                      valid indexes
	 * @throws AppException if {@code indexNoA} or {@code indexNoB} belong to the
	 *                      same course
	 * @throws AppException if {@code studentA} or {@code studentB} is not enrolled
	 *                      in the indexes they would like to swap
	 * @throws AppException if swopping the index will result in a timetable clash
	 * 
	 */

	public void swopIndex(Student studentA, int indexNoA, Student studentB, int indexNoB) throws AppException {
		if (studentA == studentB)
			throw new AppException("You cannot swap modules with yourself");

		Index indexA = Index.getIndex(indexNoA);
		Index indexB = Index.getIndex(indexNoB);

		if (indexA.getCourse() != indexB.getCourse())
			throw new AppException("Both indexes must be from the same course");

		studentA.getTimetable().assertAddIndex(indexB, indexA);
		studentB.getTimetable().assertAddIndex(indexA, indexB);

		indexA.getCourse().swopIndex(studentA, indexNoA, studentB, indexNoB);

		studentA.getTimetable().removeIndex(indexA);
		studentB.getTimetable().removeIndex(indexB);
		studentA.getTimetable().addIndex(indexB);
		studentB.getTimetable().addIndex(indexA);
	}

	/**
	 * Returns the indexes that {@code student} is enrolled in
	 * 
	 * @param student
	 * @return list of indexes that {@code student} is enrolled in
	 */
	public List<Index> getStudentIndexes(Student student) {
		List<Index> indexes = new ArrayList<Index>();

		for (Index index : student.getTimetable().getIndexes()) {
			indexes.add(index);
		}

		return indexes;
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
}
