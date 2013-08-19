package com.truemesh.squiggle;

import com.truemesh.squiggle.output.Output;
import com.truemesh.squiggle.output.Outputable;

import java.util.Set;

/**
 * @author <a href="joe@truemesh.com">Joe Walnes</a>
 * @author Nat Pryce
 */
public interface Criteria extends Outputable {
  public void write(Output out);
  public void addReferencedTablesTo(Set<Table> tables);
}
