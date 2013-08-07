package com.truemesh.squiggle.tests;

import com.truemesh.squiggle.*;
import com.truemesh.squiggle.criteria.MatchCriteria;

import static com.truemesh.squiggle.tests.support.SqlMatcher.generatesSql;
import static org.junit.Assert.assertThat;
import org.junit.Test;

public class Example003NastyStrings {
    @Test
    public void testNastyStrings() {
        Table people = new Table("people");

        SelectQuery select = new SelectQuery();

        select.addColumn(people, "firstname");

        select.addCriteria(
                new MatchCriteria(people, "foo", MatchCriteria.GREATER, "I've got a quote"));

        assertThat(select, generatesSql(
                "SELECT\n" +
                "    people.firstname\n" +
                "FROM\n" +
                "    people\n" +
                "WHERE\n" +
                "    people.foo > 'I\\'ve got a quote'"));
    }
}