package de.bht.fpa.mail.s791537.fsnavigation;

import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;

public class ViewLabelProvider extends LabelProvider {
  public static final Image FOLDER_ICON = Activator.imageDescriptorFromPlugin(Activator.PLUGIN_ID,
      "icons/folder-26.png").createImage();
  public static final Image FILE_ICON = Activator.imageDescriptorFromPlugin(Activator.PLUGIN_ID, "icons/file-26.png")
      .createImage();

  @Override
  public String getText(Object element) {
    return super.getText(element);
  }

  @Override
  public Image getImage(Object element) {
    if (!(element instanceof AbstractTreeFile)) {
      return super.getImage(element);
    }
    if (element instanceof TreeDirectory) {
      return FOLDER_ICON;
    } // element instanceof TreeFile
    return FILE_ICON;
  }
}
