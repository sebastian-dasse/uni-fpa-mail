package de.bht.fpa.mail.s791537.imapnavigation.node;

import java.util.Collection;
import java.util.LinkedList;

import de.bht.fpa.mail.s000000.common.mail.model.Folder;
import de.bht.fpa.mail.s000000.common.mail.model.Message;
import de.bht.fpa.mail.s791537.common.IMailProvider;
import de.bht.fpa.mail.s791537.common.ITreeElement;

//public class FolderNode extends AbstractImapNode implements IMailProvider {
public class FolderNode implements ITreeElement, IMailProvider {

  private final Folder folder;
  private final Collection<FolderNode> children;

  public FolderNode(Folder folder) {
    // super(folder);
    this.folder = folder;
    children = new LinkedList<FolderNode>();
    for (Folder f : folder.getFolders()) {
      children.add(new FolderNode(f));
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

  @Override
  public String getName() {
    return folder.getFullName();
  }

  @Override
  public Collection<Message> getMessages() {
    return folder.getMessages();
  }

  @Override
  public String toString() {
    return getName();
  }
}
