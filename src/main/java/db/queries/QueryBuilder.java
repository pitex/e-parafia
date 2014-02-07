package db.queries;

import db.utils.QueryPair;
import db.utils.TableColumns;
import db.utils.Tables;

import java.util.ArrayList;
import java.util.List;

import static java.lang.String.format;
import static java.util.Arrays.asList;

/**
 * @author Katarzyna Janocha, Michał Piekarz
 */
public class QueryBuilder {

    public SelectBuilder select(TableColumns.TableColumn... columns) {
        return select(asList(columns));
    }

    public SelectBuilder select(List<TableColumns.TableColumn> columns) {
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

    public class SelectBuilder {

        private final List<TableColumns.TableColumn> columns;
        private String condition = null;
        private Tables table;

        private SelectBuilder(List<TableColumns.TableColumn> columns) {
            this.columns = new ArrayList<>();
            this.columns.addAll(columns);
        }

        public SelectConditions from(Tables from) {
            if (from == null) {
                throw new IllegalArgumentException("Argument cannot be null");
            }

            this.table = from;

            return new SelectConditions();
        }

        public class SelectConditions {

            private SelectConditions() {
            }

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

                Object[] args = new Object[columns.size() + 2];
                for (int i = 0; i < columns.size(); i++) {
                    args[i] = columns.get(i).toString();
                }
                args[columns.size()] = table.toString();
                args[columns.size() + 1] = SelectBuilder.this.condition;

                return format(sb.toString(), args);
            }

            public class SelectFinalization {

                private SelectFinalization() {
                }

                public String build() {
                    return SelectConditions.this.build();
                }
            }
        }
    }

    public class InsertBuilder {
        private final Tables into;
        private final List<QueryPair> pairs;

        private InsertBuilder(Tables into) {
            this.pairs = new ArrayList<>();
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

        public class InsertFinalization {

            private InsertFinalization() {
            }

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

                Object[] args = new Object[pairs.size() * 2 + 1];
                args[0] = into.toString();
                for (int i = 0; i < pairs.size(); i++) {
                    args[i + 1] = pairs.get(i).getKey().toString();
                    args[i + 1 + pairs.size()] = pairs.get(i).getFormattedValue();
                }

                return format(sb.toString(), args);
            }
        }
    }

    public class UpdateBuilder {

        private final Tables table;
        private final List<QueryPair> setPairs;
        private String condition;

        private UpdateBuilder(Tables table) {
            this.setPairs = new ArrayList<>();
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

        public class UpdateConditions {

            private UpdateConditions() {
            }

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

                Object[] args = new Object[2 * setPairs.size() + 2];
                args[0] = table.toString();
                for (int i = 0; i < setPairs.size(); i++) {
                    args[i * 2 + 1] = setPairs.get(i).getKey().toString();
                    args[i * 2 + 2] = setPairs.get(i).getFormattedValue();
                }
                args[2 * setPairs.size() + 1] = condition;

                return format(sb.toString(), args);
            }

            public class UpdateFinalization {

                private UpdateFinalization() {
                }

                public String build() {
                    return UpdateConditions.this.build();
                }
            }
        }
    }

    public class DeleteBuilder {
        private final Tables from;
        private String condition = null;

        private DeleteBuilder(Tables from) {
            this.from = from;
        }

        public DeleteFinalization where(String condition) {
            this.condition = condition;

            return new DeleteFinalization();
        }

        public class DeleteFinalization {

            private DeleteFinalization() {
            }

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
