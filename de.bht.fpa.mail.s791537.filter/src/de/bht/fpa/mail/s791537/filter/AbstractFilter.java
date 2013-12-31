package de.bht.fpa.mail.s791537.filter;

import de.bht.fpa.mail.s000000.common.filter.IFilter;

public abstract class AbstractFilter implements IFilter {
  private final IFilter inner;
  private final String searchString;

  public AbstractFilter(IFilter inner, String searchString) {
    this.inner = inner;
    this.searchString = searchString;
  }

  // @Override
  // public Set<Message> filter(Iterable<Message> messagesToFilter) {
  // Set<Message> messages = new HashSet<Message>();
  // return inner.filter(messages);
  // }
}
