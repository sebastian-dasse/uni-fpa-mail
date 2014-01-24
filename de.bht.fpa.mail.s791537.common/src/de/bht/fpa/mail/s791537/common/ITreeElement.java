package de.bht.fpa.mail.s791537.common;

/**
 * An interface for elements in a tree structure.
 */
public interface ITreeElement {

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

  /**
   * @return The name of this <code>ITreeElement</code>.
   */
  String getName();
}
