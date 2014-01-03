package de.bht.fpa.mail.s791537.maillist;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Observable;
import java.util.Observer;

import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.ISelectionListener;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.part.ViewPart;

import de.bht.fpa.mail.s000000.common.mail.model.Message;
import de.bht.fpa.mail.s000000.common.rcp.selection.SelectionHelper;
import de.bht.fpa.mail.s000000.common.table.MessageValues;
import de.bht.fpa.mail.s791537.common.IMailProvider;
import de.ralfebert.rcputils.tables.TableViewerBuilder;
import de.ralfebert.rcputils.tables.format.Formatter;

public class MaillistView extends ViewPart implements ISelectionListener, Observer {
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
  private static final DateFormat DATE_FORMAT = SimpleDateFormat.getDateInstance(SimpleDateFormat.MEDIUM);
  private TableViewer viewer;

  @Override
  public void createPartControl(Composite parent) {

    TableViewerBuilder t = new TableViewerBuilder(parent);

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

    // t.setInput(new RandomTestDataProvider(NUMBER_OF_MESSAGES).getMessages());
    // t.setInput(RootModel.getInstance().getRoot().getMessages());
    t.setInput(null);
    viewer = t.getTableViewer();

    // most recent messages at the top
    viewer.getTable().setSortDirection(SWT.DOWN);

    // TODO 5) Zusatzfunktion Suchfeld
    // viewer.addFilter(new ViewerFilter() {
    // @Override
    // public boolean select(Viewer viewer, Object parentElement, Object
    // element) {
    //
    // String searchString = "Free"; // TODO durch echten Suchstring ersetzen,
    // // evtl. ingorecase
    //
    // // TODO instanceof-check
    //
    // Message message = (Message) element;
    // List<String> strings = new LinkedList<String>();
    // strings.add(message.getSubject());
    // strings.add(message.getText());
    // strings.add(DATE_FORMAT.format(message.getReceived()));
    // strings.add(DATE_FORMAT.format(message.getSent()));
    // for (Recipient recipient : message.getRecipients()) {
    // strings.add(recipient.getEmail());
    // strings.add(recipient.getPersonal());
    // }
    // strings.add(message.getSender().getEmail());
    // strings.add(message.getSender().getPersonal());
    //
    // for (String string : strings) {
    // if (string.contains(searchString)) {
    // return true;
    // }
    // }
    // return false;
    // }
    // });

    getSite().getPage().addSelectionListener(this);

    /*
     * TODO funktioniert nicht, wenn man die Dependency zu fsnavigation
     * entfernt; dazu evtl. RootModel ins package ...common
     */
    // RootModel.getInstance().addObserver(this);

    getSite().setSelectionProvider(viewer);
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
