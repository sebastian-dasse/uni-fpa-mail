package de.bht.fpa.mail.s791537.fsnavigation.handlers;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.ListViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
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
    // MessageDialog.openInformation(window.getShell(), "Fsnavigation",

    RootModel model = RootModel.getInstance();
    final Object[] list = model.getHistory();

    SelectionDialog dialog = new SelectionDialog(window.getShell()) {
      private ListViewer viewer;

      {
        setShellStyle(SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL);
      }

      @Override
      protected Control createDialogArea(Composite parent) {
        Composite composite = (Composite) super.createDialogArea(parent);
        createMessageArea(composite);
        viewer = new ListViewer(composite);
        viewer.setContentProvider(ArrayContentProvider.getInstance());
        viewer.setLabelProvider(new LabelProvider());
        if (list.length == 0) {
          viewer.getList().setEnabled(false);
          viewer.setInput(new Object[] { "No base directories in history." });
        } else {
          viewer.setInput(list);
        }
        return composite;
      }

      @Override
      protected void okPressed() {
        // Build a list of selected children.
        IStructuredSelection selection = (IStructuredSelection) viewer.getSelection();
        setResult(selection.toList());
        super.okPressed();
      }
    };

    dialog.setTitle("Set Base Directory in History");

    int result = dialog.open();
    Object[] res = dialog.getResult();
    // if (result == Window.CANCEL) {
    // if (res == null || res.length == 0) {
    // if (result == Window.CANCEL || res.length == 0) {
    if (res == null || res.length == 0) {
      if (res == null) {
        System.err.println("res == null -> result == Window.CANCEL");
      } else if (res.length == 0) {
        System.err.println("res.length == 0");
      }
      return null;
    }

    // for (Object obj : dialog.getResult()) {
    // System.out.println(obj);
    // }
    System.out.println(dialog.getResult()[0]);
    model.setRoot(dialog.getResult()[0].toString());
    // RootModel.getInstance().setRoot(dialog.getResult()[0]);
    return null;
  }
}
