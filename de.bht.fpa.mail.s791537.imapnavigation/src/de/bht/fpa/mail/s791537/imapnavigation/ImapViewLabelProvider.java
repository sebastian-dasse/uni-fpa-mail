package de.bht.fpa.mail.s791537.imapnavigation;

import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;

import de.bht.fpa.mail.s791537.common.ITreeElement;
import de.bht.fpa.mail.s791537.imapnavigation.node.AccountNode;
import de.bht.fpa.mail.s791537.imapnavigation.node.FolderNode;

public class ImapViewLabelProvider extends LabelProvider {
  public static final Image FOLDER_ICON = Activator.imageDescriptorFromPlugin(Activator.PLUGIN_ID,
      "icons/folder-26.png").createImage();
  public static final Image ACCOUNT_ICON = Activator.imageDescriptorFromPlugin(Activator.PLUGIN_ID,
      "icons/message_outline-26.png").createImage();

  @Override
  public String getText(Object element) {
    if (!(element instanceof ITreeElement)) {
      return super.getText(element);
    }
    return ((ITreeElement) element).getName();
  }

  @Override
  public Image getImage(Object element) {
    if (element instanceof FolderNode) {
      return FOLDER_ICON;
    }
    if (element instanceof AccountNode) {
      return ACCOUNT_ICON;
    }
    return super.getImage(element);
  }
}
