package de.bht.fpa.mail.s791537.filter;

import java.util.List;
import java.util.Set;

import de.bht.fpa.mail.s000000.common.filter.FilterOperator;
import de.bht.fpa.mail.s000000.common.filter.StringCompareHelper;
import de.bht.fpa.mail.s000000.common.mail.model.Message;
import de.bht.fpa.mail.s000000.common.mail.model.Recipient;

public class RecipientsFilter extends StringFilter {

  public RecipientsFilter(String searchString, FilterOperator filterOperator) {
    super(searchString, filterOperator);
  }

  @Override
  public Set<Message> filter(Iterable<Message> messagesToFilter) {
    for (Message message : messagesToFilter) {
      List<Recipient> recipients = message.getRecipients();
      for (Recipient recipient : recipients) {
        if (StringCompareHelper.matches(recipient.getEmail().toLowerCase(), searchString, filterOperator)
            || StringCompareHelper.matches(recipient.getPersonal().toLowerCase(), searchString, filterOperator)) {
          filteredMessages.add(message);
          continue;
        }
      }
    }
    return filteredMessages;
  }
}
