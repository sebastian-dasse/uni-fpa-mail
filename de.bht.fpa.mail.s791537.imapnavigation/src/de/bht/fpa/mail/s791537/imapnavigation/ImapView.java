package de.bht.fpa.mail.s791537.imapnavigation;

import static de.bht.fpa.mail.s000000.common.mail.model.builder.Builders.newAccountBuilder;
import static de.bht.fpa.mail.s000000.common.mail.model.builder.Builders.newFolderBuilder;

import java.util.Collection;

import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.part.ViewPart;

import de.bht.fpa.mail.s000000.common.mail.model.Account;
import de.bht.fpa.mail.s000000.common.mail.model.Message;
import de.bht.fpa.mail.s000000.common.mail.testdata.RandomTestDataProvider;
import de.bht.fpa.mail.s000000.common.rcp.selection.SelectionHelper;
import de.bht.fpa.mail.s791537.common.IMailProvider;

public class ImapView extends ViewPart {
  private TreeViewer viewer;

  @Override
  public void createPartControl(Composite parent) {
    viewer = new TreeViewer(parent, SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL | SWT.BORDER);
    viewer.setContentProvider(new ImapTreeContentProvider());
    viewer.setLabelProvider(new ImapViewLabelProvider());
    // viewer.setLabelProvider(new LabelProvider());
    viewer.setInput(createModel());

    viewer.addSelectionChangedListener(new ISelectionChangedListener() {
      @Override
      public void selectionChanged(SelectionChangedEvent event) {

        // -------------------------- test class of selection:
        // IStructuredSelection structuredSelection = (IStructuredSelection)
        // event.getSelection();
        // Object firstElement = structuredSelection.getFirstElement();
        // System.out.println(firstElement.getClass());
        // --------------------------

        IMailProvider dir = SelectionHelper.handleStructuredSelectionEvent(event, IMailProvider.class);
        if (dir == null) {
          return;
        }
        Collection<Message> messages = dir.getMessages();
        // System.out.println("Selected directory: " + dir.getFullName());
        System.out.println("Selected directory: " + dir.getPath());
        System.out.println("Number of messages: " + messages.size());
        for (Message message : messages) {
          System.out.println(message.toShortString());
        }
      }
    });

    getSite().setSelectionProvider(viewer);
  }

  private Object createModel() {
    // TODO

    Object model = new Object();
    Account account = getDummyAccount();
    Accounts accounts = new Accounts(account);
    model = account;
    System.out.println("---");
    System.out.println("<<<" + account.getFolders().iterator().next().getFullName());
    System.out.println("---");

    return model;
  }

  private Account getDummyAccount() {
    //@formatter:off
    return newAccountBuilder()
      .name("Beuth-IMAP")
      .host("imap.beuth-hochschule.de")
      .username("seb")
      .password("sebsecrete")
      .folder(
          newFolderBuilder()
            .fullName("INBOX")
            .builtMessages(new RandomTestDataProvider(20).getMessages())
            .folder(
                newFolderBuilder()
                  .fullName("Customers")
                  .builtMessages(new RandomTestDataProvider(10).getMessages())
            )
            .folder(
                newFolderBuilder()
                  .fullName("Friends")
                  .builtMessages(new RandomTestDataProvider(3).getMessages())
            )
            .folder(
                newFolderBuilder()
                  .fullName("Family")
                  .builtMessages(new RandomTestDataProvider(5).getMessages())
            )
      ).folder(
          newFolderBuilder()
            .fullName("Sent")
            .builtMessages(new RandomTestDataProvider(15).getMessages())
      ).build();
    //@formatter:on
  }

  @Override
  public void setFocus() {
    viewer.getControl().setFocus();
  }
}
