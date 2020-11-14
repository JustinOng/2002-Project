package mystars.entities;

import java.util.ArrayList;

import mystars.enums.*;
import mystars.exceptions.AppException;

public class Index extends Entity {
	private static final long serialVersionUID = 1L;
	private Course course;
	private int indexNo;
	private ArrayList<Lesson> lessons = new ArrayList<Lesson>();
	private int maxEnrolled;
	private ArrayList<Student> enrolled;
	private ArrayList<Student> waitlist;

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
	
	public static Index getIndex(int indexNo) throws AppException {
		Index index = (Index) get("index", indexNo);
		
		if (index != null) {
			return index;
		}
		
		throw new AppException(String.format("Index %d does not exist", indexNo));
	}
	
	public int getIndexNo() {
		return indexNo;
	}

	public void createLesson(LessonType type, Day day, String location, String groupNo, boolean[] week, int startPeriod,
			int endPeriod) {
		Lesson l = new Lesson(this, type, day, location, groupNo, week, startPeriod, endPeriod);
		this.lessons.add(l);
	}

	public int getVacancies() {
		return this.maxEnrolled - enrolled.size();
	}

	public ArrayList<Student> getStudentList() {
		return enrolled;
	}

	protected void addStudent(Student student) throws AppException {
		if (enrolled.contains(student)) {
			throw new AppException("Already enrolled");
		}

		if (enrolled.size() < this.maxEnrolled) {
			this.enrolled.add(student);
		} else {
			this.waitlist.add(student);
		}
	}

	protected void removeStudent(Student student) throws AppException {
		removeStudent(student, true);
	}

	protected void removeStudent(Student student, boolean allocateWaitlist) {
		if (this.enrolled.contains(student)) {
			this.enrolled.remove(student);
		}
		if (allocateWaitlist == true && this.waitlist.isEmpty() == false) {
			this.enrolled.add(this.waitlist.remove(0));
		}
	}

	public boolean hasStudent(Student student) {
		return this.enrolled.contains(student);
	}

	public boolean belongsToSameCourse(Index index) {
		return this.course.equals(index.course);
	}

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

	public Course getCourse() {
		return course;
	}
	
	public String toString() {
		return String.format("Index %d", indexNo);
	}
}
