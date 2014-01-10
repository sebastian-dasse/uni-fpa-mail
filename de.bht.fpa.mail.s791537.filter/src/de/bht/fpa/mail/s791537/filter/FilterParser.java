package de.bht.fpa.mail.s791537.filter;

import java.util.LinkedList;
import java.util.List;

import de.bht.fpa.mail.s000000.common.filter.FilterOperator;
import de.bht.fpa.mail.s000000.common.filter.FilterType;
import de.bht.fpa.mail.s000000.common.filter.IFilter;
import de.bht.fpa.mail.s000000.common.mail.model.Importance;

public class FilterParser {
  // private static final int NUMBER_OF_MESSAGES = 25; // TODO wieder entfernen

  public IFilter parseFilter(String string) {

    int bracketL = string.indexOf('(');
    // first allowed position of '(': 0
    if (bracketL < 1 || string.lastIndexOf(')') != string.length() - 1) {
      throw new IllegalArgumentException("Ungueltige Eingabe");
    }

    String start = string.substring(0, bracketL).toUpperCase();
    String end = string.substring(bracketL + 1, string.length() - 1);

    // IFilter[] filters = new IFilter[0];

    switch (start) {
    case "INTERSECTION":
      // filterGroupType = FilterGroupType.INTERSECTION;
      return new IntersectionFilter(parseFilterArray(end));
    case "UNION":
      // filterGroupType = FilterGroupType.UNION;
      return new UnionFilter(parseFilterArray(end));
    default:
      // keine Kombination
      return parseFilterType(string);
    }
  }

  private IFilter[] parseFilterArray(String string) {
    List<IFilter> filters = new LinkedList<IFilter>();

    int bracketL = string.indexOf('(');
    // first allowed position of '(': 0
    if (bracketL < 1 || string.lastIndexOf(')') != string.length() - 1) {
      throw new IllegalArgumentException("Ungueltige Eingabe");
    }

    String start = string.substring(0, bracketL).toUpperCase();
    String end = string.substring(bracketL + 1, string.length() - 1);

    return filters.toArray(new IFilter[filters.size()]);
  }

  private IFilter parseFilterType(String string) {
    IFilter filter = null;

    int bracketL = string.indexOf('(');
    if (bracketL < 1 || string.lastIndexOf(')') != string.length() - 1) {
      throw new IllegalArgumentException("Ungueltige Eingabe");
    }

    String start = string.substring(0, bracketL).toUpperCase();
    String end = string.substring(bracketL + 1, string.length() - 1);

    FilterOperator filterOperator = null;
    Object filterValue = null;

    switch (FilterType.valueOf(start)) {
    case SENDER:
      return new SenderFilter(parseStringFilterValue(end), parseFilterOperator(end));
    case RECIPIENTS:
      return new RecipientsFilter(parseStringFilterValue(string), filterOperator);
    case SUBJECT:
      return new SubjectFilter(parseStringFilterValue(string), filterOperator);
    case TEXT:
      return new TextFilter(parseStringFilterValue(string), filterOperator);
    case IMPORTANCE:
      return new ImportanceFilter((Importance) filterValue);
    case READ:
      return new ReadFilter(parseBooleanFilterValue(end));
    default:
      throw new IllegalArgumentException("Ungueltige Eingabe");
    }
  }

  private String parseStringFilterValue(String string) {
    if (string.indexOf('"') != 0 || string.indexOf('"', 1) != string.length() - 1) {
      System.out.println(string);
      throw new IllegalArgumentException("Ungueltige Eingabe");
    }
    return string.substring(1, string.length() - 1);
  }

  private boolean parseBooleanFilterValue(String string) {
    return Boolean.valueOf(string);
    // switch (string) {
    // case "true":
    // return true;
    // case "false":
    // return false;
    // default:
    // System.out.println(string);
    // throw new IllegalArgumentException("Ungueltige Eingabe");
    // }
  }

  private FilterOperator parseFilterOperator(String string) {
    switch (FilterOperator.valueOf(string)) {
    case CONTAINS:
      break;
    case CONTAINS_NOT:
      break;
    case STARTS_WITH:
      break;
    case ENDS_WITH:
      break;
    case IS:
      break;
    default:
      break;
    }
    return null;
  }

  public static void main(String[] args) {
    FilterParser parser = new FilterParser();

    // Iterable<Message> messagesToFilter = new
    // RandomTestDataProvider(NUMBER_OF_MESSAGES).getMessages();

    String string1 = "Read(false)";
    String string2 = "Union(Sender(\"karl\", contains), Recipient(\"heidi stulle\", is))";
    String string3 = "Intersection(Sender(\"karl\", startsWith), Read(true))";

    System.out.println(parser.parseFilter(string2));

    // IFilter filter = parser.parseFilter(string1);
    // Set<Message> filteredMessages = filter.filter(messagesToFilter);
    // Main.printAll(filteredMessages);
  }

}
