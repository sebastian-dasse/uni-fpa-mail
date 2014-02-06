package de.bht.fpa.mail.s791537.imapaccounts.handlers;

import java.util.Collection;
import java.util.LinkedList;

import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.dialogs.SelectionDialog;

import de.bht.fpa.mail.s000000.common.mail.model.Account;
import de.bht.fpa.mail.s791537.imapaccounts.ImapAccountLoader;
import de.bht.fpa.mail.s791537.imapnavigation.node.AccountNode;
import de.bht.fpa.mail.s791537.imapnavigation.node.Accounts;
import de.ralfebert.rcputils.tables.TableViewerBuilder;

public class ConfigureDialog extends SelectionDialog {
  // private ListViewer viewer;
  private TableViewer viewer;

  protected ConfigureDialog(Shell parentShell) {
    super(parentShell);
    // TODO Auto-generated constructor stub
  }

  @Override
  protected Control createDialogArea(Composite parent) {
    Composite composite = (Composite) super.createDialogArea(parent);

    createMessageArea(composite);
    // viewer = new ListViewer(composite);

    // System.out.println("Current relative path is: " +
    // Paths.get("").toAbsolutePath());
    Accounts accounts = ImapAccountLoader.load("imap/testAccounts.xml");

    String[] accountNames = { "eins", "zwei", "drei vier", "fünf" };
    accountNames = new String[0];

    // TODO remove later
    // Collection<String> names = new LinkedList<String>();
    // for (Object o : accounts.getChildren()) {
    // names.add(((AccountNode) o).getName());
    // }

    Collection<Account> accountList = new LinkedList<Account>();
    for (Object o : accounts.getChildren()) {
      accountList.add(((AccountNode) o).getAccount());
    }

    TableViewerBuilder t = new TableViewerBuilder(new Composite(parent, SWT.None));

    // ColumnBuilder city = t.createColumn("City");
    // city.bindToProperty("name");
    // // city.setPercentWidth(CITY_COLUMN_PERCENT_WIDTH);
    // city.useAsDefaultSortColumn();
    // city.makeEditable();
    // city.build();

    //@formatter:off
//    t.createColumn("Name1").bindToValue(new BaseValue<Account>() {
//      @Override
//      public Object get(Account account) {
//        return account.getName();
//      }
//    })
////    .setPercentWidth(SUBJECT_PERCENT_WIDTH)
//      .build();
    t.createColumn("Name").bindToProperty("name")
//      .setPercentWidth(SUBJECT_PERCENT_WIDTH)
    .makeEditable().build();
    t.createColumn("Host").bindToProperty("host")
//      .setPercentWidth(SUBJECT_PERCENT_WIDTH)
    .build();
    t.createColumn("Username").bindToProperty("username")
//    .setPercentWidth(SUBJECT_PERCENT_WIDTH)
    .build();
    t.createColumn("Pass").bindToProperty("password")
//    .setPercentWidth(SUBJECT_PERCENT_WIDTH)
      .build();
    //@formatter:on

    // t.setInput(Arrays.asList(accounts.getChildren()));
    t.setInput(accountList);

    viewer = t.getTableViewer();
    // viewer = new TableViewer(composite);
    // viewer.setContentProvider(ArrayContentProvider.getInstance());
    // viewer.setLabelProvider(new LabelProvider());

    if (!accounts.hasChildren()) {
      // viewer.getList().setEnabled(false);
      viewer.setInput(new Object[] { "Current configuration contains no accounts." });
    } else {
      // viewer.setInput(accountList);
    }
    // viewer.addDoubleClickListener(new IDoubleClickListener() {
    // @Override
    // public void doubleClick(DoubleClickEvent event) {
    // okPressed();
    // }
    // });
    return composite;
  }

  @Override
  protected void okPressed() {
    // Build a list of selected children.
    IStructuredSelection selection = (IStructuredSelection) viewer.getSelection();
    setResult(selection.toList());
    super.okPressed();
  }
}
