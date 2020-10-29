import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Iterator;

enum School {
	SCSE
};
/*
 * enum LessonType { Tutorial, Lecture, Lab, Semminar
 * 
 * }; enum Day { Monday, Tuesday, thursnesday, Thursday, Friday, Saturday,
 * Sunday
 * 
 * };
 */

public class Course {// extends index{
	protected String name;
	protected String courseCode;
	protected School school;

	// Creating HashMap for index
	protected static HashMap<String, Course> courses = new HashMap<>();

	protected HashMap<Integer, Index> indexes = new HashMap<>();
	// empty array of student objects

	// constructor of Course
	public Course(String name, String courseCode, School school) {
		this.name = name;
		this.courseCode = courseCode;
		this.school = school;
		courses.put(courseCode, this);
	}

// get the course according to the course code
	public static Course getCourse(String courseCode) {

		// get course code from HashMap
		return courses.get(courseCode);
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
		Index index = new Index(indexNo, maxEnrolled);
		indexes.put(indexNo, index);
	}

	public void createLesson(int indexNo, LessonType lessonType, Day day, String location, String groupNo, boolean[13] week ) {
		// no String courseCode i think
		Index index1 = getIndex(indexNo);
		index1.createLesson(indexNo, LessonType, day, location, groupNo, week[13]);
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

	public void register(Student student, int indexNo) {
		// check if student is already registered
		// MUST DO ON thurs IF NOT NO SLEEP
		Index index_register = indexes.get(indexNo);
		// add student to the specific course index of studentList
		index_register.addStudent(student); // throw exception if there is no vacancy in index class

	}

	public void drop(Student student, int indexNo) {
		// exception case
		// MUST DO ON thurs IF NOT NO SLEEP
		// Do find the index which have the student, then do index.removestudent
		Index index_drop = indexes.get(indexNo);
		// remove student from the specific course index of studentList
		index_drop.removeStudent(student);
	}

	public void swopIndex(Student studentA, int indexA, Student studentB, int indexB) {
		// check whether both student has index
		// MUST DO ON thurs IF NOT NO SLEEP (sleeping bag?)
		Index index1 = indexes.get(indexA);
		Index index2 = indexes.get(indexB);
		ArrayList<Student> StudentList1 = index1.getStudentList();
		ArrayList<Student> StudentList2 = index2.getStudentList();
		int compareValue1, compareValue2;
		
		for (int i = 0; i < StudentList1.size(); i++) {
			compareValue1 = (studentA.getName() == StudentList1.get(i)); // if same string return 0
			try {
				if (compareValue1 != 0) {

					throw new ExceptionCourse(studentA.getName() + " not found in the index, " + index1);
				}

			} catch (ExceptionCourse e) {

				System.err.print(e);

			}

		}
		for (int i = 0; i < StudentList1.size(); i++) {
			compareValue2 = (studentB.getName() == StudentList2.get(i)); // if same string return 0
			try {
				if (compareValue2 != 0) {
					throw new ExceptionCourse(studentB.getName() + " not found in the index, " + index2);
				}

			} catch (ExceptionCourse e) {

				System.err.print(e);
			}

		}

		if ((compareValue1 == 0) && (compareValue2 == 0)) {

			index1.removeStudent(studentA, false); // false to prevent waitlist from triggering
			index2.removeStudent(studentB, false);
			index1.addStudent(studentB);
			index2.addStudent(studentA);

		}
		

	}

	
}
