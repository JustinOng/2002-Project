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
	
}
