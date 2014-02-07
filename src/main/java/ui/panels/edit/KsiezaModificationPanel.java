package ui.panels.edit;

import java.awt.Button;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;

import model.Context;

import ui.AddingDialog;
import ui.panels.utils.CommonUI;

/**
 * @author Katarzyna Janocha, Michał Piekarz
 */
public class KsiezaModificationPanel extends JPanel {
	public KsiezaModificationPanel() {
		setLayout(new GridBagLayout());
		
		GridBagConstraints c = CommonUI.defaultGridBagConstraints();
		
		add(addElement(), c);
		add(edit(), c);
		add(remove(), c);
	}
	
	private Button addElement() {
		Button b = CommonUI.universalButton("DODAJ KAPLANA");
		
		String[] names = {""};
		
		b.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				//AddingDialog dialog = new AddingDialog(this, context, table, names);
			}
		});
		
		return b;
	}
	
	private Button edit() {
		Button b = CommonUI.universalButton("EDYTUJ DANE KAPLANA");
		
		return b;
	}
	
	private Button remove() {
		Button b = CommonUI.universalButton("USUN KAPLANA");
		
		return b;
	}
}
