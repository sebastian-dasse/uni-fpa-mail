package de.bht.fpa.mail.s791537.imapaccounts;

import java.io.File;

import javax.xml.bind.DataBindingException;
import javax.xml.bind.JAXB;

import de.bht.fpa.mail.s000000.common.mail.model.Account;
import de.bht.fpa.mail.s791537.imapnavigation.node.AccountNode;
import de.bht.fpa.mail.s791537.imapnavigation.node.Accounts;

public class Test {
  public static void main(String[] args) {

    System.out.println("Read account from XML:");
    readXML("files/testReadAccount.xml");
    System.out.println();

    String writePath = writeXML("files/testWriteAccount.xml");
    System.out.println("Written account to " + writePath);

    System.out.println("Restore account from: " + writePath);
    readXML(writePath);
    System.out.println();

    System.out.println("Read accounts from XML:");
    printAccounts(readAccountsFromXML("files/testAccounts.xml"));

    String writePath2 = writeAccountsToXML("files/testAccounts.xml");
    System.out.println("Written accounts to " + writePath2);

    System.out.println("Restore accounts from: " + writePath2);
    printAccounts(readAccountsFromXML(writePath2));
    System.out.println("Done");
  }

  private static void printAccounts(Accounts accounts) {
    for (Object o : accounts.getChildren()) {
      System.out.println(((AccountNode) o).getAccount());
    }
    System.out.println();
  }

  public static void readXML(String filename) {
    try {
      Account account = JAXB.unmarshal(new File(filename), Account.class);
      System.out.println(account);
    } catch (DataBindingException e) {
      System.err.println("File '" + filename + "' not found");
    }
  }

  public static String writeXML(String filename) {
    Account account = Accounts.generateDummyAccount("Testkonto");
    account.getFolders().clear();
    JAXB.marshal(account, new File(filename));
    return filename;
  }

  public static Accounts readAccountsFromXML(String filename) {
    Accounts accounts = null;
    try {
      accounts = JAXB.unmarshal(new File(filename), Accounts.class);
    } catch (DataBindingException e) {
      System.err.println("File '" + filename + "' not found");
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

    JAXB.marshal(accounts, new File(filename));
    return filename;
  }
}
