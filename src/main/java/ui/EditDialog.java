package ui;

import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.naming.Context;

import db.utils.TableColumns.TableColumn;
import db.utils.Tables;

/**
 * 
 * @author Katarzyna Janocha, Michał Piekarz
 * Editing item in the database.
 *
 */
public class EditDialog extends AbstractEditDialog {

	public EditDialog(Frame owner, Context context, Tables table,
			TableColumn[] names) {
		super(owner, context, table, names);
		
		createComponents();
	}

	private void createComponents() {
		//TODO: current data from table showed in the questionnaire 
		
		submitButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				submitData();
			}
		});
	}
	
	private void submitData() {
		
	}
}
