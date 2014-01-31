package de.bht.fpa.mail.s791537.imapnavigation.node;

import static de.bht.fpa.mail.s000000.common.mail.model.builder.Builders.newAccountBuilder;
import static de.bht.fpa.mail.s000000.common.mail.model.builder.Builders.newFolderBuilder;

import java.util.Collection;
import java.util.LinkedList;

import de.bht.fpa.mail.s000000.common.mail.model.Account;
import de.bht.fpa.mail.s000000.common.mail.testdata.RandomTestDataProvider;
import de.bht.fpa.mail.s791537.common.ITreeElement;

public class Accounts implements ITreeElement {

  private final Collection<AccountNode> accounts;

  public Accounts() {
    accounts = new LinkedList<AccountNode>();
  }

  public void addAccount(Account account) {
    accounts.add(new AccountNode(account));
  }

  @Override
  public boolean hasChildren() {
    return accounts.size() > 0;
  }

  @Override
  public Object[] getChildren() {
    return accounts.toArray();
  }

  public void addDummyAccount(String name) {
    //@formatter:off
    addAccount(newAccountBuilder()
      .name(name)
      .host("imap.beuth-hochschule.de")
      .username("seb")
      .password("sebsecrete")
      .folder(
          newFolderBuilder()
            .fullName("INBOX")
            .builtMessages(new RandomTestDataProvider(20).getMessages())
            .folder(
                newFolderBuilder()
                  .fullName("Customers")
                  .builtMessages(new RandomTestDataProvider(10).getMessages())
            )
            .folder(
                newFolderBuilder()
                  .fullName("Friends")
                  .builtMessages(new RandomTestDataProvider(3).getMessages())
            )
            .folder(
                newFolderBuilder()
                  .fullName("Family")
                  .builtMessages(new RandomTestDataProvider(5).getMessages())
            )
      ).folder(
          newFolderBuilder()
            .fullName("Sent")
            .builtMessages(new RandomTestDataProvider(15).getMessages())
      ).build());
    //@formatter:on
  }

  public void addGoogleAccount() {
    addAccount(generateGoogleAccount());

//    //@formatter:off
//    addAccount(newAccountBuilder()
//      .name("FPA")
//      .host("imap.gmail.com")
//      .username("bhtfpa@gmail.com2")
//      .password("B-BgxkT_anr2bubbyTLM")
//      .build());
//    //@formatter:on
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

  @Override
  public String getName() {
    return toString();
  }
}
