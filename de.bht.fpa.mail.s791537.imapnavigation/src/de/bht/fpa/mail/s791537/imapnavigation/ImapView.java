package de.bht.fpa.mail.s791537.imapnavigation;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
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
  private Accounts accounts;

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
        switch (jobName) {
        case "Synchronize IMAP":
          Display.getDefault().asyncExec(new Runnable() {
            @Override
            public void run() {
              System.out.println("IMAP synchronization done");
              viewer.setInput(createModel());
            }
          });
          break;
        case "Get IMAP":
          Display.getDefault().asyncExec(new Runnable() {
            @Override
            public void run() {
              System.out.println("IMAP account loaded");
              viewer.setInput(accounts);
            }
          });
          break;
        default:
          break;
        }
      }
    });
  }

  private Object createModel() {
    accounts = new Accounts();
    accounts.addAccount(Accounts.generateDummyAccount("IMAP-Dummy"));
    // accounts.addAccount(Accounts.generateDummyAccount("IMAP-Dummy2"));

    Job job = new Job("Get IMAP") {
      @Override
      protected IStatus run(IProgressMonitor monitor) {
        System.out.println("Loading IMAP account...");

        Account remote = ImapHelper.getAccount("FPA-Mail");
        if (remote == null) {
          // System.out.println("Loading IMAP account...");
          remote = Accounts.generateGoogleAccount();
          ImapHelper.saveAccount(remote);
        }
        accounts.addAccount(remote);
        return Status.OK_STATUS;
      }
    };
    // job.setUser(true);
    job.schedule();
    return accounts;
  }

  @Override
  public void setFocus() {
    viewer.getControl().setFocus();
  }
}
