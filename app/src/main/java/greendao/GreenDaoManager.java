package greendao;

import com.hl.htk_customer.utils.MyApplication;

/**
 * Created by Administrator on 2017/9/20.
 */

public class GreenDaoManager {

    private DaoSession mDaoSession;
    private DaoMaster mDaoMaster;

    private GreenDaoManager() {
        DaoMaster.DevOpenHelper devOpenHelper = new DaoMaster.DevOpenHelper(MyApplication.getContext(), "db_user");
        mDaoMaster = new DaoMaster(devOpenHelper.getWritableDatabase());
        mDaoSession = mDaoMaster.newSession();
    }

    public static GreenDaoManager getInstance() {
        return SingleInstanceHolder.INSTANCE;
    }

    private static class SingleInstanceHolder{
        private static final GreenDaoManager INSTANCE = new GreenDaoManager();
    }

    public DaoSession getDaoSession() {
        return mDaoSession;
    }

    public DaoSession getNewSession() {
        mDaoSession = mDaoMaster.newSession();
        return mDaoSession;
    }

    public DaoMaster getDaoMaster() {
        return mDaoMaster;
    }


}
