package panels;

import java.awt.Button;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JPanel;

import common.CommonUI;

/**
 * @author Katarzyna Janocha, Michał Piekarz
 */
public class FinanseModificationPanel extends JPanel  {
	public FinanseModificationPanel() {
		setLayout(new GridBagLayout());
		
		GridBagConstraints c = CommonUI.defaultGridBagConstraints();
		
		add(addElement(), c);
	}
	
	private Button addElement() {
		Button b = CommonUI.universalButton("DODAJ OFIARE");
		return b;
	}
}
