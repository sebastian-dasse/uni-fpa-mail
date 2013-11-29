package de.bht.fpa.mail.s791537.fsnavigation.file;

import java.io.File;
import java.io.FilenameFilter;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import javax.xml.bind.JAXB;

import de.bht.fpa.mail.s000000.common.mail.model.Message;

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
    File[] files = file.listFiles();
    if (files == null) {
      return new Object[0];
    }
    Collection<AbstractTreeFile> children = new LinkedList<AbstractTreeFile>();
    for (File f : files) {
      if (f.isDirectory()) {
        children.add(new TreeDirectory(f.getPath()));
      }
      // The navigation tree shall not view files anymore, therefore commented
      // out the following:
      // else {
      // children.add(new TreeFile(f.getPath()));
      // }
    }
    return children.toArray();
  }

  /**
   * @return A <code>List</code> containing all messages in this
   *         <code>TreeDirectory</code> that match our format, or
   *         <code>null</code> for no matching messages.
   */
  public List<Message> getMessages() {
    File[] xmlFiles = file.listFiles(new FilenameFilter() {
      @Override
      public boolean accept(File dir, String name) {
        return name.endsWith(".xml");
      }
    });
    List<Message> messages = new LinkedList<Message>();
    if (xmlFiles == null) {
      return messages;
    }
    for (File xmlFile : xmlFiles) {
      try {
        Message message = JAXB.unmarshal(xmlFile, Message.class);
        if (message == null) {
          continue;
        }
        if (message.getId() == null || message.getId() < 0) {
          continue;
        }
        messages.add(message);
      } catch (Exception e) {
        // Our application shall ignore XML files which do not match our format.
        continue;
      }
    }
    return messages;
  }
}
