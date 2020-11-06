package mystars.controllers;

import mystars.enums.*;
import mystars.entities.*;
import mystars.exceptions.AppException;

/* 1) courseManger will do all the condition and logic checking?
 * 2) Course is to just retrieve the date from the other classes?
 * 3) Both Course.java and courseManager have createCourse, createIndex etc, who will do the instantiate the object reference?
 * 4) Got many same functions in Couse.java and courseManger, eg registerCourse, dropCourse etc... who call who?
 * 5) Ask the enum everything in Course.java? 
 * 
 * 
 * 
 * 
 * */

public class CourseController {

	public void createCourse(String name, String courseCode, School School) throws AppException {
		Course course = new Course(name, courseCode, School);
	}

	public void createIndex(String courseCode, int indexNo, int maxEnrolled)
			throws AppException {
		Course c = Course.getCourse(courseCode);
		c.createIndex(indexNo, maxEnrolled);
	}

// Unsure about the parameters in the function are in the correct format
	public void createLesson(String courseCode, int indexNo, LessonType lessonType, Day day, String location,
			String groupNo, boolean[] week, int startPeriod, int endPeriod)
			throws AppException {
		Course c = Course.getCourse(courseCode);
		c.createLesson(indexNo, lessonType, day, location, groupNo, week, startPeriod, endPeriod);
	}

	public void registerCourse(Student student, String courseCode, int indexNo) throws AppException {
		Course c = Course.getCourse(courseCode);
		Index i = c.getIndex(indexNo);
		student.getTimetable().addIndex(i);
		c.register(student, indexNo);
	}

	public void registerCourse(Student student, int indexNo)
			throws AppException {
		Index i = Index.getIndex(indexNo);
		student.getTimetable().addIndex(i);
		try {
			i.getCourse().register(student, indexNo);
		} catch (AppException e) {
			throw e;
		}
	}

	public void dropCourse(Student student, int indexNo, String courseCode)
			throws AppException {
		Course c = Course.getCourse(courseCode);
		c.drop(student, indexNo);
	}

	public void changeIndex(String courseCode, Student student, int curIndexNo, int targetIndexNo)
			throws AppException {
		Course c = Course.getCourse(courseCode);
		c.changeIndex(student, curIndexNo, targetIndexNo);
	}

	public void swopIndex(String courseCode, Student studentA, int indexNoA, Student studentB, int indexNoB)
			throws AppException {
		Course c = Course.getCourse(courseCode);
		c.swopIndex(studentA, indexNoA, studentB, indexNoB);
	}
}
