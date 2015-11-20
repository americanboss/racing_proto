package jp.co.equinestudio.racing.util;

import jp.co.equinestudio.racing.dao.green.FavoriteHorse;
import jp.co.equinestudio.racing.dao.green.FavoriteHorseDao;

/**
 * Created by masakikase on 2015/11/08.
 */
public class FavoriteHorseUtils {

    private FavoriteHorseDao mFavoriteHorseDao;

    public static final long NOT_FOUND = -1;

    public FavoriteHorseUtils(final FavoriteHorseDao dao) {
        mFavoriteHorseDao = dao;
    }

    public boolean isFavorite (final String horseCode) {
        return mFavoriteHorseDao.queryBuilder().where(FavoriteHorseDao.Properties.Horse_code.eq(horseCode)).count() > 0;
    }

    public boolean add(final String horseCode, final String horseName) {
        FavoriteHorse favoriteHorse = new FavoriteHorse();
        favoriteHorse.setGroup(0);
        favoriteHorse.setHorse_code(horseCode);
        favoriteHorse.setHorse_name(horseName);
        favoriteHorse.setReg_timestamp(System.currentTimeMillis());
        mFavoriteHorseDao.insert(favoriteHorse);
        return true;
    }

    public boolean remove(final String horseCode) {
        long id = getIdByHorseCode(horseCode);
        if (id != NOT_FOUND) {
            mFavoriteHorseDao.deleteByKey(id);
            return true;
        } else {
            return false;
        }
    }

    public long getIdByHorseCode(final String horseCode) {
        FavoriteHorse favoriteHorse = mFavoriteHorseDao.queryBuilder().where(FavoriteHorseDao.Properties.Horse_code.eq(horseCode)).unique();
        if (favoriteHorse != null) {
            return favoriteHorse.getId();
        } else {
            return NOT_FOUND;
        }
    }

}
