package de.bht.fpa.mail.s791537.filter;

import java.util.Set;

import de.bht.fpa.mail.s000000.common.filter.IFilter;
import de.bht.fpa.mail.s000000.common.mail.model.Message;

public class ConcreteFilter implements IFilter {
  private final String searchString;

  public ConcreteFilter(String searchString) {
    this.searchString = searchString;
  }

  @Override
  public Set<Message> filter(Iterable<Message> messagesToFilter) {
    // TODO Auto-generated method stub
    return null;
  }

}
