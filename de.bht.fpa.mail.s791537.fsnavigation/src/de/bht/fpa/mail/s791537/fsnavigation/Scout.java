package de.bht.fpa.mail.s791537.fsnavigation;

import java.util.Observable;

/**
 * TODO DOK IT ALL!
 */
public final class Scout extends Observable {
  private static final Scout INSTANCE = new Scout();

  private Scout() {
  }

  public static Scout getInstance() {
    return INSTANCE;
  }

  public void sendMessage(Object arg) {
    if (arg != null) {
      setChanged();
      notifyObservers(arg);
    }
  }
}
