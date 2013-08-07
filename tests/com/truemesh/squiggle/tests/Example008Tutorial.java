package com.truemesh.squiggle.tests;

import com.truemesh.squiggle.SelectQuery;
import com.truemesh.squiggle.Table;
import com.truemesh.squiggle.criteria.InCriteria;
import com.truemesh.squiggle.criteria.MatchCriteria;

import static com.truemesh.squiggle.tests.support.SqlMatcher.generatesSql;
import org.junit.Test;
import static org.junit.Assert.assertThat;

public class Example008Tutorial {
    @Test
    public void tutorial() {
        // basic query
    	SelectQuery select = new SelectQuery();

        // add columns
    	Table orders = new Table("orders_table");
        select.addColumn(orders, "id");
        select.addColumn(orders, "total_price");

        // matches
        select.addCriteria(new MatchCriteria(orders, "status", MatchCriteria.EQUALS, "processed"));
        select.addCriteria(new MatchCriteria(orders, "items", MatchCriteria.LESS, 5));

        // IN...
        select.addCriteria(new InCriteria(orders, "delivery",
            new String[] { "post", "fedex", "goat" } ));

        // join
        Table warehouses = new Table("warehouses_table");
        select.addJoin(orders, "warehouse_id", warehouses, "id");

        // use joined table
        select.addColumn(warehouses, "location");
        select.addCriteria(new MatchCriteria(warehouses, "size", MatchCriteria.EQUALS, "big"));

        // build subselect query
        SelectQuery subSelect = new SelectQuery();
        Table offers = new Table("offers_table");
        subSelect.addColumn(offers, "location");
        subSelect.addCriteria(new MatchCriteria(offers, "valid", MatchCriteria.EQUALS, true));

        // add subselect to original query
        select.addCriteria(new InCriteria(warehouses, "location", subSelect));

        assertThat(select, generatesSql(
                "SELECT " +
                "    orders_table.id , " +
                "    orders_table.total_price , " +
                "    warehouses_table.location  " +
                "FROM " +
                "    orders_table , " +
                "    warehouses_table  " +
                "WHERE " +
                "    orders_table.status = 'processed' AND " +
                "    orders_table.items < 5 AND " +
                "    orders_table.delivery IN ( " +
                "        'post', 'fedex', 'goat' " +
                "    ) AND " +
                "    orders_table.warehouse_id = warehouses_table.id AND " +
                "    warehouses_table.size = 'big' AND " +
                "    warehouses_table.location IN ( " +
                "        SELECT " +
                "            offers_table.location  " +
                "        FROM " +
                "            offers_table  " +
                "        WHERE " +
                "            offers_table.valid = true  " +
                "    )"));
    }
}