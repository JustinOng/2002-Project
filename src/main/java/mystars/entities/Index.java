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
 * This index class manages creation of lessons within an index and the
 * relationship between student and index. It inherits from the Entity Class.
 */
public class Index extends Entity {
	/**
	 * ID for versioning of serialized data.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * The parent course that this index belongs to.
	 */
	private Course course;

	/**
	 * The index number of this index.
	 */
	private int indexNo;

	/**
	 * The list of lessons belonging to this index.
	 */
	private ArrayList<Lesson> lessons = new ArrayList<Lesson>();

	/**
	 * The maximum number of students that can be registered in this index.
	 */
	private int maxEnrolled;

	/**
	 * The list of students enrolled in this index.
	 */
	private ArrayList<Student> enrolled;

	/**
	 * The list of students on the waitlist for this index.
	 */
	private ArrayList<Student> waitlist;

	/**
	 * List of event callbacks to be triggered when an event occurs
	 */
	private static Map<Event, ArrayList<IndexCallback>> observers = new HashMap<Event, ArrayList<IndexCallback>>();

	/**
	 * The events which can be observed on this class.
	 */
	public enum Event {
		AllocatedWaitlist
	}

	/**
	 * Callback function to be executed when an event happens.
	 * https://stackoverflow.com/a/43192755
	 */
	public interface IndexCallback {
		void run(Index index, Student student);
	}

	/**
	 * Constructor for Index. This class is responsible for the creation of an index
	 * object.
	 * 
	 * @param course      The course the Index belongs to.
	 * @param indexNo     The index number of the Index.
	 * @param maxEnrolled The maximum enrollment size assigned to the Index.
	 * @throws AppException If the indexNo already existed.
	 */
	public Index(Course course, int indexNo, int maxEnrolled) throws AppException {
		if (indexNo < 0) {
			throw new AppException("Index Number has to be larger than 0");
		}

		if (maxEnrolled <= 0) {
			throw new AppException("Maximum Number of enrolled students has to be greater than 0");
		}

		if (get("index", indexNo) != null) {
			throw new AppException(String.format("Index %d already exists", indexNo));
		}
		this.indexNo = indexNo;
		this.course = course;
		this.maxEnrolled = maxEnrolled;
		this.enrolled = new ArrayList<Student>();
		this.waitlist = new ArrayList<Student>();
	}

	@Override
	public void markPersistent() {
		store("index", indexNo, this);
	}

	/**
	 * Retrieves the index identified by {@code indexNo}
	 * 
	 * @param indexNo Index number of the Index to retrieve
	 * @return This returns the Index with the corresponding {@code indexNo}
	 * @throws AppException if no index indeified by {@code indexNo} exists
	 */
	public static Index getIndex(int indexNo) throws AppException {
		Index index = (Index) get("index", indexNo);

		if (index != null) {
			return index;
		}

		throw new AppException(String.format("Index %d does not exist", indexNo));
	}

	/**
	 * Gets the index number of this index.
	 * 
	 * @return name This returns the index number of this index.
	 */
	public int getIndexNo() {
		return indexNo;
	}

	/**
	 * Creates a new lesson with the given information.
	 * 
	 * @param type        The new Lesons's type.
	 * @param day         The day the lesson is conducted.
	 * @param location    The location the new lesson will be conducted at.
	 * @param groupNo     The group number assigned to the particular lesson.
	 * @param week        Array of booleans indicating whether this lesson is held
	 *                    on the particular week
	 * @param startPeriod The period the lessons commences.
	 * @param endPeriod   The period the lessons concludes.
	 * @throws AppException if invalid parameters are passed to the constructor of
	 *                      Lesson
	 */
	public void createLesson(LessonType type, Day day, String location, String groupNo, boolean[] week, int startPeriod,
			int endPeriod) throws AppException {
		Lesson l = new Lesson(this, type, day, location, groupNo, week, startPeriod, endPeriod);

		if (clashesWith(l)) {
			throw new AppException("The new lesson clashes with an existing lesson");
		}

		this.lessons.add(l);
	}

	/**
	 * Retrieves lessons belonging to this index
	 * 
	 * @return List of lessons belonging to this index
	 */
	public List<Lesson> getLessons() {
		return lessons;
	}

	/**
	 * Retrieves number of vacancies in this index
	 * 
	 * @return number of vacancies in this index
	 */
	public int getVacancies() {
		return this.maxEnrolled - enrolled.size();
	}

	/**
	 * Gets the maximum number of enrolled students for this Index
	 * 
	 * @return the maximum number of enrolled students for this Index
	 */
	public int getMaxEnrolled() {
		return this.maxEnrolled;
	}

	/**
	 * Getting all of the students that enrolled in the Index.
	 * 
	 * @return enrolled This method returns all of the students that are enrolled in
	 *         the index.
	 */
	public ArrayList<Student> getStudentList() {
		return enrolled;
	}

	/**
	 * Add student to this index
	 * 
	 * @param student The student to be added into this index.
	 * @return {@code true} If the student was successfully registered, or
	 *         {@code false} If the student was placed on the waitlist.
	 * @throws AppException If student is already enrolled in the index.
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
	 * Remove student from this index.
	 * 
	 * @param student The student to be removed from this index.
	 */
	protected void removeStudent(Student student) {
		removeStudent(student, true);
	}

	/**
	 * Remove student from this index.
	 * 
	 * @param student          The student to be removed from this index.
	 * @param allocateWaitlist If {@code true}, move one student from the waitlist
	 *                         to the enrolled list
	 */
	protected void removeStudent(Student student, boolean allocateWaitlist) {
		if (this.enrolled.remove(student)) {
			// if allocateWaitlist and waitlist has students, shift first student in
			// waitlist to enrolled.
			if (allocateWaitlist && !this.waitlist.isEmpty()) {
				Student waitlistStudent = this.waitlist.remove(0);
				this.enrolled.add(waitlistStudent);
				notify(Event.AllocatedWaitlist, this, waitlistStudent);
			}
		} else {
			this.waitlist.remove(student);
		}
	}

	/**
	 * Check if the student is enrolled or on the waitlist for this index
	 * 
	 * @param student The student to be checked
	 * @return {@code true} if {@code student} is enrolled or on the waitlist
	 */
	public boolean hasStudent(Student student) {
		return this.enrolled.contains(student) || this.waitlist.contains(student);
	}

	/**
	 * Check if the student is enrolled in this index
	 * 
	 * @param student The student to be checked
	 * @return {@code true} if {@code student} is enrolled in this index
	 */
	public boolean hasEnrolledStudent(Student student) {
		return this.enrolled.contains(student);
	}

	/**
	 * Check if index belongs in the same course as another index.
	 * 
	 * @param index The index object to be checked with current index object.
	 * @return {@code true} if {@code index} belongs to the same course as this
	 *         index, or {@code false} otherwise
	 */
	public boolean belongsToSameCourse(Index index) {
		return this.course.equals(index.course);
	}

	/**
	 * Check if 2 indexes clash.
	 * 
	 * @param index The index to compare with the current index
	 * @return {@code true} if there is a clash, or {@code false} otherwise
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
	 * Check if {@code lesson} clashes any lesson in this index
	 * 
	 * @param lesson The lesson to compare with all lessons in this index
	 * @return {@code true} if there is a clash, or {@code false} otherwise
	 */
	public boolean clashesWith(Lesson lesson) {
		for (Lesson l1 : lessons) {
			if (l1.clashesWith(lesson)) {
				return true;
			}
		}

		return false;
	}

	/**
	 * Gets the course the index is a part of.
	 * 
	 * @return The course object.
	 */
	public Course getCourse() {
		return course;
	}

	/**
	 * Returns string representation of this index
	 * 
	 * @return string representation of this index
	 */
	public String toString() {
		return String.format("Index %d", indexNo);
	}

	/**
	 * Register a listener for a particular event.
	 * 
	 * @param callback The function to execute when the event identified by
	 *                 {@code evt} occurs.
	 * @param evt      The event to register the listener for.
	 */
	public static void registerObserver(IndexCallback callback, Event evt) {
		if (!observers.containsKey(evt)) {
			observers.put(evt, new ArrayList<>());
		}

		observers.get(evt).add(callback);
	}

	/**
	 * Trigger the event identified by {@code evt}.
	 * 
	 * @param evt     The event to trigger.
	 * @param index   The index on which the event occurred.
	 * @param student The student on which the event occurred.
	 */
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
