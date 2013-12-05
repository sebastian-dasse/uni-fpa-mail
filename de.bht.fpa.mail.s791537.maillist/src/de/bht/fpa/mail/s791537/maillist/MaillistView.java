package de.bht.fpa.mail.s791537.maillist;

import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.part.ViewPart;

import de.bht.fpa.mail.s000000.common.mail.testdata.RandomTestDataProvider;
import de.ralfebert.rcputils.tables.ColumnBuilder;
import de.ralfebert.rcputils.tables.TableViewerBuilder;

public class MaillistView extends ViewPart {
  public static final String ID = "de.bht.fpa.s791537.maillist.MaillistView";
  private TableViewer viewer;

  @Override
  public void createPartControl(Composite parent) {

    TableViewerBuilder t = new TableViewerBuilder(parent);

    ColumnBuilder importance = t.createColumn("Importance");
    importance.bindToProperty("importance");
    importance.build();

    ColumnBuilder received = t.createColumn("Received");
    received.bindToProperty("received");
    received.useAsDefaultSortColumn();
    received.build();

    // TODO verursacht Fehler, vermutlich weil boolean
    // ColumnBuilder read = t.createColumn("Read");
    // read.bindToProperty("read");
    // read.build();

    ColumnBuilder sender = t.createColumn("Sender");
    sender.bindToProperty("sender");
    sender.build();

    ColumnBuilder recipients = t.createColumn("Recipients");
    recipients.bindToProperty("recipients");
    recipients.build();

    ColumnBuilder subject = t.createColumn("Subject");
    subject.bindToProperty("subject");
    subject.build();

    t.setInput(new RandomTestDataProvider(50).getMessages());
    viewer = t.getTableViewer();
    // viewer.setContentProvider(provider);
    // viewer.setLabelProvider(new ColumnLabelProvider());
    // viewer.setInput(new RandomTestDataProvider(50).getMessages());

    // viewer = new TableViewer(parent);
    // viewer.setContentProvider(new IStructuredContentProvider() {
    //
    // @Override
    // public void dispose() {
    // // TODO Auto-generated method stub
    //
    // }
    //
    // @Override
    // public void inputChanged(Viewer viewer, Object oldInput, Object newInput)
    // {
    // // TODO Auto-generated method stub
    //
    // }
    //
    // @Override
    // public Object[] getElements(Object inputElement) {
    // // TODO Auto-generated method stub
    // return null;
    // }
    //
    // });
    // viewer.setLabelProvider(new ColumnLabelProvider());
    // viewer.setInput("input");

    // viewer.setInput(new RandomTestDataProvider(50).getMessages());

    // ---- Dummy-Lösung:
    // viewer.setContentProvider(ArrayContentProvider.getInstance());
    // viewer.setLabelProvider(new ColumnLabelProvider());
    // viewer.setInput(new String[] { "eins", "zwei", "drei" });

    // List<Message> list = new RandomTestDataProvider(50).getMessages();
    // for (Message message : list) {
    // System.out.println(message.getId());
    // }

  }

  @Override
  public void setFocus() {
    viewer.getControl().setFocus();
  }
}
