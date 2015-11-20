package jp.co.equinestudio.racing.Manager;

import android.content.Context;

import jp.co.equinestudio.racing.R;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 *
 */
public class BracketColorManager {

    @Accessors(prefix = "m") @Getter @Setter
    private String mBracketNumber;

    /**
     * デフォルトのコンストラクタ
     */
    public BracketColorManager() {
    }

    /**
     * 枠番の背景カラーリソース
     * @param context
     * @return
     */
    public int getBackgroundColorResourceId(final Context context) {
        if (mBracketNumber.equals("")) {
            return context.getResources().getColor(R.color.bracket_1);
        }
        int bracketNumber = Integer.parseInt(mBracketNumber);
        switch (bracketNumber) {
            case 1:
                return context.getResources().getColor(R.color.bracket_1);
            case 2:
                return context.getResources().getColor(R.color.bracket_2);
            case 3:
                return context.getResources().getColor(R.color.bracket_3);
            case 4:
                return context.getResources().getColor(R.color.bracket_4);
            case 5:
                return context.getResources().getColor(R.color.bracket_5);
            case 6:
                return context.getResources().getColor(R.color.bracket_6);
            case 7:
                return context.getResources().getColor(R.color.bracket_7);
            case 8:
                return context.getResources().getColor(R.color.bracket_8);
            default:
                return context.getResources().getColor(R.color.bracket_1);
        }
    }

    public int getTextColorResourceId(final Context context) {
        if (mBracketNumber.equals("")) {
            return context.getResources().getColor(R.color.bracket_text_black);
        }
        int bracketNumber = Integer.parseInt(mBracketNumber);
        switch (bracketNumber) {
            case 1:
            case 3:
            case 5:
            case 6:
            case 7:
            case 8:
                return context.getResources().getColor(R.color.bracket_text_black);
            case 2:
            case 4:
                return context.getResources().getColor(R.color.bracket_text_white);
            default:
                return context.getResources().getColor(R.color.bracket_text_black);
        }
    }


}
