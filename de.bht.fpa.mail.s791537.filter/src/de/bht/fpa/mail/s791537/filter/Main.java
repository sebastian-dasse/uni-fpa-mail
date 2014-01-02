package de.bht.fpa.mail.s791537.filter;

import java.util.HashSet;
import java.util.Set;

import de.bht.fpa.mail.s000000.common.filter.FilterOperator;
import de.bht.fpa.mail.s000000.common.mail.model.Importance;
import de.bht.fpa.mail.s000000.common.mail.model.Message;
import de.bht.fpa.mail.s000000.common.mail.testdata.RandomTestDataProvider;
import de.bht.fpa.mail.s000000.common.table.MessageValues;
import de.ralfebert.rcputils.properties.BaseValue;
import de.ralfebert.rcputils.properties.IValue;

public final class Main {
  private static final int NUMBER_OF_MESSAGES = 10;

  private Main() {
  }

  public static void main(String[] args) {
    Iterable<Message> messages = new RandomTestDataProvider(NUMBER_OF_MESSAGES).getMessages();
    // printAll(test0(messages), MessageValues.IMPORTANCE);
    // printAll(test1(messages), MessageValues.IMPORTANCE,
    // MessageValues.SUBJECT, MessageValues.SENDER,
    // MessageValues.RECIPIENT);
    // printAll(test2(messages));
    // printAll(test2b(messages));
    // printAll(test3(messages));
    // printAll(test4(messages));

    printAll(test5(messages), MessageValues.READ);
    printAll(test6(messages), MessageValues.RECIPIENT);
    printAll(test7(messages), MessageValues.RECIPIENT);
  }

  public static Iterable<Message> test0(Iterable<Message> messages) {
    // messages = new SenderFilter("karl",
    // FilterOperator.STARTS_WITH).filter(messages);
    // messages = new RecipientsFilter("karl stulle",
    // FilterOperator.IS).filter(messages);
    // messages = new SubjectFilter("f",
    // FilterOperator.STARTS_WITH).filter(messages);
    // messages = new TextFilter("something",
    // FilterOperator.CONTAINS).filter(messages);
    messages = new ImportanceFilter(Importance.HIGH).filter(messages);
    // messages = new ReadFilter(true).filter(messages);
    return messages;
  }

  // trivial intersection
  public static Iterable<Message> test1(Iterable<Message> messages) {
    messages = new SenderFilter("karl", FilterOperator.CONTAINS).filter(messages);
    messages = new RecipientsFilter(".de", FilterOperator.ENDS_WITH).filter(messages);
    messages = new SubjectFilter("All for nothing", FilterOperator.IS).filter(messages);
    messages = new ImportanceFilter(Importance.LOW).filter(messages);
    return messages;
  }

  public static Iterable<Message> test2(Iterable<Message> messages) {
    return new IntersectionFilter(new SenderFilter("karl", FilterOperator.CONTAINS), new RecipientsFilter(".de",
        FilterOperator.ENDS_WITH), new SubjectFilter("All for nothing", FilterOperator.IS), new ImportanceFilter(
        Importance.LOW)).filter(messages);
  }

  public static Iterable<Message> test2b(Iterable<Message> messages) {
    return new IntersectionFilter(new IntersectionFilter(new SenderFilter("karl", FilterOperator.CONTAINS),
        new RecipientsFilter(".de", FilterOperator.ENDS_WITH)), new IntersectionFilter(new SubjectFilter(
        "All for nothing", FilterOperator.IS), new ImportanceFilter(Importance.LOW))).filter(messages);
  }

  // trivial union
  public static Iterable<Message> test3(Iterable<Message> messages) {
    Set<Message> filteredMessages = new HashSet<Message>();
    filteredMessages.addAll(new SenderFilter("karl stulle", FilterOperator.IS).filter(messages));
    filteredMessages.addAll(new SubjectFilter("All for nothing", FilterOperator.IS).filter(messages));
    return filteredMessages;
  }

  public static Iterable<Message> test4(Iterable<Message> messages) {
    return new UnionFilter(new SenderFilter("karl stulle", FilterOperator.IS), new SubjectFilter("All for nothing",
        FilterOperator.IS)).filter(messages);
  }

  private static Iterable<Message> test5(Iterable<Message> messages) {
    return new ReadFilter(false).filter(messages);
  }

  private static Iterable<Message> test6(Iterable<Message> messages) {
    return new UnionFilter(new SenderFilter("karl", FilterOperator.CONTAINS), new RecipientsFilter("heidi stulle",
        FilterOperator.IS)).filter(messages);
  }

  private static Iterable<Message> test7(Iterable<Message> messages) {
    return new IntersectionFilter(new SenderFilter("karl", FilterOperator.STARTS_WITH), new ReadFilter(true))
        .filter(messages);
  }

  @SuppressWarnings("unchecked")
  public static void printAll(Iterable<Message> messages, IValue... vals) {
    int count = 0;
    for (Message message : messages) {
      for (IValue val : vals) {
        System.out.print(((BaseValue<Message>) val).get(message) + "\t\t");
      }
      System.out.println(message);
      count++;
    }
    System.out.println(count + " messages");
    System.out.println();
  }
}
