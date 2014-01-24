package de.bht.fpa.mail.s791537.common;

import java.util.Collection;

import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.SelectionChangedEvent;

import de.bht.fpa.mail.s000000.common.mail.model.Message;
import de.bht.fpa.mail.s000000.common.rcp.selection.SelectionHelper;

public class TreeSelectionChangedListener implements ISelectionChangedListener {

  @Override
  public void selectionChanged(SelectionChangedEvent event) {
    ITreeElement dir = SelectionHelper.handleStructuredSelectionEvent(event, ITreeElement.class);
    if (dir == null) {
      return;
    }
    System.out.println("Selected directory: " + dir);
    if (dir instanceof IMailProvider) {
      Collection<Message> messages = ((IMailProvider) dir).getMessages();
      System.out.println("Number of messages: " + messages.size());
      for (Message message : messages) {
        System.out.println(message.toShortString());
      }
    } else {
      System.out.println("Number of direct subdirectories: " + dir.getChildren().length);
    }
  }
}
