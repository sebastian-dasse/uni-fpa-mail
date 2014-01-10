package de.bht.fpa.mail.s791537.filter;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import de.bht.fpa.mail.s000000.common.filter.FilterOperator;
import de.bht.fpa.mail.s000000.common.filter.FilterType;
import de.bht.fpa.mail.s000000.common.filter.IFilter;
import de.bht.fpa.mail.s000000.common.mail.model.Importance;
import de.bht.fpa.mail.s000000.common.mail.model.Message;
import de.bht.fpa.mail.s000000.common.mail.testdata.RandomTestDataProvider;

public class FilterParser {
  private static final int NUMBER_OF_MESSAGES = 250;

  public IFilter parseFilter(String string) {
    if (string.length() == 0) {
      return new NullFilter();
    }

    int bracketL = string.indexOf('(');
    String filterGroupTypeString = string.substring(0, bracketL).toUpperCase();
    String contents = string.substring(bracketL + 1, string.length() - 1);

    switch (filterGroupTypeString) {
    case "INTERSECTION":
      return new IntersectionFilter(parseFilterArray(contents));
    case "UNION":
      return new UnionFilter(parseFilterArray(contents));
    default: // no combination filter
      return parseFilterType(string);
    }
  }

  private IFilter[] parseFilterArray(String string) {
    List<String> strings = new LinkedList<String>();
    List<IFilter> filters = new LinkedList<IFilter>();

    int first = 0;
    int commaAfterBracket = string.indexOf("), ") + 1;
    while (commaAfterBracket > 0) {
      strings.add(string.substring(first, commaAfterBracket));

      first = commaAfterBracket + 2;
      commaAfterBracket = string.indexOf("), ", commaAfterBracket) + 1;
    }
    strings.add(string.substring(first, string.lastIndexOf(')') + 1));

    for (String s : strings) {
      filters.add(parseFilter(s));
    }
    return filters.toArray(new IFilter[filters.size()]);
  }

  private IFilter parseFilterType(String string) {
    int bracketL = string.indexOf('(');
    String filterString = string.substring(0, bracketL).toUpperCase();
    String contents = string.substring(bracketL + 1, string.length() - 1);
    int comma = contents.indexOf(',');
    String operatorString = "";
    if (comma > 0) {
      operatorString = contents.substring(comma + 2, contents.length()).toUpperCase();
      contents = contents.substring(0, comma);
    }

    switch (FilterType.valueOf(filterString)) {
    case SENDER:
      return new SenderFilter(parseStringFilterValue(contents), parseFilterOperator(operatorString));
    case RECIPIENTS:
      return new RecipientsFilter(parseStringFilterValue(contents), FilterOperator.valueOf(operatorString));
    case SUBJECT:
      return new SubjectFilter(parseStringFilterValue(contents), FilterOperator.valueOf(operatorString));
    case TEXT:
      return new TextFilter(parseStringFilterValue(contents), FilterOperator.valueOf(operatorString));
    case IMPORTANCE:
      return new ImportanceFilter(Importance.valueOf(contents.toUpperCase()));
    case READ:
      return new ReadFilter(parseBooleanFilterValue(contents));
    default:
      throw new IllegalArgumentException("'" + string + "' is not a valid filter type");
    }
  }

  private FilterOperator parseFilterOperator(String string) {
    switch (string) {
    case "CONTAINS":
      return FilterOperator.CONTAINS;
    case "CONTAINSNOT":
      return FilterOperator.CONTAINS_NOT;
    case "STARTSWITH":
      return FilterOperator.STARTS_WITH;
    case "ENDSWITH":
      return FilterOperator.ENDS_WITH;
    case "IS":
      return FilterOperator.IS;
    default:
      throw new IllegalArgumentException("'" + string + "' ist not a valid FilterOperator");
    }
  }

  private String parseStringFilterValue(String string) {
    if (string.indexOf('"') != 0 || string.indexOf('"', 1) != string.length() - 1) {
      throw new IllegalArgumentException("String values must be enclosed in double quotes");
    }
    return string.substring(1, string.length() - 1);
  }

  private boolean parseBooleanFilterValue(String string) {
    if (!(string.equalsIgnoreCase("true") || string.equalsIgnoreCase("false"))) {
      throw new IllegalArgumentException("'" + string + "' is not a valid boolean value");
    }
    return Boolean.valueOf(string);
  }

  // ---- Test
  public static void main(String[] args) {
    FilterParser parser = new FilterParser();
    Iterable<Message> messagesToFilter = new RandomTestDataProvider(NUMBER_OF_MESSAGES).getMessages();

    String[] strings = {
        "Sender(\"heidi\", contains)",
        "Recipients(\"heidi\", contains)",
        "Subject(\"free\", contains)",
        "Text(\"tuck\", contains)",
        "Importance(low)",
        "Read(false)",
        "Intersection(Sender(\"heidi\", contains))",
        "Intersection(Sender(\"heidi\", contains), Recipients(\"heidi\", contains))",
        "Union(Sender(\"heidi\", contains))",
        "Union(Sender(\"heidi\", contains), Recipients(\"heidi\", contains))",
        "Union()",
        "Union(Text(\"tuck\", contains), Intersection(Subject(\"free\", contains)), "
            + "Sender(\"heidi\", contains), Read(true), Importance(low))" };

    IFilter[] filters = {
        new SenderFilter("heidi", FilterOperator.CONTAINS),
        new RecipientsFilter("heidi", FilterOperator.CONTAINS),
        new SubjectFilter("free", FilterOperator.CONTAINS),
        new TextFilter("tuck", FilterOperator.CONTAINS),
        new ImportanceFilter(Importance.LOW),
        new ReadFilter(false),
        new IntersectionFilter(new SenderFilter("heidi", FilterOperator.CONTAINS)),
        new IntersectionFilter(new SenderFilter("heidi", FilterOperator.CONTAINS), new RecipientsFilter("heidi",
            FilterOperator.CONTAINS)),
        new UnionFilter(new SenderFilter("heidi", FilterOperator.CONTAINS)),
        new UnionFilter(new SenderFilter("heidi", FilterOperator.CONTAINS), new RecipientsFilter("heidi",
            FilterOperator.CONTAINS)),
        new UnionFilter(),
        new UnionFilter(new TextFilter("tuck", FilterOperator.CONTAINS), new IntersectionFilter(new SubjectFilter(
            "free", FilterOperator.CONTAINS)), new SenderFilter("heidi", FilterOperator.CONTAINS),
            new ReadFilter(true), new ImportanceFilter(Importance.LOW)) };

    if (strings.length != filters.length) {
      throw new RuntimeException("the two list do not have the same length");
    }

    System.out.printf("%10s : %-10s%n", "parsed", "conventional");
    for (int i = 0; i < strings.length; i++) {
      IFilter filter1 = parser.parseFilter(strings[i]);
      IFilter filter2 = filters[i];
      Set<Message> filteredMessages1 = filter1.filter(messagesToFilter);
      Set<Message> filteredMessages2 = filter2.filter(messagesToFilter);

      System.out.printf("%10d : %3d%n", countSet(filteredMessages1), countSet(filteredMessages2));
    }

  }

  private static int countSet(Set<Message> messages) {
    int count = 0;
    for (Iterator<Message> it = messages.iterator(); it.hasNext(); it.next()) {
      count++;
    }
    return count;
  }
}
