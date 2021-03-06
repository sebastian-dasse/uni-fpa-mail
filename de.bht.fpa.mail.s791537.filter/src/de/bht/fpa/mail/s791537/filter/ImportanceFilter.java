package de.bht.fpa.mail.s791537.filter;

import java.util.HashSet;
import java.util.Set;

import de.bht.fpa.mail.s000000.common.filter.IFilter;
import de.bht.fpa.mail.s000000.common.mail.model.Importance;
import de.bht.fpa.mail.s000000.common.mail.model.Message;

public class ImportanceFilter implements IFilter {
  private final Importance searchImportance;

  public ImportanceFilter(Importance searchImportance) {
    this.searchImportance = searchImportance;
  }

  @Override
  public Set<Message> filter(Iterable<Message> messagesToFilter) {
    Set<Message> filteredMessages = new HashSet<Message>();
    for (Message message : messagesToFilter) {
      if (message.getImportance().equals(searchImportance)) {
        filteredMessages.add(message);
      }
    }
    return filteredMessages;
  }
}
