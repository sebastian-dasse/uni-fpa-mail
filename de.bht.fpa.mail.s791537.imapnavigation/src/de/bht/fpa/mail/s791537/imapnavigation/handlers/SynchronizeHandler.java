package de.bht.fpa.mail.s791537.imapnavigation.handlers;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;

import de.bht.fpa.mail.s000000.common.mail.imapsync.ImapHelper;
import de.bht.fpa.mail.s000000.common.mail.imapsync.SynchronizationException;
import de.bht.fpa.mail.s000000.common.mail.model.Account;

public class SynchronizeHandler extends AbstractHandler {

  @Override
  public Object execute(ExecutionEvent event) throws ExecutionException {
    // if
    // (!event.getCommand().getId().equals("de.bht.fpa.mail.s791537.imapnavigation.commands.synchronize"))
    // {
    // return Status.OK_STATUS;
    // }

    Job job = new Job("Synchronize IMAP") {
      @Override
      protected IStatus run(IProgressMonitor monitor) {
        System.out.println("Synchronizing IMAP...");
        Account remote = ImapHelper.getAccount("bhtfpa");
        // if (remote != null) {
        try {
          ImapHelper.syncAllFoldersToAccount(remote, monitor);
          System.out.println(">>>>> synced");
          ImapHelper.saveAccount(remote);
          System.out.println(">>>>> saved");
        } catch (SynchronizationException e) {
          e.printStackTrace();
        }
        // }
        return Status.OK_STATUS;
      }
    };
    job.setUser(true);
    job.schedule();
    return null;
  }
}
