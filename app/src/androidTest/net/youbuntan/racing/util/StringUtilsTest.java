package net.youbuntan.racing.util;

import junit.framework.TestCase;

import net.youbuntan.racing.util.StringUtils;

import java.lang.Exception;
import java.lang.String;

/**
 * 文字列に関する処理を行うUtilクラス
 */
public class StringUtilsTest extends TestCase{

    /**
     * 4桁で提供される月日のデータを2桁の月と日に分割するテスト
     * TODO: 実行してない
     * @throws Exception
     */
    public void test_convertMonthDayText() throws Exception {
        String args1 = "1020";
        String[] converted1 = StringUtils.convertMonthDayText(args1);
        assertEquals("10", converted1[StringUtils.MONTH_DAY_UTIL_MONTH]);
        assertEquals("20", converted1[StringUtils.MONTH_DAY_UTIL_DAY);

        String args2 = "0921";
        String[] converted2 = StringUtils.convertMonthDayText(args2);
        assertEquals("09", converted2[StringUtils.MONTH_DAY_UTIL_MONTH]);
        assertEquals("21", converted2[StringUtils.MONTH_DAY_UTIL_DAY);

    }


}
