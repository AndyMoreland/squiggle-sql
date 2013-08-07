package com.truemesh.squiggle.tests;

import com.truemesh.squiggle.*;
import static com.truemesh.squiggle.tests.support.SqlMatcher.generatesSql;
import org.junit.Test;
import static org.junit.Assert.assertThat;

public class Example004SelectWildcardColumn {
    @Test
    public void selectWildcardColumn() {
        Table people = new Table("people");

        SelectQuery select = new SelectQuery();

        select.addToSelection(people.getWildcard());

        assertThat(select, generatesSql(
                "SELECT " +
                 "    people.* " +
                 "FROM " +
                 "    people"));
    }
}
