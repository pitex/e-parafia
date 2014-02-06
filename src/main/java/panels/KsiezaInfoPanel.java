package panels;

import java.awt.Button;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JPanel;

import common.CommonUI;

/**
 * @author Katarzyna Janocha, Michał Piekarz
 */
public class KsiezaInfoPanel extends JPanel {

	public KsiezaInfoPanel() {
		setLayout(new GridBagLayout());
		
		GridBagConstraints c = CommonUI.defaultGridBagConstraints();
		add(all(),c);
		add(activities(),c);
		add(selectedInfo(),c);
	}
	
	private Button all() {
		Button b = CommonUI.universalButton("LISTA KAPLANOW");
		
		return b;
	}
	
	private Button activities() {
		Button b = CommonUI.universalButton("AKTYWNOSCI KAPLANOW");
		
		return b;
	}

	private Button selectedInfo() {
		Button b = CommonUI.universalButton("WYBRANE INFORMACJE O KAPLANACH");
		
		return b;
	}
}
