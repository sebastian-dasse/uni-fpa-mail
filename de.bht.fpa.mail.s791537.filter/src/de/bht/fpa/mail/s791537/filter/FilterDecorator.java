package de.bht.fpa.mail.s791537.filter;

import de.bht.fpa.mail.s000000.common.filter.IFilter;

public abstract class FilterDecorator implements IFilter {
  protected final IFilter[] filters;

  public FilterDecorator(IFilter... filters) {
    this.filters = filters;
  }
}
