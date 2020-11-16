package mystars.entities;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import mystars.enums.*;
import mystars.exceptions.AppException;

/**
 * <h1>Class: Index</h1>
 *
 *
 * This index class manages creation of lessons within an index and the
 * relationship between student and index. It inherits from the Entity Class
 *
 */
public class Index extends Entity {
	/**
	 * ID for versioning of serialized data.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Parent course that this index belongs to
	 */
	private Course course;

	/**
	 * Index number of this index
	 */
	private int indexNo;

	/**
	 * List of lessons belonging to this index
	 */
	private ArrayList<Lesson> lessons = new ArrayList<Lesson>();

	/**
	 * Maximum number of students that can be registered in this index
	 */
	private int maxEnrolled;

	/**
	 * List of students enrolled in this index
	 */
	private ArrayList<Student> enrolled;

	/**
	 * List of students on the waitlist for this index
	 */
	private ArrayList<Student> waitlist;

	public enum Event {
		AllocatedWaitlist
	}

	// https://stackoverflow.com/a/43192755
	public interface IndexCallback {
		void run(Index index, Student student);
	}

	private static Map<Event, ArrayList<IndexCallback>> observers = new HashMap<Event, ArrayList<IndexCallback>>();

	/**
	 * Constructor for Index. This class is responsible for the creation of an index
	 * object.
	 * 
	 * @param course      The course the Index belongs to
	 * @param indexNo     The index number of the Index.
	 * @param maxEnrolled The maximum enrollment size assigned to the Index.
	 * @throws AppException If the indexNo already existed.
	 */
	public Index(Course course, int indexNo, int maxEnrolled) throws AppException {
		if (get("index", indexNo) != null) {
			throw new AppException(String.format("Index %d already exists", indexNo));
		}

		this.indexNo = indexNo;
		this.course = course;
		this.maxEnrolled = maxEnrolled;
		this.enrolled = new ArrayList<Student>();
		this.waitlist = new ArrayList<Student>();

		store("index", indexNo, this);
	}

	/**
	 * Gets index from indexNo input
	 * 
	 * @param courseCode This parameter is the course's course code.
	 * @return Course This returns the Course that corresponds the course code
	 *         input.
	 * @throws AppException If the courseCode does not exist.
	 */
	public static Index getIndex(int indexNo) throws AppException {
		Index index = (Index) get("index", indexNo);

		if (index != null) {
			return index;
		}

		throw new AppException(String.format("Index %d does not exist", indexNo));
	}

	/**
	 * Gets Index's index number.
	 * 
	 * @return name This returns the Index's index number.
	 */
	public int getIndexNo() {
		return indexNo;
	}

	/**
	 * Creates a new lesson with the given information and adds it into an array
	 * list of lesson objects
	 * 
	 * @param type        The new Lesons's type
	 * @param day         The day the lesson is conducted
	 * @param location    The location the new lesson will be conduted at
	 * @param groupNo     The group number assigned to the particular lesson
	 * @param week        The list of weeks the lesson will be conducted on
	 * @param startPeriod The period the lessons commences
	 * @param endPeriod   The period the lessons concludes
	 */
	public void createLesson(LessonType type, Day day, String location, String groupNo, boolean[] week, int startPeriod,
			int endPeriod) {
		Lesson l = new Lesson(this, type, day, location, groupNo, week, startPeriod, endPeriod);
		this.lessons.add(l);
	}

	/**
	 * Gets the vacancies of the Index
	 * 
	 * @return The available vacancy for the Index by subtracting the number of
	 *         students enrolled from the maximum enrolment size.
	 */
	public int getVacancies() {
		return this.maxEnrolled - enrolled.size();
	}

	/**
	 * Getting all of the students that enrolled in the Index
	 * 
	 * @return enrolled This method returns all of the students that enrolled in the
	 *         index
	 */
	public ArrayList<Student> getStudentList() {
		return enrolled;
	}

	/**
	 * Add student to index if they are not already enrolled and if there is still
	 * vacancy.
	 * 
	 * @param student student to be added into index
	 * @return {@code true} if student was successfully registered
	 * @return {@code false} if the student was placed on the waitlist
	 * @throws AppException if student is already enrolled in the index
	 */
	protected boolean addStudent(Student student) throws AppException {
		if (enrolled.contains(student)) {
			throw new AppException("Already enrolled");
		}

		// if there is vacancy, add the student. else, put them in waitlist.
		if (enrolled.size() < this.maxEnrolled) {
			this.enrolled.add(student);
			return true;
		} else {
			this.waitlist.add(student);
			return false;
		}
	}

	/**
	 * Remove student from index.
	 * 
	 * @param student student to be removed from index
	 */
	protected void removeStudent(Student student) throws AppException {
		removeStudent(student, true);
	}

	/**
	 * Remove student from index if student is already enrolled.
	 * 
	 * @param student          student to be removed from index
	 * @param allocateWaitlist check if student is allocated to waitlist.
	 */
	protected void removeStudent(Student student, boolean allocateWaitlist) {
		this.enrolled.remove(student);

		// if allocateWaitlist and waitlist has students, shift firest student in
		// waitlist to enrolled.
		if (allocateWaitlist && !this.waitlist.isEmpty()) {
			Student waitlistStudent = this.waitlist.remove(0);
			this.enrolled.add(waitlistStudent);
			notify(Event.AllocatedWaitlist, this, waitlistStudent);
		}
	}

	/**
	 * check if student is enrolled
	 * 
	 * @param student student to be checked if they are an element in array list of
	 *                enrolled students.
	 */
	public boolean hasStudent(Student student) {
		return this.enrolled.contains(student);
	}

	/**
	 * Check if index belongs in the same course as another index
	 * 
	 * @param index index object to be checked with current index object.
	 */
	public boolean belongsToSameCourse(Index index) {
		return this.course.equals(index.course);
	}

	/**
	 * Check if 2 indexes clash.
	 * 
	 * @param index index object to be checked with current index object.
	 * @return true if there is a clash
	 */
	public boolean clashesWith(Index index) {
		// yes, this is inefficient, much smarter to handle clash in Timetable
		for (Lesson l1 : lessons) {
			for (Lesson l2 : index.lessons) {
				if (l1.clashesWith(l2)) {
					return true;
				}
			}
		}

		return false;
	}

	/**
	 * Gets the course the index is a part of
	 * 
	 * @return course This returns the course object.
	 */
	public Course getCourse() {
		return course;
	}

	/**
	 * Converts index number to string dataype
	 * 
	 * @return index number in specified string format.
	 */
	public String toString() {
		return String.format("Index %d", indexNo);
	}

	public static void registerObserver(IndexCallback callback, Event evt) {
		if (!observers.containsKey(evt)) {
			observers.put(evt, new ArrayList<>());
		}

		observers.get(evt).add(callback);
	}

	private static void notify(Event evt, Index index, Student student) {
		List<IndexCallback> callbacks = observers.get(evt);

		if (callbacks == null) {
			return;
		}

		for (IndexCallback callback : callbacks) {
			callback.run(index, student);
		}
	}
}
