package de.bht.fpa.mail.s791537.fsnavigation;

import java.util.Observable;
import java.util.Observer;

import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.part.ViewPart;

import de.bht.fpa.mail.s791537.common.TreeSelectionChangedListener;

public class NavigationView extends ViewPart implements Observer {
  private TreeViewer viewer;

  /**
   * This is a callback that will allow us to create the viewer and initialize
   * it.
   */
  @Override
  public void createPartControl(Composite parent) {
    // a TreeViewer is a Jface widget, which shows trees
    viewer = new TreeViewer(parent, SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL | SWT.BORDER);

    // We set the ContentProvider for the TreeViewer. This class prepares data
    // to be shown by the TreeViewer.
    viewer.setContentProvider(new ViewContentProvider());

    // We set the LabelProvider. This class decides how elements in the tree are
    // shown by returning a text and an optional icon.
    viewer.setLabelProvider(new ViewLabelProvider());

    // Here we set the root of the tree. The framework will ask for more data
    // when the user expands tree items.
    viewer.setInput(createModel());

    viewer.addSelectionChangedListener(new TreeSelectionChangedListener());

    RootModel.getInstance().addObserver(this);

    getSite().setSelectionProvider(viewer);
  }

  /**
   * We will set up a model to initialize tree hierarchy.
   */
  private Object createModel() {
    return RootModel.getInstance().getRoot();
  }

  /**
   * Passing the focus request to the viewer's control.
   */
  @Override
  public void setFocus() {
    viewer.getControl().setFocus();
  }

  /**
   * Updates the base directory of this view on change of the observed
   * <code>RootModel</code>.
   */
  @Override
  public void update(Observable o, Object arg) {
    viewer.setInput(arg);
  }
}