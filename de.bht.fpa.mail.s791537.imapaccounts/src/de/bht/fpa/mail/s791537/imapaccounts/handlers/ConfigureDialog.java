package de.bht.fpa.mail.s791537.imapaccounts.handlers;

import java.util.Collection;
import java.util.LinkedList;

import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.ListViewer;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.dialogs.SelectionDialog;

import de.bht.fpa.mail.s791537.imapaccounts.ImapAccountLoader;
import de.bht.fpa.mail.s791537.imapnavigation.node.AccountNode;
import de.bht.fpa.mail.s791537.imapnavigation.node.Accounts;

public class ConfigureDialog extends SelectionDialog {
  private ListViewer viewer;

  // private TableViewer viewer;

  protected ConfigureDialog(Shell parentShell) {
    super(parentShell);
    // TODO Auto-generated constructor stub
  }

  @Override
  protected Control createDialogArea(Composite parent) {
    Composite composite = (Composite) super.createDialogArea(parent);
    // Composite composite = parent; // TODO refactor composite = parent

    createMessageArea(composite);
    // viewer = new ListViewer(composite);

    // System.out.println("Current relative path is: " +
    // Paths.get("").toAbsolutePath());
    Accounts accounts = ImapAccountLoader.load("imap/testAccounts.xml");

    String[] accountNames = { "eins", "zwei", "drei vier", "fünf" };
    accountNames = new String[0];

    Collection<String> names = new LinkedList<String>();
    for (Object o : accounts.getChildren()) {
      names.add(((AccountNode) o).getName());
    }

    // TableViewerBuilder t = new TableViewerBuilder(new Composite(parent,
    // SWT.None));
    // t.setInput(Arrays.asList(accounts.getChildren()));
    // viewer = t.getTableViewer();

    viewer = new ListViewer(composite);
    viewer.setContentProvider(ArrayContentProvider.getInstance());
    viewer.setLabelProvider(new LabelProvider());

    if (!accounts.hasChildren()) {
      // viewer.getList().setEnabled(false);
      viewer.setInput(new Object[] { "Current configuration contains no accounts." });
    } else {
      viewer.setInput(names);
    }
    viewer.addDoubleClickListener(new IDoubleClickListener() {
      @Override
      public void doubleClick(DoubleClickEvent event) {
        okPressed();
      }
    });
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
