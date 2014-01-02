package de.bht.fpa.mail.s791537.filter.tests;

import junit.framework.TestCase;

import org.junit.Test;

import de.bht.fpa.mail.s000000.common.mail.model.Message;
import de.bht.fpa.mail.s000000.common.mail.testdata.RandomTestDataProvider;
import de.bht.fpa.mail.s791537.filter.ReadFilter;

public class ReadTest extends TestCase {
  private Iterable<Message> messagesToFilter;
  private Iterable<Message> filteredMessages;
  private static final int NUMBER_OF_MESSAGES = 100;

  public ReadTest(String name) {
    super(name);
  }

  @Override
  protected void setUp() {
    messagesToFilter = new RandomTestDataProvider(NUMBER_OF_MESSAGES).getMessages();
  }

  @Test
  public void testRead() {
    filteredMessages = new ReadFilter(false).filter(messagesToFilter);
    for (Message message : filteredMessages) {
      assertFalse("message is read", message.isRead());
    }
  }
}
