package de.bht.fpa.mail.s791537.imapnavigation.node;

import static de.bht.fpa.mail.s000000.common.mail.model.builder.Builders.newAccountBuilder;
import static de.bht.fpa.mail.s000000.common.mail.model.builder.Builders.newFolderBuilder;

import java.util.Collection;
import java.util.LinkedList;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import de.bht.fpa.mail.s000000.common.mail.model.Account;
import de.bht.fpa.mail.s000000.common.mail.testdata.RandomTestDataProvider;
import de.bht.fpa.mail.s791537.common.ITreeElement;

@XmlRootElement
public class Accounts implements ITreeElement {

  private static final int MAX_NUM_DUMMY_MESSAGES = 25;

  @XmlElement(name = "accountNode")
  private final Collection<AccountNode> accounts;

  public Accounts() {
    accounts = new LinkedList<AccountNode>();
  }

  public void addAccount(Account account) {
    accounts.add(new AccountNode(account));
  }

  public void removeAccount(Account account) {
    accounts.remove(new AccountNode(account));
  }

  public void removeAccountNode(AccountNode accountNode) {
    accounts.remove(accountNode);
  }

  /**
   * Removes the first account in the list matching the specified name.
   */
  public void removeAccount(String accountName) {
    for (AccountNode node : accounts) {
      if (node.getAccount().getName().equals(accountName)) {
        removeAccountNode(node);
        // accounts.remove(node);
      }
    }
  }

  @Override
  public boolean hasChildren() {
    return accounts.size() > 0;
  }

  @Override
  public Object[] getChildren() {
    return accounts.toArray();
  }

  public static Account generateGoogleAccount() {
    //@formatter:off
    return newAccountBuilder()
        .name("FPA-Mail")
        .host("imap.gmail.com")
        .username("bhtfpa@gmail.com")
        .password("B-BgxkT_anr2bubbyTLM")
        .build();
    //@formatter:on
  }

  public static Account generateDummyAccount(String name) {
    //@formatter:off
    return newAccountBuilder()
      .name(name)
      .host("imap.beuth-hochschule.de")
      .username("seb")
      .password("sebsecrete")
      .folder(
          newFolderBuilder()
            .fullName("INBOX")
            .builtMessages(new RandomTestDataProvider((int) (Math.random() * MAX_NUM_DUMMY_MESSAGES) + 1).getMessages())
            .folder(
                newFolderBuilder()
                  .fullName("Customers")
                  .builtMessages(
                      new RandomTestDataProvider((int) (Math.random() * MAX_NUM_DUMMY_MESSAGES) + 1).getMessages())
            )
            .folder(
                newFolderBuilder()
                  .fullName("Friends")
                  .builtMessages(
                      new RandomTestDataProvider((int) (Math.random() * MAX_NUM_DUMMY_MESSAGES) + 1).getMessages())
            )
            .folder(
                newFolderBuilder()
                  .fullName("Family")
                  .builtMessages(
                      new RandomTestDataProvider((int) (Math.random() * MAX_NUM_DUMMY_MESSAGES) + 1).getMessages())
            )
      ).folder(
          newFolderBuilder()
            .fullName("Sent")
            .builtMessages(new RandomTestDataProvider((int) (Math.random() * MAX_NUM_DUMMY_MESSAGES) + 1).getMessages())
      ).build();
    //@formatter:on
  }

  @Override
  public String getName() {
    return toString();
  }
}
