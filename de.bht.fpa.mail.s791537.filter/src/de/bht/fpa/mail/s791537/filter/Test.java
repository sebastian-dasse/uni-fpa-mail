package de.bht.fpa.mail.s791537.filter;

import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import de.bht.fpa.mail.s000000.common.filter.FilterOperator;
import de.bht.fpa.mail.s000000.common.filter.IFilter;
import de.bht.fpa.mail.s000000.common.mail.model.Importance;
import de.bht.fpa.mail.s000000.common.mail.model.Message;
import de.bht.fpa.mail.s000000.common.mail.model.Recipient;
import de.bht.fpa.mail.s000000.common.mail.model.Sender;
import de.bht.fpa.mail.s000000.common.mail.testdata.RandomTestDataProvider;

public class Test {
  public static void main(String[] args) {
    Iterable<Message> messages = new RandomTestDataProvider(100).getMessages();
    // print(test1(messages));
    // System.out.println();
    // print(test2(messages));
    // System.out.println();
    print(test3(messages));
    System.out.println();
    print(test4(messages));
  }

  // intersection
  private static Iterable<Message> test1(Iterable<Message> messages) {
    messages = new SenderFilter("tulle", FilterOperator.CONTAINS).filter(messages);
    messages = new RecipientsFilter(".de", FilterOperator.ENDS_WITH).filter(messages);
    messages = new SubjectFilter("All for nothing", FilterOperator.IS).filter(messages);
    messages = new ImportanceFilter(Importance.LOW).filter(messages);
    return messages;
  }

  private static Iterable<Message> test2(Iterable<Message> messages) {
    List<IFilter> filters = new LinkedList<IFilter>();
    filters.add(new SenderFilter("karl", FilterOperator.CONTAINS));
    filters.add(new RecipientsFilter(".de", FilterOperator.ENDS_WITH));
    filters.add(new SubjectFilter("All for nothing", FilterOperator.IS));
    filters.add(new ImportanceFilter(Importance.LOW));
    return new IntersectionFilter(filters).filter(messages);
  }

  // union
  private static Iterable<Message> test3(Iterable<Message> messages) {
    Set<Message> filteredMessages = new HashSet<Message>();
    filteredMessages.addAll(new SenderFilter("karl stulle", FilterOperator.IS).filter(messages));
    // filteredMessages.addAll(new RecipientsFilter(".de",
    // FilterOperator.ENDS_WITH).filter(messages));
    filteredMessages.addAll(new SubjectFilter("All for nothing", FilterOperator.IS).filter(messages));
    // filteredMessages.addAll(new
    // ImportanceFilter(Importance.LOW).filter(messages));
    return filteredMessages;
  }

  private static Iterable<Message> test4(Iterable<Message> messages) {
    List<IFilter> filters = new LinkedList<IFilter>();
    filters.add(new SenderFilter("karl stulle", FilterOperator.IS));
    // filters.add(new RecipientsFilter(".de", FilterOperator.ENDS_WITH));
    filters.add(new SubjectFilter("All for nothing", FilterOperator.IS));
    // filters.add(new ImportanceFilter(Importance.LOW));
    return new UnionFilter(filters).filter(messages);
  }

  private static void print(Iterable<Message> messages) {
    Iterator<Message> it = messages.iterator();
    int count = 0;
    while (it.hasNext()) {
      Message m = it.next();
      Sender s = m.getSender();
      List<Recipient> r = m.getRecipients();
      StringBuilder sb = new StringBuilder();
      sb.append("sender: ").append(s.getPersonal()).append(" <").append(s.getEmail()).append(">");
      sb.append(", importance: ").append(m.getImportance());
      sb.append(", subject: ").append(m.getSubject());
      sb.append(", recipients: ");
      for (Recipient recipient : r) {
        sb.append(recipient.getPersonal()).append(" <").append(recipient.getEmail()).append(">, ");
      }
      sb.deleteCharAt(sb.length() - 1);
      System.out.println(sb.toString());
      count++;
    }
    System.out.println(count + " messages");
  }
}
