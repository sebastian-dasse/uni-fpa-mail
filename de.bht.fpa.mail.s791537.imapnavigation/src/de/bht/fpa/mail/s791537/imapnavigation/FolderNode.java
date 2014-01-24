package de.bht.fpa.mail.s791537.imapnavigation;

import java.util.Collection;
import java.util.LinkedList;

import de.bht.fpa.mail.s000000.common.mail.model.BaseEntity;
import de.bht.fpa.mail.s000000.common.mail.model.Folder;

public class FolderNode extends AbstractImapNode {

  private final Collection<FolderNode> children;

  public FolderNode(BaseEntity node) {
    super(node);
    children = new LinkedList<FolderNode>();
    for (Folder folder : ((Folder) node).getFolders()) {
      children.add(new FolderNode(folder));
    }
  }

  @Override
  public boolean hasChildren() {
    return children.size() > 0;
  }

  @Override
  public Object[] getChildren() {
    return children.toArray();
  }

  // TODO auch bei MessageNode überschreiben?
  @Override
  public String toString() {
    return ((Folder) node).getFullName();
  }
}
