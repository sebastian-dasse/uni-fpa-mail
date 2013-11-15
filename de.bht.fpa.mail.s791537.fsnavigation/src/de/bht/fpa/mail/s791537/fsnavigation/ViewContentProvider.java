package de.bht.fpa.mail.s791537.fsnavigation;

import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.Viewer;

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
    if (!hasChildren(parentElement)) {
      return new Object[0];
    }
    // TODO instanceof
    return ((AbstractTreeFile) parentElement).getChildren();

    // TODO FRAGE: ?-Operator nicht ok? (von Checkstyle moniert):
    // return hasChildren(parentElement) ? ((AbstractTreeFile)
    // parentElement).getChildren() : new Object[0];
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

    // TODO FRAGE: ?-Operator nicht ok? (von Checkstyle moniert):
    // return element instanceof AbstractTreeFile ? ((AbstractTreeFile)
    // element).hasChildren() : false;
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