package panels;

import java.awt.Button;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JPanel;

import common.CommonUI;

/**
 * @author Katarzyna Janocha, Michał Piekarz
 */
public class ParafianieModificationPanel extends JPanel {
	public ParafianieModificationPanel() {
		setLayout(new GridBagLayout());
		
		GridBagConstraints c = CommonUI.defaultGridBagConstraints();
		
		add(addElement(), c);
		add(edit(), c);
		add(dead(), c);
		add(remove(), c);
	}
	
	private Button edit() {
		Button b = CommonUI.universalButton("EDYTUJ DANE PARAFIANINA");
		
		return b;
	}
	
	private Button addElement() {
		Button b = CommonUI.universalButton("DODAJ PARAFIANINA");
		
		return b;
	}
	
	private Button dead() {
		Button b = CommonUI.universalButton("ZGON PARAFIANINA");
		
		return b;
	}
	
	private Button remove() {
		Button b = CommonUI.universalButton("USUN PARAFIANINA");
		
		return b;
	}
}
