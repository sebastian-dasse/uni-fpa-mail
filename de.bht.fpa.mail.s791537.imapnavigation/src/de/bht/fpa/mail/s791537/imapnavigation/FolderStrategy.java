package de.bht.fpa.mail.s791537.imapnavigation;

import java.util.List;

import de.bht.fpa.mail.s000000.common.mail.model.BaseEntity;
import de.bht.fpa.mail.s000000.common.mail.model.Folder;

public class FolderStrategy implements IStrategy {

  @Override
  public List<Folder> getFolders(BaseEntity parentElement) {
    return ((Folder) parentElement).getFolders();
  }

  @Override
  public boolean hasFolders(BaseEntity parentElement) {
    return ((Folder) parentElement).getFolders().size() > 0;
  }

}
