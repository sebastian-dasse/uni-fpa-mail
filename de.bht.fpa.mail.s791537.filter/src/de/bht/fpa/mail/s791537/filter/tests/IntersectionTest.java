package de.bht.fpa.mail.s791537.filter.tests;

/**
 * Note that all search strings are in lower case, because the string filters
 * are not case sensitive.
 */
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
import de.bht.fpa.mail.s791537.filter.IntersectionFilter;
import de.bht.fpa.mail.s791537.filter.ReadFilter;
import de.bht.fpa.mail.s791537.filter.SenderFilter;
import de.bht.fpa.mail.s791537.filter.SubjectFilter;

public class IntersectionTest extends TestCase {
  private Iterable<Message> messagesToFilter;
  private Iterable<Message> filteredMessages;

  private static final int NUMBER_OF_EXPECTED_MESSAGES_FOR_TEST_1 = 1;
  private static final int NUMBER_OF_EXPECTED_MESSAGES_FOR_TEST_2 = 3;
  private static final int NUMBER_OF_EXPECTED_MESSAGES_FOR_TEST_3 = 3;

  public IntersectionTest(String name) {
    super(name);
  }

  @Override
  protected void setUp() {
    messagesToFilter = new FileSystemTestDataProvider(new File("files/testdata")).getMessages();
  }

  @Test
  public void test1() {
    String searchString = "free willy is finally free";
    Importance searchImportance = Importance.NORMAL;
    filteredMessages = new IntersectionFilter(new SubjectFilter(searchString, FilterOperator.IS), new ImportanceFilter(
        searchImportance)).filter(messagesToFilter);
    assertEquals("number of messages", NUMBER_OF_EXPECTED_MESSAGES_FOR_TEST_1, count(filteredMessages));
    for (Message message : filteredMessages) {
      assertEquals("subject", searchString, message.getSubject().toLowerCase());
      assertEquals("importance", searchImportance, message.getImportance());
    }
  }

  @Test
  public void test2() {
    String searchString = "s ";
    filteredMessages = new IntersectionFilter(new SubjectFilter(searchString, FilterOperator.CONTAINS), new ReadFilter(
        true)).filter(messagesToFilter);
    assertEquals("number of messages", NUMBER_OF_EXPECTED_MESSAGES_FOR_TEST_2, count(filteredMessages));
    for (Message message : filteredMessages) {
      assertTrue("subject does not contain " + searchString, message.getSubject().toLowerCase().contains(searchString));
      assertTrue("message is not read", message.isRead());
    }
  }

  @Test
  public void test3() {
    String searchString1 = "sch";
    String searchString2 = ".de";
    filteredMessages = new IntersectionFilter(new SenderFilter(searchString1, FilterOperator.CONTAINS),
        new SenderFilter(searchString2, FilterOperator.ENDS_WITH), new ImportanceFilter(Importance.NORMAL))
        .filter(messagesToFilter);
    assertEquals("number of messages", NUMBER_OF_EXPECTED_MESSAGES_FOR_TEST_3, count(filteredMessages));
    for (Message message : filteredMessages) {
      Sender sender = message.getSender();
      assertTrue("sender" + searchString1, sender.getEmail().toLowerCase().contains(searchString1)
          || sender.getPersonal().toLowerCase().contains(searchString1));
      assertTrue("sender" + searchString2, sender.getEmail().toLowerCase().endsWith(searchString2)
          || sender.getPersonal().toLowerCase().endsWith(searchString2));
      assertTrue("importance", message.getImportance().equals(Importance.NORMAL));
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
