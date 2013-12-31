package de.bht.fpa.mail.s791537.filter;

import java.util.List;

import de.bht.fpa.mail.s000000.common.filter.IFilter;

public abstract class FilterDecorator implements IFilter {
  protected final List<IFilter> filters;

  public FilterDecorator(List<IFilter> filters) {
    this.filters = filters;
  }

}
