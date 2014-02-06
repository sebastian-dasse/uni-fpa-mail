package de.bht.fpa.mail.s791537.imapaccounts.handlers;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.dialogs.SelectionDialog;
import org.eclipse.ui.handlers.HandlerUtil;

import de.bht.fpa.mail.s791537.imapaccounts.ImapAccountLoader;
import de.bht.fpa.mail.s791537.imapnavigation.node.Accounts;

public class AddHandler extends AbstractHandler {
  public static final int ADD = 0;
  public static final int EDIT = 1;
  public static final int DELETE = 2;

  @Override
  public Object execute(ExecutionEvent event) throws ExecutionException {
    // TODO Auto-generated method stub

    System.out.println("Hallo vom AddHandler");

    Accounts accounts = ImapAccountLoader.load("imap/testAccounts.xml");

    IWorkbenchWindow window = HandlerUtil.getActiveWorkbenchWindowChecked(event);
    Shell shell = window.getShell();
    // ConfigureDialog dialog = new ConfigureDialog(window.getShell());
    Dialog dialog = new MessageDialog(shell, "Configure accounts", null, "My message", MessageDialog.NONE,
        new String[] { "Add account", "Edit account", "Delete account" }, 0);
    // dialog.setTitle("Configure accounts");

    int result = dialog.open();

    switch (result) {
    case ADD:
      System.out.println("add");
      // Dialog editDialog = new EditDialog(window.getShell());
      // editDialog.open();
      SelectionDialog configureDialog = new ConfigureDialog(window.getShell());
      configureDialog.open();
      Object[] res = configureDialog.getResult();
      if (res != null) {
        for (Object o : res) {
          System.out.println(o);
        }
      }
      break;
    case EDIT:
      System.out.println("edit");
      break;
    case DELETE:
      System.out.println("delete");
      break;
    default:
      System.out.println("cancelled");
      break;
    }
    return null;
  }

}
