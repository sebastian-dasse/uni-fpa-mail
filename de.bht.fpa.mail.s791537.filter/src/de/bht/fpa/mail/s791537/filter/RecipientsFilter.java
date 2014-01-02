package de.bht.fpa.mail.s791537.filter;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import de.bht.fpa.mail.s000000.common.filter.FilterOperator;
import de.bht.fpa.mail.s000000.common.filter.IFilter;
import de.bht.fpa.mail.s000000.common.filter.StringCompareHelper;
import de.bht.fpa.mail.s000000.common.mail.model.Message;
import de.bht.fpa.mail.s000000.common.mail.model.Recipient;

public class RecipientsFilter implements IFilter {
  private final String searchString;
  private final FilterOperator filterOperator;

  public RecipientsFilter(String searchString, FilterOperator filterOperator) {
    this.searchString = searchString;
    this.filterOperator = filterOperator;
  }

  @Override
  public Set<Message> filter(Iterable<Message> messagesToFilter) {
    Set<Message> filteredMessages = new HashSet<Message>();
    for (Message message : messagesToFilter) {
      List<Recipient> recipients = message.getRecipients();
      for (Recipient recipient : recipients) {
        if (StringCompareHelper.matches(recipient.getEmail(), searchString, filterOperator)
            || StringCompareHelper.matches(recipient.getPersonal(), searchString, filterOperator)) {
          filteredMessages.add(message);
          continue;
        }
      }
    }
    return filteredMessages;
  }
}
