package panels;

import java.awt.Button;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JPanel;

import common.CommonUI;

/**
 * @author Katarzyna Janocha, Michał Piekarz
 */
public class WydarzeniaInfoPanel extends JPanel {
	public WydarzeniaInfoPanel() {
		setLayout(new GridBagLayout());
		
		GridBagConstraints c = CommonUI.defaultGridBagConstraints();
		
		add(all(), c);
		add(specified(), c);
	}
	
	private Button all() {
		Button b = CommonUI.universalButton("WYSWIETL WSZYSTKIE WYDARZENIA");
		return b;
	}
	
	private Button specified() {
		Button b = CommonUI.universalButton("WYSWIETL WYDARZENIA KONKRETNEGO TYPU");
		return b;
	}
}
