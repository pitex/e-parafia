package panels;

import java.awt.Button;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JPanel;

import common.CommonUI;

/**
 * @author Katarzyna Janocha, Michał Piekarz
 */
public class WydarzeniaModificationPanel extends JPanel {
	public WydarzeniaModificationPanel() {
		setLayout(new GridBagLayout());
		
		GridBagConstraints c = CommonUI.defaultGridBagConstraints();

		add(remove(), c);
		add(addChrzest(), c);
		add(addBierzmowanie(), c);

	}
	
	private Button remove() {
		Button b = CommonUI.universalButton("DODAJ BIERZMOWANIE");
		
		return b;
	}
	
	private Button addChrzest() {
		Button b = CommonUI.universalButton("DODAJ CHRZEST");
		
		return b;
	}
	
	private Button addKomunia() {
		Button b = CommonUI.universalButton("DODAJ KOMUNIE SWIETA");
		
		return b;
	}
	
	private Button addBierzmowanie() {
		Button b = CommonUI.universalButton("DODAJ BIERZMOWANIE");
		
		return b;
	}

	private Button addSlub() {
		Button b = CommonUI.universalButton("DODAJ SLUB");
		
		return b;
	}
	
	private Button addPogrzeb() {
		Button b = CommonUI.universalButton("DODAJ POGRZEB");
		
		return b;
	}
}
