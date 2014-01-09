package de.bht.fpa.mail.s791537.filter;

import de.bht.fpa.mail.s000000.common.filter.FilterOperator;
import de.bht.fpa.mail.s000000.common.mail.model.Message;
import de.bht.fpa.mail.s000000.common.mail.testdata.RandomTestDataProvider;
import de.bht.fpa.mail.s000000.common.table.MessageValues;
import de.ralfebert.rcputils.properties.BaseValue;
import de.ralfebert.rcputils.properties.IValue;

public final class Main {
  private static final int NUMBER_OF_MESSAGES = 25;

  private Main() {
  }

  public static void main(String[] args) {
    Iterable<Message> messages = new RandomTestDataProvider(NUMBER_OF_MESSAGES).getMessages();

    printAll(test1(messages), MessageValues.READ);
    printAll(test2(messages), MessageValues.SENDER, MessageValues.RECIPIENT);
    printAll(test3(messages), MessageValues.SENDER, MessageValues.READ);
  }

  private static Iterable<Message> test1(Iterable<Message> messages) {
    return new ReadFilter(false).filter(messages);
  }

  private static Iterable<Message> test2(Iterable<Message> messages) {
    return new UnionFilter(new SenderFilter("karl", FilterOperator.CONTAINS), new RecipientsFilter("heidi stulle",
        FilterOperator.IS)).filter(messages);
  }

  private static Iterable<Message> test3(Iterable<Message> messages) {
    return new IntersectionFilter(new SenderFilter("karl", FilterOperator.STARTS_WITH), new ReadFilter(true))
        .filter(messages);
  }

  @SuppressWarnings("unchecked")
  public static void printAll(Iterable<Message> messages, IValue... vals) {
    System.out.println("-----------------------------------------");
    int count = 0;
    for (Message message : messages) {
      for (IValue val : vals) {
        System.out.print(((BaseValue<Message>) val).get(message) + "   ***   ");
      }
      System.out.println(message);
      count++;
    }
    System.out.println("-----------------------------------------");
    System.out.println(count + " messages");
    System.out.println("-----------------------------------------");
    System.out.println();
  }
}
