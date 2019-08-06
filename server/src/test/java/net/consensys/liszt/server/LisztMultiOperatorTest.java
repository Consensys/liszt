package net.consensys.liszt.server;

import org.junit.Before;

public class LisztMultiOperatorTest {

  private LisztManager lisztManager1;
  private LisztManager lisztManager2;

  @Before
  public void setUp() {
    short rid1 = 0;
    lisztManager1 = new LisztManagerImp(rid1);
    short rid2 = 1;
    lisztManager2 = new LisztManagerImp(rid2);
  }
}
