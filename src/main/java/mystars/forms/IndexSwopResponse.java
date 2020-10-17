package mystars.forms;

public class IndexSwopResponse {
	private final int indexA;
	private final int indexB;
	private final String username;
	private final String password;
	
	public IndexSwopResponse(int indexA, int indexB, String username, String password) {
		this.indexA = indexA;
		this.indexB = indexB;
		this.username = username;
		this.password = password;
	}
	
	public int getIndexA() {
		return indexA;
	}
	
	public int getIndexB() {
		return indexB;
	}
	
	public String getUsername() {
		return username;
	}
	
	public String getPassword() {
		return password;
	}
}
