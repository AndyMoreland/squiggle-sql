package com.truemesh.squiggle.tests;

import static org.junit.Assert.assertThat;

import org.junit.Test;

import com.truemesh.squiggle.SelectQuery;
import com.truemesh.squiggle.Table;
import com.truemesh.squiggle.tests.support.SqlMatcher;
import com.truemesh.squiggle.criteria.BetweenCriteria;
import com.truemesh.squiggle.criteria.InCriteria;
import com.truemesh.squiggle.criteria.IsNotNullCriteria;
import com.truemesh.squiggle.criteria.IsNullCriteria;
import com.truemesh.squiggle.criteria.MatchCriteria;

public class Example002WhereCriteria {
	@Test
    public void whereCriteria() {
        Table people = new Table("people");

        SelectQuery select = new SelectQuery();

        select.addColumn(people, "firstname");
        select.addColumn(people, "lastname");

        select.addCriteria(
                new MatchCriteria(people, "height", MatchCriteria.GREATER, 1.8));
        select.addCriteria(
                new InCriteria(people, "department", new String[] {"I.T.", "Cooking"}));
        select.addCriteria(
                new BetweenCriteria(people.getColumn("age"), 18, 30));

        assertThat(select, SqlMatcher.generatesSql(
                "SELECT " +
                "    people.firstname , " +
                "    people.lastname " +
                "FROM " +
                "    people " +
                "WHERE " +
                "    people.height > 1.8 AND " +
                "    people.department IN ( " +
                "        'I.T.', 'Cooking' " +
                "    ) AND" +
                "    people.age BETWEEN 18 AND 30"));

    }
	
	@Test
    public void nullCriteria() {
        Table people = new Table("people");
        
        SelectQuery select = new SelectQuery();
        
        select.addToSelection(people.getWildcard());

        select.addCriteria(
        		new IsNullCriteria(people.getColumn("name")));
        select.addCriteria(
                new IsNotNullCriteria(people.getColumn("age")));

        assertThat(select, SqlMatcher.generatesSql(
                "SELECT " +
                "    people.* " +
                "FROM " +
                "    people " +
                "WHERE " +
                "    people.name IS NULL AND " +
                "    people.age IS NOT NULL"));
    }

    @Test
    public void betweenCriteriaWithColumns() {
        Table rivers = new Table("rivers");

        SelectQuery select = new SelectQuery();

        select.addColumn(rivers, "name");
        select.addColumn(rivers, "level");

        select.addCriteria(
                new BetweenCriteria(rivers.getColumn("level"), rivers.getColumn("lower_limit"), rivers.getColumn("upper_limit")));

        assertThat(select, SqlMatcher.generatesSql(
                "SELECT " +
                "    rivers.name , " +
                "    rivers.level " +
                "FROM " +
                "    rivers " +
                "WHERE " +
                "    rivers.level BETWEEN rivers.lower_limit AND rivers.upper_limit"));
    }
}