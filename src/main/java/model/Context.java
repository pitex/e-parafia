package model;

import java.util.prefs.Preferences;

import static java.util.prefs.Preferences.userNodeForPackage;

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
