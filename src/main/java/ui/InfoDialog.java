package ui;

import db.Database;
import db.queries.QueryBuilder;
import db.utils.QueryPair;
import db.utils.Tables;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import static db.utils.TableColumns.TableColumn;
import static java.awt.BorderLayout.EAST;
import static java.awt.BorderLayout.NORTH;
import static java.awt.Dialog.ModalityType.APPLICATION_MODAL;
import static java.awt.FlowLayout.RIGHT;
import static javax.swing.JOptionPane.*;

/**
 * @author Katarzyna Janocha, Michał Piekarz
 *         Table showing selected rows.
 */
public class InfoDialog extends JDialog {

    private Tables tableName;
    private JTable table;
    private JButton editButton;
    private JButton removeButton;
    private boolean editable;

    public InfoDialog(Tables tableName, ResultSet rowData, boolean editable) {
        super();

        this.tableName = tableName;
        this.editable = editable;

        setModalityType(APPLICATION_MODAL);

        createComponents(rowData);

        initUI();
    }

    public InfoDialog(Tables tableName, ResultSet rowData) {
        this(tableName, rowData, true);
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

        editButton.setEnabled(editable);
        removeButton.setEnabled(editable);

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
        setLayout(new BorderLayout());

        JPanel panel = new JPanel(new FlowLayout(RIGHT, 5, 5));
        panel.add(editButton);
        panel.add(removeButton);

        add(table, NORTH);
        add(panel, EAST);

        pack();
    }

    private void edit() {
        List<QueryPair> pairs = getSelectedRow();
        TableColumn[] names = new TableColumn[pairs.size()];

        for (int i = 0; i < pairs.size(); i++) {
            names[i] = pairs.get(i).getKey();
        }

        EditDialog dialog = new EditDialog((Frame) this.getOwner(), tableName, pairs, names);
        dialog.setVisible(true);
    }

    private void remove() {
        int it = table.getSelectedRow();

        List<QueryPair> row = getSelectedRow();
        QueryBuilder qb = new QueryBuilder();
        String query = qb.deleteFrom(tableName).where(row.get(0).getKey() + " = " + row.get(0).getFormattedValue()).build();

        try {
            Database.executeQuery(query);
            showMessageDialog(this, "Usunięto!", "EUREKA", INFORMATION_MESSAGE);
        } catch (SQLException e) {
            e.printStackTrace();
            showMessageDialog(this, e.getMessage(), "ERROR", ERROR_MESSAGE);
        }

        ((InfoTableModel) table.getModel()).removeRow(it);
        table.revalidate();
        this.revalidate();
    }

    @SuppressWarnings("unchecked")
    private List<QueryPair> getSelectedRow() {
        int row = table.getSelectedRow();
        Vector<Vector> vec = ((InfoTableModel) table.getModel()).getDataVector();
        List<QueryPair> pairs = new ArrayList<>();

        for (int i = 0; i < table.getModel().getColumnCount(); i++) {
            Object value = vec.get(row).get(i);
            if(value == null) {
                value = "";
            }

            pairs.add(new QueryPair(table.getModel().getColumnName(i), value));
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
