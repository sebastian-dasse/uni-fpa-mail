package de.bht.fpa.mail.s791537.filter.tests;

import static de.bht.fpa.mail.s791537.filter.tests.MessageUtils.count;

import java.io.File;
import java.util.HashSet;

import junit.framework.TestCase;

import org.junit.Test;

import de.bht.fpa.mail.s000000.common.filter.FilterOperator;
import de.bht.fpa.mail.s000000.common.mail.model.Importance;
import de.bht.fpa.mail.s000000.common.mail.model.Message;
import de.bht.fpa.mail.s000000.common.mail.model.Sender;
import de.bht.fpa.mail.s000000.common.mail.testdata.FileSystemTestDataProvider;
import de.bht.fpa.mail.s791537.filter.ImportanceFilter;
import de.bht.fpa.mail.s791537.filter.ReadFilter;
import de.bht.fpa.mail.s791537.filter.SenderFilter;
import de.bht.fpa.mail.s791537.filter.SubjectFilter;
import de.bht.fpa.mail.s791537.filter.UnionFilter;

/**
 * Note that all search strings are in lower case, because the string filters
 * are not case sensitive.
 */
public class UnionTest extends TestCase {
  private Iterable<Message> messagesToFilter;
  private Iterable<Message> filteredMessages;

  private static final int NUMBER_OF_EXPECTED_MESSAGES_FOR_TEST_1 = 1;
  private static final int NUMBER_OF_EXPECTED_MESSAGES_FOR_TEST_2 = 8;

  public UnionTest(String name) {
    super(name);
  }

  @Override
  protected void setUp() {
    // messagesToFilter = new
    // RandomTestDataProvider(NUMBER_OF_MESSAGES).getMessages();
    messagesToFilter = new FileSystemTestDataProvider(new File("files/testdata")).getMessages();
  }

  @Test
  public void test1() {
    String searchString = "free willy is finally free";
    Importance searchImportance = Importance.HIGH;
    filteredMessages = new UnionFilter(new SubjectFilter(searchString, FilterOperator.CONTAINS), new ImportanceFilter(
        searchImportance)).filter(messagesToFilter);
    assertEquals("number of messages", NUMBER_OF_EXPECTED_MESSAGES_FOR_TEST_1, count(filteredMessages));
    for (Message message : filteredMessages) {
      assertTrue(searchString.contains(message.getSubject().toLowerCase())
          || searchImportance.equals(message.getImportance()));
    }
  }

  @Test
  public void test2() {
    String searchString = ".de";
    filteredMessages = new UnionFilter(new SenderFilter(searchString, FilterOperator.ENDS_WITH), new ReadFilter(true))
        .filter(messagesToFilter);
    assertEquals("number of messages", NUMBER_OF_EXPECTED_MESSAGES_FOR_TEST_2, count(filteredMessages));
    for (Message message : filteredMessages) {
      Sender sender = message.getSender();
      assertTrue(sender.getEmail().toLowerCase().endsWith(searchString)
          || sender.getPersonal().toLowerCase().endsWith(searchString) || message.isRead());
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
