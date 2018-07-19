package com.xuechuan.xcedu.db;

import java.util.Map;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.AbstractDaoSession;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.identityscope.IdentityScopeType;
import org.greenrobot.greendao.internal.DaoConfig;

import com.xuechuan.xcedu.db.DownVideoDb;
import com.xuechuan.xcedu.db.UserInfomDb;

import com.xuechuan.xcedu.db.DownVideoDbDao;
import com.xuechuan.xcedu.db.UserInfomDbDao;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.

/**
 * {@inheritDoc}
 * 
 * @see org.greenrobot.greendao.AbstractDaoSession
 */
public class DaoSession extends AbstractDaoSession {

    private final DaoConfig downVideoDbDaoConfig;
    private final DaoConfig userInfomDbDaoConfig;

    private final DownVideoDbDao downVideoDbDao;
    private final UserInfomDbDao userInfomDbDao;

    public DaoSession(Database db, IdentityScopeType type, Map<Class<? extends AbstractDao<?, ?>>, DaoConfig>
            daoConfigMap) {
        super(db);

        downVideoDbDaoConfig = daoConfigMap.get(DownVideoDbDao.class).clone();
        downVideoDbDaoConfig.initIdentityScope(type);

        userInfomDbDaoConfig = daoConfigMap.get(UserInfomDbDao.class).clone();
        userInfomDbDaoConfig.initIdentityScope(type);

        downVideoDbDao = new DownVideoDbDao(downVideoDbDaoConfig, this);
        userInfomDbDao = new UserInfomDbDao(userInfomDbDaoConfig, this);

        registerDao(DownVideoDb.class, downVideoDbDao);
        registerDao(UserInfomDb.class, userInfomDbDao);
    }
    
    public void clear() {
        downVideoDbDaoConfig.clearIdentityScope();
        userInfomDbDaoConfig.clearIdentityScope();
    }

    public DownVideoDbDao getDownVideoDbDao() {
        return downVideoDbDao;
    }

    public UserInfomDbDao getUserInfomDbDao() {
        return userInfomDbDao;
    }

}