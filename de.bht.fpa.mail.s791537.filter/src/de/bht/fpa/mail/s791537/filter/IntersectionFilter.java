package de.bht.fpa.mail.s791537.filter;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import de.bht.fpa.mail.s000000.common.filter.IFilter;
import de.bht.fpa.mail.s000000.common.mail.model.Message;

public class IntersectionFilter extends FilterDecorator {

  public IntersectionFilter(List<IFilter> filters) {
    super(filters);
  }

  @Override
  public Set<Message> filter(Iterable<Message> messagesToFilter) {
    Set<Message> filteredMessages = new HashSet<Message>();
    filteredMessages.addAll((Collection<? extends Message>) messagesToFilter);
    for (IFilter filter : filters) {
      Set<Message> temp = filter.filter(messagesToFilter);
      filteredMessages.retainAll(temp);
      // filteredMessages = filter.filter(messagesToFilter);
    }
    return filteredMessages;
  }
}
