package ui.panels;

import model.Context;

import javax.swing.*;
import java.awt.*;

import static java.awt.FlowLayout.LEFT;
import static java.awt.GridBagConstraints.*;

/**
 * @author Katarzyna Janocha, Michał Piekarz
 */
public class SettingsPanel extends JPanel {

    private final Context context;
    private JLabel usernameLabel;
    private JButton newUserButton;
    private JButton changePasswordButton;
    private JTextField newUserTextField;
    private JPasswordField oldPasswordField;
    private JPasswordField newPasswordField;
    private JPasswordField newPasswordField2;
    private JPasswordField newUserPasswordField;
    private JPasswordField newUserPasswordField2;

    public SettingsPanel(Context context) {
        super();

        this.context = context;

        initComponents();

        setLayout(new FlowLayout(LEFT));
        add(createPanel());
    }

    public void init() {
        usernameLabel.setText("Użytkownik: " + context.username);
    }

    private JPanel createPanel() {
        JPanel panel = new JPanel(new GridBagLayout());

        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(2, 2, 3, 3);
        c.gridx = 0;
        c.anchor = CENTER;
        c.gridwidth = 4;

        panel.add(usernameLabel, c);

        c.gridwidth = 2;
        panel.add(new JLabel("Zmień hasło"), c);

        c.gridwidth = 1;
        c.anchor = EAST;
        panel.add(new JLabel("Stare hasło:"), c);
        panel.add(new JLabel("Nowe hasło:"), c);
        panel.add(new JLabel("Powtórz hasło:"), c);

        c.gridx = 1;
        c.anchor = WEST;
        c.fill = HORIZONTAL;
        panel.add(oldPasswordField, c);
        panel.add(newPasswordField, c);
        panel.add(newPasswordField2, c);

        c.anchor = EAST;
        c.fill = NONE;
        panel.add(changePasswordButton, c);

        c.gridx = 2;
        c.gridwidth = 2;
        c.anchor = CENTER;
        panel.add(new JLabel("Nowy użytkownik"), c);

        c.gridwidth = 1;
        c.anchor = EAST;
        panel.add(new JLabel("Nazwa:"), c);
        panel.add(new JLabel("Hasło:"), c);
        panel.add(new JLabel("Powtórz hasło:"), c);

        c.gridx = 3;
        c.anchor = WEST;
        c.fill = HORIZONTAL;
        panel.add(newUserTextField, c);
        panel.add(newUserPasswordField, c);
        panel.add(newUserPasswordField2, c);

        c.anchor = EAST;
        c.fill = NONE;
        panel.add(newUserButton, c);

        return panel;
    }

    private void initComponents() {
        int TEXT_FIELD_SIZE = 10;

        usernameLabel = new JLabel();

        newUserButton = new JButton("Dodaj");
        changePasswordButton = new JButton("Zmień");

        newUserTextField = new JTextField(TEXT_FIELD_SIZE);
        oldPasswordField = new JPasswordField(TEXT_FIELD_SIZE);
        newPasswordField = new JPasswordField(TEXT_FIELD_SIZE);
        newPasswordField2 = new JPasswordField(TEXT_FIELD_SIZE);
        newUserPasswordField = new JPasswordField(TEXT_FIELD_SIZE);
        newUserPasswordField2 = new JPasswordField(TEXT_FIELD_SIZE);

        newUserTextField.setMinimumSize(newUserTextField.getPreferredSize());
        oldPasswordField.setMinimumSize(oldPasswordField.getPreferredSize());
        newPasswordField.setMinimumSize(newPasswordField.getPreferredSize());
        newPasswordField2.setMinimumSize(newPasswordField2.getPreferredSize());
        newUserPasswordField.setMinimumSize(newUserPasswordField.getPreferredSize());
        newUserPasswordField2.setMinimumSize(newUserPasswordField2.getPreferredSize());
    }
}
