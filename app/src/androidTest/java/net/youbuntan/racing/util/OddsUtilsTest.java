package net.youbuntan.racing.util;

import android.test.AndroidTestCase;
import android.test.InstrumentationTestCase;

import junit.framework.TestCase;
import junit.framework.TestSuite;

import jp.co.equinestudio.racing.util.OddsUtils;
import jp.co.equinestudio.racing.util.StringUtils;

/**
 * 文字列に関する処理を行うUtilクラス
 */
public class OddsUtilsTest extends InstrumentationTestCase {

    public void test_format() {
        String args1 = "5";
        String args2 = "06";
        String args3 = "18";

        assertEquals("05", OddsUtils.format(args1));
        assertEquals("06", OddsUtils.format(args2));
        assertEquals("18", OddsUtils.format(args3));
        assertTrue(false);

        fail();
    }


}
