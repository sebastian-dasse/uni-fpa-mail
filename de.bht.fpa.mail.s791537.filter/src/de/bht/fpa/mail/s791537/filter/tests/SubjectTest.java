package de.bht.fpa.mail.s791537.filter.tests;

import junit.framework.TestCase;

import org.junit.Test;

import de.bht.fpa.mail.s000000.common.filter.FilterOperator;
import de.bht.fpa.mail.s000000.common.mail.model.Message;
import de.bht.fpa.mail.s000000.common.mail.testdata.RandomTestDataProvider;
import de.bht.fpa.mail.s791537.filter.SubjectFilter;

public class SubjectTest extends TestCase {
  private Iterable<Message> messagesToFilter;
  private Iterable<Message> filteredMessages;
  private static final int NUMBER_OF_MESSAGES = 100;

  public SubjectTest(String name) {
    super(name);
  }

  @Override
  protected void setUp() {
    messagesToFilter = new RandomTestDataProvider(NUMBER_OF_MESSAGES).getMessages();
  }

  @Test
  public void testIs() {
    String searchString = "My pants are on the run";
    filteredMessages = new SubjectFilter(searchString, FilterOperator.IS).filter(messagesToFilter);
    for (Message message : filteredMessages) {
      assertEquals(searchString, message.getSubject());
    }
  }

  @Test
  public void testContains() {
    String searchString = "the";
    filteredMessages = new SubjectFilter(searchString, FilterOperator.CONTAINS).filter(messagesToFilter);
    for (Message message : filteredMessages) {
      assertTrue("subject does not contain " + searchString, message.getSubject().contains(searchString));
    }
  }

  @Test
  public void testContainsNot() {
    String searchString = "My pants are on the run";
    filteredMessages = new SubjectFilter(searchString, FilterOperator.CONTAINS_NOT).filter(messagesToFilter);
    for (Message message : filteredMessages) {
      assertTrue("subject does contain " + searchString, !message.getSubject().contains(searchString));
    }
  }

  @Test
  public void testStartsWith() {
    String searchString = "My pants are on the run";
    filteredMessages = new SubjectFilter(searchString, FilterOperator.STARTS_WITH).filter(messagesToFilter);
    for (Message message : filteredMessages) {
      assertTrue("subject does not start with " + searchString, message.getSubject().startsWith(searchString));
    }
  }

  @Test
  public void testEndsWith() {
    String searchString = "My pants are on the run";
    filteredMessages = new SubjectFilter(searchString, FilterOperator.ENDS_WITH).filter(messagesToFilter);
    for (Message message : filteredMessages) {
      assertTrue("subject does not end with " + searchString, message.getSubject().endsWith(searchString));
    }
  }
}
