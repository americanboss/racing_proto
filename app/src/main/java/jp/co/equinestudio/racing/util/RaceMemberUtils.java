package jp.co.equinestudio.racing.util;

import java.util.ArrayList;

import jp.co.equinestudio.racing.model.RaceMember;

/**
 * Created by masakikase on 2016/12/06.
 */
public class RaceMemberUtils {

    public static RaceMember getHorseName(final ArrayList<RaceMember> raceMembers, final int gateNumber) {
        String horseName = "";
        for (RaceMember raceMember : raceMembers) {
            if (raceMember.getGateNumber() == gateNumber) {
                return raceMember;
            }
        }
        return null;
    }

}
