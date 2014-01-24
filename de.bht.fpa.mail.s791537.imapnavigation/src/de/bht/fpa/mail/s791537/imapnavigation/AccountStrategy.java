package de.bht.fpa.mail.s791537.imapnavigation;

import java.util.List;

import de.bht.fpa.mail.s000000.common.mail.model.Account;
import de.bht.fpa.mail.s000000.common.mail.model.BaseEntity;
import de.bht.fpa.mail.s000000.common.mail.model.Folder;

public class AccountStrategy implements IStrategy {

  @Override
  public List<Folder> getFolders(BaseEntity parentElement) {
    return ((Account) parentElement).getFolders();
  }

  @Override
  public boolean hasFolders(BaseEntity parentElement) {
    return ((Account) parentElement).getFolders().size() > 0;
  }

}
