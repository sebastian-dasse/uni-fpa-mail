package de.bht.fpa.mail.s791537.maillist;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Iterator;

import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.part.ViewPart;

import de.bht.fpa.mail.s000000.common.mail.model.Message;
import de.bht.fpa.mail.s000000.common.mail.model.Recipient;
import de.bht.fpa.mail.s000000.common.mail.testdata.RandomTestDataProvider;
import de.bht.fpa.mail.s000000.common.table.MessageValues;
import de.ralfebert.rcputils.properties.BaseValue;
import de.ralfebert.rcputils.tables.TableViewerBuilder;
import de.ralfebert.rcputils.tables.format.Formatter;

public class MaillistView extends ViewPart {
  private static final int IMPORTANCE_PIXEL_WIDTH = 35;
  private static final int RECEIVED_PIXEL_WIDTH = 85;
  private static final int READ_PIXEL_WIDTH = 50;
  private static final int SENDER_PERCENT_WIDTH = 25;
  private static final int RECIPIENTS_PERCENT_WIDTH = 25;
  private static final int SUBJECT_PERCENT_WIDTH = 50;
  private static final int NUMBER_OF_MESSAGES = 50;
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

    t.createColumn("Sender").bindToValue(new BaseValue<Message>() {
      @Override
      public Object get(Message message) {
        return message.getSender().getEmail();
      }
    }).setPercentWidth(SENDER_PERCENT_WIDTH).build();

    t.createColumn("Recipients").bindToValue(new BaseValue<Message>() {
      @Override
      public Object get(Message message) {
        Iterator<Recipient> iter = message.getRecipients().iterator();
        StringBuilder sb = new StringBuilder();
        if (iter.hasNext()) {
          sb.append(iter.next().getEmail());
        }
        while (iter.hasNext()) {
          sb.append(", ").append(iter.next().getEmail());
        }
        return sb.toString();
      }
    }).setPercentWidth(RECIPIENTS_PERCENT_WIDTH).build();

    t.createColumn("Subject").bindToValue(MessageValues.SUBJECT).setPercentWidth(SUBJECT_PERCENT_WIDTH).build();

    t.setInput(new RandomTestDataProvider(NUMBER_OF_MESSAGES).getMessages());
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
  }

  @Override
  public void setFocus() {
    viewer.getControl().setFocus();
  }
}
