package mystars.entities;

import java.util.ArrayList;
import java.util.HashMap;

import mystars.enums.*;
import mystars.exceptions.AppException;

/**
 * <h1>Class: Course</h1>
 * 
 * This class performs all the functions required for courseController. It inherits from the Entity class.
 */
public class Course extends Entity {
	/**
	 * ID for versioning of serialized data.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * The name of the course.
	 */
	protected String name;

	/**
	 * The course code of the course.
	 */
	protected String courseCode;

	/**
	 * The school that the course belongs to.
	 */
	protected School school;

	/**
	 * A hash map of Index according to the index number in the hash Map.
	 */
	protected HashMap<Integer, Index> indexes = new HashMap<Integer, Index>();

	/**
	 * Constructor for Course This class is responsible for the creation of a course
	 * object.
	 * 
	 * @param name       The Course's name.
	 * @param courseCode The Course Code.
	 * @param school     The Course's school.
	 * @throws AppException If the courseCode already existed.
	 */
	public Course(String name, String courseCode, School school) throws AppException {
		if (name.isBlank()) {
			throw new AppException("name cannot be blank");
		}

		if (courseCode.isBlank()) {
			throw new AppException("courseCode cannot be blank");
		}
		
		courseCode = courseCode.toUpperCase();

		if (get("course", courseCode) != null) {
			throw new AppException(String.format("%s already exists", courseCode));
		}

		this.name = name;
		this.courseCode = courseCode;
		this.school = school;
		store("course", courseCode, this);
	}

	/**
	 * Gets Course from a course code input
	 * 
	 * @param courseCode This parameter is the course's course code.
	 * @return Course This returns the Course that corresponds the course code
	 *         input.
	 * @throws AppException If the courseCode does not exist.
	 */
	public static Course getCourse(String courseCode) throws AppException {
		courseCode = courseCode.toUpperCase();
		Course course = (Course) get("course", courseCode);
		if (course != null) {
			return course;
		}

		throw new AppException(String.format("%s is not a valid course code", courseCode));
	}

	/**
	 * Gets Course's name.
	 * 
	 * @return name This returns the Course's name.
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * Gets the course's course code.
	 * 
	 * @return courseCode This returns the courseCode.
	 */
	public String getCourseCode() {
		return this.courseCode;
	}

	/**
	 * Gets the Course's School
	 * 
	 * @return school This returns the Course's school.
	 */
	public School getSchool() {
		return this.school;
	}

	/**
	 * Creates a new Index with the given index number and maximum enrolled number
	 * of students and place it into the hash map for indexes
	 * 
	 * @param indexNo     The new Index's Index number
	 * @param maxEnrolled The maximum enrolled students allowed for the new Index
	 * @throws AppException if invalid parameters are passed to the constructor of Index
	 */
	public void createIndex(int indexNo, int maxEnrolled) throws AppException {
		Index index = new Index(this, indexNo, maxEnrolled);
		indexes.put(indexNo, index);
	}

	/**
	 * Gets the index object from this course if the index of the course exist
	 * 
	 * @param indexNo The Index's index number
	 * @return The object index according to the index number
	 * @throws AppException If the index number is not found for the index
	 */
	public Index getIndex(int indexNo) throws AppException {
		if (indexes.containsKey(indexNo)) {
			return indexes.get(indexNo);
		}

		throw new AppException(String.format("Index %d was not found", indexNo));
	}

	/**
	 * Creates a new lesson
	 * 
	 * @param indexNo     The index number that the lesson belongs to
	 * @param lessonType  The lesson's lesson type
	 * @param day         Which day the lesson is on
	 * @param location    The location the lesson is at
	 * @param groupNo     The group number the lesson belongs to
	 * @param week        Whether the lesson falls on odd or even
	 * @param startPeriod The starting time when the lesson begins
	 * @param endPeriod   The ending time when the lesson begins
	 * @throws AppException if the index number is not found in the index
	 * @throws AppException if invalid parameters are passed to the constructor of Lesson
	 */
	public void createLesson(int indexNo, LessonType lessonType, Day day, String location, String groupNo,
			boolean[] week, int startPeriod, int endPeriod) throws AppException {
		// no String courseCode i think
		Index index = getIndex(indexNo);
		index.createLesson(lessonType, day, location, groupNo, week, startPeriod, endPeriod);
	}

	/**
	 * Retrieves all courses
	 * 
	 * @return list of all courses
	 */
	@SuppressWarnings("unchecked")
	public static ArrayList<Course> getAllCourses() {
		return (ArrayList<Course>) (ArrayList<?>) getAll("course");
	}

	/**
	 * Getting all of the students that registered in the course
	 * 
	 * @return studentList This method returns all of the students that registered
	 *         in the course as a new ArrayList
	 */
	public ArrayList<Student> getStudentList() {

		ArrayList<Student> studentList = new ArrayList<Student>();

		for (Index i : indexes.values()) {
			studentList.addAll(i.getStudentList());
		}

		return studentList;
	}

	/**
	 * Gets all the list of indexes that is from the course
	 * 
	 * @return A new Array List of indexes from the course
	 */
	public ArrayList<Index> getIndexes() {
		return new ArrayList<Index>(indexes.values());
	}

	/**
	 * Check if the student is in the index
	 * 
	 * @param student The student that you want check with
	 * @return the index if the student is in the index
	 * @return null if the student is not in the index
	 */
	public Index findStudent(Student student) {
		for (Index i : indexes.values()) {
			if (i.hasStudent(student))
				return i;
		}
		return null;
	}

	/**
	 * Gets the index that the student is in
	 * 
	 * @param student The student that you want check with
	 * @return the index if the student is in the index
	 * @throws AppException if the student is not enrolled in the course
	 */
	public Index getStudentIndex(Student student) throws AppException {
		Index index = findStudent(student);

		if (index == null)
			throw new AppException(String.format("Student %s is not enrolled in the course", student.getMatricNo()));

		return index;
	}

	/**
	 * Registers the student in the target index
	 * 
	 * @param student The student to be registered in the index
	 * @param indexNo The index that the student is to be registered to
	 * @return {@code true} if student was successfully registered
	 * @return {@code false} if the student was placed on the waitlist
	 * @throws AppException if the student is already registered for index indexNo
	 */
	public boolean register(Student student, int indexNo) throws AppException {
		if (findStudent(student) != null) {
			throw new AppException(String.format("Already registered for %d in %s", indexNo, this));
		}

		Index index = getIndex(indexNo);
		return index.addStudent(student);
	}

	/**
	 * Drops the student from the target index.
	 * 
	 * @param student The student that wants to drop the index.
	 * @return the current index if the dropping of index is successful.
	 * @throws AppException if the student is not in the index that the student
	 *                      wants to drop.
	 */
	public Index drop(Student student) throws AppException {
		Index index = getStudentIndex(student);
		index.removeStudent(student);
		return index;
	}

	/**
	 * Swop indexNoA of StudentA with indexNoB of StudentB
	 * 
	 * @param studentA The first student that wants to swop index
	 * @param studentB The second student that wants to swop index
	 * @param indexNoA The index that the first student wants to swop
	 * @param indexNoB The other index that the second student wants to swop
	 * @throws AppException If indexNoA and indexNoB is the same index
	 * @throws AppException If studentA is not enrolled in indexNoA
	 * @throws AppException If studentB is not enrolled in indexNoB
	 */
	public void swopIndex(Student studentA, int indexNoA, Student studentB, int indexNoB) throws AppException {

		if (indexNoA == indexNoB) {
			throw new AppException("Both indexes must be different");
		}

		Index indexA = getIndex(indexNoA);
		Index indexB = getIndex(indexNoB);

		if (!indexA.hasEnrolledStudent(studentA)) {
			throw new AppException(String.format("%s is not enrolled in index %d", studentA.getMatricNo(), indexNoA));
		}

		if (!indexB.hasEnrolledStudent(studentB)) {
			throw new AppException(String.format("%s is not enrolled in index %d", studentB.getMatricNo(), indexNoB));
		}

		indexA.removeStudent(studentA, false);
		indexB.removeStudent(studentB, false);
		indexA.addStudent(studentB);
		indexB.addStudent(studentA);
	}

	/**
	 * This method changes the student's index from the current index to the
	 * targeted index of targetIndexNo
	 * 
	 * @param student       The student that wants to change index
	 * @param targetIndexNo The index number that the student wants to change to
	 * @throws AppException If the student is not registered in the current index
	 * @throws AppException If targetIndexNo is not found
	 * @throws AppException If targetIndexNo of the student has no more vacancies.
	 */
	public void changeIndex(Student student, int targetIndexNo) throws AppException {
		Index indexCur = getStudentIndex(student);
		Index indexTarget = getIndex(targetIndexNo);
		
		if (indexCur == indexTarget) {
			throw new AppException("You cannot change to the same index");
		}

		if (indexTarget.getVacancies() == 0) {
			// we explicitly want vacancies here so that the student cannot be swop to a
			// waitlisted index
			throw new AppException(String.format("%s has no more vacancies", indexTarget));
		}

		indexCur.removeStudent(student);
		indexTarget.addStudent(student);
	}

	/**
	 * Changes the course code name to a string format
	 * 
	 * @return the course code as a string
	 */
	public String toString() {
		return String.format("%s (%s)", name, courseCode);
	}
}
