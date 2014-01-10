package de.bht.fpa.mail.s791537.filter.tests;

import java.util.Iterator;

import de.bht.fpa.mail.s000000.common.mail.model.Message;
import de.ralfebert.rcputils.properties.BaseValue;
import de.ralfebert.rcputils.properties.IValue;

public final class MessageUtils {

  private MessageUtils() {

  }

  public static int count(Iterable<Message> messages) {
    int count = 0;
    for (Iterator<Message> it = messages.iterator(); it.hasNext(); it.next()) {
      count++;
    }
    return count;
  }

  @SuppressWarnings("unchecked")
  public static void printAll(Iterable<Message> messages, IValue... vals) {
    for (Message message : messages) {
      System.out.printf("ID: %20d   ***   ", message.getId());
      for (IValue val : vals) {
        System.out.print(((BaseValue<Message>) val).get(message) + "\t***   ");
      }
      System.out.println();
    }
  }

}
