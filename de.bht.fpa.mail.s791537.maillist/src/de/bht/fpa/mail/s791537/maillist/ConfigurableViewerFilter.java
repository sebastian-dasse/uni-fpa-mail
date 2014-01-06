package de.bht.fpa.mail.s791537.maillist;

import java.util.HashSet;
import java.util.Set;

import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;

import de.bht.fpa.mail.s000000.common.filter.IFilter;
import de.bht.fpa.mail.s000000.common.filter.NullFilter;
import de.bht.fpa.mail.s000000.common.mail.model.Message;

public class ConfigurableViewerFilter extends ViewerFilter {
  private IFilter filter;

  public ConfigurableViewerFilter() {

    // TODO Null-Filter implementieren und hier einsetzen, oder so ok?
    this.filter = new NullFilter();
    // this.filter = new IFilter() {
    //
    // @Override
    // public Set<Message> filter(Iterable<Message> messagesToFilter) {
    // return new HashSet<Message>();
    // }
    // };
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
