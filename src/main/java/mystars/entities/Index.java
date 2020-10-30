package mystars.entities;

import java.util.ArrayList;

import mystars.enums.*;
import mystars.exceptions.*;

public class Index {

	private Course course;
	private int indexNo;
	private ArrayList<Lesson> lessons;
	private int maxEnrolled;
	private ArrayList<Student> enrolled;
	private ArrayList<Student> waitlist;

	public Index(Course course, int indexNo, int maxEnrolled) {
		this.indexNo = indexNo;
		this.course = course;
		this.maxEnrolled = maxEnrolled;
		this.enrolled = new ArrayList<Student>();
		this.waitlist = new ArrayList<Student>();
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

	public void addStudent(Student student) throws StudentAlreadyEnrolledException {
		if (enrolled.contains(student)) {
			throw new StudentAlreadyEnrolledException();
		}

		if (enrolled.size() < this.maxEnrolled) {
			this.enrolled.add(student);
		} else {
			this.waitlist.add(student);
		}
	}

	public void removeStudent(Student student) throws StudentNotEnrolledException {
		removeStudent(student, true);
	}

	public void removeStudent(Student student, boolean allocateWaitlist) throws StudentNotEnrolledException {
		if (!this.enrolled.contains(student)) {
			throw new StudentNotEnrolledException();
		}

		this.enrolled.remove(student);
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
}
