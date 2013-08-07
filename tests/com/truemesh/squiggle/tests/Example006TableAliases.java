package com.truemesh.squiggle.tests;

import com.truemesh.squiggle.*;
import static com.truemesh.squiggle.tests.support.SqlMatcher.generatesSql;
import org.junit.Test;
import static org.junit.Assert.assertThat;

public class Example006TableAliases {
    @Test
    public void tableAliases() {
        Table employees = new Table("people", "employees");
        Table managers = new Table("people", "managers");

        SelectQuery select = new SelectQuery(); // base table

        select.addColumn(employees, "firstname");
        select.addColumn(managers, "firstname");

        select.addJoin(employees, "managerID", managers, "id");

        assertThat(select, generatesSql(
                "SELECT " +
                "    employees.firstname , " +
                "    managers.firstname " +
                "FROM " +
                "    people employees , " +
                "    people managers " +
                "WHERE " +
                "    employees.managerID = managers.id"));
    }
}