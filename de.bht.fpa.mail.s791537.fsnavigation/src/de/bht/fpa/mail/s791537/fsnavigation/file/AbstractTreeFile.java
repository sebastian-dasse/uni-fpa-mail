package de.bht.fpa.mail.s791537.fsnavigation.file;

import java.io.File;

import de.bht.fpa.mail.s791537.common.ITreeElement;

/**
 * This abstract class represents a file in a file tree. It is basically a
 * wrapped up <code>java.io.File</code>.
 * <p>
 * It provides a method for checking whether or not an instance of this class
 * has children and another one for obtaining those children as an
 * <code>Object</code> array. Both methods act as if there were no children and
 * must be overwritten if any other behaviour is desired.
 */
public abstract class AbstractTreeFile implements ITreeElement {
  /**
   * The path of this <code>AbstractTreeFile</code>.
   */
  protected final File file;

  /**
   * Constructs a new <code>AbstractTreeFile</code> with the specified path.
   * 
   * @param path
   *          The path of the <code>AbstractTreeFile</code>.
   */
  public AbstractTreeFile(String path) {
    File newFile = new File(path);
    if (!newFile.exists()) {
      throw new IllegalArgumentException("The specified path is not valid.");
    }
    file = newFile;
  }

  /**
   * @return <code>true</code> if this <code>AbstractTreeFile</code> has
   *         children, otherwise <code>false</code>.
   */
  @Override
  public boolean hasChildren() {
    return false;
  }

  /**
   * @return An Object array containing the children of this
   *         <code>AbstractTreeFile</code>, or an empty array for no children.
   */
  @Override
  public Object[] getChildren() {
    return new Object[0];
  }

  /**
   * @return The name of this <code>AbstractTreeFile</code>.
   */
  @Override
  public String getName() {
    return file.getName();
    // return file.getPath();
  }

  /**
   * @return The path of this <code>AbstractTreeFile</code>.
   */
  public String getPath() {
    return file.getPath();
  }

  @Override
  public String toString() {
    // return getClass().getSimpleName() + "[file=\"" + file.toString() + "\"]";
    return file.getPath();
  }
}
