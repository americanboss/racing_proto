package net.youbuntan.racing.util;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * Created by masakikase on 2016/12/07.
 */
public class RacingTestSuite extends TestSuite {
    public static Test suite() {
        TestSuite suite = new TestSuite();
        suite.addTest(new OddsUtilsTest());

        return suite;
    }

}
