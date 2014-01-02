package de.bht.fpa.mail.s791537.filter;

import java.util.HashSet;
import java.util.Set;

import de.bht.fpa.mail.s000000.common.filter.IFilter;
import de.bht.fpa.mail.s000000.common.mail.model.Message;

public class ReadFilter implements IFilter {
  private final boolean read;

  public ReadFilter(boolean read) {
    this.read = read;
  }

  @Override
  public Set<Message> filter(Iterable<Message> messagesToFilter) {
    Set<Message> filteredMessages = new HashSet<Message>();
    for (Message message : messagesToFilter) {
      if (message.isRead() == read) {
        filteredMessages.add(message);
      }
    }
    return filteredMessages;
  }
}
