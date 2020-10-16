package mystars.forms.cgui;

import java.util.List;
import java.util.Arrays;

import com.googlecode.lanterna.gui2.*;

import mystars.forms.IUserInterfaceObserver;
import mystars.forms.Observer;

public class ItemSelectorForm extends Observer implements RadioBoxList.Listener {
	private String itemLabel;
	private List<String> items;

	public AbstractWindow getWindow() {
		AbstractWindow window = new BasicWindow();

		final RadioBoxList<String> radioBoxList = new RadioBoxList<String>();
		radioBoxList.addListener(this);
		for (String s : items) {
			radioBoxList.addItem(s);
		}
		
		window.setComponent(radioBoxList);
		
		window.setTitle(itemLabel);
		window.setHints(Arrays.asList(Window.Hint.CENTERED));
		KeyStrokeListener listener = new KeyStrokeListener();
		window.addWindowListener(listener);

		return window;
	}

	public void pushObserver(String item) {
		for (IUserInterfaceObserver observer : this.observers) {
			observer.onItemSelect(itemLabel, item);
		}
	}

	public void setItemType(String itemType) {
		this.itemLabel = itemType;
	}

	public void setItemList(List<String> items) {
		this.items = items;
	}

	public void onSelectionChanged(int selectedIndex, int previousSelection) {
		pushObserver(items.get(selectedIndex));
	}
}
