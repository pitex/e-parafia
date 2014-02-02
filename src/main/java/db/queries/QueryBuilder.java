package db.queries;

import db.utils.QueryPair;
import db.utils.Tables;

import java.util.ArrayList;
import java.util.List;

import static db.utils.TableValues.TableValue;
import static java.lang.String.format;
import static java.util.Arrays.asList;

/**
 * @author Michał Piekarz
 */
public class QueryBuilder
{
    public QueryBuilder()
    {

    }

    public SelectBuilder select(TableValue... columns)
    {
        return select(asList(columns));
    }

    public SelectBuilder select(List<TableValue> columns)
    {
        return new SelectBuilder(columns);
    }

    public InsertBuilder insertInto(Tables table)
    {
        return new InsertBuilder(table);
    }

    public UpdateBuilder update(Tables table)
    {
        return new UpdateBuilder(table);
    }

    public DeleteBuilder deleteFrom(Tables table)
    {
        return new DeleteBuilder(table);
    }

    private class SelectBuilder
    {
        private final List<TableValue> columns;
        private String whereCondition = null;
        private Tables table;

        public SelectBuilder(List<TableValue> columns)
        {
            this.columns = new ArrayList<TableValue>();
            this.columns.addAll(columns);
        }

        public SelectBuilder from(Tables from)
        {
            this.table = from;

            return this;
        }

        public SelectBuilder where(String condition)
        {
            whereCondition = condition;

            return this;
        }

        public String build()
        {
            if (table == null)
            {
                throw new IllegalArgumentException("Must specify table to select from.");
            }

            StringBuilder sb = new StringBuilder();

            sb.append("SELECT %s");
            for (int i = 0; i < columns.size() - 1; i++)
            {
                sb.append(", %s");
            }

            sb.append(" FROM %s");
            if (whereCondition != null)
            {
                sb.append(" WHERE %s");
            }
            sb.append(";");

            String[] args = new String[columns.size() + 2];
            for (int i = 0; i < columns.size(); i++)
            {
                args[i] = columns.get(i).toString();
            }
            args[columns.size()] = table.toString();
            args[columns.size() + 1] = whereCondition;

            return format(sb.toString(), args);
        }
    }

    private class InsertBuilder
    {
        private final Tables into;
        private final List<QueryPair> pairs;

        public InsertBuilder(Tables into)
        {
            this.pairs = new ArrayList<QueryPair>();
            this.into = into;
        }

        public InsertBuilder values(QueryPair... pairs)
        {
            return values(asList(pairs));
        }

        public InsertBuilder values(List<QueryPair> pairs)
        {
            this.pairs.addAll(pairs);

            return this;
        }

        public String build()
        {
            if (pairs.size() == 0)
            {
                throw new IllegalArgumentException("Must give some values to insert.");
            }

            StringBuilder sb = new StringBuilder();
            sb.append("INSERT INTO %s(%s");
            for (int i = 0; i < pairs.size() - 1; i++)
            {
                sb.append(", %s");
            }
            sb.append(") VALUES(%s");
            for (int i = 0; i < pairs.size() - 1; i++)
            {
                sb.append(", %s");
            }
            sb.append(");");

            String[] args = new String[pairs.size() * 2 + 1];
            args[0] = into.toString();
            for (int i = 0; i < pairs.size(); i++)
            {
                args[i + 1] = pairs.get(i).getKey().toString();
                args[i + 1 + pairs.size()] = pairs.get(i).getFormattedValue();
            }

            return format(sb.toString(), args);
        }
    }

    private class UpdateBuilder
    {
        private final Tables table;
        private final List<QueryPair> setPairs;
        private String condition;

        public UpdateBuilder(Tables table)
        {
            this.setPairs = new ArrayList<QueryPair>();
            this.table = table;
        }

        public UpdateBuilder set(QueryPair... pairs)
        {
            return set(asList(pairs));
        }

        public UpdateBuilder set(List<QueryPair> pairs)
        {
            this.setPairs.addAll(pairs);

            return this;
        }

        public UpdateBuilder where(String condition)
        {
            this.condition = condition;

            return this;
        }

        public String build()
        {
            StringBuilder sb = new StringBuilder();

            if (setPairs.size() == 0)
            {
                throw new IllegalArgumentException("Must update some values.");
            }

            sb.append("UPDATE %s SET %s = %s");
            for (int i = 0; i < setPairs.size() - 1; i++)
            {
                sb.append(", %s = %s");
            }
            if (condition != null)
            {
                sb.append("WHERE %s");
            }
            sb.append(";");

            String[] args = new String[2 * setPairs.size() + 2];
            args[0] = table.toString();
            for (int i = 0; i < setPairs.size(); i++)
            {
                args[i * 2 + 1] = setPairs.get(i).getKey().toString();
                args[i * 2 + 2] = setPairs.get(i).getFormattedValue();
            }
            args[2 * setPairs.size() + 1] = condition;

            return format(sb.toString(), args);
        }
    }

    private class DeleteBuilder
    {
        private final Tables from;
        private String condition = null;

        public DeleteBuilder(Tables from)
        {
            this.from = from;
        }

        public DeleteBuilder where(String condition)
        {
            this.condition = condition;

            return this;
        }

        public String build()
        {
            StringBuilder sb = new StringBuilder();

            sb.append("DELETE FROM %s");
            if (condition != null)
            {
                sb.append(" WHERE %s");
            }
            sb.append(";");

            return format(sb.toString(), from, condition);
        }
    }
}
