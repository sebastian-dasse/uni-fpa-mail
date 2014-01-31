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

    System.out.println(">>>>> HALLO WELT vom Handler");

    Job job = new Job("my job") {

      @Override
      protected IStatus run(IProgressMonitor monitor) {

        // ---- TEST
        System.out.println("running \"my job\"");

        Account remote = ImapHelper.getAccount("bhtfpa");
        if (remote != null) {
          System.out.println("[handler]: remote: " + remote.getHost());
          System.out.println("[handler]: remote: " + remote);

          try {
            ImapHelper.syncAllFoldersToAccount(remote, monitor);
            System.out.println(">>>>> synced");
            // ImapHelper.saveAccount(remote);
            System.out.println(">>>>> saved");
          } catch (SynchronizationException e) {
            System.out.println(">>>>> FEHLER");
            e.printStackTrace();
          } finally {
            System.out.println(">>>>> finally");
          }
        } else {
          System.out.println("account = null");
          //
          // // try {
          // // ImapHelper.syncAllFoldersToAccount(account, monitor);
          // // System.out.println(">>>>> synced");
          // // ImapHelper.saveAccount(account);
          // // System.out.println(">>>>> saved");
          // // } catch (SynchronizationException e) {
          // // // TODO Auto-generated catch block
          // // System.out.println(">>>>> FEHLER");
          // // e.printStackTrace();
          // // } finally {
          // // System.out.println(">>>>> finally");
          // // }
          // remote = Accounts.generateGoogleAccount();
        }
        // accounts.add(remote);

        // System.out.println(">>>>> try to sync all folders");
        // try {
        // // ImapHelper.syncAllFoldersToAccount(account, monitor);
        // System.out.println(">>>>> synced");
        // } catch (SynchronizationException e) {
        // // TODO Auto-generated catch block
        // System.out.println(">>>>> FEHLER");
        // e.printStackTrace();
        // } finally {
        // System.out.println(">>>>> finally");
        // }
        // System.out.println(">>>>> try to save");
        // ImapHelper.saveAccount(account);

        // String name = remote == null ? "not found" : remote.getName();
        // System.out.println(name);

        return Status.OK_STATUS;
      }

    };
    job.setUser(true);
    job.schedule();

    // Job.getJobManager().addJobChangeListener(new JobChangeAdapter() {
    // @Override
    // public void scheduled(IJobChangeEvent event) {
    //
    // // System.out.println(event.getJob().getName());
    //
    // // if ("my job".equals(event.getJob().getName())) {
    // // System.out.println("scheduled");
    // // }
    // }
    //
    // @Override
    // public void running(IJobChangeEvent event) {
    // if ("my job".equals(event.getJob().getName())) {
    // System.out.println("running");
    // }
    // }
    //
    // @Override
    // public void done(IJobChangeEvent event) {
    //
    // String jobName = event.getJob().getName();
    // if ("my job".equals(jobName)) {
    // System.out.println(jobName + " done");
    // }
    // }
    // });

    return null;
  }
}
