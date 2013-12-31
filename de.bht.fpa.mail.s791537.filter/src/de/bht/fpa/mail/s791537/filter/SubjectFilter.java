package de.bht.fpa.mail.s791537.filter;

import java.util.HashSet;
import java.util.Set;

import de.bht.fpa.mail.s000000.common.filter.FilterOperator;
import de.bht.fpa.mail.s000000.common.filter.IFilter;
import de.bht.fpa.mail.s000000.common.filter.StringCompareHelper;
import de.bht.fpa.mail.s000000.common.mail.model.Message;

public class SubjectFilter implements IFilter {
  private final String searchString;
  private final FilterOperator filterOperator;

  public SubjectFilter(String searchString, FilterOperator filterOperator) {
    this.searchString = searchString;
    this.filterOperator = filterOperator;
  }

  @Override
  public Set<Message> filter(Iterable<Message> messagesToFilter) {
    Set<Message> filteredMessages = new HashSet<Message>();
    for (Message message : messagesToFilter) {
      if (StringCompareHelper.matches(message.getSubject(), searchString, filterOperator)) {
        filteredMessages.add(message);
      }
    }
    return filteredMessages;
  }
}
