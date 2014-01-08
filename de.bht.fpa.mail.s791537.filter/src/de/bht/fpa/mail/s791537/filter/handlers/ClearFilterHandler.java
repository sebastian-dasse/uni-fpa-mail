package de.bht.fpa.mail.s791537.filter.handlers;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;

import de.bht.fpa.mail.s791537.filter.NullFilter;

public class ClearFilterHandler extends AbstractHandler {

  @Override
  public Object execute(ExecutionEvent event) throws ExecutionException {

    // TODO remove
    System.out.println("handle clearFilter command");

    return new NullFilter();
  }
}
