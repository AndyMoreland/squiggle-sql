package com.truemesh.squiggle.tests;

import static com.truemesh.squiggle.tests.support.SqlMatcher.generatesSql;
import static org.junit.Assert.assertThat;

import org.junit.Test;

import com.truemesh.squiggle.Order;
import com.truemesh.squiggle.SelectQuery;
import com.truemesh.squiggle.Table;

public class Example001SimplestSelect {
    @Test
    public void simpleSelect() {
        Table people = new Table("people");

        SelectQuery select = new SelectQuery();

        select.addColumn(people, "firstname");
        select.addColumn(people, "lastname");

        select.addOrder(people, "age", Order.DESCENDING);

        assertThat(select, generatesSql(
                "SELECT  people.firstname , people.lastname " +
                "FROM people " +
                "ORDER BY people.age DESC"));
    }
}
