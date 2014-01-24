package de.bht.fpa.mail.s791537.imapnavigation;

import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.part.ViewPart;

import de.bht.fpa.mail.s791537.common.TreeSelectionChangedListener;
import de.bht.fpa.mail.s791537.imapnavigation.node.Accounts;

public class ImapView extends ViewPart {
  private TreeViewer viewer;

  @Override
  public void createPartControl(Composite parent) {
    viewer = new TreeViewer(parent, SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL | SWT.BORDER);
    viewer.setContentProvider(new ImapTreeContentProvider());
    viewer.setLabelProvider(new ImapViewLabelProvider());
    viewer.setInput(createModel());

    viewer.addSelectionChangedListener(new TreeSelectionChangedListener());

    getSite().setSelectionProvider(viewer);
  }

  private Object createModel() {
    Accounts accounts = new Accounts();
    accounts.addDummyAccount();
    accounts.addDummyAccount();
    return accounts;
  }

  @Override
  public void setFocus() {
    viewer.getControl().setFocus();
  }
}
