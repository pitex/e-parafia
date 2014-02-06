package model;

import java.util.prefs.Preferences;

/**
 * @author Katarzyna Janocha, Michał Piekarz
 */
public class Context {

    private final Preferences preferences;
    private String username;

    public Context(Preferences preferences) {
        this.preferences = preferences;
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
