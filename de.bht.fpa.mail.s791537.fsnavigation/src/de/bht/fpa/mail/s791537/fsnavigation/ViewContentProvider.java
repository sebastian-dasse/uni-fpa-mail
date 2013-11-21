package de.bht.fpa.mail.s791537.fsnavigation;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.LinkedList;
import java.util.Scanner;

import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.Viewer;

import de.bht.fpa.mail.s791537.file.AbstractTreeFile;
import de.bht.fpa.mail.s791537.file.TreeDirectory;
import de.bht.fpa.mail.s791537.file.TreeFile;

public class ViewContentProvider implements ITreeContentProvider {

  /**
   * This method is called when the user expands a tree node in the View. The
   * parameter of the method is the selected item, and the method is expected to
   * return the direct children of the item.
   * 
   * @param parentElement
   *          the expanded element in the tree, for which the framework requests
   *          the children
   */
  @Override
  public Object[] getChildren(Object parentElement) {
    if (!(parentElement instanceof AbstractTreeFile) || !hasChildren(parentElement)) {
      return new Object[0];
    }

    // ---- neu --
    // TODO das meiste davon sollte von der NavView aus aufgerufen werden
    // TODO Ordner ohne Unterordner sollten keine Aufklapp-Dreiecke mehr haben
    AbstractTreeFile parent = (AbstractTreeFile) parentElement;
    System.out.println("Selected directory: " + parent.getPath());
    Object[] children = parent.getChildren();
    LinkedList<AbstractTreeFile> dirList = new LinkedList<AbstractTreeFile>();
    LinkedList<AbstractTreeFile> fileList = new LinkedList<AbstractTreeFile>();
    int numOfMsgs = 0;
    for (Object child : children) {
      if (child instanceof TreeDirectory) {
        dirList.add((TreeDirectory) child);
      } else { // child instanceof TreeFile
        TreeFile file = (TreeFile) child;
        if (file.getName().endsWith(".xml")) {
          fileList.add(file);
          numOfMsgs++;
        }
      }
    }
    System.out.println("Number of messages: " + numOfMsgs);
    for (AbstractTreeFile file : fileList) {
      System.out.print(": ");
      try {
        Scanner in = new Scanner(new FileReader(new File(file.getPath())));
        while (in.hasNext()) {
          System.out.println(in.nextLine());
        }
      } catch (FileNotFoundException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }
    }
    return dirList.toArray();
    // ----
    // return ((AbstractTreeFile) parentElement).getChildren();
  }

  /**
   * This method is called when the user expands a tree node in the View. The
   * framework asks if the given element has any children. The parameter of the
   * method is a tree item, and the method is expected to return
   * <code>true></code> if the item has children, or <code>false</code> if it
   * has no children.
   * 
   * @param element
   *          a tree item, for which the framework wants to know if it has
   *          children
   */
  @Override
  public boolean hasChildren(Object element) {
    if (!(element instanceof AbstractTreeFile)) {
      return false;
    }
    return ((AbstractTreeFile) element).hasChildren();
  }

  // ==========================================================================
  // In our simple tree, you don't need to change any of the following methods.
  // ==========================================================================

  @Override
  public Object[] getElements(Object parent) {
    return getChildren(parent);
  }

  @Override
  public void inputChanged(Viewer v, Object oldInput, Object newInput) {
  }

  @Override
  public void dispose() {
  }

  @Override
  public Object getParent(Object element) {
    return null;
  }

}