package ui.panels.edit;

import javax.swing.JTabbedPane;

public class WydarzeniaModificationPanel extends JTabbedPane {
	public WydarzeniaModificationPanel() {
		add("DODAJ/USUŃ", new WydarzeniaAddPanel());
		add("EDYTUJ", new WydarzeniaEditPanel());
	}
}
