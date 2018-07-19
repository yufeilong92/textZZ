package com.xuechuan.xcedu.db.DbHelp;

import android.content.Context;

import com.xuechuan.xcedu.XceuAppliciton.MyAppliction;
import com.xuechuan.xcedu.db.UserInfomDb;
import com.xuechuan.xcedu.db.UserInfomDbDao;
import com.xuechuan.xcedu.utils.SaveUUidUtil;
import com.xuechuan.xcedu.utils.StringUtil;
import com.xuechuan.xcedu.vo.Db.UserLookVideoVo;
import com.xuechuan.xcedu.vo.UserBean;
import com.xuechuan.xcedu.vo.UserInfomVo;
import com.xuechuan.xcedu.vo.Db.UserLookVo;

import java.util.ArrayList;
import java.util.List;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: xcedu
 * @Package com.xuechuan.xcedu.db.DbHelp
 * @Description: db辅助类
 * @author: L-BackPacker
 * @date: 2018/5/9 11:17
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018
 */
public class DbHelperAssist {
    private UserInfomDbDao dao;
    private static DbHelperAssist assist = null;
    /**
     * 初始值
     */
    private int NUMBER = 0;
    /**
     * 循环次数
     */
    private int XUNHUNNUBER = 3;
    /**
     * 执行次数
     */
    private int NEXTNUMBER = 0;

    public DbHelperAssist() {
        NEXTNUMBER = NUMBER;
        if (dao == null) {
            if (DBHelper.getDaoSession() != null)
                DBHelper.getDaoSession().clear();
            dao = DBHelper.getDaoSession().getUserInfomDbDao();
        }

    }

    public static DbHelperAssist getInstance(Context mContext) {
        if (assist == null) {
            assist = new DbHelperAssist();
            if (DBHelper.getDaoSession() == null) {
                DBHelper.initDb(mContext);
            }
        }
        return assist;
    }

    /**
     * 删除所有
     */
    public void delectAll() {
        dao.deleteAll();
    }

    /**
     * 查询所有
     *
     * @return
     */
    public List<UserInfomDb> queryAll() {
        List<UserInfomDb> userInfomDbs = dao.loadAll();
        if (userInfomDbs == null) {
            return null;
        }
        return userInfomDbs;
    }

    /**
     * 更新技术
     *
     * @param mdata 跟新记录
     */
    public void upDataSkillRecord(List<UserLookVo> mdata) {
        if (mdata == null || mdata.isEmpty()) {
            return;
        }
//        String userId = SaveUUidUtil.getInstance().getUserId();
//        UserInfomDb infomDb = dao.queryBuilder().where(UserInfomDbDao.Properties.Moid.eq(userId)).unique();
        UserInfomDb infomDb = queryWithuuUserInfom();
        if (infomDb == null) {
            if (NEXTNUMBER >= XUNHUNNUBER) {
                NEXTNUMBER = NUMBER;
                return;
            }
            ++NEXTNUMBER;
            UserInfomVo userInfom = MyAppliction.getInstance().getUserInfom();
            saveUserInfom(userInfom);
            upDataSkillRecord(mdata);
        }
        NEXTNUMBER = NUMBER;
        List<UserLookVo> skillData = infomDb.getSkillData();
        if (skillData == null) {
            skillData = new ArrayList<>();
        } else {
            for (int i = 0; i < skillData.size(); i++) {
                UserLookVo userLookVo = mdata.get(0);
                UserLookVo lookVo = skillData.get(i);
                if (userLookVo.getChapterId().equals(lookVo.getChapterId())) {
                    skillData.remove(i);
                }
            }
        }

        skillData.addAll(mdata);
        infomDb.setSkillData(skillData);
        dao.update(infomDb);
    }

    /**
     * 跟新综合记录
     *
     * @param mdata 跟新记录
     */
    public void upDataColoctRecord(List<UserLookVo> mdata) {
        if (mdata == null || mdata.isEmpty()) {
            return;
        }
//        String userId = SaveUUidUtil.getInstance().getUserId();
//        UserInfomDb infomDb = dao.queryBuilder().where(UserInfomDbDao.Properties.Moid.eq(userId)).unique();
        UserInfomDb infomDb = queryWithuuUserInfom();

        if (infomDb == null) {
            if (NEXTNUMBER >= XUNHUNNUBER) {
                NEXTNUMBER = NUMBER;
                return;
            }
            ++NEXTNUMBER;
            UserInfomVo userInfom = MyAppliction.getInstance().getUserInfom();
            saveUserInfom(userInfom);
            upDataColoctRecord(mdata);
        }
        NEXTNUMBER = NUMBER;
        List<UserLookVo> coloctData = infomDb.getColoctData();
        if (coloctData == null) {
            coloctData = new ArrayList<>();
        } else {
            for (int i = 0; i < coloctData.size(); i++) {
                UserLookVo userLookVo = mdata.get(0);
                UserLookVo lookVo = coloctData.get(i);
                if (userLookVo.getChapterId().equals(lookVo.getChapterId())) {
                    coloctData.remove(i);
                }
            }
        }
        coloctData.addAll(mdata);
        infomDb.setColoctData(coloctData);
        dao.update(infomDb);
    }

    /**
     * 跟新案例
     *
     * @param mdata 跟新记录
     */
    public void upDataCaseRecord(List<UserLookVo> mdata) {
        if (mdata == null || mdata.isEmpty()) {
            return;
        }
//        String userId = SaveUUidUtil.getInstance().getUserId();
//        UserInfomDb infomDb = dao.queryBuilder().where(UserInfomDbDao.Properties.Moid.eq(userId)).unique();
        UserInfomDb infomDb = queryWithuuUserInfom();

        if (infomDb == null) {
            if (NEXTNUMBER >= XUNHUNNUBER) {
                NEXTNUMBER = NUMBER;
                return;
            }
            ++NEXTNUMBER;
            UserInfomVo userInfom = MyAppliction.getInstance().getUserInfom();
            saveUserInfom(userInfom);
            upDataCaseRecord(mdata);
        }
        NEXTNUMBER = NUMBER;
        List<UserLookVo> caselData = infomDb.getCaseData();
        if (caselData == null) {
            caselData = new ArrayList<>();
        } else {
            for (int i = 0; i < caselData.size(); i++) {
                UserLookVo userLookVo = mdata.get(0);
                UserLookVo lookVo = caselData.get(i);
                if (userLookVo.getChapterId().equals(lookVo.getChapterId())) {
                    caselData.remove(i);
                }
            }
        }
        caselData.addAll(mdata);
        infomDb.setCaseData(caselData);
        dao.update(infomDb);
    }

    /**
     * 保存数据
     *
     * @param vo
     */
    public void saveUserInfom(UserInfomVo vo) {
        if (vo == null) {
            return;
        }
        //用户信息
        UserBean user = vo.getData().getUser();
        String id = String.valueOf(user.getId());
        SaveUUidUtil.getInstance().delectUUid();
        SaveUUidUtil.getInstance().putUUID(id);
        UserInfomDb infomDb = dao.queryBuilder().where(UserInfomDbDao.Properties.Moid.eq(id)).unique();
        //数据库表
        UserInfomDb userInfomDb = new UserInfomDb();
        if (infomDb != null) {
            userInfomDb = infomDb;
            userInfomDb.setVo(vo);
            userInfomDb.setMoid(String.valueOf(user.getId()));
            userInfomDb.setToken(user.getToken());
            userInfomDb.setTokenTime(user.getTokenexpire());
            MyAppliction.getInstance().setUserInfom(vo);
            dao.update(userInfomDb);
        } else {
            //更新信息
            userInfomDb.setVo(vo);
            userInfomDb.setMoid(String.valueOf(user.getId()));
            userInfomDb.setToken(user.getToken());
            userInfomDb.setTokenTime(user.getTokenexpire());
            MyAppliction.getInstance().setUserInfom(vo);
            dao.insert(userInfomDb);

        }

    }


    /**
     * 获取用户信息
     *
     * @param id
     * @return
     */
    public UserInfomDb queryWithuuId(String id) {
        if (StringUtil.isEmpty(id)) {
            return null;
        }
        UserInfomDb infomDb = dao.queryBuilder().where(UserInfomDbDao.Properties.Moid.eq(id)).unique();
        if (infomDb == null) {
            return null;
        }
        return infomDb;
    }

    /**
     * 获取用户信息
     *
     * @return
     */
    public UserInfomDb queryWithuuUserInfom() {
        String userId = SaveUUidUtil.getInstance().getUserId();
        if (DBHelper.getDaoSession() != null)
            DBHelper.getDaoSession().clear();
        UserInfomDb infomDb = dao.queryBuilder().where(UserInfomDbDao.Properties.Moid.eq(userId)).unique();
        if (infomDb == null) {
            return null;
        }
        return infomDb;
    }

    /***
     * 查询技术章节
     * @param id
     * @return
     */
    public List<UserLookVo> querySkill(String id) {
        UserInfomDb userInfomDb = queryWithuuId(id);
        return userInfomDb.getSkillData();
    }

    /***
     * 查询综合章节
     * @param id
     * @return
     */
    public List<UserLookVo> queryColoct(String id) {
        UserInfomDb userInfomDb = queryWithuuId(id);
        return userInfomDb.getColoctData();
    }

    /***
     * 查询案例章节
     * @param id
     * @return
     */
    public List<UserLookVo> queryCase(String id) {
        UserInfomDb userInfomDb = queryWithuuId(id);
        return userInfomDb.getCaseData();
    }

    /**
     * 更新模式选择
     *
     * @param day
     */
    public void upDataDayOrNight(String day) {
        if (StringUtil.isEmpty(day)) {
            return;
        }
//        String userId = SaveUUidUtil.getInstance().getUserId();
//        UserInfomDb userInfomDb = queryWithuuId(userId);
        UserInfomDb userInfomDb = queryWithuuUserInfom();

        if (userInfomDb == null) {
            if (NEXTNUMBER >= XUNHUNNUBER) {
                NEXTNUMBER = NUMBER;
                return;
            }
            ++NEXTNUMBER;
            UserInfomVo vo = MyAppliction.getInstance().getUserInfom();
            saveUserInfom(vo);
            upDataDayOrNight(day);
        }
        NEXTNUMBER = NUMBER;
        userInfomDb.setShowDayOrNight(day);
        dao.update(userInfomDb);
    }

    /**
     * 更新自动跳下一题
     *
     * @param isGo
     */
    public void upDataNextGo(boolean isGo) {
//        String userId = SaveUUidUtil.getInstance().getUserId();
//        UserInfomDb userInfomDb = queryWithuuId(userId);
        UserInfomDb userInfomDb = queryWithuuUserInfom();

        if (userInfomDb == null) {
            if (NEXTNUMBER >= XUNHUNNUBER) {
                NEXTNUMBER = NUMBER;
                return;
            }
            ++NEXTNUMBER;
            UserInfomVo vo = MyAppliction.getInstance().getUserInfom();
            saveUserInfom(vo);
            upDataNextGo(isGo);
        }
        NEXTNUMBER = NUMBER;
        userInfomDb.setUserNextGo(isGo);
        dao.update(userInfomDb);
    }

    /**
     * 更新删除次数
     *
     * @param number
     */
    public void upDataDelect(String number) {
        if (StringUtil.isEmpty(number)) {
            return;
        }
//        String userId = SaveUUidUtil.getInstance().getUserId();
//        UserInfomDb userInfomDb = queryWithuuId(userId);
        UserInfomDb userInfomDb = queryWithuuUserInfom();

        if (userInfomDb == null) {
            if (NEXTNUMBER >= XUNHUNNUBER) {
                NEXTNUMBER = NUMBER;
                return;
            }
            ++NEXTNUMBER;
            UserInfomVo vo = MyAppliction.getInstance().getUserInfom();
            userInfomDb.setVo(vo);
            saveUserInfom(vo);
            upDataDelect(number);
        }
        NEXTNUMBER = NUMBER;
        userInfomDb.setDelectQuestion(number);
        dao.update(userInfomDb);
    }

    /**
     * 更新删用户查看记录
     */
    public void upLooklistDelect() {

        UserInfomDb infomDb = queryWithuuUserInfom();
        if (infomDb == null) {
            UserInfomVo vo = MyAppliction.getInstance().getUserInfom();
            infomDb.setVo(vo);
            saveUserInfom(vo);
        }
        ArrayList<UserLookVo> vos = new ArrayList<>();
        infomDb.setWrongDataSkill(vos);
        infomDb.setWrongDataCase(vos);
        infomDb.setWrongDataColoct(vos);
        dao.update(infomDb);
    }

    /**
     * 更新自动跳下一题
     *
     * @param number
     */
    public void upDataBuyInfom(String number, boolean isbuy) {
        if (StringUtil.isEmpty(number)) {
            return;
        }
//        String userId = SaveUUidUtil.getInstance().getUserId();
//        UserInfomDb userInfomDb = queryWithuuId(userId);
        UserInfomDb userInfomDb = queryWithuuUserInfom();

        if (userInfomDb == null) {
            if (NEXTNUMBER >= XUNHUNNUBER) {
                NEXTNUMBER = NUMBER;
                return;
            }
            ++NEXTNUMBER;
            UserInfomVo vo = MyAppliction.getInstance().getUserInfom();
            saveUserInfom(vo);
            upDataBuyInfom(number, isbuy);
        }
        NEXTNUMBER = NUMBER;
        if (number.equals("1")) {
            userInfomDb.setSkillBook(isbuy);
        } else if (number.equals("2")) {
            userInfomDb.setColligateBook(isbuy);
        } else if (number.equals("3")) {
            userInfomDb.setCaseBook(isbuy);
        }

        dao.update(userInfomDb);
    }


    /**
     * 更新错误记录数据
     */
    public void upDataWoringSkill(List<UserLookVo> mdata) {
        if (mdata == null || mdata.isEmpty()) {
            return;
        }
        UserInfomDb infomDb = queryWithuuUserInfom();

        if (infomDb == null) {
            if (NEXTNUMBER >= XUNHUNNUBER) {
                NEXTNUMBER = NUMBER;
                return;
            }
            ++NEXTNUMBER;
            UserInfomVo userInfom = MyAppliction.getInstance().getUserInfom();
            saveUserInfom(userInfom);
            upDataWoringSkill(mdata);
        }
        NEXTNUMBER = NUMBER;
        List<UserLookVo> skillData = infomDb.getWrongDataSkill();
        if (skillData == null) {
            skillData = mdata;
            for (int i = 0; i < skillData.size(); i++) {
                UserLookVo vo = skillData.get(i);
                vo.setChapterId(vo.getChapterId());
                vo.setNextId("1");
            }

        } else {
            boolean isSave = false;
            int numbers = -1;
            for (int i = 0; i < skillData.size(); i++) {
                UserLookVo woringvo = skillData.get(i);
                for (int i1 = 0; i1 < mdata.size(); i1++) {
                    UserLookVo userLookVo = mdata.get(i1);
                    if (woringvo.getChapterId().equals(userLookVo.getChapterId())) {
                        isSave = true;
                        numbers = i;

                    }
                }
            }
            if (isSave) {
                UserLookVo vo = skillData.get(numbers);
                String nextId = vo.getNextId();
                if (StringUtil.isEmpty(nextId)) {
                    vo.setNextId("1");
                } else {
                    int number = Integer.parseInt(nextId);
                    number += 1;
                    vo.setNextId(number + "");
                }
            } else {
                UserLookVo vo = mdata.get(0);
                vo.setNextId("1");
                skillData.add(vo);
            }

        }

        infomDb.setWrongDataSkill(skillData);
        if (DBHelper.getDaoSession() != null)
            DBHelper.getDaoSession().clear();
        dao.update(infomDb);
    }

    /***
     * 跟新对数
     * @param mdata
     */
    public void updateWoringCase(List<UserLookVo> mdata) {
        if (mdata == null || mdata.isEmpty()) {
            return;
        }
        UserInfomDb infomDb = queryWithuuUserInfom();

        if (infomDb == null) {
            if (NEXTNUMBER >= XUNHUNNUBER) {
                NEXTNUMBER = NUMBER;
                return;
            }
            ++NEXTNUMBER;
            UserInfomVo userInfom = MyAppliction.getInstance().getUserInfom();
            saveUserInfom(userInfom);
            updateWoringCase(mdata);
        }
        NEXTNUMBER = NUMBER;
        List<UserLookVo> caselData = infomDb.getWrongDataCase();
        if (caselData == null) {
            caselData = mdata;
            for (int i = 0; i < caselData.size(); i++) {
                UserLookVo vo = caselData.get(i);
                vo.setChapterId(vo.getChapterId());
                vo.setNextId("1");
            }
        } else {
            boolean isSave = false;
            int numbers = -1;
            for (int i = 0; i < caselData.size(); i++) {
                UserLookVo woringvo = caselData.get(i);
                for (int i1 = 0; i1 < mdata.size(); i1++) {
                    UserLookVo userLookVo = mdata.get(i1);
                    if (woringvo.getChapterId().equals(userLookVo.getChapterId())) {
                        isSave = true;
                        numbers = i;
                    }
                }
            }
            if (isSave) {
                UserLookVo vo = caselData.get(numbers);
                String nextId = vo.getNextId();
                if (StringUtil.isEmpty(nextId)) {
                    vo.setNextId("1");
                } else {
                    int number = Integer.parseInt(nextId);
                    number += 1;
                    vo.setNextId(number + "");
                }
            } else {
                UserLookVo vo = mdata.get(0);
                vo.setNextId("1");
                caselData.add(vo);
            }

        }
        infomDb.setWrongDataCase(caselData);
        dao.update(infomDb);
    }

    /**
     * 更新错题对数
     *
     * @param mdata
     */
    public void updataWoringColoct(List<UserLookVo> mdata) {
        if (mdata == null || mdata.isEmpty()) {
            return;
        }
        UserInfomDb infomDb = queryWithuuUserInfom();

        if (infomDb == null) {
            if (NEXTNUMBER >= XUNHUNNUBER) {
                NEXTNUMBER = NUMBER;
                return;
            }
            ++NEXTNUMBER;
            UserInfomVo userInfom = MyAppliction.getInstance().getUserInfom();
            saveUserInfom(userInfom);
            updataWoringColoct(mdata);
        }
        NEXTNUMBER = NUMBER;
        List<UserLookVo> colocData = infomDb.getWrongDataColoct();
        if (colocData == null) {
            colocData = mdata;
            for (int i = 0; i < colocData.size(); i++) {
                UserLookVo vo = colocData.get(i);
                vo.setChapterId(vo.getChapterId());
                vo.setNextId("1");
            }
        } else {
            boolean isSave = false;
            int numbers = -1;
            for (int i = 0; i < colocData.size(); i++) {
                UserLookVo woringvo = colocData.get(i);
                for (int i1 = 0; i1 < mdata.size(); i1++) {
                    UserLookVo userLookVo = mdata.get(i1);
                    if (woringvo.getChapterId().equals(userLookVo.getChapterId())) {
                        isSave = true;
                        numbers = i;
                    }
                }
            }
            if (isSave) {
                UserLookVo vo = colocData.get(numbers);
                String nextId = vo.getNextId();
                if (StringUtil.isEmpty(nextId)) {
                    vo.setNextId("1");
                } else {
                    int number = Integer.parseInt(nextId);
                    number += 1;
                    vo.setNextId(number + "");
                }
            } else {
                UserLookVo vo = mdata.get(0);
                vo.setNextId("1");
                colocData.add(vo);
            }
        }
        infomDb.setWrongDataColoct(colocData);
        dao.update(infomDb);
    }

    /**
     * 保存错题集合
     *
     * @param mdata
     */
    public void saveWoringSkill(List<UserLookVo> mdata) {
        if (mdata == null || mdata.isEmpty()) {
            return;
        }
        UserInfomDb infomDb = queryWithuuUserInfom();
        if (infomDb == null) {
            if (NEXTNUMBER >= XUNHUNNUBER) {
                NEXTNUMBER = NUMBER;
                return;
            }
            ++NEXTNUMBER;
            UserInfomVo userInfom = MyAppliction.getInstance().getUserInfom();
            saveUserInfom(userInfom);
            saveWoringSkill(mdata);
        }
        NEXTNUMBER = NUMBER;
        List<UserLookVo> wrongDataSkill = infomDb.getWrongDataSkill();
        if (wrongDataSkill == null || wrongDataSkill.isEmpty()) {
            wrongDataSkill = new ArrayList<>();
        } else {
            for (int i = 0; i < wrongDataSkill.size(); i++) {
                UserLookVo userLookVo = wrongDataSkill.get(i);
                for (int j = 0; j < mdata.size(); j++) {
                    UserLookVo userLookVo1 = mdata.get(j);
                    if (userLookVo.getChapterId().equals(userLookVo1.getChapterId())) {
                        String nextId = userLookVo.getNextId();
                        if (StringUtil.isEmpty(nextId)) {
                            wrongDataSkill.remove(i);
                        } else {
                            mdata.remove(j);
                        }
                    }
                }
            }
        }
        wrongDataSkill.addAll(mdata);
        infomDb.setWrongDataSkill(wrongDataSkill);
        dao.update(infomDb);
    }

    /***
     *  保存错题集合
     * @param mdata
     */
    public void saveWoringColoct(List<UserLookVo> mdata) {
        if (mdata == null || mdata.isEmpty()) {
            return;
        }
        UserInfomDb infomDb = queryWithuuUserInfom();

        if (infomDb == null) {
            if (NEXTNUMBER >= XUNHUNNUBER) {
                NEXTNUMBER = NUMBER;
                return;
            }
            ++NEXTNUMBER;
            UserInfomVo userInfom = MyAppliction.getInstance().getUserInfom();
            saveUserInfom(userInfom);
            saveWoringColoct(mdata);
        }
        NEXTNUMBER = NUMBER;
        List<UserLookVo> wrongDatacoloct = infomDb.getWrongDataColoct();
        if (wrongDatacoloct == null || wrongDatacoloct.isEmpty()) {
            wrongDatacoloct = new ArrayList<>();
        } else {
            for (int i = 0; i < wrongDatacoloct.size(); i++) {
                UserLookVo userLookVo = wrongDatacoloct.get(i);
                for (int j = 0; j < mdata.size(); j++) {
                    UserLookVo userLookVo1 = mdata.get(j);
                    if (userLookVo.getChapterId().equals(userLookVo1.getChapterId())) {
                        String nextId = userLookVo.getNextId();
                        if (StringUtil.isEmpty(nextId)) {
                            wrongDatacoloct.remove(i);
                        } else {
                            mdata.remove(j);
                        }
                    }
                }
            }
        }
        wrongDatacoloct.addAll(mdata);
        infomDb.setWrongDataColoct(wrongDatacoloct);
        dao.update(infomDb);
    }

    /**
     * 保存错题集合
     *
     * @param mdata
     */
    public void saveWoringCase(List<UserLookVo> mdata) {
        if (mdata == null || mdata.isEmpty()) {
            return;
        }
        UserInfomDb infomDb = queryWithuuUserInfom();
        if (infomDb == null) {
            if (NEXTNUMBER >= XUNHUNNUBER) {
                NEXTNUMBER = NUMBER;
                return;
            }
            ++NEXTNUMBER;
            UserInfomVo userInfom = MyAppliction.getInstance().getUserInfom();
            saveUserInfom(userInfom);
            saveWoringCase(mdata);
        }
        NEXTNUMBER = NUMBER;
        List<UserLookVo> wrongDataCase = infomDb.getWrongDataCase();
        if (wrongDataCase == null || wrongDataCase.isEmpty()) {
            wrongDataCase = new ArrayList<>();
        } else {
            for (int i = 0; i < wrongDataCase.size(); i++) {
                UserLookVo userLookVo = wrongDataCase.get(i);
                for (int j = 0; j < mdata.size(); j++) {
                    UserLookVo userLookVo1 = mdata.get(j);
                    if (userLookVo.getChapterId().equals(userLookVo1.getChapterId())) {
                        String nextId = userLookVo.getNextId();
                        if (StringUtil.isEmpty(nextId)) {
                            wrongDataCase.remove(i);
                        } else {
                            mdata.remove(j);
                        }
                    }
                }
            }
        }
        wrongDataCase.addAll(mdata);
        infomDb.setWrongDataCase(wrongDataCase);
        dao.update(infomDb);
    }

    /***
     * 删除满足条件id
     * @param id id //章节id
     * @param mark 类型
     */
    public void delectDBWring(String id, String mark) {
        if (StringUtil.isEmpty(id)) {
            return;
        }
        UserInfomDb infomDb = queryWithuuUserInfom();
        if (infomDb == null) {
            return;
        }
        if (mark.equals("1")) {
            List<UserLookVo> skill = infomDb.getWrongDataSkill();
            if (skill == null || skill.isEmpty()) {
                return;
            }
            for (int i = 0; i < skill.size(); i++) {
                UserLookVo userLookVo = skill.get(i);
                if (userLookVo.getChapterId().equals(id)) {
                    skill.remove(i);
                }
            }

            infomDb.setWrongDataSkill(skill);
            dao.update(infomDb);
        }
        if (mark.equals("2")) {
            List<UserLookVo> skill = infomDb.getWrongDataColoct();
            if (skill == null || skill.isEmpty()) {
                return;
            }
            for (int i = 0; i < skill.size(); i++) {
                UserLookVo userLookVo = skill.get(i);
                if (userLookVo.getChapterId().equals(id)) {
                    skill.remove(i);
                }
            }
            infomDb.setWrongDataColoct(skill);
            dao.update(infomDb);
        }

        if (mark.equals("3")) {
            List<UserLookVo> skill = infomDb.getWrongDataCase();
            if (skill == null || skill.isEmpty()) {
                return;
            }
            for (int i = 0; i < skill.size(); i++) {
                UserLookVo userLookVo = skill.get(i);
                if (userLookVo.getChapterId().equals(id)) {
                    skill.remove(i);
                }
            }
            infomDb.setWrongDataCase(skill);
            dao.update(infomDb);
        }
    }

    public void saveLookVideo(UserLookVideoVo vo) {
        if (vo == null) {
            return;
        }
//        String userId = SaveUUidUtil.getInstance().getUserId();
//        if (DBHelper.getDaoSession() != null)
//            DBHelper.getDaoSession().clear();
//        UserInfomDb infomDb = dao.queryBuilder().where(UserInfomDbDao.Properties.Moid.eq(userId)).unique();
        UserInfomDb infomDb = queryWithuuUserInfom();
        if (infomDb == null) {
            if (NEXTNUMBER >= XUNHUNNUBER) {
                NEXTNUMBER = NUMBER;
                return;
            }
            ++NEXTNUMBER;
            UserInfomVo userInfom = MyAppliction.getInstance().getUserInfom();
            saveUserInfom(userInfom);
            saveLookVideo(vo);
        }
        NEXTNUMBER = NUMBER;
        List<UserLookVideoVo> lookVideolist = infomDb.getLookVideolist();
        if (lookVideolist == null || lookVideolist.isEmpty()) {
            lookVideolist = new ArrayList<>();
            lookVideolist.add(vo);
        } else {
            boolean isSavebefter = false;
            int savebefer = -1;
            for (int i = 0; i < lookVideolist.size(); i++) {
                UserLookVideoVo userLookVo = lookVideolist.get(i);
                if (userLookVo.getKid().equals(vo.getKid())) {
                    isSavebefter = true;
                    savebefer = i;
                }
            }
            if (isSavebefter) {
                if (savebefer != -1) {
                    lookVideolist.remove(savebefer);
                    lookVideolist.add(vo);
                }
            } else {
                lookVideolist.add(vo);
            }


        }
        infomDb.setLookVideolist(lookVideolist);
        dao.update(infomDb);

    }

    /**
     * 查询用户视频记录
     *
     * @param kid
     * @return
     */
    public UserLookVideoVo queryUserLookVideoWithKid(String kid) {
//        String userId = SaveUUidUtil.getInstance().getUserId();
//        if (DBHelper.getDaoSession() != null)
//            DBHelper.getDaoSession().clear();
//        UserInfomDb infomDb = dao.queryBuilder().where(UserInfomDbDao.Properties.Moid.eq(userId)).unique();
        UserInfomDb infomDb = queryWithuuUserInfom();
        if (infomDb == null) {
            return null;
        }
        List<UserLookVideoVo> lookVideolist = infomDb.getLookVideolist();
        if (lookVideolist == null || lookVideolist.isEmpty()) {
            return null;
        }
        for (UserLookVideoVo vo : lookVideolist) {
            if (vo.getKid().equals(kid)) {
                return vo;
            }
        }
        return null;
    }
}
