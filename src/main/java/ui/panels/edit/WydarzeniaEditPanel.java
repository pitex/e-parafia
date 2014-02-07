package ui.panels.edit;

import java.awt.Button;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JPanel;

import ui.panels.utils.CommonUI;

public class WydarzeniaEditPanel extends JPanel {
	public WydarzeniaEditPanel() {
		setLayout(new GridBagLayout());
		
		GridBagConstraints c = CommonUI.defaultGridBagConstraints();

		add(intencja(), c);
		add(chrzest(), c);
		add(komunia(), c);
		add(bierzmowanie(), c);
		add(slub(), c);
		add(pogrzeb(), c);
		add(wizyta(), c);
	}
	
	private Button intencja() {
		Button b = CommonUI.universalButton("EDYTUJ INTENCJĘ MSZALNĄ");
		
		return b;
	}
	
	private Button chrzest() {
		Button b = CommonUI.universalButton("EDYTUJ CHRZEST");
		
		return b;
	}
	
	private Button komunia() {
		Button b = CommonUI.universalButton("EDYTUJ PIERWSZĄ KOMUNIĘ");
		
		return b;
	}
	
	private Button bierzmowanie() {
		Button b = CommonUI.universalButton("EDYTUJ BIERZMOWANIE");
		
		return b;
	}
	
	private Button slub() {
		Button b = CommonUI.universalButton("EDYTUJ ŚLUB");
		
		return b;
	}
	
	private Button pogrzeb() {
		Button b = CommonUI.universalButton("EDYTUJ POGRZEB");
		
		return b;
	}
	
	private Button wizyta() {
		Button b = CommonUI.universalButton("EDYTUJ WIZYTĘ DUSZPASTERSKĄ");
		
		return b;
	}
}
