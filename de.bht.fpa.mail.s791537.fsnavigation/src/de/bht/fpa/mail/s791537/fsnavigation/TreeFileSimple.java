package de.bht.fpa.mail.s791537.fsnavigation;

// TODO: git push funktioniert nicht aus eclipse heraus
import java.io.File;
import java.util.Collection;
import java.util.LinkedList;

/**
 * This simple class represents a file in a file tree. It is basically a wrapped
 * up <code>java.io.File</code>. It provides a method for checking if an
 * instance of <code>TreeFileSimmple</code> is actually a file or a directory, a
 * method for checking whether or not an instance of this class has children and
 * another one for obtaining those children as an <code>Object</code> array.
 */
@Deprecated
public class TreeFileSimple {
  private final File file;

  public TreeFileSimple(String path) {
    file = new File(path);
  }

  public boolean isDirectory() {
    return file.isDirectory();
  }

  public boolean isFile() {
    return file.isFile();
  }

  public boolean hasChildren() {
    File[] files = file.listFiles();
    return files != null && files.length > 0;
  }

  public Object[] getChildren() {
    Collection<TreeFileSimple> children = new LinkedList<TreeFileSimple>();
    for (File f : file.listFiles()) {
      children.add(new TreeFileSimple(f.getPath()));
    }
    return children.toArray();
  }

  @Override
  public String toString() {
    return file.getName();
  }
}
