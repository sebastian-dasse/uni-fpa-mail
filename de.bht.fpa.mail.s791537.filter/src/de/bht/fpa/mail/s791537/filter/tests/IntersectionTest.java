package de.bht.fpa.mail.s791537.filter.tests;

import junit.framework.TestCase;

import org.junit.Test;

import de.bht.fpa.mail.s000000.common.filter.FilterOperator;
import de.bht.fpa.mail.s000000.common.mail.model.Importance;
import de.bht.fpa.mail.s000000.common.mail.model.Message;
import de.bht.fpa.mail.s000000.common.mail.model.Sender;
import de.bht.fpa.mail.s000000.common.mail.testdata.RandomTestDataProvider;
import de.bht.fpa.mail.s791537.filter.ImportanceFilter;
import de.bht.fpa.mail.s791537.filter.IntersectionFilter;
import de.bht.fpa.mail.s791537.filter.ReadFilter;
import de.bht.fpa.mail.s791537.filter.SenderFilter;
import de.bht.fpa.mail.s791537.filter.SubjectFilter;

public class IntersectionTest extends TestCase {
  private Iterable<Message> messagesToFilter;
  private Iterable<Message> filteredMessages;
  private static final int NUMBER_OF_MESSAGES = 100;

  public IntersectionTest(String name) {
    super(name);
  }

  @Override
  protected void setUp() {
    messagesToFilter = new RandomTestDataProvider(NUMBER_OF_MESSAGES).getMessages();
  }

  @Test
  public void test1() {
    String searchString = "Free Willy is finally free";
    Importance searchImportance = Importance.HIGH;
    filteredMessages = new IntersectionFilter(new SubjectFilter(searchString, FilterOperator.IS), new ImportanceFilter(
        Importance.HIGH)).filter(messagesToFilter);
    for (Message message : filteredMessages) {
      assertEquals("subject", searchString, message.getSubject());
      assertEquals("importance", searchImportance, message.getImportance());
    }
  }

  @Test
  public void test2() {
    String searchString = "the";
    filteredMessages = new IntersectionFilter(new SubjectFilter(searchString, FilterOperator.CONTAINS), new ReadFilter(
        true)).filter(messagesToFilter);
    for (Message message : filteredMessages) {
      assertTrue("subject does not contain " + searchString, message.getSubject().contains(searchString));
      assertTrue("message is not read", message.isRead());
    }
  }

  @Test
  public void test3() {
    String searchString = ".de";
    filteredMessages = new IntersectionFilter(new SenderFilter(searchString, FilterOperator.ENDS_WITH),
        new ImportanceFilter(Importance.NORMAL)).filter(messagesToFilter);
    for (Message message : filteredMessages) {
      Sender sender = message.getSender();
      assertTrue("sender" + searchString,
          sender.getEmail().endsWith(searchString) || sender.getPersonal().endsWith(searchString));
      assertTrue("importance", message.getImportance().equals(Importance.NORMAL));
    }
  }
}
