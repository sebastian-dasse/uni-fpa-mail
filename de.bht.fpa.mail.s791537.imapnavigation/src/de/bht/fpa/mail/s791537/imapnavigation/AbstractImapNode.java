package de.bht.fpa.mail.s791537.imapnavigation;

import de.bht.fpa.mail.s000000.common.mail.model.BaseEntity;
import de.bht.fpa.mail.s000000.common.mail.model.Folder;
import de.bht.fpa.mail.s000000.common.mail.model.Message;
import de.bht.fpa.mail.s791537.common.ITreeElement;

public abstract class AbstractImapNode implements ITreeElement {

  protected BaseEntity node;

  public AbstractImapNode(BaseEntity node) {
    if (!(node instanceof Message || node instanceof Folder)) {
      throw new IllegalArgumentException("The specified is not valid, because it is neither Message nor Folder.");
    }
    this.node = node;
  }

  @Override
  public boolean hasChildren() {
    return false;
  }

  @Override
  public Object[] getChildren() {
    return new Object[0];
  }

  @Override
  public String toString() {
    return getClass().getSimpleName() + "[node=\"" + node.toString() + "\"]";
  }
}
