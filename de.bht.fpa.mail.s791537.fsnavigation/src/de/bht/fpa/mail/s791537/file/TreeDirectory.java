package de.bht.fpa.mail.s791537.file;

import java.io.File;
import java.util.Collection;
import java.util.LinkedList;


/**
 * This class represents a directory in a file tree.
 */
public class TreeDirectory extends AbstractTreeFile {

  public TreeDirectory(String path) {
    super(path);
  }

  @Override
  public boolean hasChildren() {
    File[] files = file.listFiles();
    return files != null && files.length > 0;
  }

  @Override
  public Object[] getChildren() {
    Collection<AbstractTreeFile> children = new LinkedList<AbstractTreeFile>();
    for (File f : file.listFiles()) {
      if (f.isDirectory()) {
        children.add(new TreeDirectory(f.getPath()));
      } else {
        children.add(new TreeFile(f.getPath()));
      }
    }
    return children.toArray();
  }

}
