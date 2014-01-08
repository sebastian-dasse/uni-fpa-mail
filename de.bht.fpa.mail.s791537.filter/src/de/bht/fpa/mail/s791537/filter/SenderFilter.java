package de.bht.fpa.mail.s791537.filter;

import java.util.HashSet;
import java.util.Set;

import de.bht.fpa.mail.s000000.common.filter.FilterOperator;
import de.bht.fpa.mail.s000000.common.filter.StringCompareHelper;
import de.bht.fpa.mail.s000000.common.mail.model.Message;
import de.bht.fpa.mail.s000000.common.mail.model.Sender;

public class SenderFilter extends StringFilter {

  public SenderFilter(String searchString, FilterOperator filterOperator) {
    super(searchString, filterOperator);
  }

  @Override
  public Set<Message> filter(Iterable<Message> messagesToFilter) {
    Set<Message> filteredMessages = new HashSet<Message>();
    for (Message message : messagesToFilter) {
      Sender sender = message.getSender();
      if (StringCompareHelper.matches(sender.getEmail().toLowerCase(), searchString, filterOperator)
          || StringCompareHelper.matches(sender.getPersonal().toLowerCase(), searchString, filterOperator)) {
        filteredMessages.add(message);
      }
    }
    return filteredMessages;
  }
}
