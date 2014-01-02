package de.bht.fpa.mail.s791537.filter;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import de.bht.fpa.mail.s000000.common.filter.IFilter;
import de.bht.fpa.mail.s000000.common.mail.model.Message;

public class UnionFilter extends FilterDecorator {

  public UnionFilter(List<IFilter> filters) {
    super(filters);
  }

  @Override
  public Set<Message> filter(Iterable<Message> messagesToFilter) {
    Set<Message> filteredMessages = new HashSet<Message>();
    for (IFilter filter : filters) {
      filteredMessages.addAll(filter.filter(messagesToFilter));
    }
    return filteredMessages;
  }
}
