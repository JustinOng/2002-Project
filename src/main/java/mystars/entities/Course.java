package mystars.entities;

import java.util.ArrayList;
import java.util.HashMap;

import mystars.enums.*;
import mystars.exceptions.AppException;

public class Course extends Entity {
	private static final long serialVersionUID = 1L;
	
	protected String name;
	protected String courseCode;
	protected School school;

	protected HashMap<Integer, Index> indexes = new HashMap<Integer, Index>();

	// constructor of Course
	public Course(String name, String courseCode, School school) throws AppException {
		if (get(courseCode) != null) {
			throw new AppException(String.format("%s already exists", courseCode));
		}

		this.name = name;
		this.courseCode = courseCode;
		this.school = school;
		store(courseCode, this);
	}

// get the course according to the course code
	public static Course getCourse(String courseCode) throws AppException {
		Course course = (Course) get(courseCode);
		if (course != null) {
			return course;
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
	
	public ArrayList<Index> getIndexes() {
		return new ArrayList<Index>(indexes.values());
	}

	public Index findStudent(Student student) {
		for (Index i : indexes.values()) {
			if (i.hasStudent(student))
				return i;
		}

		return null;
	}

	public Index getStudentIndex(Student student) throws AppException {
		Index index = findStudent(student);
		
		if (index == null)
			throw new AppException(String.format("Student %s is not enrolled in the course", student.getMatricNo()));
		
		return index;
	}

	public void register(Student student, int indexNo) throws AppException {
		// check if student is already registered
		// MUST DO ON thurs IF NOT NO SLEEP

		if (findStudent(student) != null) {
			throw new AppException(String.format("Already registered for %d in %s", indexNo, this));
		}

		Index index = getIndex(indexNo);
		// add student to the specific course index of studentList
		index.addStudent(student); // throw AppException if there is no vacancy in index class
	}

	public Index drop(Student student) throws AppException {
		Index index = getStudentIndex(student);

		index.removeStudent(student);
		return index;
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

	public void changeIndex(Student student, int targetIndexNo) throws AppException {
		Index indexCur = getStudentIndex(student);
		Index indexTarget = getIndex(targetIndexNo);

		if (indexTarget.getVacancies() == 0) {
			// we explicitly want vacancies here so that the student cannot be swap to a
			// waitlisted index
			throw new AppException(String.format("Index %s has no more vacancies", indexTarget));
		}

		indexCur.removeStudent(student);
		indexTarget.addStudent(student);
	}

	public String toString() {
		return String.format("%s (%s)", name, courseCode);
	}
}
