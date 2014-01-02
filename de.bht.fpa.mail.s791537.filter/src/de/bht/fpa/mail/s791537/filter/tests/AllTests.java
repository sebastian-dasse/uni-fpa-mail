package de.bht.fpa.mail.s791537.filter.tests;

import junit.framework.Test;
import junit.framework.TestSuite;

// @RunWith(Suite.class)
// @SuiteClasses({ FilterTest.class, SubjectTest.class, ReadTest.class, IntersectionTest.class, UnionTest.class })
public final class AllTests {

  private AllTests() {
  }

  public static Test suite() {
    final TestSuite suite = new TestSuite();
    suite.addTestSuite(SubjectTest.class);
    suite.addTestSuite(ReadTest.class);
    suite.addTestSuite(IntersectionTest.class);
    suite.addTestSuite(UnionTest.class);
    return suite;
  }
}
