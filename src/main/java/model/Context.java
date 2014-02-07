package model;

import java.sql.Connection;
import java.util.prefs.Preferences;

/**
 * @author Katarzyna Janocha, Michał Piekarz
 */
public class Context {

    private static Context context = new Context();
    private final Preferences preferences;
    private Connection connection;
    private String username;

    public static Context getContext() {
        return context;
    }

    private Context() {
        this.preferences = Preferences.userNodeForPackage(Context.class);
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
