package db.utils;

import static db.utils.ColumnTypes.*;
import static db.utils.TableColumns.TableColumn;

/**
 * @author Katarzyna Janocha, Michał Piekarz
 */
public class QueryPair {
    private final TableColumn key;
    private final Object value;

    public QueryPair(TableColumn key, Object value) {
        this.key = key;
        this.value = value;
    }

    public QueryPair(String key, Object value) {
        this.key = getColumn(key);
        this.value = value;
    }

    private TableColumn getColumn(String name) {
        Class<?> clazz = TableColumns.class;

        for (Class c : clazz.getClasses()) {
            if (TableColumn.class.isAssignableFrom(c)) {
                try {
                    return (TableColumn) Enum.valueOf(c, name.toUpperCase());
                } catch (Exception ignore) {
                }
            }
        }

        return null;
    }

    public TableColumn getKey() {
        return key;
    }

    public String getValue() {
        return value.toString();
    }

    public String getFormattedValue() {
        switch (key.getType()) {
            case STRING:
                return "'" + value.toString() + "'";
            case BOOLEAN:
                if (value.toString().toLowerCase().equals("true")) {
                    return "TRUE";
                }
                return "FALSE";
            case INTEGER:
                return value.toString();
            case DATE:
                return "date '" + value.toString() + "'";
        }
        return value.toString();
    }
}
