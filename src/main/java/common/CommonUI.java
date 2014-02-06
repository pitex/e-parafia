package common;

import java.awt.Button;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.Insets;

public class CommonUI {
	public static Dimension BUTTON_DIMENSION = new Dimension(1000,90);
	
	public static Button universalButton(String s) {
		Button b = new Button(s);
		b.setPreferredSize(CommonUI.BUTTON_DIMENSION);
		b.setFont(new Font(Font.DIALOG, Font.BOLD, 20));
		b.setBackground(new Color(176, 224, 230));
		return b;
	}
	
	public static GridBagConstraints defaultGridBagConstraints() {
		GridBagConstraints c = new GridBagConstraints();
		c.gridx = 0;
		c.anchor = GridBagConstraints.CENTER;
		c.insets = new Insets(2,2,3,3);
		return c;
	}
}
