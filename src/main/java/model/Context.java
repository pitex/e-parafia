package model;

import com.sun.corba.se.spi.orb.StringPair;

import javax.swing.*;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.prefs.Preferences;

import static java.sql.DriverManager.getConnection;
import static java.util.prefs.Preferences.userNodeForPackage;
import static javax.swing.BoxLayout.Y_AXIS;
import static javax.swing.JOptionPane.*;

/**
 * @author Katarzyna Janocha, Michał Piekarz
 */
public class Context {

    private static Context context = new Context();
    private final Preferences preferences;
    private String username;

    private Context() {
        this.preferences = userNodeForPackage(Context.class);


    }

    public static Context getContext() {
        return context;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Preferences getPreferences() {
        return preferences;
    }
}
