package com.truemesh.squiggle.criteria;

import com.truemesh.squiggle.*;
import com.truemesh.squiggle.output.Output;

import java.util.Set;

/**
 * @author <a href="joe@truemesh.com">Joe Walnes</a>
 * @author Nat Pryce
 */
public class InCriteria implements Criteria {
    private final Matchable matched;
    private final ValueSet valueSet;

    public InCriteria(Matchable matchable, ValueSet valueSet) {
        this.matched = matchable;
        this.valueSet = valueSet;
    }

    public InCriteria(Matchable column, String... values) {
        this.matched = column;
        this.valueSet = new LiteralValueSet(values);
    }

    public InCriteria(Matchable column, long... values) {
        this.matched = column;
        this.valueSet = new LiteralValueSet(values);
    }

    public InCriteria(Matchable column, double... values) {
        this.matched = column;
        this.valueSet = new LiteralValueSet(values);
    }

    public InCriteria(Table table, String columnname, ValueSet valueSet) {
        this(table.getColumn(columnname), valueSet);
    }

    public InCriteria(Table table, String columnname, String[] values) {
        this(table.getColumn(columnname), values);
    }

    public InCriteria(Table table, String columnname, double[] values) {
        this(table.getColumn(columnname), values);
    }

    public InCriteria(Table table, String columnname, long[] values) {
        this(table.getColumn(columnname), values);
    }

    public Matchable getMatched() {
        return matched;
    }

    public void write(Output out) {
        matched.write(out);
        out.println(" IN (");
        out.indent();
        valueSet.write(out);
        out.println();
        out.unindent();
        out.print(")");
    }

    public void addReferencedTablesTo(Set<Table> tables) {
        matched.addReferencedTablesTo(tables);
    }
}
