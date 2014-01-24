package de.bht.fpa.mail.s791537.common;

import java.util.Collection;

import de.bht.fpa.mail.s000000.common.mail.model.Message;

/**
 * An interface which ensures that implementing classes are able to provide a
 * collection of <code>Message</code>s and their name.
 */
public interface IMailProvider {

  /**
   * @return A <code>Collection&lt;Message&gt;</code>.
   */
  Collection<Message> getMessages();

  /**
   * @return The name of this <code>IMailProvider</code>.
   */
  String getName();
}
