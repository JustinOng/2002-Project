package mystars.forms;

public interface IUserInterfaceObserver {
	public void onLogin(String username, String password, String loginType);
	public void onItemSelect(String itemType, String item);
}
