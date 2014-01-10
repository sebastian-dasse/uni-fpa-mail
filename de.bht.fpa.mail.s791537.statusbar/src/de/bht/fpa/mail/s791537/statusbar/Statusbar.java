package de.bht.fpa.mail.s791537.statusbar;

import org.eclipse.ui.IStartup;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;

import de.bht.fpa.mail.s000000.common.rcp.statusbar.StatusBarHelper;

public class Statusbar implements IStartup {

  public static final String PLUGIN_ID = "de.bht.fpa.mail.s791537.statusbar";

  @Override
  public void earlyStartup() {
    // TODO Auto-generated method stub

    System.out.println("Statusbar.earlyStartup()");

    // -- works
    // System.out.println(StatusBarHelper.getStatusLineManager());
    // StatusBarHelper.setMessage("hi");

    final IWorkbench workbench = PlatformUI.getWorkbench();
    System.out.println("statusbar: workbench: " + workbench);
    IWorkbenchWindow window = workbench.getActiveWorkbenchWindow();
    System.out.println("statusbar: window: " + window);

    if (window != null) {
      System.out.println("window != null");

      // window.getActivePage().addSelectionListener(new ISelectionListener() {
      //
      // @Override
      // public void selectionChanged(IWorkbenchPart part, ISelection selection)
      // {
      // IMailProvider dir =
      // SelectionHelper.handleStructuredSelection(selection,
      // IMailProvider.class);
      // if (dir == null) {
      // return;
      // }
      // System.out.println("Directory '" + dir.getPath() + "' was selected");
      // }
      // });

      System.out.println(StatusBarHelper.getStatusLineManager());
      StatusBarHelper.setMessage("hi");

      // IWorkbenchWindowConfigurer configurer = getWindowConfigurer();
      // configurer.setInitialSize(new Point(WIDTH, HEIGHT));
      // IWorkbenchWindowConfigurer configurer = getWindowConfigurer();
      // configurer.setInitialSize(new Point(WIDTH, HEIGHT));
      // configurer.setShowCoolBar(false);
      // configurer.setShowStatusLine(false);
    }
  }
}
