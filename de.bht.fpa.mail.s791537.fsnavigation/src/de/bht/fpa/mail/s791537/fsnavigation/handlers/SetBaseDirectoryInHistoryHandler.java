package de.bht.fpa.mail.s791537.fsnavigation.handlers;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.widgets.List;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.dialogs.ListDialog;
import org.eclipse.ui.handlers.HandlerUtil;

// TODO not yet working
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
    // "Hello, Eclipse world");

    // Dialog dialog = new SetBaseDirectoryInHistoryDialog(window.getShell());
    // dialog.open();
    //

    List list = new List(null, 0);

    ListDialog dialog = new ListDialog(window.getShell());
    dialog.setTitle("der Titel");
    dialog.setMessage("die Message");
    dialog.setContentProvider(ArrayContentProvider.getInstance());
    dialog.setLabelProvider(new LabelProvider());
    dialog.setInput(list);

    dialog.getTableViewer().getTable().setEnabled(false);

    dialog.open();

    return null;
  }
}
