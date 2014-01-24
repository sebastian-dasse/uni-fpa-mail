package de.bht.fpa.mail.s791537.imapnavigation;

import java.util.Collection;
import java.util.LinkedList;

import de.bht.fpa.mail.s000000.common.mail.model.Account;
import de.bht.fpa.mail.s000000.common.mail.model.Folder;
import de.bht.fpa.mail.s000000.common.mail.model.Message;
import de.bht.fpa.mail.s791537.common.IMailProvider;
import de.bht.fpa.mail.s791537.common.ITreeContentProvider;

public class MyAccount implements IMailProvider, ITreeContentProvider {
  private final Account account;
  private final Collection<Folder> children;

  public MyAccount(Account account) {
    System.out.println("account created");
    this.account = account;
    children = account.getFolders();
  }

  @Override
  public Collection<Message> getMessages() {
    System.out.println("tried to get messages");
    // account.getFolders();
    return new LinkedList<Message>();
  }

  @Override
  public String getPath() {
    System.out.println("tried to get path");
    return account.getName();
  }

  @Override
  public boolean hasChildren() {
    return children.size() > 0;
  }

  @Override
  public Object[] getChildren() {
    return children.toArray();
  }
}
