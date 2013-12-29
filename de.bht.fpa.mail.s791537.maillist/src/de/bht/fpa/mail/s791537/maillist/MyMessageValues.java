package de.bht.fpa.mail.s791537.maillist;

import java.util.Iterator;

import de.bht.fpa.mail.s000000.common.mail.model.Message;
import de.bht.fpa.mail.s000000.common.mail.model.Recipient;
import de.ralfebert.rcputils.properties.BaseValue;
import de.ralfebert.rcputils.properties.IValue;

public final class MyMessageValues {
  private MyMessageValues() {

  }

  public static final IValue SENDER_EMAIL = new BaseValue<Message>() {
    @Override
    public Object get(Message message) {
      return message.getSender().getEmail();
    }
  };

  public static final IValue RECIPIENT_EMAIL = new BaseValue<Message>() {
    @Override
    public Object get(Message message) {
      StringBuilder sb = new StringBuilder();
      Iterator<Recipient> iter = message.getRecipients().iterator();
      if (iter.hasNext()) {
        sb.append(iter.next().getEmail());
      }
      while (iter.hasNext()) {
        sb.append(", ").append(iter.next().getEmail());
      }
      return sb.toString();
    }
  };
}
