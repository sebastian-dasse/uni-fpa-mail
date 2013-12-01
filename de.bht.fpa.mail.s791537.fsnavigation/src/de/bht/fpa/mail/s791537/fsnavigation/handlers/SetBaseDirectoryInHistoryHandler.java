package de.bht.fpa.mail.s791537.fsnavigation.handlers;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.ListViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.dialogs.SelectionDialog;
import org.eclipse.ui.handlers.HandlerUtil;

import de.bht.fpa.mail.s791537.fsnavigation.RootModel;

/**
 * Our sample handler extends AbstractHandler, an IHandler base class.
 * 
 * @see org.eclipse.core.commands.IHandler
 * @see org.eclipse.core.commands.AbstractHandler
 */
public class SetBaseDirectoryInHistoryHandler extends AbstractHandler {
  /**
   * The constructor.
   */
  public SetBaseDirectoryInHistoryHandler() {
  }

  /**
   * the command has been executed, so extract extract the needed information
   * from the application context.
   */
  @Override
  public Object execute(ExecutionEvent event) throws ExecutionException {
    IWorkbenchWindow window = HandlerUtil.getActiveWorkbenchWindowChecked(event);
    RootModel model = RootModel.getInstance();
    Object[] history = model.getHistory();
    SelectionDialog dialog = new SetBaseDirectoryInHistoryDialog(window.getShell(), history);
    dialog.setTitle("Set Base Directory in History");
    dialog.open();
    Object[] result = dialog.getResult();
    if (result == null || result.length == 0) {
      return null;
    }
    model.setRoot(dialog.getResult()[0].toString());
    return null;
  }
}

// =============================================================================
/**
 * A dialog which lets the user choose from a list of directories that have been
 * selected in the past.
 */
class SetBaseDirectoryInHistoryDialog extends SelectionDialog {
  private ListViewer viewer;
  private final Object[] history;

  protected SetBaseDirectoryInHistoryDialog(Shell parentShell, Object[] history) {
    super(parentShell);
    this.history = history;
    setShellStyle(SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL);
  }

  @Override
  protected Control createDialogArea(Composite parent) {
    Composite composite = (Composite) super.createDialogArea(parent);
    createMessageArea(composite);
    viewer = new ListViewer(composite);
    viewer.setContentProvider(ArrayContentProvider.getInstance());
    viewer.setLabelProvider(new LabelProvider());
    if (history.length == 0) {
      viewer.getList().setEnabled(false);
      viewer.setInput(new Object[] { "No base directories in history." });
    } else {
      viewer.setInput(history);
    }
    viewer.addDoubleClickListener(new IDoubleClickListener() {
      @Override
      public void doubleClick(DoubleClickEvent event) {
        okPressed();
      }
    });
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
