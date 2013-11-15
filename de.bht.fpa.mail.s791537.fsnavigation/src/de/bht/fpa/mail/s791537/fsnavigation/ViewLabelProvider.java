package de.bht.fpa.mail.s791537.fsnavigation;

import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;

public class ViewLabelProvider extends LabelProvider {

  @Override
  public String getText(Object element) {
    return super.getText(element);
  }

  @Override
  public Image getImage(Object element) {
    if (!(element instanceof AbstractTreeFile)) {
      return super.getImage(element);
    }
    String imageFilePath = "";
    if (element instanceof TreeDirectory) {
      imageFilePath = "icons/folder-26.png";
    } else { // element instanceof TreeFile
      imageFilePath = "icons/file-26.png";
    }
    return Activator.imageDescriptorFromPlugin(Activator.PLUGIN_ID, imageFilePath).createImage();
  }
}
