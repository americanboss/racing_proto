package jp.co.equinestudio.racing.util;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import jp.co.equinestudio.racing.fragment.race.RaceOddsFragment;

/**
 * Created by masakikase on 2016/12/06.
 */
public class OddsUtils {

    public class Table {
        public static final int WIN = 0;
        public static final int BRACKET = 1;
        public static final int QUINELLA = 2;
        public static final int WIDE = 3;
        public static final int EXACTA = 4;
        public static final int TRIO = 5;
        public static final int TRIFECTA = 6;
        public static final int ODDS_MULTIPLE = 7;

        public static final int ORDER_BY_ODDS = 0;
        public static final int ORDER_BY_BRACKET = 1;
        public static final int ORDER_BY_GATE = 2;
        public static final int PATTERN_NAGASHI = 3;
        public static final int PATTERN_BOX = 4;
        public static final int PATTERN_FORMATION = 5;
        public static final int PATTERN_NAGASHI_J1 = 6;
        public static final int PATTERN_NAGASHI_J2 = 7;


    }

    public static boolean isMultipleSelect(final int orderBy) {
        return orderBy == Table.PATTERN_FORMATION || orderBy == Table.PATTERN_BOX || orderBy == Table.PATTERN_NAGASHI || orderBy == Table.PATTERN_NAGASHI_J1 || orderBy == Table.PATTERN_NAGASHI_J2;
    }

    // ボックスの組み合わせを取得する
    public static List<String> getBoxKeys(final List<String> args, final boolean isCombination, final boolean isTrio, final boolean isBracket) {
        List<String> list = new ArrayList<>();
        final int length = args.size();
        for (int i = 0; i < length; i++) {
            int jj;
            if (isCombination) {
                jj = i;
                if (!isBracket) {
                    jj++;
                }
            } else {
                jj = 0;
            }

            for (int j = jj; j < length; j++) {
                if (isTrio) {
                    // 3連勝系
                    int kk;
                    if (isCombination) {
                        kk = j;
                        if (!isBracket) {
                            kk++;
                        }
                    } else {
                        kk = 0;
                    }
                    for (int k = kk; k < length; k++) {
                        list = addFormatString(list, args.get(i), args.get(j), args.get(k), true);
                    }
                } else {
                    // 2連勝系
                    list = addFormatString(list, args.get(i), args.get(j), !isBracket);
                }
            }
        }
        return list;
    }

    // フォーメーションの組み合わせを取得する
    public static List<String> getFormationKeys(final List<String> args1, List<String> args2, List<String> args3, final boolean isCombination, final boolean isTrio, final boolean isBracket) {
        List<String> list = new ArrayList<>();
        final int length1 = args1.size();
        final int length2 = args2.size();
        final int length3;
        if (isTrio) {
            length3 = args3.size();
        } else {
            length3 = 0;
        }
        for (int i = 0; i < length1; i++) {
            for (int j = 0; j < length2; j++) {
                if (isTrio) {
                    for (int k = 0; k < length3; k++) {
                        // 順(123)
                        list = addFormatString(list, args1.get(i), args2.get(j), args3.get(k), true);
                        if (isCombination) {
                            // 132
                            list = addFormatString(list, args1.get(i), args3.get(k), args2.get(j), true);
                            // 213
                            list = addFormatString(list, args2.get(j), args1.get(i), args3.get(k), true);
                            // 231
                            list = addFormatString(list, args2.get(j), args3.get(k), args1.get(i), true);
                            // 312
                            list = addFormatString(list, args3.get(k), args1.get(i), args2.get(j), true);
                            // 321
                            list = addFormatString(list, args3.get(k), args2.get(j), args1.get(i), true);
                        }
                    }
                } else {
                    // 順
                    list = addFormatString(list, args1.get(i), args2.get(j), !isBracket);
                    if (isCombination) {
                        list = addFormatString(list, args2.get(j), args1.get(i), !isBracket);
                    }
                }

            }
        }
        return list;
    }

    public static List<String> getNagashiKeys(final String key, List<String> args, int nagashiPosition, final boolean isCombination, final boolean isBracket) {
        List<String> list = new ArrayList<>();
        final int length = args.size();
        for (int i = 0; i < length; i++) {
            if (isCombination) {
                list = addFormatString(list, key, args.get(i), !isBracket);
                list = addFormatString(list, args.get(i), key, !isBracket);
            } else {
                if (nagashiPosition == 0) {
                    list = addFormatString(list, key, args.get(i), !isBracket);
                } else {
                    list = addFormatString(list, args.get(i), key, !isBracket);
                }
            }
        }
        return list;
    }

    public static List<String> getNagashiJ1Keys(final String key, List<String> args1, List<String> args2, int nagashiPosition, final boolean isCombination) {
        List<String> list = new ArrayList<>();
        final int length1 = args1.size();
        final int length2 = args2.size();
        for (int i = 0; i < length1; i++) {
            for (int j = 0; j < length2; j++) {
                if (isCombination) {
                    list = addFormatString(list, key, args1.get(i), args2.get(j));
                    list = addFormatString(list, key, args2.get(j), args1.get(i));
                    list = addFormatString(list, args1.get(i), key, args2.get(j));
                    list = addFormatString(list, args1.get(i), args2.get(j), key);
                    list = addFormatString(list, args2.get(j), key, args1.get(i));
                    list = addFormatString(list, args2.get(j), args1.get(i), key);
                } else {
                    if (nagashiPosition == 0) {
                        list = addFormatString(list, key, args1.get(i), args2.get(j));
                        list = addFormatString(list, key, args2.get(j), args1.get(i));
                    } else if (nagashiPosition == 1) {
                        list = addFormatString(list, args1.get(i), key, args2.get(j));
                        list = addFormatString(list, args2.get(j), key, args1.get(i));
                    } else if (nagashiPosition == 2) {
                        list = addFormatString(list, args1.get(i), args2.get(j), key);
                        list = addFormatString(list, args2.get(j), args1.get(i), key);
                    }
                }
            }
        }
        return list;
    }

    public static List<String> getNagashiJ2Keys(final String key1, final String key2, List<String> args, int nagashiPosition, final boolean isCombination) {
        List<String> list = new ArrayList<>();
        final int length = args.size();
        for (int i = 0; i < length; i++) {
            if (isCombination) {
                list = addFormatString(list, key1, key2, args.get(i), true);
                list = addFormatString(list, key1, args.get(i), key2, true);
                list = addFormatString(list, key2, key1, args.get(i), true);
                list = addFormatString(list, key2, args.get(i), key1, true);
                list = addFormatString(list, args.get(i), key1, key2, true);
                list = addFormatString(list, args.get(i), key2, key1, true);
            } else {
                if (nagashiPosition == 0) {
                    // 1,2着流し
                    list = addFormatString(list, key1, key2, args.get(i), true);
                } else if (nagashiPosition == 1) {
                    // 1,3着流し
                    list = addFormatString(list, key1, args.get(i), key2, true);
                } else if (nagashiPosition == 2) {
                    //2,3着流し
                    list = addFormatString(list, args.get(i), key1, key2, true);
                }
            }
        }
        return list;
    }

    private static List<String> addFormatString(final List<String> list, final String arg1, final String arg2, final String arg3, final boolean format) {
        StringBuilder builder = new StringBuilder();
        if (format) {
            builder.append(format(arg1));
            builder.append(format(arg2));
            if (arg3 != null) {
                builder.append(format(arg3));
            }
        } else {
            builder.append(arg1);
            builder.append(arg2);
            if (arg3 != null) {
                builder.append(arg3);
            }
        }
        if (!list.contains(builder.toString())) {
            list.add(builder.toString());
        }
        return list;

    }

    private static List<String> addFormatString(final List<String> list, final String arg1, final String arg2, final boolean format) {
        return addFormatString(list, arg1, arg2, null, format);
    }

    private static List<String> addFormatString(final List<String> list, final String arg1, final String arg2, final String arg3) {
        return addFormatString(list, arg1, arg2, arg3, true);
    }

    public static String format(final String arg) {
        if (arg.length() == 1) {
            StringBuilder builder = new StringBuilder();
            builder.append("0").append(arg);
            return builder.toString();
        } else {
            return arg;
        }
    }
}
