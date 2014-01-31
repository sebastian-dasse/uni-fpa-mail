package de.bht.fpa.mail.s791537.imapnavigation;

import org.eclipse.core.runtime.jobs.IJobChangeEvent;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.core.runtime.jobs.JobChangeAdapter;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.part.ViewPart;

import de.bht.fpa.mail.s000000.common.mail.imapsync.ImapHelper;
import de.bht.fpa.mail.s000000.common.mail.model.Account;
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

    Job.getJobManager().addJobChangeListener(new JobChangeAdapter() {
      @Override
      public void done(IJobChangeEvent event) {
        String jobName = event.getJob().getName();
        if ("Synchronize IMAP".equals(jobName)) {
          System.out.println("IMAP synchronization done");

          // viewer.setInput(new AccountNode(event.getJob().get));
          Display.getDefault().asyncExec(new Runnable() {

            @Override
            public void run() {
              Account remote = ImapHelper.getAccount("bhtfpa");
              // System.out.println("[view]: account: " + remote);

              // viewer.setInput(new AccountNode(remote));
              // viewer.setInput(accounts);
              viewer.setInput(createModel());
              // viewer.refresh();
            }

          });
        }
      }
    });
  }

  private Object createModel() {
    Accounts accounts = new Accounts();
    accounts.addDummyAccount("Beuth-IMAP");
    accounts.addDummyAccount("Beuth-IMAP2");

    Account remote = ImapHelper.getAccount("bhtfpa");
    if (remote == null) {
      remote = Accounts.generateGoogleAccount();
      // ImapHelper.saveAccount(remote);
      // System.out.println("remote saved");
    } else {
      // System.out.println("[view]: account = " + remote.getName());
    }
    accounts.addAccount(remote);

    // accounts.addGoogleAccount();
    //
    // Object o = accounts.getChildren()[0];
    // if (!(o instanceof Account)) {
    // System.out.println(o.getClass().getName());
    // // throw new RuntimeException();
    // }
    // if (!(o instanceof AccountNode)) {
    // System.out.println(o.getClass().getName());
    // // throw new RuntimeException();
    // } else {
    // System.out.println(((AccountNode) o).getName());
    // }

    // ImapHelper.saveAccount((Account) o);
    //
    // Account account = ImapHelper.getAccount("Beuth-IMAP");
    // if (account != null) {
    // System.out.println(account.getHost());
    // } else {
    // System.out.println("account = null");
    // }

    return accounts;
  }

  @Override
  public void setFocus() {
    viewer.getControl().setFocus();
  }
}
