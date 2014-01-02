package de.bht.fpa.mail.s791537.filter;

import java.util.Set;

import de.bht.fpa.mail.s000000.common.filter.FilterOperator;
import de.bht.fpa.mail.s000000.common.filter.StringCompareHelper;
import de.bht.fpa.mail.s000000.common.mail.model.Message;

public class SubjectFilter extends StringFilter {

  public SubjectFilter(String searchString, FilterOperator filterOperator) {
    super(searchString, filterOperator);
  }

  @Override
  public Set<Message> filter(Iterable<Message> messagesToFilter) {
    for (Message message : messagesToFilter) {
      if (StringCompareHelper.matches(message.getSubject().toLowerCase(), searchString, filterOperator)) {
        filteredMessages.add(message);
      }
    }
    return filteredMessages;
  }
}
