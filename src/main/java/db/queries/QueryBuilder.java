package db.queries;

import db.utils.QueryPair;
import db.utils.Tables;

import java.util.ArrayList;
import java.util.List;

import static db.utils.TableValues.TableValue;
import static java.lang.String.format;
import static java.util.Arrays.asList;

/**
 * @author Katarzyna Janocha, Michał Piekarz
 */
public class QueryBuilder {

    public SelectBuilder select(TableValue... columns) {
        return select(asList(columns));
    }

    public SelectBuilder select(List<TableValue> columns) {
        return new SelectBuilder(columns);
    }

    public InsertBuilder insertInto(Tables table) {
        return new InsertBuilder(table);
    }

    public UpdateBuilder update(Tables table) {
        return new UpdateBuilder(table);
    }

    public DeleteBuilder deleteFrom(Tables table) {
        return new DeleteBuilder(table);
    }

    private class SelectBuilder {

        private final List<TableValue> columns;
        private String condition = null;
        private Tables table;

        public SelectBuilder(List<TableValue> columns) {
            this.columns = new ArrayList<TableValue>();
            this.columns.addAll(columns);
        }

        public SelectConditions from(Tables from) {
            if (from == null) {
                throw new IllegalArgumentException("Argument cannot be null");
            }

            this.table = from;

            return new SelectConditions();
        }

        private class SelectConditions {

            public SelectFinalization where(String condition) {
                SelectBuilder.this.condition = condition;

                return new SelectFinalization();
            }

            public String build() {
                StringBuilder sb = new StringBuilder();

                sb.append("SELECT %s");
                for (int i = 0; i < columns.size() - 1; i++) {
                    sb.append(", %s");
                }

                sb.append(" FROM %s");
                if (SelectBuilder.this.condition != null) {
                    sb.append(" WHERE %s");
                }
                sb.append(";");

                String[] args = new String[columns.size() + 2];
                for (int i = 0; i < columns.size(); i++) {
                    args[i] = columns.get(i).toString();
                }
                args[columns.size()] = table.toString();
                args[columns.size() + 1] = SelectBuilder.this.condition;

                return format(sb.toString(), args);
            }

            private class SelectFinalization {

                public String build() {
                    return SelectConditions.this.build();
                }
            }
        }
    }

    private class InsertBuilder {
        private final Tables into;
        private final List<QueryPair> pairs;

        public InsertBuilder(Tables into) {
            this.pairs = new ArrayList<QueryPair>();
            this.into = into;
        }

        public InsertFinalization values(QueryPair... pairs) {
            return values(asList(pairs));
        }

        public InsertFinalization values(List<QueryPair> pairs) {
            if (pairs == null || pairs.size() == 0) {
                throw new IllegalArgumentException("Must give some values to insert.");
            }

            this.pairs.addAll(pairs);

            return new InsertFinalization();
        }

        private class InsertFinalization {

            public String build() {
                StringBuilder sb = new StringBuilder();
                sb.append("INSERT INTO %s(%s");
                for (int i = 0; i < pairs.size() - 1; i++) {
                    sb.append(", %s");
                }
                sb.append(") VALUES(%s");
                for (int i = 0; i < pairs.size() - 1; i++) {
                    sb.append(", %s");
                }
                sb.append(");");

                String[] args = new String[pairs.size() * 2 + 1];
                args[0] = into.toString();
                for (int i = 0; i < pairs.size(); i++) {
                    args[i + 1] = pairs.get(i).getKey().toString();
                    args[i + 1 + pairs.size()] = pairs.get(i).getFormattedValue();
                }

                return format(sb.toString(), args);
            }
        }
    }

    private class UpdateBuilder {

        private final Tables table;
        private final List<QueryPair> setPairs;
        private String condition;

        public UpdateBuilder(Tables table) {
            this.setPairs = new ArrayList<QueryPair>();
            this.table = table;
        }

        public UpdateConditions set(QueryPair... pairs) {
            return set(asList(pairs));
        }

        public UpdateConditions set(List<QueryPair> pairs) {
            if (pairs == null || pairs.size() == 0) {
                throw new IllegalArgumentException("Must update some values.");
            }

            this.setPairs.addAll(pairs);

            return new UpdateConditions();
        }

        private class UpdateConditions {

            public UpdateFinalization where(String condition) {
                UpdateBuilder.this.condition = condition;

                return new UpdateFinalization();
            }

            public String build() {
                StringBuilder sb = new StringBuilder();

                sb.append("UPDATE %s SET %s = %s");
                for (int i = 0; i < setPairs.size() - 1; i++) {
                    sb.append(", %s = %s");
                }
                if (condition != null) {
                    sb.append("WHERE %s");
                }
                sb.append(";");

                String[] args = new String[2 * setPairs.size() + 2];
                args[0] = table.toString();
                for (int i = 0; i < setPairs.size(); i++) {
                    args[i * 2 + 1] = setPairs.get(i).getKey().toString();
                    args[i * 2 + 2] = setPairs.get(i).getFormattedValue();
                }
                args[2 * setPairs.size() + 1] = condition;

                return format(sb.toString(), args);
            }

            private class UpdateFinalization {

                public String build() {
                    return UpdateConditions.this.build();
                }
            }
        }
    }

    private class DeleteBuilder {
        private final Tables from;
        private String condition = null;

        public DeleteBuilder(Tables from) {
            this.from = from;
        }

        public DeleteFinalization where(String condition) {
            this.condition = condition;

            return new DeleteFinalization();
        }

        private class DeleteFinalization {

            public String build() {
                StringBuilder sb = new StringBuilder();

                sb.append("DELETE FROM %s");
                if (condition != null) {
                    sb.append(" WHERE %s");
                }
                sb.append(";");

                return format(sb.toString(), from, condition);
            }
        }
    }
}
