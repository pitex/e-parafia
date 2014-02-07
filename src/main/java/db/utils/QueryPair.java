package db.utils;

import static db.utils.ColumnTypes.*;

/**
 * @author Katarzyna Janocha, Michał Piekarz
 */
public class QueryPair {
	private final TableColumns.TableColumn key;
	private final Object value;

	public QueryPair(TableColumns.TableColumn key, Object value) {
		this.key = key;
		this.value = value;
	}

	public TableColumns.TableColumn getKey() {
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
			if ((Boolean) value) {
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
