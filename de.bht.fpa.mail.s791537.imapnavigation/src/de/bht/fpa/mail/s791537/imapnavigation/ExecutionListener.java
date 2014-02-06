package de.bht.fpa.mail.s791537.imapnavigation;

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.IExecutionListener;
import org.eclipse.core.commands.NotHandledException;
import org.eclipse.jface.viewers.StructuredViewer;

import de.bht.fpa.mail.s791537.imapnavigation.node.Accounts;

/**
 * Listens to all (!) executed commands.
 */
public class ExecutionListener implements IExecutionListener {
  private final StructuredViewer viewer;

  public ExecutionListener(StructuredViewer viewer) {
    this.viewer = viewer;
  }

  @Override
  public void notHandled(String commandId, NotHandledException exception) {
    // TODO Auto-generated method stub

  }

  @Override
  public void postExecuteFailure(String commandId, ExecutionException exception) {
    // TODO Auto-generated method stub

  }

  @Override
  public void postExecuteSuccess(String commandId, Object returnValue) {
    System.out.println(commandId + " " + returnValue);
    if (!(returnValue instanceof Accounts)) {
      return;
    }
    if (commandId.equals("de.bht.fpa.mail.s791537.imapaccounts.commands.delete")) {
      System.out.println("aha - delete");
    } else {
      System.out.println(commandId);
    }
    viewer.setInput(returnValue);
    viewer.refresh();
  }

  @Override
  public void preExecute(String commandId, ExecutionEvent event) {
    // TODO Auto-generated method stub

  }

}
