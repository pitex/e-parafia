package ui;

import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JTextField;

/**
 * @author Katarzyna Janocha, Michał Piekarz
 * 
 * This is the main adding class, 
 * which means pressing all buttons responsible for adding new item to the database 
 * results in showing this dialog. The user has to fill it.
 */
public class AddingDialog extends JDialog {
	
	private List<JTextField> textFields;
	private JButton submitButton;
	private JButton cancelButton;
	private int len;
	
	private final Context context;
	
	public AddingDialog(Frame owner, Context context, String... names) {
		super(owner);
		
		this.context = context;
		textFields = new ArrayList<>();
		
		setModalityType(ModalityType.APPLICATION_MODAL);
		
		createComponents(names);
	}
	
	private void createComponents(String... names) {
		len = names.length;
		
		cancelButton = new JButton("Cancel");
		submitButton = new JButton("Submit");
		
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AddingDialog.this.dispose();
            }
        });
		
        submitButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				addContent();
				//i tu notyfikacja czy sie udalo
			}
		});
        
		for (int i = 0; i < len; i++) {
			JTextField tf = new JTextField(200);
			tf.setName(names[i]);
			
			textFields.add(tf);
		}
	}
	
	private void addContent() {
		
	}
}
