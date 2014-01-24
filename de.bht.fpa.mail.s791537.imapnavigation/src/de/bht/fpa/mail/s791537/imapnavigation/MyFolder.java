package de.bht.fpa.mail.s791537.imapnavigation;

import java.util.Collection;

import de.bht.fpa.mail.s000000.common.mail.model.Folder;
import de.bht.fpa.mail.s000000.common.mail.model.Message;
import de.bht.fpa.mail.s791537.common.IMailProvider;

public class MyFolder implements IMailProvider {
  private final Folder folder;

  public MyFolder(Folder folder) {
    this.folder = folder;
  }

  @Override
  public Collection<Message> getMessages() {
    return folder.getMessages();
  }

  @Override
  public String getPath() {
    return folder.getFullName();
  }
}
