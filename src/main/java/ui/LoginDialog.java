package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;

import static java.awt.Dialog.ModalityType.APPLICATION_MODAL;
import static javax.swing.JOptionPane.ERROR_MESSAGE;
import static javax.swing.JOptionPane.showMessageDialog;
import static model.Context.getContext;
import static security.Encoder.encrypt;

/**
 * @author Katarzyna Janocha, Michał Piekarz
 */
public class LoginDialog extends JDialog {

    private JTextField loginTextField;
    private JPasswordField pwdTextField;
    private JButton loginButton;
    private JButton cancelButton;

    public LoginDialog(Frame owner) {
        super(owner, "Login");

        setModalityType(APPLICATION_MODAL);
        setLocationRelativeTo(owner);

        initPreferences();

        createComponents();

        createContentPane();

        pack();
    }

    private void initPreferences() {
        if (getContext().getPreferences().get("admin", null) == null) {
            try {
                getContext().getPreferences().put("admin", encrypt("admin"));
            } catch (GeneralSecurityException | UnsupportedEncodingException e) {
                e.printStackTrace();
                showMessageDialog(this, e.getMessage(), "Error", ERROR_MESSAGE);
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

        String pwd = getContext().getPreferences().get(username, null);
        String hash;

        try {
            hash = encrypt(pass);
        } catch (GeneralSecurityException | UnsupportedEncodingException e1) {
            e1.printStackTrace();
            showMessageDialog(this, e1.getMessage(), "Error", ERROR_MESSAGE);
            return;
        }

        if (!hash.equals(pwd)) {
            JOptionPane.showMessageDialog(this, "Wrong login or password", "Error", JOptionPane.ERROR_MESSAGE);
        } else {
            getContext().setUsername(username);
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
