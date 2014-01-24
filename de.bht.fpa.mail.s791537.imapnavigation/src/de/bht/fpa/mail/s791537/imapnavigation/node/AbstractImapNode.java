package de.bht.fpa.mail.s791537.imapnavigation.node;

import de.bht.fpa.mail.s000000.common.mail.model.BaseEntity;
import de.bht.fpa.mail.s791537.common.ITreeElement;

// FIXME not used? remove
public abstract class AbstractImapNode implements ITreeElement {

  protected BaseEntity element;

  // public AbstractImapNode(Account account) {
  // element = account;
  // }
  //
  // public AbstractImapNode(Folder folder) {
  // element = folder;
  // }
  //
  // @Override
  // public abstract boolean hasChildren();
  //
  // @Override
  // public abstract Object[] getChildren();
  //
  // @Override
  // public abstract String getName();

  @Override
  public String toString() {
    return getName();
  }
}
