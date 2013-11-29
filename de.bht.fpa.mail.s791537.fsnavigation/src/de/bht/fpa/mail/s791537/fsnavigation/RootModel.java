package de.bht.fpa.mail.s791537.fsnavigation;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Observable;
import java.util.Scanner;

import de.bht.fpa.mail.s791537.fsnavigation.file.TreeDirectory;

/**
 * This class remembers the selected base directory for the navigation tree. It
 * is by default set to the user's home directory. It will automatically store
 * the last selection for the sessions to come.
 */
public final class RootModel extends Observable {
  private static final File HISTORY = new File(System.getProperty("user.home") + "\\fpa-mailer\\history");
  private static final RootModel INSTANCE = new RootModel();
  private TreeDirectory root;

  private RootModel() {
    String rootpath = readRoot();
    if (rootpath == null) {
      root = new TreeDirectory(System.getProperty("user.home"));
    } else {
      root = new TreeDirectory(rootpath);
    }
  }

  public static RootModel getInstance() {
    return INSTANCE;
  }

  public void setRoot(Object arg) {
    if (arg != null) {
      root = new TreeDirectory(arg.toString());
      writeRoot();
      setChanged();
      notifyObservers(root);
    }
  }

  public TreeDirectory getRoot() {
    return root;
  }

  private void writeRoot() {
    FileWriter out = null;
    try {
      if (!HISTORY.exists()) {
        new File(HISTORY.getPath().substring(0, HISTORY.getPath().lastIndexOf('\\'))).mkdir();
        HISTORY.createNewFile();
      }
      out = new FileWriter(HISTORY);
      out.write(root.getPath());
    } catch (IOException e) {
      e.printStackTrace();
    } finally {
      if (out != null) {
        try {
          out.close();
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
    }
  }

  private String readRoot() {
    if (!HISTORY.exists()) {
      return null;
    }
    Scanner in = null;
    try {
      in = new Scanner(new FileReader(HISTORY));
      if (!in.hasNextLine()) {
        return null;
      }
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }
    String path = in.nextLine();
    in.close();
    return path;

    // -- falls es mehrere Zeilen zu lesen gibt:
    // System.out.println("-----");
    // while (in.hasNextLine()) {
    // System.out.println(in.nextLine());
    // }
  }
}
