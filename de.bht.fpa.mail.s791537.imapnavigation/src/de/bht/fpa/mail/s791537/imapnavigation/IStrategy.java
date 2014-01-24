package de.bht.fpa.mail.s791537.imapnavigation;

import java.util.List;

import de.bht.fpa.mail.s000000.common.mail.model.BaseEntity;
import de.bht.fpa.mail.s000000.common.mail.model.Folder;

public interface IStrategy {

  List<Folder> getFolders(BaseEntity element);

  boolean hasFolders(BaseEntity element);
}
