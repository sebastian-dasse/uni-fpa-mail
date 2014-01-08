package de.bht.fpa.mail.s791537.filter.handlers;

import java.util.LinkedList;
import java.util.List;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.handlers.HandlerUtil;

import de.bht.fpa.mail.s000000.common.filter.FilterCombination;
import de.bht.fpa.mail.s000000.common.filter.FilterDialog;
import de.bht.fpa.mail.s000000.common.filter.FilterOperator;
import de.bht.fpa.mail.s000000.common.filter.FilterType;
import de.bht.fpa.mail.s000000.common.filter.IFilter;
import de.bht.fpa.mail.s000000.common.mail.model.Importance;
import de.bht.fpa.mail.s791537.filter.ImportanceFilter;
import de.bht.fpa.mail.s791537.filter.IntersectionFilter;
import de.bht.fpa.mail.s791537.filter.ReadFilter;
import de.bht.fpa.mail.s791537.filter.RecipientsFilter;
import de.bht.fpa.mail.s791537.filter.SenderFilter;
import de.bht.fpa.mail.s791537.filter.SubjectFilter;
import de.bht.fpa.mail.s791537.filter.TextFilter;
import de.bht.fpa.mail.s791537.filter.UnionFilter;

public class ConfigureFilterHandler extends AbstractHandler {

  @Override
  public Object execute(ExecutionEvent event) throws ExecutionException {

    // TODO remove
    System.out.println("handle configureFilter command");

    IWorkbenchWindow window = HandlerUtil.getActiveWorkbenchWindowChecked(event);
    FilterDialog dialog = new FilterDialog(window.getShell());
    dialog.open();

    List<FilterCombination> filterCombinations = dialog.getFilterCombinations();
    if (filterCombinations == null) {
      return null;
    }

    List<IFilter> filters = new LinkedList<IFilter>();
    for (FilterCombination filterCombination : filterCombinations) {
      FilterType filterType = filterCombination.getFilterType();
      Object filterValue = filterCombination.getFilterValue();
      FilterOperator filterOperator = filterCombination.getFilterOperator();

      switch (filterType) {
      case SENDER:
        filters.add(new SenderFilter((String) filterValue, filterOperator));
        break;
      case RECIPIENTS:
        filters.add(new RecipientsFilter((String) filterValue, filterOperator));
        break;
      case SUBJECT:
        filters.add(new SubjectFilter((String) filterValue, filterOperator));
        break;
      case TEXT:
        filters.add(new TextFilter((String) filterValue, filterOperator));
        break;
      case IMPORTANCE:
        filters.add(new ImportanceFilter((Importance) filterValue));
        break;
      case READ:
        filters.add(new ReadFilter((boolean) filterValue));
        break;
      default:
        break;
      }
    }

    switch (dialog.getFilterGroupType()) {
    case INTERSECTION:

      // TODO remove
      System.out.println("> set IntersectionFilter");
      return new IntersectionFilter(filters.toArray(new IFilter[filters.size()]));
    case UNION:

      // TODO remove
      System.out.println("> set UnionFilter");
      return new UnionFilter(filters.toArray(new IFilter[filters.size()]));
    default:
      return null;
    }
  }
}
