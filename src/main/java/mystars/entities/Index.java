package mystars.entities;

import java.util.ArrayList;

import mystars.enums.*;

public class Index {

	private Course course;
	private int indexNo;
	private ArrayList<Lesson> lessons;
	private int maxEnrolled;
	private ArrayList<Student> enrolled;
	private ArrayList<Student> waitlist;

	public Index(Course course, int indexNo, int maxEnrolled, int maxWaitlist) {
		this.indexNo = indexNo;
		this.course = course;
		this.maxEnrolled = maxEnrolled;
		this.maxWaitlist = maxWaitlist;
		this.enrolled = new ArrayList<Student>;
		this.waitlist = new ArrayList<Student>;
	}

	public void createLesson(LessonType type, Day day, String location, String groupNo, boolean[] week, int period) {
		Lesson l = new Lesson(this, type, day, location, groupNo, week, period);
		this.lessons.add(l);
	}

	public int getVacancies() {
		return this.maxEnrolled - enrolled.size();
	}

	public ArrayList<Student> getStudentList() {
		return enrolled;
	}

	public void addStudent(Student student) {
		try {
			if (enrolled.size() < this.maxEnrolled) {
				this.enrolled.add(student);
			} else if (waitlist.size() < maxWaitlist) {
				this.waitlist.add(student);
			}
		} catch (Exception e) {
			throw e;
		}
	}

	public void removeStudent(Student student, boolean allocateWaitlist) {
		try {
			this.enrolled.remove(student);
		} catch (Exception e) {
			System.out.println("Student not enrolled.");
		}
		if (allocateWaitlist == true && this.waitlist.isEmpty() == false) {
			this.enrolled.add(this.waitlist.remove(0));
		}
	}

}
