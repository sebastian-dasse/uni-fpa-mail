package de.bht.fpa.mail.s791537.filter.tests;

import static de.bht.fpa.mail.s791537.filter.tests.MessageUtils.count;

import java.io.File;
import java.util.Collection;
import java.util.HashSet;

import junit.framework.TestCase;

import org.junit.Test;

import de.bht.fpa.mail.s000000.common.filter.FilterOperator;
import de.bht.fpa.mail.s000000.common.mail.model.Message;
import de.bht.fpa.mail.s000000.common.mail.testdata.FileSystemTestDataProvider;
import de.bht.fpa.mail.s791537.filter.SubjectFilter;

/**
 * Note that all search strings are in lower case, because the string filters
 * are not case sensitive.
 */
public class SubjectTest extends TestCase {
  private Collection<Message> messagesToFilter;
  private Iterable<Message> filteredMessages;

  private static final int NUMBER_OF_MESSAGES_SUBJECT_IS = 2;
  private static final int NUMBER_OF_MESSAGES_SUBJECT_CONTAINS = 5;
  private static final int NUMBER_OF_MESSAGES_SUBJECT_CONTAINS_NOT = 7;
  private static final int NUMBER_OF_MESSAGES_SUBJECT_STARTS_WITH = 2;
  private static final int NUMBER_OF_MESSAGES_SUBJECT_ENDS_WITH = 3;

  public SubjectTest(String name) {
    super(name);
  }

  @Override
  protected void setUp() {
    messagesToFilter = new FileSystemTestDataProvider(new File("files/testdata")).getMessages();
  }

  @Test
  public void testIs() {
    String searchString = "my pants are on the run";
    filteredMessages = new SubjectFilter(searchString, FilterOperator.IS).filter(messagesToFilter);
    assertEquals("number of messages with IS subject", NUMBER_OF_MESSAGES_SUBJECT_IS, count(filteredMessages));
    for (Message message : filteredMessages) {
      assertEquals(searchString.toLowerCase(), message.getSubject().toLowerCase());
    }
  }

  @Test
  public void testContains() {
    String searchString = "th";
    filteredMessages = new SubjectFilter(searchString, FilterOperator.CONTAINS).filter(messagesToFilter);
    assertEquals("number of messages with CONTAINS subject", NUMBER_OF_MESSAGES_SUBJECT_CONTAINS,
        count(filteredMessages));
    for (Message message : filteredMessages) {
      assertTrue("subject does not contain " + searchString, message.getSubject().toLowerCase().contains(searchString));
    }
  }

  @Test
  public void testContainsNot() {
    String searchString = "My pants are on the run";
    filteredMessages = new SubjectFilter(searchString, FilterOperator.CONTAINS_NOT).filter(messagesToFilter);
    assertEquals("number of messages with CONTAINS_NOT subject", NUMBER_OF_MESSAGES_SUBJECT_CONTAINS_NOT,
        count(filteredMessages));
    for (Message message : filteredMessages) {
      assertTrue("subject does contain " + searchString, !message.getSubject().toLowerCase().contains(searchString));
    }
  }

  @Test
  public void testStartsWith() {
    String searchString = "a";
    filteredMessages = new SubjectFilter(searchString, FilterOperator.STARTS_WITH).filter(messagesToFilter);
    assertEquals("number of messages with STARTS_WITH subject", NUMBER_OF_MESSAGES_SUBJECT_STARTS_WITH,
        count(filteredMessages));
    for (Message message : filteredMessages) {
      assertTrue("subject does not start with " + searchString,
          message.getSubject().toLowerCase().startsWith(searchString));
    }
  }

  @Test
  public void testEndsWith() {
    String searchString = "!";
    filteredMessages = new SubjectFilter(searchString, FilterOperator.ENDS_WITH).filter(messagesToFilter);
    assertEquals("number of messages with ENDS_WITH subject", NUMBER_OF_MESSAGES_SUBJECT_ENDS_WITH,
        count(filteredMessages));
    for (Message message : filteredMessages) {
      assertTrue("subject does not end with " + searchString, message.getSubject().toLowerCase().endsWith(searchString));
    }
  }

  @Test
  public void testEmptyCollection() {
    String searchString = "a";
    filteredMessages = new SubjectFilter(searchString, FilterOperator.CONTAINS).filter(new HashSet<Message>());
    assertEquals("number of messages with empty filter collection", 0, count(filteredMessages));
  }

  @Test
  public void testNull() {
    String searchString = "a";
    try {
      filteredMessages = new SubjectFilter(searchString, FilterOperator.CONTAINS).filter(null);
      fail("should not happen");
    } catch (NullPointerException e) {
      assertTrue("filteredMessages == " + filteredMessages, filteredMessages == null);
    }
  }
}
