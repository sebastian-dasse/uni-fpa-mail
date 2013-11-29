package de.bht.fpa.mail.s791537.fsnavigation;

import java.util.List;
import java.util.Observable;
import java.util.Observer;

import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.part.ViewPart;

import de.bht.fpa.mail.s000000.common.mail.model.Message;
import de.bht.fpa.mail.s000000.common.rcp.selection.SelectionHelper;
import de.bht.fpa.mail.s791537.fsnavigation.file.TreeDirectory;

public class NavigationView extends ViewPart implements Observer {
  public static final String ID = "de.bht.fpa.s791537.fsnavigation.NavigationView";
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

    viewer.addSelectionChangedListener(new ISelectionChangedListener() {
      @Override
      public void selectionChanged(SelectionChangedEvent event) {
        TreeDirectory dir = SelectionHelper.handleStructuredSelectionEvent(event, TreeDirectory.class);
        if (dir == null) {
          return;
        }
        List<Message> messages = dir.getMessages();
        System.out.println("Selected directory: " + dir.getPath());
        System.out.println("Number of messages: " + messages.size());
        for (Message message : messages) {
          System.out.println(message.toShortString());
        }
      }
    });
    
    RootModel.getInstance().addObserver(this);
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

  @Override
  public void update(Observable o, Object arg) {
    viewer.setInput(arg);
  }
}