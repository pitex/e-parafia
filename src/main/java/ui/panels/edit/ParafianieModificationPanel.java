package ui.panels.edit;

import java.awt.Button;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;

import db.utils.TableColumns;
import db.utils.Tables;

import ui.AddingDialog;
import ui.EditDialog;
import ui.panels.utils.CommonUI;

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
		
		b.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				EditDialog dialog = new EditDialog(
						(Frame)ParafianieModificationPanel.this.getTopLevelAncestor(),
						Tables.PARAFIANIE,
						TableColumns.Parafianie.values()
						);
				dialog.setVisible(true);
			}
		});
		
		return b;
	}
	
	private Button addElement() {
		Button b = CommonUI.universalButton("DODAJ PARAFIANINA");
		
		b.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				AddingDialog dialog = new AddingDialog(
						(Frame)ParafianieModificationPanel.this.getTopLevelAncestor(),
						Tables.PARAFIANIE,
						TableColumns.Parafianie.values()
						);
				dialog.setVisible(true);
			}
		});
		
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
