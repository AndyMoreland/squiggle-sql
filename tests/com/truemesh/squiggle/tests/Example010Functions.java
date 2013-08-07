package com.truemesh.squiggle.tests;

import static com.truemesh.squiggle.tests.support.SqlMatcher.generatesSql;
import com.truemesh.squiggle.tests.support.SqlMatcher;
import static org.junit.Assert.assertThat;

import org.junit.Test;

import com.truemesh.squiggle.FunctionCall;
import com.truemesh.squiggle.SelectQuery;
import com.truemesh.squiggle.Table;
import com.truemesh.squiggle.criteria.BetweenCriteria;
import com.truemesh.squiggle.literal.IntegerLiteral;
import com.truemesh.squiggle.literal.StringLiteral;

public class Example010Functions {
    @Test
    public void functions() {
    	SelectQuery select = new SelectQuery();

    	Table table = new Table("t");

    	select.addToSelection(new FunctionCall("sheep"));
    	select.addToSelection(new FunctionCall("cheese", new IntegerLiteral(10)));
    	select.addToSelection(new FunctionCall("tomato", new StringLiteral("red"), table.getColumn("c")));

        assertThat(select, generatesSql(
                "SELECT " +
                "    sheep() , " +
                "    cheese(10) , " +
                "    tomato('red', t.c) " +
                "FROM " +
                "    t "));

    }

    @Test
    public void usingFunctionsInMatchCriteria() {
        Table cards = new Table("credit_cards");

        SelectQuery select = new SelectQuery();

        select.addToSelection(cards.getColumn("number"));
        select.addToSelection(cards.getColumn("issue"));
        
        select.addCriteria(
                new BetweenCriteria(new FunctionCall("getDate"),
                        cards.getColumn("issue_date"), cards.getColumn("expiry_date")));

        assertThat(select, SqlMatcher.generatesSql(
                "SELECT " +
                        "    credit_cards.number , " +
                        "    credit_cards.issue " +
                        "FROM " +
                        "    credit_cards " +
                        "WHERE " +
                        "    getDate() BETWEEN credit_cards.issue_date AND credit_cards.expiry_date"));
    }

    @Test
    public void selectingFunctionThatDoesNotReferToTables() {
    	SelectQuery select = new SelectQuery();
    	select.addToSelection(new FunctionCall("getdate"));
    	
    	assertThat(select, generatesSql(
    			"SELECT" +
    			"    getdate()"));
    }
}