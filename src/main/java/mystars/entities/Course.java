package mystars.entities;

import java.util.ArrayList;
import java.util.HashMap;

import mystars.enums.*;
import mystars.exceptions.AppException;

public class Course {
	protected String name;
	protected String courseCode;
	protected School school;

	// Creating HashMap for index
	protected static HashMap<String, Course> courses = new HashMap<String, Course>();

	protected HashMap<Integer, Index> indexes = new HashMap<Integer, Index>();
	// empty array of student objects

	// constructor of Course
	public Course(String name, String courseCode, School school) throws AppException {
		if (courses.containsKey(courseCode)) {
			throw new AppException(String.format("%s already exists", courseCode));
		}

		this.name = name;
		this.courseCode = courseCode;
		this.school = school;
		courses.put(courseCode, this);
	}

// get the course according to the course code
	public static Course getCourse(String courseCode) throws AppException {
		if (courses.containsKey(courseCode)) {
			return courses.get(courseCode);
		}

		throw new AppException(String.format("%s is not a valid course code", courseCode));
	}

	// accessor method to get the name of the class course
	public String getName() {
		return this.name;
	}

	// accessor method to get the course code of the class course
	public String getCourseCode() {
		return this.courseCode;
	}

	// accessor method to get the school of the class course
	public School getSchool() {
		return this.school;
	}

	public void createIndex(int indexNo, int maxEnrolled) throws AppException {
		// ask why does create index only have 2 parameter but the index class have 6
		Index index = new Index(this, indexNo, maxEnrolled);
		indexes.put(indexNo, index);
	}

	public Index getIndex(int indexNo) throws AppException {
		if (indexes.containsKey(indexNo)) {
			return indexes.get(indexNo);
		}

		throw new AppException(String.format("Index %d was not found", indexNo));
	}

	public void createLesson(int indexNo, LessonType lessonType, Day day, String location, String groupNo,
			boolean[] week, int startPeriod, int endPeriod) throws AppException {
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

	public void register(Student student, int indexNo) throws AppException {
		// check if student is already registered
		// MUST DO ON thurs IF NOT NO SLEEP

		for (Index i : indexes.values()) {
			if (i.hasStudent(student)) {
				throw new AppException(
						String.format("Already registered for %s in %s", i, i.getCourse()));
			}
		}

		Index index = getIndex(indexNo);
		// add student to the specific course index of studentList
		index.addStudent(student); // throw AppException if there is no vacancy in index class
	}

	public Index drop(Student student) throws AppException {
		for (Index i : indexes.values()) {
			if (!i.hasStudent(student)) continue;
			
			i.removeStudent(student);
			return i;
		}
		
		throw new AppException("Student is not enrolled in any index of this course");
	}

	public void swopIndex(Student studentA, int indexNoA, Student studentB, int indexNoB) throws AppException {
		// check whether both student has index
		// MUST DO ON thurs IF NOT NO SLEEP (sleeping bag?)
		Index indexA = getIndex(indexNoA);
		Index indexB = getIndex(indexNoB);

		if (!indexA.hasStudent(studentA) || !indexB.hasStudent(studentB)) {
			throw new AppException();
		}

		indexA.removeStudent(studentA, false); // false to prevent waitlist from triggering
		indexB.removeStudent(studentB, false);
		try {
			indexA.addStudent(studentB);
			indexB.addStudent(studentA);
		} catch (AppException e) {
			// this should never happen - we just removed the students
			e.printStackTrace();
		}
	}

	public void changeIndex(Student student, int curIndexNo, int targetIndexNo)
			throws AppException {
		Index indexCur = getIndex(curIndexNo);
		Index indexTarget = getIndex(targetIndexNo);

		if (indexTarget.getVacancies() == 0) {
			// we explicitly want vacancies here so that the student cannot be swap to a
			// waitlisted index
			throw new AppException(String.format("%s has no more vacancies", indexTarget));
		}

		indexCur.removeStudent(student);

		try {
			indexTarget.addStudent(student);
		} catch (AppException e) {
			// this cannot happen - one Student cannot be enrolled in more than one index of
			// the same course
			e.printStackTrace();
		}
	}

	public String toString() {
		return String.format("%s (%s)", name, courseCode);
	}
}
