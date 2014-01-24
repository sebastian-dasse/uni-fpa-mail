package de.bht.fpa.mail.s791537.imapnavigation;

import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.Viewer;

import de.bht.fpa.mail.s791537.common.ITreeElement;

public class ImapTreeContentProvider implements ITreeContentProvider {

  @Override
  public void dispose() {
  }

  @Override
  public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
  }

  @Override
  public Object[] getElements(Object inputElement) {
    return getChildren(inputElement);
  }

  @Override
  public Object[] getChildren(Object parentElement) {
    if (!(parentElement instanceof ITreeElement)) {
      return new Object[0];
    }
    return ((ITreeElement) parentElement).getChildren();
  }

  @Override
  public Object getParent(Object element) {
    return null;
  }

  @Override
  public boolean hasChildren(Object element) {
    if (!(element instanceof ITreeElement)) {
      return false;
    }
    return ((ITreeElement) element).hasChildren();
  }

}
