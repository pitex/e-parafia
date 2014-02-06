package ui;

import model.Context;
import security.Encoder;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;
import java.util.prefs.Preferences;

import static java.awt.Dialog.ModalityType.APPLICATION_MODAL;

/**
 * @author Katarzyna Janocha, Michał Piekarz
 */
public class LoginDialog extends JDialog {

    private final Preferences pref = Preferences.userNodeForPackage(LoginDialog.class);
    private final Context context;
    private JTextField loginTextField;
    private JPasswordField pwdTextField;
    private JButton loginButton;
    private JButton cancelButton;

    public LoginDialog(Frame owner, Context context) {
        super(owner, "Login");
        this.context = context;

        setModalityType(APPLICATION_MODAL);
        setLocationRelativeTo(owner);

        initPreferences();

        createComponents();

        createContentPane();

        pack();
    }

    private void initPreferences() {
        if (pref.get("admin", null) == null) {
            try {
                pref.put("admin", Encoder.encrypt("admin"));
            }
            catch (GeneralSecurityException | UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
    }

    private void createComponents() {
        loginTextField = new JTextField(20);
        pwdTextField = new JPasswordField(20);
        loginButton = new JButton("Login");
        cancelButton = new JButton("Cancel");

        KeyAdapter adapter = new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    checkCredentials();
                }
            }
        };

        loginTextField.addKeyListener(adapter);
        pwdTextField.addKeyListener(adapter);

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                checkCredentials();
            }
        });
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                LoginDialog.this.dispose();
            }
        });
    }

    private void checkCredentials() {
        String username = loginTextField.getText();
        String pass = new String(pwdTextField.getPassword());

        String pwd = pref.get(username, null);
        String hash = "";

        try {
            hash = Encoder.encrypt(pass);
        }
        catch (GeneralSecurityException | UnsupportedEncodingException e1) {
            e1.printStackTrace();
        }

        if (!hash.equals(pwd)) {
            JOptionPane.showMessageDialog(this, "Wrong login or password", "Error", JOptionPane.ERROR_MESSAGE);
        } else {
            context.username = username;
            getOwner().setVisible(true);
            dispose();
        }
    }

    private void createContentPane() {
        JPanel contentPane = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        c.insets = new Insets(3, 3, 2, 2);
        c.anchor = GridBagConstraints.NORTH;
        c.gridwidth = 2;
        c.gridx = 1;

        contentPane.add(loginTextField, c);
        contentPane.add(pwdTextField, c);
        c.gridwidth = 1;
        c.weightx = 1;
        contentPane.add(loginButton, c);
        c.gridx = 2;
        contentPane.add(cancelButton, c);

        c.gridx = 0;
        c.weightx = 0;
        c.anchor = GridBagConstraints.EAST;
        contentPane.add(new JLabel("Login:"), c);
        contentPane.add(new JLabel("Hasło:"), c);

        setContentPane(contentPane);
    }

    public void addCancelButtonListener(ActionListener l) {
        cancelButton.addActionListener(l);
    }
}
