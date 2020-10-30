import java.util.ArrayList;

public class Timetable {
	
	private ArrayList<Index> indexes;
	
	public void addIndex(Course course, int indexNo) {
		try {
			indexes.add(course, indexNo);
		} catch(Exception e) {
			System.out.println("Index does not exist.");
		}
	}
	
	public void removeIndex(int indexNo) {
		try {
			indexes.remove(indexNo);
		} catch(Exception e){
			System.out.println("Index not found.");
		}
	}
	
	public void checkClash(Lesson lessonA, Lesson lessonB) {
		if (lessonA.week == lessonB.week && lessonA.day==lessonB.day && lessonA.period == lessonB.period) {
			System.out.println("There is a clash between " + lessonA.index + " and " + lessonB.index);			
		}		
	}
	
}
