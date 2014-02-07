package ui;

import java.awt.Frame;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.swing.JDialog;
import javax.swing.JTextField;

/**
 * @author Katarzyna Janocha, Michał Piekarz
 */
public class AddingDialog extends JDialog {
	
	private List<JTextField> textFields;
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
		
		for (int i = 0; i < len; i++) {
			JTextField tf = new JTextField(200);
			tf.setName(names[i]);
			
			textFields.add(tf);
		}
	}
}
