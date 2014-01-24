package de.bht.fpa.mail.s791537.imapnavigation;

import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;

import de.bht.fpa.mail.s000000.common.mail.model.Folder;

public class ImapViewLabelProvider extends LabelProvider {

  @Override
  public String getText(Object element) {
    // TODO

    if (!(element instanceof Folder)) {
      return super.getText(element);
    }
    return ((Folder) element).getFullName();
  }

  @Override
  public Image getImage(Object element) {
    // TODO

    return null;
  }
}
