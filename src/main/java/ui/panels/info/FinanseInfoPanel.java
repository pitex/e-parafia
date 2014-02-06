package ui.panels.info;

import java.awt.Button;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JPanel;

import ui.panels.utils.CommonUI;

/**
 * @author Katarzyna Janocha, Michał Piekarz
 */
public class FinanseInfoPanel extends JPanel {
	public FinanseInfoPanel() {
		setLayout(new GridBagLayout());
		
		GridBagConstraints c = CommonUI.defaultGridBagConstraints();
		
		add(all(), c);
		add(ofiaryOkazje(), c);
		add(ofiaryParafianie(), c);
	}
	
	private Button all() {
		Button b = CommonUI.universalButton("WSZYSTKIE OFIARY");
		return b;
	}
	
	private Button ofiaryOkazje() {
		Button b = CommonUI.universalButton("OFIARY Z KONKRETNYCH OKAZJI");
		return b;
	}
	
	private Button ofiaryParafianie() {
		Button b = CommonUI.universalButton("OFIARY KONKRETNYCH PARAFIAN");
		return b;
	}	
}
