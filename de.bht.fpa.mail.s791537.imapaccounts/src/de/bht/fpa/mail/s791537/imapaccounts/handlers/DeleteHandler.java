package de.bht.fpa.mail.s791537.imapaccounts.handlers;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.ListViewer;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.dialogs.SelectionDialog;
import org.eclipse.ui.handlers.HandlerUtil;

import de.bht.fpa.mail.s791537.imapaccounts.ImapAccountLoader;
import de.bht.fpa.mail.s791537.imapnavigation.node.AccountNode;
import de.bht.fpa.mail.s791537.imapnavigation.node.Accounts;

public class DeleteHandler extends AbstractHandler {

  // private final String path = "imap/testAccounts.xml";
  private final String path = "imap/test.xml";
  private final Accounts accounts = ImapAccountLoader.load(path);

  @Override
  public Object execute(ExecutionEvent event) throws ExecutionException {
    // TODO Auto-generated method stub

    System.out.println("Hallo vom DeleteHandler");

    // Accounts accounts = ImapAccountLoader.load("imap/testAccounts.xml");

    IWorkbenchWindow window = HandlerUtil.getActiveWorkbenchWindowChecked(event);
    SelectionDialog dialog = new AccountSelectionDialog2(window.getShell(), accounts);
    dialog.open();
    Object[] result = dialog.getResult();
    if (result != null) {
      for (Object o : result) {
        System.out.println(o);
        String name = (String) o;
        System.out.println("name: " + name);
        accounts.removeAccount(name);
      }
      System.out.println("after delete:");
      System.out.println("--");
      System.out.println("num of accounts: " + accounts.getChildren().length);
      for (Object o : accounts.getChildren()) {
        System.out.println(o);
      }
      System.out.println("--");
      ImapAccountLoader.save(accounts, path);
    } else {
      System.out.println("nothing selected");
    }

    return accounts;
  }
}

// =============================================================================

class AccountSelectionDialog2 extends SelectionDialog {
  private ListViewer viewer;
  private final Accounts accounts;

  protected AccountSelectionDialog2(Shell parentShell, Accounts accounts) {
    super(parentShell);
    this.accounts = accounts;
  }

  @Override
  protected Control createDialogArea(Composite parent) {
    Composite composite = (Composite) super.createDialogArea(parent);
    createMessageArea(composite);
    viewer = new ListViewer(composite);
    viewer.setContentProvider(ArrayContentProvider.getInstance());
    viewer.setLabelProvider(new LabelProvider());
    if (!accounts.hasChildren()) {
      viewer.getList().setEnabled(false);
      viewer.setInput(new Object[] { "No accounts configured." });
    } else {
      Object[] objs = accounts.getChildren();
      String[] names = new String[objs.length];
      for (int i = 0; i < objs.length; i++) {
        names[i] = ((AccountNode) objs[i]).getAccount().getName();
      }
      viewer.setInput(names);
    }
    // viewer.addDoubleClickListener(new IDoubleClickListener() {
    // @Override
    // public void doubleClick(DoubleClickEvent event) {
    // okPressed();
    // }
    // });
    return composite;
  }

  /**
   * Notifies that the ok button of this dialog has been pressed. Sets the
   * result to the selection made by the user. Finally sets this dialog's return
   * code to <code>Window.OK</code> and closes the dialog.
   */
  @Override
  protected void okPressed() {
    // Build a list of selected children.
    IStructuredSelection selection = (IStructuredSelection) viewer.getSelection();
    setResult(selection.toList());
    super.okPressed();
  }
}