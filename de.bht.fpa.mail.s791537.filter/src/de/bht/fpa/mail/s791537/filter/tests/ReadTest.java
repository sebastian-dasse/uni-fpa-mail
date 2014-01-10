package de.bht.fpa.mail.s791537.filter.tests;

import static de.bht.fpa.mail.s791537.filter.tests.MessageUtils.count;

import java.io.File;
import java.util.Collection;
import java.util.HashSet;

import junit.framework.TestCase;

import org.junit.Test;

import de.bht.fpa.mail.s000000.common.mail.model.Message;
import de.bht.fpa.mail.s000000.common.mail.testdata.FileSystemTestDataProvider;
import de.bht.fpa.mail.s791537.filter.ReadFilter;

public class ReadTest extends TestCase {
  private Collection<Message> messagesToFilter;
  private Iterable<Message> filteredMessages;

  private static final int NUMBER_OF_UNREAD_MESSAGES = 5;
  private static final int NUMBER_OF_READ_MESSAGES = 4;

  public ReadTest(String name) {
    super(name);
  }

  @Override
  protected void setUp() {
    messagesToFilter = new FileSystemTestDataProvider(new File("files/testdata")).getMessages();
  }

  @Test
  public void testReadIsFalse() {
    filteredMessages = new ReadFilter(false).filter(messagesToFilter);
    assertEquals("number of unread messages", NUMBER_OF_UNREAD_MESSAGES, count(filteredMessages));
    for (Message message : filteredMessages) {
      assertFalse("message is read, but should be unread", message.isRead());
    }
  }

  @Test
  public void testReadIsTrue() {
    filteredMessages = new ReadFilter(true).filter(messagesToFilter);
    assertEquals("number of read messages", NUMBER_OF_READ_MESSAGES, count(filteredMessages));
    for (Message message : filteredMessages) {
      assertFalse("message is unread, but should be read", !message.isRead());
    }
  }

  @Test
  public void testEmptyCollection() {
    filteredMessages = new ReadFilter(true).filter(new HashSet<Message>());
    assertEquals("number of messages with empty filter collection", 0, count(filteredMessages));
  }

  @Test
  public void testNull() {
    try {
      filteredMessages = new ReadFilter(true).filter(null);
      fail("should not happen");
    } catch (NullPointerException e) {
      assertTrue("filteredMessages == " + filteredMessages, filteredMessages == null);
    }
  }
}
