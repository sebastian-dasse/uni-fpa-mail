package de.bht.fpa.mail.s791537.imapaccounts;

import java.io.File;

import javax.xml.bind.DataBindingException;
import javax.xml.bind.JAXB;

import de.bht.fpa.mail.s000000.common.mail.model.Account;
import de.bht.fpa.mail.s791537.imapnavigation.node.AccountNode;
import de.bht.fpa.mail.s791537.imapnavigation.node.Accounts;

public class Test {
  public static void main(String[] args) {
    readXML("files/testReadAccount.xml");
    System.out.println("---");
    String writePath = writeXML("files/testWriteAccount.xml");
    readXML(writePath);
    System.out.println("---");

    printAccounts(readAccountsFromXML("files/testAccounts.xml"));
    String writePath2 = writeAccountsToXML("files/testAccounts.xml");
    printAccounts(readAccountsFromXML(writePath2));
    System.out.println("Done");
  }

  private static void printAccounts(Accounts accounts) {
    for (Object o : accounts.getChildren()) {
      System.out.println(((AccountNode) o).getAccount());
    }
    System.out.println("---");
  }

  public static void readXML(String filename) {
    File xmlFile = new File(filename);
    try {
      Account account = JAXB.unmarshal(xmlFile, Account.class);
      System.out.println(account);
    } catch (DataBindingException e) {
      System.err.println("File '" + xmlFile + "' not found");
    }
  }

  public static String writeXML(String filename) {
    Account account = Accounts.generateDummyAccount("Testkonto");
    account.getFolders().clear();

    File xmlFile = new File(filename);
    JAXB.marshal(account, xmlFile);
    return filename;
  }

  public static Accounts readAccountsFromXML(String filename) {
    Accounts accounts = null;
    File xmlFile = new File(filename);

    try {
      accounts = JAXB.unmarshal(xmlFile, Accounts.class);
      // System.out.println(accounts);
    } catch (DataBindingException e) {
      System.err.println("File '" + xmlFile + "' not found");
    }
    return accounts;
  }

  public static String writeAccountsToXML(String filename) {
    Accounts accounts = new Accounts();

    Account account = Accounts.generateDummyAccount("Testkonto");
    account.getFolders().clear();
    accounts.addAccount(account);

    Account account2 = Accounts.generateDummyAccount("Testkonto2");
    account2.getFolders().clear();
    accounts.addAccount(account2);

    File xmlFile = new File(filename);
    JAXB.marshal(accounts, xmlFile);
    return filename;
  }
}
