package de.bht.fpa.mail.s791537.imapnavigation.node;

import java.util.Collection;
import java.util.LinkedList;

import javax.xml.bind.annotation.XmlElement;

import de.bht.fpa.mail.s000000.common.mail.model.Account;
import de.bht.fpa.mail.s000000.common.mail.model.Folder;
import de.bht.fpa.mail.s791537.common.ITreeElement;

//public class AccountNode extends AbstractImapNode {
public class AccountNode implements ITreeElement {

  @XmlElement
  private Account account;

  private final Collection<FolderNode> children;

  private AccountNode() {
    account = null;
    children = new LinkedList<FolderNode>();
  }

  public AccountNode(Account account) {
    // super(account);
    this();
    this.account = account;
    for (Folder f : account.getFolders()) {
      children.add(new FolderNode(f));
    }
  }

  public Account getAccount() {
    return account;
  }

  @Override
  public boolean hasChildren() {
    return children.size() > 0;
  }

  @Override
  public Object[] getChildren() {
    return children.toArray();
  }

  @Override
  public String getName() {
    return account.getName();
  }

  @Override
  public String toString() {
    return getName();
  }
}
