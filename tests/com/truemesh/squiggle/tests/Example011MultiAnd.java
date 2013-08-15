package com.truemesh.squiggle.tests;

import com.truemesh.squiggle.Literal;
import com.truemesh.squiggle.SelectQuery;
import com.truemesh.squiggle.Table;
import com.truemesh.squiggle.WildCardColumn;
import com.truemesh.squiggle.criteria.MultiInCriteria;
import com.truemesh.squiggle.literal.StringLiteral;
import com.truemesh.squiggle.tests.support.SqlMatcher;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static org.junit.Assert.assertThat;

public class Example011MultiAnd {

  @Test
  public void multiAnd() {
    Table user = new Table("user");

    SelectQuery select = new SelectQuery();

    select.addToSelection(new WildCardColumn(user));

    List<String> columns = new ArrayList<String>();
    Collection<List<Literal>> values = new ArrayList<List<Literal>>();
    columns.add("first_column");
    columns.add("second_column");
    columns.add("third_column");

    List<Literal> firstValueTuple = new ArrayList<Literal>();
    List<Literal> secondValueTuple = new ArrayList<Literal>();

    firstValueTuple.add(new StringLiteral("first_value"));
    firstValueTuple.add(new StringLiteral("second_value"));
    firstValueTuple.add(new StringLiteral("third_value"));

    secondValueTuple.add(new StringLiteral("first_value"));
    secondValueTuple.add(new StringLiteral("second_value"));
    secondValueTuple.add(new StringLiteral("third_value"));

    values.add(firstValueTuple);
    values.add(secondValueTuple);

    select.addCriteria(new MultiInCriteria(user, columns, values));

    assertThat(select, SqlMatcher.generatesSql(
        "SELECT " +
            "    user.* " +
            "FROM " +
            "    user " +
            "WHERE " +
            "    ( ( user.first_column = 'first_value' AND ( user.second_column = 'second_value' AND user.third_column = 'third_value' ) ) OR " +
            "    ( user.first_column = 'first_value' AND ( user.second_column = 'second_value' AND user.third_column = 'third_value' ) ) )"));
  }

}
