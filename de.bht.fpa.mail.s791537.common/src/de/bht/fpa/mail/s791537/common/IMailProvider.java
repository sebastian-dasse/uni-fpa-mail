package de.bht.fpa.mail.s791537.common;

import de.bht.fpa.mail.s000000.common.mail.model.Message;

public interface IMailProvider {

  public Iterable<Message> getMessages();
}
