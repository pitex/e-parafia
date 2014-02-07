package ui.panels;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static java.awt.FlowLayout.LEFT;
import static java.awt.GridBagConstraints.*;
import static javax.swing.JOptionPane.*;
import static model.Context.getContext;
import static security.Encoder.encrypt;

/**
 * @author Katarzyna Janocha, Michał Piekarz
 */
public class SettingsPanel extends JPanel {

    private List<Component> adminComponents;
    private JLabel usernameLabel;
    private JButton newUserButton;
    private JButton changePasswordButton;
    private JTextField newUserTextField;
    private JPasswordField oldPasswordField;
    private JPasswordField newPasswordField;
    private JPasswordField newPasswordField2;
    private JPasswordField newUserPasswordField;
    private JPasswordField newUserPasswordField2;

    public SettingsPanel() {
        super();

        this.adminComponents = new ArrayList<>();

        initComponents();

        setLayout(new FlowLayout(LEFT));
        add(createPanel());
    }

    public void init() {
        usernameLabel.setText("Użytkownik: " + getContext().getUsername());

        boolean vis = getContext().getUsername().equals("admin");

        for (Component component : adminComponents) {
            component.setEnabled(vis);
        }
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

        adminComponents.addAll(Arrays.asList(newUserButton, newUserPasswordField, newUserPasswordField2, newUserTextField));

        addListeners();
    }

    private void addListeners() {
        changePasswordButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleChangePassword();
            }
        });
        newUserButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleNewUser();
            }
        });
    }

    private void handleChangePassword() {
        String oldPass = new String(oldPasswordField.getPassword());
        String newPass = new String(newPasswordField.getPassword());
        String newPass2 = new String(newPasswordField2.getPassword());
        String pwd = getContext().getPreferences().get(getContext().getUsername(), null);
        String hash;

        try {
            hash = encrypt(oldPass);
        } catch (GeneralSecurityException | UnsupportedEncodingException e1) {
            e1.printStackTrace();
            showMessageDialog(this, e1.getMessage(), "Error", ERROR_MESSAGE);
            return;
        }

        if (!hash.equals(pwd)) {
            showMessageDialog(this, "Zły login lub hasło!", "Error", ERROR_MESSAGE);
            return;
        }
        if (!newPass.equals(newPass2)) {
            showMessageDialog(this, "Hasła się nie zgadzają!", "Error", ERROR_MESSAGE);
            return;
        }
        if (newPass.length() < 3) {
            showMessageDialog(this, "Zbyt krótkie hasło!", "Error", ERROR_MESSAGE);
            return;
        }

        try {
            getContext().getPreferences().put(getContext().getUsername(), encrypt(newPass));
        } catch (GeneralSecurityException | UnsupportedEncodingException e) {
            e.printStackTrace();
            showMessageDialog(this, e.getMessage(), "Error", ERROR_MESSAGE);
            return;
        }

        oldPasswordField.setText("");
        newPasswordField.setText("");
        newPasswordField2.setText("");
        showMessageDialog(this, "Zmieniono hasło!", "", INFORMATION_MESSAGE);
    }

    private void handleNewUser() {
        String username = newUserTextField.getText();
        String pass = new String(newUserPasswordField.getPassword());
        String pass2 = new String(newUserPasswordField2.getPassword());

        if (username.length() < 4) {
            showMessageDialog(this, "Nazwa użytkownika musi mieć przynajmniej 4 litery", "Error", ERROR_MESSAGE);
            return;
        }
        if (!pass.equals(pass2)) {
            showMessageDialog(this, "Hasła się nie zgadzają", "Error", ERROR_MESSAGE);
            return;
        }
        if (pass.length() < 3) {
            showMessageDialog(this, "Zbyt krótkie hasło!", "Error", ERROR_MESSAGE);
            return;
        }

        try {
            getContext().getPreferences().put(username, encrypt(pass));
        } catch (GeneralSecurityException | UnsupportedEncodingException e) {
            e.printStackTrace();
            showMessageDialog(this, e.getMessage(), "Error", ERROR_MESSAGE);
            return;
        }

        newUserTextField.setText("");
        newUserPasswordField.setText("");
        newUserPasswordField2.setText("");
        showMessageDialog(this, "Utworzono nowego użytkownika!", "", INFORMATION_MESSAGE);
    }
}
