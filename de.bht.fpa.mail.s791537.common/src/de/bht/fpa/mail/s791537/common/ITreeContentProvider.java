package de.bht.fpa.mail.s791537.common;

/**
 * TODO not used? use org.eclipse.jface.viewers.ITreeContentProvider instead?
 * remove!
 */
public interface ITreeContentProvider {

  /**
   * @return <code>true</code> if this <code>ITreeElement</code> has children,
   *         otherwise <code>false</code>.
   */
  boolean hasChildren();

  /**
   * @return An Object array containing the children of this
   *         <code>ITreeElement</code>, or an empty array for no children.
   */
  Object[] getChildren();
}
