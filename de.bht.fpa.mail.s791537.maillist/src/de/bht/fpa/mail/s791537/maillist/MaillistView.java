package de.bht.fpa.mail.s791537.maillist;

import java.text.SimpleDateFormat;
import java.util.Iterator;

import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.ViewerCell;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.part.ViewPart;

import de.bht.fpa.mail.s000000.common.mail.model.Importance;
import de.bht.fpa.mail.s000000.common.mail.model.Message;
import de.bht.fpa.mail.s000000.common.mail.model.Recipient;
import de.bht.fpa.mail.s000000.common.mail.testdata.RandomTestDataProvider;
import de.bht.fpa.mail.s000000.common.table.MessageValues;
import de.ralfebert.rcputils.properties.BaseValue;
import de.ralfebert.rcputils.tables.ColumnBuilder;
import de.ralfebert.rcputils.tables.ICellFormatter;
import de.ralfebert.rcputils.tables.TableViewerBuilder;
import de.ralfebert.rcputils.tables.format.Formatter;

public class MaillistView extends ViewPart {

  private static final int NUMBER_OF_MESSAGES = 50;
  private static final Image LOW_ICON = Activator.imageDescriptorFromPlugin(Activator.PLUGIN_ID,
      "icons/low_importance-26.png").createImage();
  private static final Image NORMAL_ICON = Activator.imageDescriptorFromPlugin(Activator.PLUGIN_ID, "icons/ok-26.png")
      .createImage();
  private static final Image HIGH_ICON = Activator.imageDescriptorFromPlugin(Activator.PLUGIN_ID,
      "icons/high_importance-26.png").createImage();

  private TableViewer viewer;

  @Override
  public void createPartControl(final Composite parent) {

    TableViewerBuilder t = new TableViewerBuilder(parent);

    ColumnBuilder importance = t.createColumn("Importance");
    importance.bindToProperty("importance");
    importance.format(new ICellFormatter() {
      @Override
      public void formatCell(ViewerCell cell, Object value) {
        // TODO Auto-generated method stub

        switch ((Importance) value) {
        case LOW:
          cell.setImage(LOW_ICON);
          break;
        case NORMAL:
          cell.setImage(NORMAL_ICON);
          break;
        case HIGH:
          cell.setImage(HIGH_ICON);
          break;
        default:
          // TODO sollte hier irgendetwas passieren?
          break;
        }
        cell.setText("");
      }
    });
    importance.build();

    ColumnBuilder received = t.createColumn("Received");
    received.bindToProperty("received");
    // received.bindToValue(MessageValues.RECEIVED);
    received.format(Formatter.forDate(SimpleDateFormat.getDateInstance(SimpleDateFormat.MEDIUM)));
    // alternativ: mit Uhrzeit
    // received.format(Formatter.forDate(SimpleDateFormat.getDateTimeInstance(2,
    // 3)));
    received.useAsDefaultSortColumn();
    received.build();

    ColumnBuilder read = t.createColumn("Read");
    read.bindToValue(MessageValues.READ);
    read.build();

    ColumnBuilder sender = t.createColumn("Sender");
    // sender.bindToProperty("sender");
    sender.bindToValue(new BaseValue<Message>() {
      @Override
      public Object get(Message message) {
        return message.getSender().getEmail();
      }
    });

    // sender.format(valueFormatter)
    sender.build();

    ColumnBuilder recipients = t.createColumn("Recipients");
    // recipients.bindToProperty("recipients");
    recipients.bindToValue(new BaseValue<Message>() {
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
    });
    recipients.build();

    ColumnBuilder subject = t.createColumn("Subject");
    subject.bindToProperty("subject");
    // subject.bindToValue(MessageValues.SUBJECT);
    subject.build();

    t.setInput(new RandomTestDataProvider(NUMBER_OF_MESSAGES).getMessages());
    viewer = t.getTableViewer();
  }

  @Override
  public void setFocus() {
    viewer.getControl().setFocus();
  }
}
