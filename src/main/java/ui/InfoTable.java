package ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JTable;

/**
 * 
 * @author Katarzyna Janocha, Michał Piekarz
 * Table showing selected rows.
 *
 */
public class InfoTable extends JTable {
	
	private JButton editButton;
	private JButton removeButton;
	private static String[] names;
	
	public InfoTable(ResultSet rowData) {
		super(parseRowDataToObjects(rowData), names);
		
		createComponents();
	}
	
	private void createComponents() {
		editButton = new JButton("EDYTUJ");
		removeButton = new JButton("USUŃ");
		
		editButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				edit();
			}
		});
		
		removeButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				remove();
			}
		});
	}
	
	private static Object[][] parseRowDataToObjects(ResultSet rowData) {
		Object [][] res;
		try {
			while(rowData.next()) {
				
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return res;
	}
	
	private void edit() {
		
	}
	
	private void remove() {
		
	}
}
