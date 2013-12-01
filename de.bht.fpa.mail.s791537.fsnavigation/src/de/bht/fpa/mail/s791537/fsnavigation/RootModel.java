package de.bht.fpa.mail.s791537.fsnavigation;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.LinkedList;
import java.util.Observable;
import java.util.Scanner;

import de.bht.fpa.mail.s791537.fsnavigation.file.TreeDirectory;

/**
 * This class remembers the selected base directory for the navigation tree. It
 * is by default set to the user's home directory. The last selection will be
 * remembered automatically for the next start of the application. What's more,
 * all past selections are stored in list for the sessions to come.
 */
public final class RootModel extends Observable {
  private static final File HISTORY = new File(System.getProperty("user.home") + "\\fpa-mailer\\history");
  private static final RootModel INSTANCE = new RootModel();
  private TreeDirectory root;
  // TODO this list could become rather long, maybe we should restrain it to a
  // certain size
  private final LinkedList<String> history;

  private RootModel() {
    history = readHistory();
    if (history == null || history.isEmpty()) { // FIXME
      root = new TreeDirectory(System.getProperty("user.home"));
    } else {
      root = new TreeDirectory(history.peekFirst());
    }
  }

  /**
   * @return The sole instance of this class.
   */
  public static RootModel getInstance() {
    return INSTANCE;
  }

  /**
   * @return The <code>TreeDirectory</code> that is currently set as base
   *         directory.
   */
  public TreeDirectory getRoot() {
    return root;
  }

  /**
   * Sets the base directory of the navigation tree to the specified path.
   * Afterwards the path is added to the history, which is then written to the
   * hard drive. Finally all registered observers are being notified.
   * 
   * @param path
   *          The new path for the base directory.
   */
  public void setRoot(String path) {
    if (path != null) {
      root = new TreeDirectory(path);
      history.remove(path); // avoid double entries
      history.push(path);
      writeHistory();
      setChanged();
      notifyObservers(root);
    }
  }

  /**
   * Returns an array representing the list of base directories that were
   * selected in the past, or an empty array if no selections have been made so
   * far.
   * 
   * @return An array containing all of the past selections.
   */
  public Object[] getHistory() {
    return history.toArray();
  }

  private void writeHistory() {
    PrintWriter out = null;
    try {
      if (!HISTORY.exists()) {
        new File(HISTORY.getPath().substring(0, HISTORY.getPath().lastIndexOf('\\'))).mkdir();
        HISTORY.createNewFile();
      }
      out = new PrintWriter(new FileWriter(HISTORY));
      for (String path : history) {
        out.println(path);
      }
    } catch (IOException e) {
      e.printStackTrace();
    } finally {
      if (out != null) {
        out.close();
      }
    }
  }

  private LinkedList<String> readHistory() {
    LinkedList<String> list = new LinkedList<String>();
    if (!HISTORY.exists()) {
      return list;
    }
    Scanner in = null;
    try {
      in = new Scanner(new FileReader(HISTORY));
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }
    while (in.hasNextLine()) {
      list.add(in.nextLine());
    }
    in.close();
    return list;
  }
}
