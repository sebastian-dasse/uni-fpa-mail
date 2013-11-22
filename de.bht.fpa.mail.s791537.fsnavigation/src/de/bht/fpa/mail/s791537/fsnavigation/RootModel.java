package de.bht.fpa.mail.s791537.fsnavigation;

import java.util.Observable;

import de.bht.fpa.mail.s791537.fsnavigation.file.TreeDirectory;

/**
 * TODO DOK IT ALL!
 */
public final class RootModel extends Observable {
  private static final RootModel INSTANCE = new RootModel();
  private TreeDirectory root;

  private RootModel() {
    root = new TreeDirectory(System.getProperty("user.home"));
  }

  public static RootModel getInstance() {
    return INSTANCE;
  }

  public void setRoot(Object arg) {
    if (arg != null) {
      root = new TreeDirectory(arg.toString());
      setChanged();
      notifyObservers(root);
    }
  }

  public TreeDirectory getRoot() {
    return root;
  }
}
