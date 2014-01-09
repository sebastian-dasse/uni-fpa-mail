package de.bht.fpa.mail.s791537.filter.tests;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import junit.framework.TestCase;

import org.junit.Test;

import de.bht.fpa.mail.s000000.common.mail.model.Importance;
import de.bht.fpa.mail.s000000.common.mail.model.Message;
import de.bht.fpa.mail.s000000.common.mail.model.builder.MessageBuilder;
import de.bht.fpa.mail.s000000.common.mail.model.builder.RecipientBuilder;
import de.bht.fpa.mail.s000000.common.mail.model.builder.SenderBuilder;
import de.bht.fpa.mail.s791537.filter.ReadFilter;

public class ReadTest extends TestCase {
  private List<Message> messagesToFilter;
  private Iterable<Message> filteredMessages;

  // private static final int NUMBER_OF_MESSAGES = 100;

  public ReadTest(String name) {
    super(name);
  }

  @Override
  protected void setUp() {
    // messagesToFilter = new
    // RandomTestDataProvider(NUMBER_OF_MESSAGES).getMessages();
    messagesToFilter = new LinkedList<Message>();
    messagesToFilter.add(MessageBuilder
        .newMessageBuilder()
        .sender(SenderBuilder.newSenderBuilder())
        .recipient(RecipientBuilder.newRecipientBuilder())
        .subject("Party next Friday")
        .text(
            "Hi, \ncome and join us for BBQ next Friday. We'll hang out and go to this nice new bar later. \n"
                + "Hope to see you there! Sue").importance(Importance.NORMAL).read(false).sent(new Date()).build());
  }

  @Test
  public void testRead() {
    filteredMessages = new ReadFilter(false).filter(messagesToFilter);
    for (Message message : filteredMessages) {
      assertFalse("message is read", message.isRead());
    }
  }
}
