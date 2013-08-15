package com.truemesh.squiggle.criteria;

import com.truemesh.squiggle.Column;
import com.truemesh.squiggle.Criteria;
import com.truemesh.squiggle.Literal;
import com.truemesh.squiggle.LiteralValueSet;
import com.truemesh.squiggle.Table;
import com.truemesh.squiggle.ValueSet;
import com.truemesh.squiggle.output.Output;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class MultiInCriteria implements Criteria {
  private final Table table;
  private final Collection<List<Literal>> valueSets;
  private final ArrayList<Column> columnList;

  public MultiInCriteria(Table table, List<String> columnNames, Collection<List<Literal>> valueSets) {
    this.table = table;
    this.valueSets = valueSets;
    this.columnList = new ArrayList<Column>();
    for (String columnName : columnNames) {
      columnList.add(table.getColumn(columnName));
    }
  }

  @Override
  public void write(Output out) {
    out.print("(");

    for (int i = 0; i < columnList.size(); i++) {
      columnList.get(i).write(out);
      if (i != columnList.size() - 1) {
        out.print(", ");
      }
    }
    out.println(")");
    out.println(" IN (");
    out.indent();
    Iterator<List<Literal>> valueSetsIterator;
    for (valueSetsIterator = valueSets.iterator(); valueSetsIterator.hasNext(); ) {
      out.print("( ");
      ValueSet valueSet = new LiteralValueSet(valueSetsIterator.next());
      valueSet.write(out);
      out.print(" )");
      if (valueSetsIterator.hasNext()) {
        out.print(", ");
      }
    }
    out.println();
    out.unindent();
    out.print(")");
  }

  @Override
  public void addReferencedTablesTo(Set<Table> tables) {
    for (Column col : columnList) {
      col.addReferencedTablesTo(tables);
    }
  }
}
