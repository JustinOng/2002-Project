package mystars.controllers;

import mystars.enums.*;

import java.util.HashMap;

import mystars.entities.*;
import mystars.exceptions.AppException;

/**
 * <h1>Class: courseController</h1>
 * 
 * This courseController class manages the creation of course, course index and lesson. 
 * In addition, it manages registration of course and dropping of course.
 * It also manages the changing of index and swopping of index.
 * 
 */

public class CourseController {
	/**
	 * This method creates a new course.
	 * 
	 * @param name 		    The course's name.
	 * @param courseCode    The course's code.
	 * @param school 	    The course's school.
	 * @throws AppException If courseCode already exist.
	 */

	public void createCourse(String name, String courseCode, School school) throws AppException {
		Course course = new Course(name, courseCode, school);
	}
	/**
	 * This method creates a new index.
	 * 
	 * @param courseCode    The index course's code.
	 * @param indexNo       The index no.
	 * @param maxEnrolled   The max enrolled of the course index.
	 * @throws AppException If courseCode is not found.
	 */

	public void createIndex(String courseCode, int indexNo, int maxEnrolled) throws AppException {
		Course c = Course.getCourse(courseCode);
		c.createIndex(indexNo, maxEnrolled);
	}
	/**
	 * This method creates a new lesson.
	 * 
	 * @param courseCode    The lesson's course code.
	 * @param indexNo       The lesson's index no.
	 * @param lessonType    The lesson type of the lesson.
	 * @param day           The lesson's day.
	 * @param Location      The lesson's location.
	 * @param groupNo       The lesson's group number.
	 * @param week  	    The lesson's week.
	 * @param startPeriod   The lesson's start period.
	 * @param endPeriod	    The lesson's end period.
	 * @throws AppException If courseCode is not found.
	 * @throws AppException If indexNo is not found.
	 */

	public void createLesson(String courseCode, int indexNo, LessonType lessonType, Day day, String location,
			String groupNo, boolean[] week, int startPeriod, int endPeriod) throws AppException {
		Course c = Course.getCourse(courseCode);
		c.createLesson(indexNo, lessonType, day, location, groupNo, week, startPeriod, endPeriod);
	}

	public void registerCourse(Student student, String courseCode, int indexNo) throws AppException {
		Course c = Course.getCourse(courseCode);
		Index i = c.getIndex(indexNo);
		student.getTimetable().addIndex(i);
		c.register(student, indexNo);
	}
	/**
	 * This method register the student to a course.
	 * 
	 * @param student The student's.
	 * @param indexNo The index no the student wants to register.
	 * @throws AppException If indexNo is not found.
	 * @throws AppException If index has already been registered.
	 * @throws AppException If index clashes with another index.
	 * @throws AppException If indexNo of the student has already been registered.
	 */
	public void registerCourse(Student student, int indexNo) throws AppException {
		Index index = Index.getIndex(indexNo);
		student.getTimetable().assertAddIndex(index);
		
		index.getCourse().register(student, indexNo);
		student.getTimetable().addIndex(index);
	}
	/**
	 * This method drop the student from a course.
	 * 
	 * @param student 	 	The student who wants to drop the course.
	 * @param courseCode 	The course that the student wants to drop.
	 * @throws AppException If courseCode already exist.
	 * @throws AppException If student is not enrolled in the course.
	 *  
	 */
	public void dropCourse(Student student, String courseCode) throws AppException {
		Course course = Course.getCourse(courseCode);
		Index index = course.drop(student);
		student.getTimetable().removeIndex(index);
	}
	/**
	 * This method changes the student's index from the current index 
	 * to the targeted index of targetIndexNo
	 * 
	 * @param courseCode    The course's code.
	 * @param student       The student that wants to change index.
	 * @param targetIndexNo The index number that the student wants to change to.
	 * @throws AppException If courseCode is not found.
	 * @throws AppException If student is not enrolled in the course.
	 * @throws AppException If targetIndexNo is not found.
	 * @throws AppException If student's timetable cannot accommodate newIndex after removing curIndex. 
	 * @throws AppException If targetIndexNo of the student has no more vacancies.
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
	 * @param studentA      The studentA's.
	 * @param indexNoA      The studentA's index.
	 * @param studentB 	    The studentB's.
	 * @param indexNoB      The studentB's index.
	 * @throws AppException If studentA is studentB.
	 * @throws AppException If indexNoA is not found.
	 * @throws AppException If indexNoB is not found.
	 * @throws AppException If indexA.getCourse() is not same as indexB.getCourse()
	 * @throws AppException If studentA's timetable cannot accommodate indexNoB after removing indexA.
	 * @throws AppException If studentB's timetable cannot accommodate indexNoA after removing indexB.
	 * @throws AppException If indexNoA and indexNoB is the same index.
	 * @throws AppException If studentA is not enrolled in indexNoA.
	 * @throws AppException If studentB is not enrolled in indexNoB.
	 * 
	 */

	public void swopIndex(Student studentA, int indexNoA, Student studentB, int indexNoB)
			throws AppException {
		if (studentA == studentB) throw new AppException("You cannot swap modules with yourself");		
		
		Index indexA = Index.getIndex(indexNoA);
		Index indexB = Index.getIndex(indexNoB);
		
		if (indexA.getCourse() != indexB.getCourse()) throw new AppException("Both indexes must be from the same course");
		
		studentA.getTimetable().assertAddIndex(indexB, indexA);
		studentB.getTimetable().assertAddIndex(indexA, indexB);
		
		indexA.getCourse().swopIndex(studentA, indexNoA, studentB, indexNoB);
		
		studentA.getTimetable().removeIndex(indexA);
		studentB.getTimetable().removeIndex(indexB);
		studentA.getTimetable().addIndex(indexB);
		studentB.getTimetable().addIndex(indexA);
	}

	
	public HashMap<String, String> getRegisteredInfo(Student student) {
		HashMap<String, String> data = new HashMap<String, String>();

		for (Index i : student.getTimetable().getIndexes()) {
			data.put(String.format("%s: %s", i.getCourse(), i), i.getCourse().getCourseCode());
		}

		return data;
	}

	public HashMap<String, Integer> getIndexInfo(String courseCode) throws AppException {
		HashMap<String, Integer> data = new HashMap<String, Integer>();
		
		Course course = Course.getCourse(courseCode);
		
		for (Index i : course.getIndexes()) {
			data.put(i.toString(), i.getIndexNo());
		}
		
		return data;
	}
}
