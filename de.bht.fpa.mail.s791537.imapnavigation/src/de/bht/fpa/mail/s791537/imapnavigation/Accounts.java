package de.bht.fpa.mail.s791537.imapnavigation;

import java.util.Collection;
import java.util.LinkedList;

import de.bht.fpa.mail.s000000.common.mail.model.Account;
import de.bht.fpa.mail.s000000.common.mail.model.Message;
import de.bht.fpa.mail.s791537.common.IMailProvider;
import de.bht.fpa.mail.s791537.common.ITreeContentProvider;

public class Accounts implements IMailProvider, ITreeContentProvider {
  private final Collection<Account> accounts;

  public Accounts(Account account) {

    accounts = new LinkedList<Account>();
    // account.get

  }

  @Override
  public Collection<Message> getMessages() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public String getPath() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public boolean hasChildren() {
    // TODO Auto-generated method stub
    return false;
  }

  @Override
  public Object[] getChildren() {
    // TODO Auto-generated method stub
    return null;
  }

}
