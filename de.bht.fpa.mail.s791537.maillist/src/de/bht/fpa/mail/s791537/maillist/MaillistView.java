package de.bht.fpa.mail.s791537.maillist;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Observable;
import java.util.Observer;

import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.ISelectionListener;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.commands.ICommandService;
import org.eclipse.ui.part.ViewPart;

import de.bht.fpa.mail.s000000.common.mail.model.Message;
import de.bht.fpa.mail.s000000.common.rcp.selection.SelectionHelper;
import de.bht.fpa.mail.s000000.common.table.MessageValues;
import de.bht.fpa.mail.s791537.common.IMailProvider;
import de.ralfebert.rcputils.tables.TableViewerBuilder;
import de.ralfebert.rcputils.tables.format.Formatter;

public class MaillistView extends ViewPart implements ISelectionListener, Observer {
  public MaillistView() {
  }

  private static final int IMPORTANCE_PIXEL_WIDTH = 35;
  private static final int RECEIVED_PIXEL_WIDTH = 85;
  private static final int READ_PIXEL_WIDTH = 50;
  private static final int SENDER_PERCENT_WIDTH = 25;
  private static final int RECIPIENTS_PERCENT_WIDTH = 25;
  private static final int SUBJECT_PERCENT_WIDTH = 50;
  // private static final int NUMBER_OF_MESSAGES = 50;
  private static final Image LOW_ICON = Activator.imageDescriptorFromPlugin(Activator.PLUGIN_ID,
      "icons/low_importance-26.png").createImage();
  private static final Image NORMAL_ICON = Activator.imageDescriptorFromPlugin(Activator.PLUGIN_ID, "icons/ok-26.png")
      .createImage();
  private static final Image HIGH_ICON = Activator.imageDescriptorFromPlugin(Activator.PLUGIN_ID,
      "icons/high_importance-26.png").createImage();
  static final DateFormat DATE_FORMAT = SimpleDateFormat.getDateInstance(SimpleDateFormat.MEDIUM);
  private TableViewer viewer;
  private Text searchText;
  private String searchString = "";

  @Override
  public void createPartControl(Composite parent) {

    parent.setLayout(new GridLayout(2, false));

    Label searchLabel = new Label(parent, SWT.NONE);
    searchLabel.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
    searchLabel.setText("Search:");

    searchText = new Text(parent, SWT.BORDER);
    searchText.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

    Composite tableArea = new Composite(parent, SWT.NONE);
    GridData gdTableArea = new GridData(SWT.FILL, SWT.FILL, true, true, 2, 1);
    // gdTableArea.heightHint = 31;
    tableArea.setLayoutData(gdTableArea);

    TableViewerBuilder t = new TableViewerBuilder(tableArea);

    t.createColumn("Importance").bindToValue(MessageValues.IMPORTANCE).setPixelWidth(IMPORTANCE_PIXEL_WIDTH).build()
        .setLabelProvider(new ColumnLabelProvider() {

          @Override
          public String getText(Object element) {
            return null;
          }

          @Override
          public Image getImage(Object element) {
            switch (((Message) element).getImportance()) {
            case LOW:
              return LOW_ICON;
            case NORMAL:
              return NORMAL_ICON;
            case HIGH:
              return HIGH_ICON;
            default:
              return null;
            }
          }
        });
    t.createColumn("Received").bindToValue(MessageValues.RECEIVED).format(Formatter.forDate(DATE_FORMAT))
        .useAsDefaultSortColumn().setPixelWidth(RECEIVED_PIXEL_WIDTH).build();
    t.createColumn("Read").bindToValue(MessageValues.READ).setPixelWidth(READ_PIXEL_WIDTH).build();
    t.createColumn("Sender").bindToValue(MyMessageValues.SENDER_EMAIL).setPercentWidth(SENDER_PERCENT_WIDTH).build();
    t.createColumn("Recipients").bindToValue(MyMessageValues.RECIPIENT_EMAIL).setPercentWidth(RECIPIENTS_PERCENT_WIDTH)
        .build();
    t.createColumn("Subject").bindToValue(MessageValues.SUBJECT).setPercentWidth(SUBJECT_PERCENT_WIDTH).build();

    t.setInput(null);
    viewer = t.getTableViewer();

    // most recent messages at the top
    viewer.getTable().setSortDirection(SWT.DOWN);

    // searchText.addKeyListener(new KeyAdapter() {
    //
    // @Override
    // public void keyPressed(KeyEvent e) {
    // searchString = searchText.getText().toLowerCase();
    // viewer.refresh();
    // }
    // });

    searchText.addModifyListener(new ModifyListener() {

      @Override
      public void modifyText(ModifyEvent e) {
        searchString = searchText.getText().toLowerCase();
        viewer.refresh();
      }
    });

    // fast filtering as you type
    viewer.addFilter(new FastViewerFilter(searchText));

    getSite().getPage().addSelectionListener(this);

    /*
     * TODO funktioniert nicht, wenn man die Dependency zu fsnavigation
     * entfernt; dazu evtl. RootModel ins package ...common
     */
    // RootModel.getInstance().addObserver(this);

    getSite().setSelectionProvider(viewer);

    final IWorkbench workbench = PlatformUI.getWorkbench();
    ICommandService commandService = (ICommandService) workbench.getService(ICommandService.class);

    // configurable filter
    viewer.addFilter(new ConfigurableViewerFilter());

    // commandService.addExecutionListener(new ExecutionListener(viewer));
    commandService.addExecutionListener(new ExecutionListener(viewer));
  }

  @Override
  public void dispose() {
    getSite().getPage().removeSelectionListener(this);
    super.dispose();
  }

  @Override
  public void setFocus() {
    viewer.getControl().setFocus();
  }

  /**
   * Updates the list of messages shown in this view as selected in the
   * <code>NavigationView</code>.
   */
  @Override
  public void selectionChanged(IWorkbenchPart part, ISelection selection) {
    // TreeDirectory dir = SelectionHelper.handleStructuredSelection(selection,
    // TreeDirectory.class);
    IMailProvider dir = SelectionHelper.handleStructuredSelection(selection, IMailProvider.class);
    if (dir == null) {
      return;
    }
    viewer.setInput(dir.getMessages());
  }

  // TODO not used, if this is not added as observer to the RootModel
  /**
   * Clears the list of messages shown in this view on change of the observed
   * <code>RootModel</code>.
   */
  @Override
  public void update(Observable o, Object arg) {
    // viewer.setInput(((TreeDirectory) arg).getMessages());
    viewer.setInput(null);
  }
}
