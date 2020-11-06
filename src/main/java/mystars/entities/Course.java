package mystars.entities;

import java.util.ArrayList;
import java.util.HashMap;

import mystars.enums.*;
import mystars.exceptions.*;

public class Course {
	protected String name;
	protected String courseCode;
	protected School school;

	// Creating HashMap for index
	protected static HashMap<String, Course> courses = new HashMap<String, Course>();

	protected HashMap<Integer, Index> indexes = new HashMap<Integer, Index>();
	// empty array of student objects

	// constructor of Course
	public Course(String name, String courseCode, School school) {
		this.name = name;
		this.courseCode = courseCode;
		this.school = school;
		courses.put(courseCode, this);
	}

// get the course according to the course code
	public static Course getCourse(String courseCode) throws CourseNotFoundException {
		if (courses.containsKey(courseCode)) {
			return courses.get(courseCode);
		}

		throw new CourseNotFoundException();
	}

	// accessor method to get the name of the class course
	public String getName() {
		return this.name;
	}

	// accessor method to get the course code of the class course
	public String courseCode() {
		return this.courseCode;
	}

	// accessor method to get the school of the class course
	public School getSchool() {
		return this.school;
	}

	public void createIndex(int indexNo, int maxEnrolled) {
		// ask why does create index only have 2 parameter but the index class have 6
		Index index = new Index(this, indexNo, maxEnrolled);
		indexes.put(indexNo, index);
	}

	public Index getIndex(int indexNo) throws IndexNotFoundException {
		if (indexes.containsKey(indexNo)) {
			return indexes.get(indexNo);
		}

		throw new IndexNotFoundException();
	}

	public void createLesson(int indexNo, LessonType lessonType, Day day, String location, String groupNo,
			boolean[] week, int startPeriod, int endPeriod) throws IndexNotFoundException {
		// no String courseCode i think
		Index index = getIndex(indexNo);
		index.createLesson(lessonType, day, location, groupNo, week, startPeriod, endPeriod);
	}

	public ArrayList<Student> getStudentList() {
		// to create an array of all the students in the course
		ArrayList<Student> studentList = new ArrayList<Student>();

		// adding all the new students to the array list
		for (Index i : indexes.values()) {
			studentList.addAll(i.getStudentList());
		}

		return studentList;
	}

	public void register(Student student, int indexNo) throws IndexNotFoundException, StudentAlreadyEnrolledException {
		// check if student is already registered
		// MUST DO ON thurs IF NOT NO SLEEP
		
		for (Index i : indexes.values()) {
			if (i.hasStudent(student)) {
				throw new StudentAlreadyEnrolledException();
			}
		}
		
		Index index = getIndex(indexNo);
		// add student to the specific course index of studentList
		index.addStudent(student); // throw exception if there is no vacancy in index class
	}

	public void drop(Student student, int indexNo) throws IndexNotFoundException, StudentNotEnrolledException {
		// exception case
		// MUST DO ON thurs IF NOT NO SLEEP
		// Do find the index which have the student, then do index.removestudent
		Index index = getIndex(indexNo);
		// remove student from the specific course index of studentList
		index.removeStudent(student);
	}

	public void swopIndex(Student studentA, int indexNoA, Student studentB, int indexNoB)
			throws IndexNotFoundException, StudentNotEnrolledException {
		// check whether both student has index
		// MUST DO ON thurs IF NOT NO SLEEP (sleeping bag?)
		Index indexA = getIndex(indexNoA);
		Index indexB = getIndex(indexNoB);

		if (!indexA.hasStudent(studentA) || !indexB.hasStudent(studentB)) {
			throw new StudentNotEnrolledException();
		}

		indexA.removeStudent(studentA, false); // false to prevent waitlist from triggering
		indexB.removeStudent(studentB, false);
		try {
			indexA.addStudent(studentB);
			indexB.addStudent(studentA);
		} catch (StudentAlreadyEnrolledException e) {
			// this should never happen - we just removed the students
			e.printStackTrace();
		}
	}

	public void changeIndex(Student student, int curIndexNo, int targetIndexNo)
			throws IndexNotFoundException, IndexFullException, StudentNotEnrolledException {
		Index indexCur = getIndex(curIndexNo);
		Index indexTarget = getIndex(targetIndexNo);

		if (indexTarget.getVacancies() == 0) {
			// we explicitly want vacancies here so that the student cannot be swap to a waitlisted index
			throw new IndexFullException();
		}
		
		indexCur.removeStudent(student);
		
		try {
			indexTarget.addStudent(student);
		} catch (StudentAlreadyEnrolledException e) {
			// this cannot happen - one Student cannot be enrolled in more than one index of the same course
			e.printStackTrace();
		}
	}
	
	public String toString() {
		return String.format("%s (%s)", name, courseCode);
	}
}
