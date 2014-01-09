package de.bht.fpa.mail.s791537.statusbar;

import org.eclipse.ui.IStartup;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;

public class Statusbar implements IStartup {

  @Override
  public void earlyStartup() {
    // TODO Auto-generated method stub

    final IWorkbench workbench = PlatformUI.getWorkbench();
    IWorkbenchWindow window = workbench.getActiveWorkbenchWindow();
    if (window != null) {
      System.out.println("HI");

      // IWorkbenchWindowConfigurer configurer = getWindowConfigurer();
      // configurer.setInitialSize(new Point(WIDTH, HEIGHT));
      // IWorkbenchWindowConfigurer configurer = getWindowConfigurer();
      // configurer.setInitialSize(new Point(WIDTH, HEIGHT));
      // configurer.setShowCoolBar(false);
      // configurer.setShowStatusLine(false);
    }
  }
}
