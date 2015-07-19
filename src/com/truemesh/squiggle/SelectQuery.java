package com.truemesh.squiggle;

import static com.truemesh.squiggle.Projection.NONE;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import com.truemesh.squiggle.output.Output;
import com.truemesh.squiggle.output.Outputable;
import com.truemesh.squiggle.output.ToStringer;

public class SelectQuery implements Outputable
{
    private final Table baseTable;
    private final List<Column> columns;
    private boolean isDistinct = false;
    private final List<Criteria> criteria;
    private final List<Order> order;
    private Integer firstResult = null;
    private Integer maxResults = null;
    private Projection projection = NONE;
    private String sumCol = "";
    private String maxCol = "";

    public SelectQuery(Table baseTable)
    {
        this.baseTable = baseTable;
        columns = new ArrayList<Column>();
        criteria = new ArrayList<Criteria>();
        order = new ArrayList<Order>();
    }

    public void addColumn(Column column)
    {
        columns.add(column);
    }

    /**
     * Syntax sugar for addColumn(Column).
     */
    public void addColumn(Table table, String colName)
    {
        addColumn(table.getColumn(colName));
    }

    public void addCriteria(Criteria criteria)
    {
        this.criteria.add(criteria);
    }

    /**
     * Syntax sugar for addCriteria(JoinCriteria)
     */
    public void addJoin(Table srcTable, String srcColName, Table destTable, String destColName)
    {
        addCriteria(new JoinCriteria(srcTable.getColumn(srcColName), destTable.getColumn(destColName)));
    }

    public void addOrder(Order order)
    {
        this.order.add(order);
    }

    /**
     * Syntax sugar for addOrder(Order).
     */
    public void addOrder(Table table, String colName, boolean ascending)
    {
        addOrder(new Order(table.getColumn(colName), ascending));
    }

    public Table getBaseTable()
    {
        return baseTable;
    }

    public Integer getFirstResult()
    {
        return firstResult;
    }

    public Integer getMaxResults()
    {
        return maxResults;
    }

    public Projection getProjection()
    {
        return projection;
    }

    public String getSumCol()
    {
        return sumCol;
    }

    public boolean isDistinct()
    {
        return isDistinct;
    }

    public List listColumns()
    {
        return Collections.unmodifiableList(columns);
    }

    public List listCriteria()
    {
        return Collections.unmodifiableList(criteria);
    }

    public List listOrder()
    {
        return Collections.unmodifiableList(order);
    }

    public void removeColumn(Column column)
    {
        columns.remove(column);
    }

    public void removeCriteria(Criteria criteria)
    {
        this.criteria.remove(criteria);
    }

    public void removeFirstResult()
    {
        this.firstResult = null;
    }

    public void removeMaxResults()
    {
        this.maxResults = null;
    }

    public void removeOrder(Order order)
    {
        this.order.remove(order);
    }

    public void setDistinct(boolean distinct)
    {
        isDistinct = distinct;
    }

    public void setFirstResult(final Integer firstResult)
    {
        this.firstResult = firstResult;
    }

    public void setMaxResults(final Integer maxResults)
    {
        this.maxResults = maxResults;
    }

    public void setProjection(final Projection projection)
    {
        this.projection = projection;
    }

    public void setSumCol(final String sumCol)
    {
        this.sumCol = sumCol;
    }

    public void setMaxCol(final String colName)
    {
        this.maxCol = colName;
    }

    public void setSumProjection(final String colName)
    {
        setProjection(Projection.SUM);
        setSumCol(colName);
    }

    public void setMaxProjection(final String colName)
    {
        setProjection(Projection.MAX);
        setMaxCol(colName);
    }

    @Override
    public String toString()
    {
        return ToStringer.toString(this).trim();
    }

    public void write(Output out)
    {
        out.print("SELECT ");
        if (isDistinct)
        {
            out.print(" DISTINCT ");
        }

        switch (getProjection())
        {
            case NONE:
                // Add columns to select
                out.indent();
                appendList(out, columns, ",");
                out.unindent();
                break;
            case COUNT:
                out.print("COUNT(*) ");
                break;
            case SUM:
                out.print("SUM(").print(sumCol).print(") ");
                break;
            case MAX:
                out.print("MAX(").print(maxCol).print(") ");
                break;
        }

        // Add tables to select from
        out.print("FROM ");

        // Determine all tables used in query
        out.indent();
        appendList(out, findAllUsedTables(), ",");
        out.unindent();

        // Add criteria
        if (criteria.size() > 0)
        {
            out.print("WHERE ");
            out.indent();
            appendList(out, criteria, "AND ");
            out.unindent();
        }

        // Add order
        if (order.size() > 0)
        {
            out.print("ORDER BY ");
            out.indent();
            appendList(out, order, ",");
            out.unindent();
        }

        // Add limit
        if (null != getMaxResults())
        {
            out.print("LIMIT ");
            if (null != getFirstResult())
            {
                out.print(getFirstResult());
                out.print(',');
            }
            out.print(getMaxResults());
        }
    }

    /**
     * Iterate through a Collection and append all entries (using .toString())
     * to a StringBuffer.
     */
    private void appendList(Output out, Collection collection, String separator)
    {
        Iterator i = collection.iterator();
        boolean hasNext = i.hasNext();

        while (hasNext)
        {
            Outputable curr = (Outputable) i.next();
            hasNext = i.hasNext();
            curr.write(out);
            out.print(' ');
            if (hasNext)
            {
                out.print(separator);
            }
        }
    }

    /**
     * Find all the tables used in the query (from columns, criteria and order).
     * 
     * @return List of {@link com.truemesh.squiggle.Table}s
     */
    private List findAllUsedTables()
    {
        List<Table> allTables = new ArrayList<Table>();
        allTables.add(baseTable);
        return allTables;
    }
}
