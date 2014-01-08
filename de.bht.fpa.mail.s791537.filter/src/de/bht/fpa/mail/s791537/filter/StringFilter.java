package de.bht.fpa.mail.s791537.filter;

import de.bht.fpa.mail.s000000.common.filter.FilterOperator;
import de.bht.fpa.mail.s000000.common.filter.IFilter;

/**
 * An abstract base class for all string based filters.
 * <p>
 * Note that the specified searchString will be converted to lower case, because
 * the comparison is meant to ignore the case of the strings. Implementing
 * subclasses must convert their target strings to lower case for correct
 * results.
 */
public abstract class StringFilter implements IFilter {
  protected final String searchString;
  protected final FilterOperator filterOperator;

  public StringFilter(String searchString, FilterOperator filterOperator) {
    this.searchString = searchString.toLowerCase();
    this.filterOperator = filterOperator;
  }
}
