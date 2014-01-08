package de.bht.fpa.mail.s791537.maillist;

import java.util.HashSet;
import java.util.Set;

import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;

import de.bht.fpa.mail.s000000.common.filter.IFilter;
import de.bht.fpa.mail.s000000.common.filter.NullFilter;
import de.bht.fpa.mail.s000000.common.mail.model.Message;

/**
 * Allows to use any IFilter as ViewerFilter.
 */
public class ConfigurableViewerFilter extends ViewerFilter {
  private IFilter filter;

  public ConfigurableViewerFilter() {
    this.filter = new NullFilter();
  }

  public void setFilter(IFilter filter) {
    this.filter = filter;
  }

  @Override
  public boolean select(Viewer viewer, Object parentElement, Object element) {
    Set<Message> singleMessageSet = new HashSet<Message>();
    singleMessageSet.add((Message) element);
    return !filter.filter(singleMessageSet).isEmpty();
  }
}
