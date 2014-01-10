package de.bht.fpa.mail.s791537.filter;

import java.util.HashSet;
import java.util.Set;

import de.bht.fpa.mail.s000000.common.filter.IFilter;
import de.bht.fpa.mail.s000000.common.mail.model.Message;

public class IntersectionFilter extends FilterDecorator {

  public IntersectionFilter(IFilter... filters) {
    super(filters);
  }

  @Override
  public Set<Message> filter(Iterable<Message> messagesToFilter) {
    Set<Message> filteredMessages = new HashSet<Message>();
    for (Message message : messagesToFilter) {
      filteredMessages.add(message);
    }
    for (IFilter filter : filters) {
      filteredMessages.retainAll(filter.filter(filteredMessages));
    }
    return filteredMessages;
  }
}
