package net.youbuntan.racing.model;

import android.test.AndroidTestCase;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.Exception;
import java.lang.String;
import java.lang.reflect.Type;

import jp.co.equinestudio.racing.logic.AssetsLogic;
import jp.co.equinestudio.racing.model.Odds;

/**
 * オッズ処理のテストクラス
 */
public class OddsTest extends AndroidTestCase{

    public void test_buildFromJson() throws Exception {
        String raceJson = AssetsLogic.getStringAsset(getContext(), "odds/odds.static.201009040111.json");
        Gson gson = new Gson();
        Type listType = new TypeToken<Odds>() { }. getType();
        Odds odds = gson.fromJson(raceJson, listType);

        assertNotNull(odds);
        assertNotNull(odds.getWinShowOddsMap());
        assertNotNull(odds.getBracketMap());
        assertNotNull(odds.getQuinellaMap());
        assertNotNull(odds.getWideMap());
        assertNotNull(odds.getExactaMap());
        assertNotNull(odds.getTrioMap());
        assertNotNull(odds.getTrifectaMap());

    }

}
