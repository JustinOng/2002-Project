package mystars.controllers;

import java.util.ArrayList;

import mystars.enums.*;
import mystars.entities.*;
import mystars.exceptions.*;

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

	public void createCourse(String name, String courseCode, School School) {
		Course course = new Course(name, courseCode, School);
	}

	public void createIndex(String courseCode, int indexNo, int maxEnrolled) throws CourseNotFoundException {
		Course c = Course.getCourse(courseCode);
		c.createIndex(indexNo, maxEnrolled);
	}

// Unsure about the parameters in the function are in the correct format
	public void createLesson(String courseCode, int indexNo, LessonType lessonType, Day day, String location,
			String groupNo, boolean[] week, int startPeriod, int endPeriod)
			throws CourseNotFoundException, IndexNotFoundException {
		Course c = Course.getCourse(courseCode);
		c.createLesson(indexNo, lessonType, day, location, groupNo, week, startPeriod, endPeriod);
	}

	public void registerCourse(Student student, String courseCode, int indexNo) throws CourseNotFoundException,
			IndexNotFoundException, StudentAlreadyEnrolledException, CourseAlreadyAddedException, IndexClashException {
		Course c = Course.getCourse(courseCode);
		Index i = c.getIndex(indexNo);
		student.getTimetable().addIndex(i);
		c.register(student, indexNo);
	}

	public void dropCourse(Student student, int indexNo, String courseCode)
			throws CourseNotFoundException, IndexNotFoundException, StudentNotEnrolledException {
		Course c = Course.getCourse(courseCode);
		c.drop(student, indexNo);
	}

	public void changeIndex(String courseCode, Student student, int curIndexNo, int targetIndexNo)
			throws CourseNotFoundException, IndexNotFoundException, StudentNotEnrolledException, IndexFullException {
		Course c = Course.getCourse(courseCode);
		c.changeIndex(student, curIndexNo, targetIndexNo);
	}

	public void swopIndex(String courseCode, Student studentA, int indexNoA, Student studentB, int indexNoB)
			throws CourseNotFoundException, IndexNotFoundException, StudentNotEnrolledException {
		Course c = Course.getCourse(courseCode);
		c.swopIndex(studentA, indexNoA, studentB, indexNoB);
	}
}
