package db;

import com.sun.corba.se.spi.orb.StringPair;

import javax.swing.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.prefs.Preferences;

import static java.sql.DriverManager.getConnection;
import static javax.swing.BoxLayout.Y_AXIS;
import static javax.swing.JOptionPane.*;

/**
 * @author Katarzyna Janocha, Michał Piekarz
 */
public class Database {

    private static final String USER = "psqlu";
    private static final String PASS = "psqlp";

    public static ResultSet executeQueryWithResult(String query) throws SQLException {
        Preferences preferences = Preferences.userNodeForPackage(Database.class);
        if (preferences.get(USER, null) == null) {
            askForCredentials(preferences);
        }

        Connection connection;
        Statement statement;
        ResultSet resultSet;

        connection = getConnection("jdbc:postgresql://localhost/e-parafia",
                preferences.get(USER, null), preferences.get(PASS, null));
        statement = connection.createStatement();
        resultSet = statement.executeQuery(query);


        connection.close();
        statement.close();

        return resultSet;
    }

    public static void executeQuery(String query) throws SQLException {
        Preferences preferences = Preferences.userNodeForPackage(Database.class);
        if (preferences.get(USER, null) == null) {
            askForCredentials(preferences);
        }

        Connection connection;
        Statement statement;

        connection = getConnection("jdbc:postgresql://localhost/e-parafia",
                preferences.get(USER, null), preferences.get(PASS, null));
        statement = connection.createStatement();
        statement.executeQuery(query);

        connection.close();
        statement.close();
    }

    private static void askForCredentials(Preferences preferences) {
        while (true) {
            StringPair credentials = getLoginCredentials();
            Connection connection;

            try {
                connection = getConnection("jdbc:postgresql://localhost/e-parafia", credentials.getFirst(), credentials.getSecond());

                preferences.put(USER, credentials.getFirst());
                preferences.put(PASS, credentials.getSecond());

                connection.close();

                break;
            } catch (SQLException e) {
                showErrorMessage(e);
            }
        }
    }

    private static StringPair getLoginCredentials() {
        String username = showInputDialog("Użytkowink (Baza Danych)");
        String password = "";

        JPasswordField pf = new JPasswordField();

        JPanel panel = new JPanel();
        BoxLayout layout = new BoxLayout(panel, Y_AXIS);
        panel.setLayout(layout);

        panel.add(new JLabel("Hasło do bazy"));
        panel.add(pf);

        int okCxl = showConfirmDialog(null, panel, "Hasło do bazy", OK_CANCEL_OPTION, QUESTION_MESSAGE);

        if (okCxl == OK_OPTION) {
            password = new String(pf.getPassword());
        }

        return new StringPair(username, password);
    }

    private static void showErrorMessage(Exception e) {
        e.printStackTrace();
        showMessageDialog(null, e.getMessage(), "ERROR", ERROR_MESSAGE);
    }
}
