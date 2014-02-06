package panels;

import java.awt.Button;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JPanel;

import common.CommonUI;

/**
 * @author Katarzyna Janocha, Michał Piekarz
 */
public class ParafianieInfoPanel extends JPanel {
	public ParafianieInfoPanel() {
		setLayout(new GridBagLayout());
		
		GridBagConstraints c = CommonUI.defaultGridBagConstraints();
		
		add(all(), c);
		add(pomocnicy(), c);
		add(ministranci(), c);
		add(lektorzy(), c);
		add(szafarze(), c);
	}
	
	private Button all() {
		Button b = CommonUI.universalButton("WSZYSCY PARAFIANIE");
		return b;
	}
	
	private Button pomocnicy() {
		Button b = CommonUI.universalButton("POMAGAJACY");
		return b;
	}
	
	private Button ministranci() {
		Button b = CommonUI.universalButton("MINISTRANCI");
		return b;
	}
	
	private Button lektorzy() {
		Button b = CommonUI.universalButton("LEKTORZY");
		return b;
	}
	
	private Button szafarze() {
		Button b = CommonUI.universalButton("SZAFARZE");
		return b;
	}
}
