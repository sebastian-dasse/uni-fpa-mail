package de.bht.fpa.mail.s791537.filter;

import java.util.HashSet;
import java.util.Set;

import de.bht.fpa.mail.s000000.common.filter.FilterOperator;
import de.bht.fpa.mail.s000000.common.filter.IFilter;
import de.bht.fpa.mail.s000000.common.filter.StringCompareHelper;
import de.bht.fpa.mail.s000000.common.mail.model.Message;
import de.bht.fpa.mail.s000000.common.mail.model.Sender;

public class SenderFilter implements IFilter {
  private final String searchString;
  private final FilterOperator filterOperator;

  public SenderFilter(String searchString, FilterOperator filterOperator) {
    this.searchString = searchString;
    this.filterOperator = filterOperator;
    // public SenderFilter(IFilter inner, String searchString) {
    // super(inner, searchString);
  }

  @Override
  public Set<Message> filter(Iterable<Message> messagesToFilter) {
    // TODO - ignorecase für searchString und sender.(...)
    // - Konstruktor so sinnvoll?
    // - abstrakte Superklasse für Decorator

    Set<Message> filteredMessages = new HashSet<Message>();
    for (Message message : messagesToFilter) {
      Sender sender = message.getSender();
      if (StringCompareHelper.matches(sender.getEmail(), searchString, filterOperator)
          || StringCompareHelper.matches(sender.getPersonal(), searchString, filterOperator)) {
        filteredMessages.add(message);
      }
    }
    return filteredMessages;
  }
}
