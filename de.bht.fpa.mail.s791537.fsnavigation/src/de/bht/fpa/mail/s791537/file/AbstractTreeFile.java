package de.bht.fpa.mail.s791537.file;

import java.io.File;

/**
 * This abstract class represents a file in a file tree. It is basically a
 * wrapped up <code>java.io.File</code>.
 * <p>
 * It provides a method for checking whether or not an instance of this class
 * has children and another one for obtaining those children as an
 * <code>Object</code> array. Both methods act as if there were no children and
 * must be overwritten if any other behaviour is desired.
 */
public abstract class AbstractTreeFile {
  protected final File file;

  public AbstractTreeFile(String path) {
    File newFile = new File(path);
    if (!newFile.exists()) {
      throw new IllegalArgumentException("The specified path is not valid.");
    }
    file = newFile;
  }

  public boolean hasChildren() {
    return false;
  }

  public Object[] getChildren() {
    return new Object[0];
  }

  public String getName() {
    return file.getName();
  }

  public String getPath() {
    return file.getPath();
  }

  @Override
  public String toString() {
    return getClass().getSimpleName() + "[file=\"" + file.toString() + "\"]";
  }
}
