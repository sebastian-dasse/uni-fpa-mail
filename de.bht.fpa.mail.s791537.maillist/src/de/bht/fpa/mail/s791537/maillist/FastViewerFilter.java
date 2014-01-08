package de.bht.fpa.mail.s791537.maillist;

import static de.bht.fpa.mail.s791537.maillist.MaillistView.DATE_FORMAT;

import java.util.LinkedList;
import java.util.List;

import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.swt.widgets.Text;

import de.bht.fpa.mail.s000000.common.mail.model.Message;
import de.bht.fpa.mail.s000000.common.mail.model.Recipient;

public class FastViewerFilter extends ViewerFilter {
  private final Text searchText;

  public FastViewerFilter(Text searchText) {
    this.searchText = searchText;
  }

  @Override
  public boolean select(Viewer viewer, Object parentElement, Object element) {
    final String searchString = searchText.getText().toLowerCase();
    for (String string : listFieldsToBeCompared((Message) element)) {
      if (string.toLowerCase().contains(searchString)) {
        return true;
      }
    }
    return false;
  }

  private List<String> listFieldsToBeCompared(Message message) {
    List<String> strings = new LinkedList<String>();
    strings.add(message.getSubject());
    strings.add(message.getText());
    strings.add(DATE_FORMAT.format(message.getReceived()));
    strings.add(DATE_FORMAT.format(message.getSent()));
    for (Recipient recipient : message.getRecipients()) {
      strings.add(recipient.getEmail());
      strings.add(recipient.getPersonal());
    }
    strings.add(message.getSender().getEmail());
    strings.add(message.getSender().getPersonal());
    return strings;
  }
}
