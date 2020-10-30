import java.util.ArrayList;

public class Index {

	private Course course;
	private int indexNo;
	private ArrayList<Lesson> lessons;
	private int maxEnrolled;
	private int maxWaitlist;
	private ArrayList<Student> enrolled;
	private ArrayList<Student> waitList;
	
	public Index(Course course, int indexNo, int maxEnrolled, int maxWaitlist) {
		this.indexNo = indexNo;
		this.course = course;
		this.maxEnrolled = maxEnrolled;
		this.maxWaitlist = maxWaitlist;
		this.enrolled = null;
		this.waitList = null;
	}

	public void createLesson(LessonType type, Day day, String location, String groupNo, boolean[] week) {
		Lesson l = new Lesson(type, day, location, groupNo, week[13]);
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
			}
			else if(waitlist.size() < maxWaitlist) {
				this.waitList.add(student);
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
		if (allocateWaitlist==true && this.waitList.isEmpty()==false) {
			this.enrolled.add(this.waitList[0]);
			this.waitList.remove(0);
		}
	}
	
	
	
	
}
