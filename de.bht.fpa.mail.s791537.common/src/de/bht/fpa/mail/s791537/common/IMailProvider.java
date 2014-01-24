package de.bht.fpa.mail.s791537.common;

import java.util.Collection;

import de.bht.fpa.mail.s000000.common.mail.model.Message;

/**
 * An interface which ensures that implementing classes are able to provide a
 * collection of <code>Message</code>s and know their path.
 */
public interface IMailProvider {

  Collection<Message> getMessages();

  String getPath();
}
