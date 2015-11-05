package net.youbuntan.racing.util;

import android.content.Context;

import net.youbuntan.racing.R;
import net.youbuntan.racing.constants.RacingConstants;
import net.youbuntan.racing.model.RaceMember;

/**
 * 文字列に関する処理を行うUtilクラス
 */
public class StringUtils {

    public static final int MONTH_DAY_UTIL_MONTH = 0;
    public static final int MONTH_DAY_UTIL_DAY = 1;


    /**
     * 月日の4桁テキストを2桁の月と日に分割する
     * @param monthDay 月日の4桁テキスト
     * @return 2桁の月と日の配列文字列
     */
    public static String[] convertMonthDayText(String monthDay) {
        String[] converted = new String[2];
        converted[MONTH_DAY_UTIL_MONTH] = monthDay.substring(0, 2);
        converted[MONTH_DAY_UTIL_DAY] = monthDay.substring(2, 2);
        return converted;
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




}
