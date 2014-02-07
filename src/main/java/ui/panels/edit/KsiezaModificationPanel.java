package ui.panels.edit;

import java.awt.Button;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JPanel;

import db.utils.TableColumns;
import db.utils.Tables;
import db.utils.TableColumns.TableColumn;

import model.Context;

import ui.AddingDialog;
import ui.EditDialog;
import ui.panels.utils.CommonUI;

/**
 * @author Katarzyna Janocha, Michał Piekarz
 */
public class KsiezaModificationPanel extends JPanel {
	public KsiezaModificationPanel() {
		setLayout(new GridBagLayout());
		
		GridBagConstraints c = CommonUI.defaultGridBagConstraints();
		
		add(addElement(), c);
	}
	
	private Button addElement() {
		Button b = CommonUI.universalButton("DODAJ KAPLANA");
		
		b.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				AddingDialog dialog = new AddingDialog(
						(Frame)KsiezaModificationPanel.this.getTopLevelAncestor(), Tables.KAPLANI, 
						TableColumns.Kaplani.values());
				dialog.setVisible(true);
			}
		});
		
		return b;
	}
}
