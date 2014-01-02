package de.bht.fpa.mail.s791537.filter.tests;

import junit.framework.TestCase;

import org.junit.Test;

import de.bht.fpa.mail.s000000.common.filter.FilterOperator;
import de.bht.fpa.mail.s000000.common.mail.model.Importance;
import de.bht.fpa.mail.s000000.common.mail.model.Message;
import de.bht.fpa.mail.s000000.common.mail.model.Sender;
import de.bht.fpa.mail.s000000.common.mail.testdata.RandomTestDataProvider;
import de.bht.fpa.mail.s791537.filter.ImportanceFilter;
import de.bht.fpa.mail.s791537.filter.ReadFilter;
import de.bht.fpa.mail.s791537.filter.SenderFilter;
import de.bht.fpa.mail.s791537.filter.SubjectFilter;
import de.bht.fpa.mail.s791537.filter.UnionFilter;

public class UnionTest extends TestCase {
  private Iterable<Message> messagesToFilter;
  private Iterable<Message> filteredMessages;
  private static final int NUMBER_OF_MESSAGES = 100;

  public UnionTest(String name) {
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
    filteredMessages = new UnionFilter(new SubjectFilter(searchString, FilterOperator.CONTAINS), new ImportanceFilter(
        searchImportance)).filter(messagesToFilter);
    for (Message message : filteredMessages) {
      assertTrue(searchString.contains(message.getSubject()) || searchImportance.equals(message.getImportance()));
    }
  }

  @Test
  public void test2() {
    String searchString = ".de";
    filteredMessages = new UnionFilter(new SenderFilter(searchString, FilterOperator.ENDS_WITH), new ReadFilter(true))
        .filter(messagesToFilter);
    for (Message message : filteredMessages) {
      Sender sender = message.getSender();
      assertTrue(sender.getEmail().endsWith(searchString) || sender.getPersonal().endsWith(searchString)
          || message.isRead());
    }
  }
}
