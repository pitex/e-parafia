package db.utils;

import static db.utils.TableValues.TableValue;

/**
 * @author Michał Piekarz
 */
public class QueryPair
{
    private final TableValue key;
    private final String value;

    public QueryPair(TableValue key, String value)
    {
        this.key = key;
        this.value = value;
    }

    public TableValue getKey()
    {
        return key;
    }

    public String getValue()
    {
        return value;
    }
}
