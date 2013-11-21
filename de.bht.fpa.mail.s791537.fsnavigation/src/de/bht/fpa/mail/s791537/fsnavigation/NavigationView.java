package de.bht.fpa.mail.s791537.fsnavigation;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Observable;
import java.util.Observer;
import java.util.Scanner;

import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.part.ViewPart;

import de.bht.fpa.mail.s791537.file.AbstractTreeFile;
import de.bht.fpa.mail.s791537.file.TreeDirectory;
import de.bht.fpa.mail.s791537.file.TreeFile;

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
        if (event.getSelection().isEmpty()) {
          return;
        }
        // TODO wie kriegt man das verdammte File aus der ISelection???
        System.out.println(event.getSelection());
        if (event.getSelection() instanceof IStructuredSelection) {
          IStructuredSelection ssel = (IStructuredSelection) event.getSelection();
          Object obj = ssel.getFirstElement();
          if (!(obj instanceof TreeDirectory)) {
            return;
          }
          TreeDirectory dir = (TreeDirectory) obj;
          Object[] children = dir.getChildren();
          LinkedList<AbstractTreeFile> fileList = new LinkedList<AbstractTreeFile>();
          int numOfMsgs = 0;
          for (Object child : children) {
            if (child instanceof TreeFile) {
              TreeFile file = (TreeFile) child;
              if (file.getName().endsWith(".xml")) {
                fileList.add(file);
                numOfMsgs++;
              }
            }
          }

          Iterator<AbstractTreeFile> iterator = fileList.iterator();
          while (iterator.hasNext()) {
            AbstractTreeFile file = iterator.next();

            // path = path.replace('\\', '/');
            Scanner in = null;
            try {
              // System.err.println(path);
              FileReader fr = new FileReader(file.getPath());
              in = new Scanner(fr);
              while (in.hasNext()) {
                System.out.println(in.nextLine());
              }
            } catch (FileNotFoundException e) {
              // TODO Auto-generated catch block
              e.printStackTrace();
            } finally {
              in.close();
            }
          }
        }
        System.out.println("*******************");
      }
    });

    // TODO DOK IT!
    RootModel.getInstance().addObserver(this);
  }

  /**
   * We will set up a model to initialize tree hierarchy.
   */
  private Object createModel() {
    // return new TreeDirectory(System.getProperty("user.home"));
    return RootModel.getInstance().getRoot();
  }

  /**
   * Passing the focus request to the viewer's control.
   */
  @Override
  public void setFocus() {
    viewer.getControl().setFocus();
  }

  // TODO DOK IT!
  @Override
  public void update(Observable o, Object arg) {
    viewer.setInput(arg);
  }
}