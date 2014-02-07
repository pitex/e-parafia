package ui;

import db.utils.QueryPair;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

/**
 * @author Katarzyna Janocha, Michał Piekarz
 *         Table showing selected rows.
 */
public class InfoTable extends JPanel {

    private JTable table;
    private JButton editButton;
    private JButton removeButton;

    public InfoTable(ResultSet rowData) {
        super();

        createComponents(rowData);

        initUI();
    }

    private static TableModel buildTableModel(ResultSet rs) {

        try {
            ResultSetMetaData metaData = rs.getMetaData();

            Vector<String> columnNames = new Vector<>();
            int columnCount = metaData.getColumnCount();
            for (int column = 1; column <= columnCount; column++) {
                columnNames.add(metaData.getColumnName(column));
            }

            Vector<Vector<Object>> data = new Vector<>();
            while (rs.next()) {
                Vector<Object> vector = new Vector<>();
                for (int columnIndex = 1; columnIndex <= columnCount; columnIndex++) {
                    vector.add(rs.getObject(columnIndex));
                }
                data.add(vector);
            }

            return new InfoTableModel(data, columnNames);

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    private void createComponents(ResultSet rowData) {
        editButton = new JButton("EDYTUJ");
        removeButton = new JButton("USUŃ");
        table = new JTable(buildTableModel(rowData));
        table.setRowSelectionAllowed(true);

        editButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                edit();
            }
        });
        removeButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                remove();
            }
        });
    }

    private void initUI() {

    }

    private void edit() {

    }

    private void remove() {
    }

    @SuppressWarnings("unchecked")
    private List<QueryPair> getSelectedRow() {
        int row = table.getSelectedRow();
        Vector<Vector> vec = ((InfoTableModel)table.getModel()).getDataVector();
        List<QueryPair> pairs = new ArrayList<>();

        for (int i=0; i<table.getModel().getColumnCount(); i++) {
            pairs.add(new QueryPair(table.getModel().getColumnName(i), vec.get(row).get(i)));
        }

        return pairs;
    }

    private static class InfoTableModel extends DefaultTableModel {

        public InfoTableModel(Vector data, Vector columnNames) {
            super(data, columnNames);
        }

        @Override
        public boolean isCellEditable(int row, int column) {
            return false;
        }

        public void removeRow(int row) {
            dataVector.remove(row);
        }
    }
}
