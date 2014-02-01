package de.bht.fpa.mail.s791537.imapaccounts;

import java.io.File;

import javax.xml.bind.DataBindingException;
import javax.xml.bind.JAXB;

import de.bht.fpa.mail.s000000.common.mail.model.Account;

public class ImapAccountLoader {
  public Account load(String filename) {
    File xmlFile = new File(filename);
    Account account = null;
    try {
      account = JAXB.unmarshal(xmlFile, Account.class);
    } catch (DataBindingException e) {
      System.err.println("File '" + xmlFile + "' not found");
    }
    return account;
  }
}
