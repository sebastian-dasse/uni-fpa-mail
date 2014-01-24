package de.bht.fpa.mail.s791537.imapnavigation;

import java.util.List;

import de.bht.fpa.mail.s000000.common.mail.model.BaseEntity;
import de.bht.fpa.mail.s000000.common.mail.model.Folder;

public class FolderGetter {
  private IStrategy strategy;

  public void setStrategy(IStrategy strategy) {
    this.strategy = strategy;
  }

  public Folder[] getFolders(BaseEntity element) {
    List<Folder> folders = strategy.getFolders(element);
    return folders.toArray(new Folder[folders.size()]);
  }
}
