package com.truemesh.squiggle.tests;

import com.truemesh.squiggle.*;
import com.truemesh.squiggle.criteria.InCriteria;
import com.truemesh.squiggle.criteria.MatchCriteria;

import static com.truemesh.squiggle.tests.support.SqlMatcher.generatesSql;
import static org.junit.Assert.assertThat;
import org.junit.Test;

public class Example007SubSelect {
    @Test
    public void testSubSelect() {
        Table people = new Table("people");
        Table taxcodes = new Table("taxcodes");

        SelectQuery select = new SelectQuery();
        select.addColumn(people, "firstname");

        SelectQuery subSelect = new SelectQuery();
        subSelect.addColumn(taxcodes, "id");
        subSelect.addCriteria(new MatchCriteria(taxcodes, "valid", MatchCriteria.EQUALS, true));

        select.addCriteria(new InCriteria(people, "taxcode", subSelect));

        assertThat(select, generatesSql(
                "SELECT " +
                "    people.firstname " +
                "FROM " +
                "    people " +
                "WHERE " +
                "    people.taxcode IN ( " +
                "        SELECT " +
                "            taxcodes.id " +
                "        FROM " +
                "            taxcodes " +
                "        WHERE " +
                "            taxcodes.valid = true " +
                "    )"));
    }
}
