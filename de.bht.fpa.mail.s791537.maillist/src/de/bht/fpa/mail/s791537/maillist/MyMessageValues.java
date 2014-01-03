package de.bht.fpa.mail.s791537.maillist;

import de.bht.fpa.mail.s000000.common.mail.model.Message;
import de.bht.fpa.mail.s000000.common.mail.model.Recipient;
import de.bht.fpa.mail.s000000.common.mail.model.Sender;
import de.ralfebert.rcputils.properties.BaseValue;
import de.ralfebert.rcputils.properties.IValue;

public final class MyMessageValues {
  private MyMessageValues() {

  }

  public static final IValue SENDER_EMAIL = new BaseValue<Message>() {
    @Override
    public Object get(Message message) {
      Sender sender = message.getSender();
      return sender.getPersonal() + " <" + sender.getEmail() + ">";
    }
  };

  public static final IValue RECIPIENT_EMAIL = new BaseValue<Message>() {
    @Override
    public Object get(Message message) {
      StringBuilder sb = new StringBuilder();
      for (Recipient recipient : message.getRecipients()) {
        sb.append(recipient.getPersonal()).append(" <").append(recipient.getEmail()).append(">, ");
      }
      return sb.substring(0, sb.length() - 2);
    }
  };
}
