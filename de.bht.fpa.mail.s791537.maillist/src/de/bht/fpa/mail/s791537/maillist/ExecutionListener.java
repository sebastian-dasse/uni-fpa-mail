package de.bht.fpa.mail.s791537.maillist;

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.IExecutionListener;
import org.eclipse.core.commands.NotHandledException;
import org.eclipse.jface.viewers.StructuredViewer;
import org.eclipse.jface.viewers.ViewerFilter;

import de.bht.fpa.mail.s000000.common.filter.IFilter;

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
  }

  @Override
  public void postExecuteFailure(String commandId, ExecutionException exception) {
  }

  @Override
  public void postExecuteSuccess(String commandId, Object returnValue) {

    if (!(returnValue instanceof IFilter)) {
      return;
    }
    IFilter filter = (IFilter) returnValue;
    // viewer.addFilter(new ConfigurableViewerFilter(filter));

    System.out.println(">>>>>>>>>>>>>> " + viewer.getFilters().length);

    for (ViewerFilter viewerFilter : viewer.getFilters()) {
      if (viewerFilter instanceof ConfigurableViewerFilter) {
        // viewer.removeFilter(viewerFilter);
        // viewerFilter = new ConfigurableViewerFilter(filter);
        ((ConfigurableViewerFilter) viewerFilter).setFilter(filter);
      }
    }
    // viewer.addFilter(new ConfigurableViewerFilter(filter));

    System.out.println(">>>>>>>>>>>>>> " + viewer.getFilters().length);

    // TODO Filter wieder entfernen, Null-Filter
    // viewer.removeFilter(filter);

    // System.out.println(viewer.getFilters().length);
    viewer.refresh();
  }

  @Override
  public void preExecute(String commandId, ExecutionEvent event) {
  }

}
