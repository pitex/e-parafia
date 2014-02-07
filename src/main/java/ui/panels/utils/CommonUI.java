package ui.panels.utils;

import db.Database;
import db.queries.QueryBuilder;
import db.utils.Tables;
import ui.InfoDialog;

import java.awt.*;
import java.sql.ResultSet;
import java.sql.SQLException;

import static db.utils.TableColumns.Common.ALL;

public class CommonUI {
    public static Dimension BUTTON_DIMENSION = new Dimension(1000, 90);

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
        c.insets = new Insets(2, 2, 3, 3);
        return c;
    }

    public static String[] tablesValuesAsStringArray(Tables[] t) {
        String s[] = new String[t.length];
        for (int i = 0; i < t.length; i++) {
            s[i] = t[i].toString();
        }
        return s;
    }

    public static void selectAllFrom(Tables t, boolean editable) {
        ResultSet rs;

        try {
            rs = Database.executeQueryWithResult(new QueryBuilder().
                    select(ALL).from(t).build());
        } catch (SQLException e1) {
            e1.printStackTrace();
            return;
        }

        InfoDialog table = new InfoDialog(t, rs, editable);
        table.setVisible(true);
    }

}
