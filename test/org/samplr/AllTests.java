package org.samplr;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({org.samplr.server.inject.AllTests.class, org.samplr.server.model.AllTests.class, org.samplr.server.utility.AllTests.class})
public class AllTests {

}
