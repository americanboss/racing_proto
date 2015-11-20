package jp.co.equinestudio.racing.util;

import android.content.Context;

import jp.co.equinestudio.racing.R;
import jp.co.equinestudio.racing.constants.RacingConstants;
import jp.co.equinestudio.racing.model.RaceMember;

import java.util.HashMap;

/**
 * 文字列に関する処理を行うUtilクラス
 */
public class StringUtils {

    public static final int MONTH_DAY_UTIL_MONTH = 0;
    public static final int MONTH_DAY_UTIL_DAY = 1;

    public static String getWeekdayText(final String code) {
        if (code == null) {
            // TODO ここはNPE出す
            return "謎";
        }
        return getWeekdayMap().get(code);

    }

    public static HashMap<String, String> getWeekdayMap() {
        HashMap<String, String> map = new HashMap<>();
        map.put("1", "(土)");
        map.put("2", "(日)");
        map.put("3", "(月)");
        map.put("4", "(火)");
        map.put("5", "(水)");
        map.put("6", "(木)");
        map.put("7", "(金)");
        return map;

    }


    /**
     * 月日の4桁テキストを2桁の月と日に分割する
     * @param monthDay 月日の4桁テキスト
     * @return 2桁の月と日の配列文字列
     */
    public static String[] convertMonthDayText(String monthDay) {
        String[] converted = new String[2];
        converted[MONTH_DAY_UTIL_MONTH] = monthDay.substring(0, 2);
        converted[MONTH_DAY_UTIL_DAY] = monthDay.substring(2, 4);
        return converted;
    }

    public static String getMonthDayText(final String monthDay){
        String[] convertedMonthDayText = StringUtils.convertMonthDayText(monthDay);
        StringBuilder builder = new StringBuilder();
        builder.append(convertedMonthDayText[MONTH_DAY_UTIL_MONTH]).append("/").append(convertedMonthDayText[MONTH_DAY_UTIL_DAY]);
        return builder.toString();
    }

    public static String getResultByRaceMember(final Context context, final RaceMember raceMember) {
        StringBuilder builder = new StringBuilder();
        if (RacingConstants.RaceMember.RACE_RESULT_ACCIDENT.equals(raceMember.getResult())) {
            // アクシデントコードの場合
            builder.append(context.getResources().getString(R.string.label_result_accident));
        } else {
            // アクシデントコードなし、着順
            builder.append(Integer.toString(Integer.parseInt(raceMember.getResult())))
                    .append(context.getResources().getString(R.string.label_result));
        }
        return builder.toString();
    }

    public static String getRaceCourseText(final String code) {
        if (code == null) {
            // TODO ここはNPE出す
            return "どこ";
        }
        return getRaceCourseMap().get(code);
    }

    public static HashMap<String, String> getRaceCourseMap() {
        HashMap<String, String> map = new HashMap<>();
        map.put("01", "札幌");
        map.put("02", "函館");
        map.put("03", "福島");
        map.put("04", "新潟");
        map.put("05", "東京");
        map.put("06", "中山");
        map.put("07", "中京");
        map.put("08", "京都");
        map.put("09", "阪神");
        map.put("10", "小倉");
        return map;

    }





}
