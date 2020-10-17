package mystars.forms.cgui;

import java.util.List;
import java.util.Arrays;

import com.googlecode.lanterna.gui2.*;

import mystars.forms.*;

public class ItemSelectorForm implements RadioBoxList.Listener {
	private AbstractWindow window = new BasicWindow();
	private TextResponse response;
	private List<String> items;
	
	public TextResponse getResponse(MultiWindowTextGUI gui, String title, List<String> items) {
		this.items = items;

		RadioBoxList<String> radioBoxList = new RadioBoxList<String>();
		radioBoxList.addListener(this);
		for (String s : items) {
			radioBoxList.addItem(s);
		}
		
		window.setComponent(radioBoxList);
		
		window.setTitle(title);
		window.setHints(Arrays.asList(Window.Hint.CENTERED));
		
		KeyStrokeListener listener = new KeyStrokeListener();
		window.addWindowListener(listener);
		
		gui.addWindowAndWait(window);

		return response;
	}

	public void onSelectionChanged(int selectedIndex, int previousSelection) {
		response = new TextResponse(items.get(selectedIndex));
		window.close();
	}
}
