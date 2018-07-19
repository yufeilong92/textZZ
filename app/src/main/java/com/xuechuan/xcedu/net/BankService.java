package com.xuechuan.xcedu.net;

import android.content.Context;

import com.xuechuan.xcedu.R;
import com.xuechuan.xcedu.base.BaseHttpServcie;
import com.xuechuan.xcedu.net.view.StringCallBackView;
import com.xuechuan.xcedu.utils.L;
import com.xuechuan.xcedu.vo.GetParamVo;
import com.xuechuan.xcedu.vo.UserBean;
import com.xuechuan.xcedu.vo.UserInfomVo;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: xcedu
 * @Package com.xuechuan.xcedu.net
 * @Description: 题目
 * @author: L-BackPacker
 * @date: 2018/4/20 16:52
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018
 */
public class BankService extends BaseHttpServcie {
    private Context mContext;
    private static BankService service;

    public BankService(Context mContext) {
        this.mContext = mContext;
    }

    public static BankService getInstance(Context context) {
        if (service == null) {
            service = new BankService(context);
        }
        return service;
    }

    /**
     * 获取科目编号章节列表
     *
     * @param couresid
     * @param callBackView
     */
    public void requestChapterList(String couresid, StringCallBackView callBackView) {
        ArrayList<GetParamVo> listParamVo = getListParamVo();
        GetParamVo paramVo = getParamVo();
        paramVo.setParam("courseid");
        paramVo.setValue(couresid);
        listParamVo.add(paramVo);
        String url = getUrl(mContext, R.string.http_getCnapter);
        requestHttpServiceGet(mContext, url, listParamVo, true, callBackView);
    }

    /**
     * 根据科目获取所有练习题题号
     *
     * @param coruresid    科目编号
     * @param callBackView
     */
    public void requestCourseQuestionIdsList(String coruresid, StringCallBackView callBackView) {
        ArrayList<GetParamVo> listParamVo = getListParamVo();
        GetParamVo paramVo = getParamVo();
        paramVo.setParam("courseid");
        paramVo.setValue(coruresid);
        listParamVo.add(paramVo);
        String url = getUrl(mContext, R.string.http_coursequestionids);
        requestHttpServiceGet(mContext, url, listParamVo, true, callBackView);
    }

    /**
     * 题库首页获取错题和收藏数
     *
     * @param courseid
     * @param callBackView
     */
    public void requestErrSetandFav(String courseid, StringCallBackView callBackView) {
        UserInfomVo login = isLogin(mContext);
        if (login == null) {
            return;
        }
        UserBean user = login.getData().getUser();
        ArrayList<GetParamVo> listParamVo = getListParamVo();
        GetParamVo paramVo = getParamVo();
        paramVo.setParam("courseid");
        paramVo.setValue(courseid);
        listParamVo.add(paramVo);
        GetParamVo paramVo1 = getParamVo();
        paramVo1.setParam("staffid");
        paramVo1.setValue(String.valueOf(user.getId()));
        listParamVo.add(paramVo1);
        String url = getUrl(mContext, R.string.http_errsetandfav);
        requestHttpServiceGet(mContext, url, listParamVo, true, callBackView);
    }

    /**
     * 获取所有练习题库题号
     *
     * @param chapterid
     * @param callBackView
     */
    public void requestChapterQuestionids(String chapterid, StringCallBackView callBackView) {
        ArrayList<GetParamVo> listParamVo = getListParamVo();
        GetParamVo paramVo = getParamVo();
        paramVo.setParam("chapterid");
        paramVo.setValue(chapterid);
        listParamVo.add(paramVo);
        String url = getUrl(mContext, R.string.http_chapterquestionids);
        requestHttpServiceGet(mContext, url, listParamVo, true, callBackView);
    }

    /**
     * 获取科目获取题库标签
     *
     * @param couresid
     * @param callBackView
     */
    public void requestionTags(String couresid, StringCallBackView callBackView) {
        ArrayList<GetParamVo> listParamVo = getListParamVo();
        GetParamVo paramVo = getParamVo();
        paramVo.setParam("courseid");
        paramVo.setValue(couresid);
        listParamVo.add(paramVo);
        String url = getUrl(mContext, R.string.http_questiontags);
        requestHttpServiceGet(mContext, url, listParamVo, true, callBackView);
    }

    /**
     * 获取用户错题/收藏题统计信息
     *
     * @param couresid
     * @param tagtype      类型err 或者 fav
     * @param callBackView
     */
    public void requestQuestionTagScount(String couresid, String tagtype, StringCallBackView callBackView) {
        UserInfomVo login = isLogin(mContext);
        if (login == null) {
            return;
        }
        UserBean user = login.getData().getUser();

        ArrayList<GetParamVo> listParamVo = getListParamVo();
        GetParamVo paramVo = getParamVo();
        paramVo.setParam("courseid");
        paramVo.setValue(couresid);
        listParamVo.add(paramVo);
        GetParamVo paramVo1 = getParamVo();
        paramVo1.setParam("staffid");
        paramVo1.setValue(String.valueOf(user.getId()));
        listParamVo.add(paramVo1);
        GetParamVo paramVo2 = getParamVo();
        paramVo2.setParam("tagtype");
        paramVo2.setValue(tagtype);
        listParamVo.add(paramVo2);
        String url = getUrl(mContext, R.string.http_questiontagscount);
        requestHttpServiceGet(mContext, url, listParamVo, true, callBackView);
    }

    /**
     * 根据标签编号获取用户收藏题或者错题题号
     *
     * @param coursid
     * @param tagtype
     * @param tagid
     * @param callBackView
     */
    public void requestQuestionTagids(String coursid, String tagtype, String tagid, StringCallBackView
            callBackView) {
        UserInfomVo login = isLogin(mContext);
        if (login == null) {
            return;
        }
        UserBean user = login.getData().getUser();
        ArrayList<GetParamVo> listParamVo = getListParamVo();
        GetParamVo paramVo = getParamVo();
        paramVo.setParam("courseid");
        paramVo.setValue(coursid);
        listParamVo.add(paramVo);
        GetParamVo paramVo1 = getParamVo();
        paramVo1.setParam("tagtype");
        paramVo1.setValue(tagtype);
        listParamVo.add(paramVo1);
        GetParamVo paramVo2 = getParamVo();
        paramVo2.setParam("staffid");
        paramVo2.setValue(String.valueOf(user.getId()));
        listParamVo.add(paramVo2);
        GetParamVo paramVo3 = getParamVo();
        paramVo3.setParam("tagid");
        paramVo3.setValue(tagid);
        listParamVo.add(paramVo3);
        String url = getUrl(mContext, R.string.http_questiontagids);
        requestHttpServiceGet(mContext, url, listParamVo, true, callBackView);

    }

    /***
     * 根据题编号获取题目详情
     * @param questionid
     * @param callBackView
     */
    public void requestDetail(String questionid, StringCallBackView callBackView) {
        UserInfomVo login = isLogin(mContext);
        if (login == null) {
            return;
        }
        UserBean user = login.getData().getUser();
        ArrayList<GetParamVo> listParamVo = getListParamVo();
        GetParamVo paramVo = getParamVo();
        paramVo.setParam("questionid");
        paramVo.setValue(questionid);
        GetParamVo paramVo1 = getParamVo();
        GetParamVo paramVo2 = getParamVo();
        paramVo2.setParam("staffid");
        paramVo2.setValue(String.valueOf(user.getId()));
        listParamVo.add(paramVo2);
        listParamVo.add(paramVo);
        String url = getUrl(mContext, R.string.http_datail);
        requestHttpServiceGet(mContext, url, listParamVo, true, callBackView);
        L.e("requestDetail");

    }

    /***
     * 获取题目评论
     * @param questionid 编号
     * @param callBackView
     */
    public void reqiestQuestionCmment(String questionid, int page, StringCallBackView callBackView) {
        UserInfomVo login = isLogin(mContext);
        if (login == null) {
            return;
        }
        if (page <= 0) {
            page = 1;
        }
        UserBean user = login.getData().getUser();
        ArrayList<GetParamVo> listParamVo = getListParamVo();
        GetParamVo paramVo = getParamVo();
        paramVo.setParam("staffid");
        paramVo.setValue(String.valueOf(user.getId()));
        GetParamVo paramVo1 = getParamVo();
        paramVo1.setParam("questionid");
        paramVo1.setValue(questionid);
        GetParamVo paramVo2 = getParamVo();
        paramVo2.setParam("page");
        paramVo2.setValue(String.valueOf(page));
        GetParamVo paramVo3 = getParamVo();
        paramVo3.setParam("pagesize");
        paramVo3.setValue("10");
        listParamVo.add(paramVo3);
        listParamVo.add(paramVo2);
        listParamVo.add(paramVo1);
        listParamVo.add(paramVo);
        String url = getUrl(mContext, R.string.http_questioncmment);
        requestHttpServiceGet(mContext, url, listParamVo, true, callBackView);
    }

    /**
     * .获取题目评论的评论
     *
     * @param questionid   编号
     * @param commentid    评论编号
     * @param callBackView
     */
    public void requestCommentComment(int page, String questionid, String commentid, StringCallBackView callBackView) {
        UserInfomVo login = isLogin(mContext);
        if (login == null) {
            return;
        }
        UserBean user = login.getData().getUser();
        ArrayList<GetParamVo> listParamVo = getListParamVo();
        GetParamVo paramVo = getParamVo();
        paramVo.setParam("questionid");
        paramVo.setValue(questionid);
        listParamVo.add(paramVo);
        GetParamVo paramVo1 = getParamVo();
        paramVo1.setParam("commentid");
        paramVo1.setValue(commentid);
        listParamVo.add(paramVo1);
        GetParamVo paramVo2 = getParamVo();
        paramVo2.setParam("staffid");

        paramVo2.setValue(String.valueOf(user.getId()));
        listParamVo.add(paramVo2);
        GetParamVo paramVo3 = getParamVo();
        paramVo3.setParam("page");
        paramVo3.setValue(String.valueOf(page));
        GetParamVo paramVo4 = getParamVo();
        paramVo4.setParam("pagesize");
        paramVo4.setValue("10");
        listParamVo.add(paramVo3);
        listParamVo.add(paramVo4);
        String url = getUrl(mContext, R.string.http_commentcomment);
        requestHttpServiceGet(mContext, url, listParamVo, true, callBackView);
    }

    /**
     * 用户是否购买此科目
     *
     * @param courseid
     * @param callBackView
     */
    public void requestIsBought(String courseid, StringCallBackView callBackView) {
        UserInfomVo login = isLogin(mContext);
        if (login == null) {
            return;
        }
        UserBean user = login.getData().getUser();
        ArrayList<GetParamVo> listParamVo = getListParamVo();
   /*     GetParamVo paramVo = getParamVo();
        paramVo.setParam("courseid");
        paramVo.setValue(courseid);
        listParamVo.add(paramVo);*/
        GetParamVo paramVo2 = getParamVo();
        paramVo2.setParam("staffid");
        paramVo2.setValue(String.valueOf(user.getId()));
        listParamVo.add(paramVo2);
        String url = getUrl(mContext, R.string.http_isbought);
        requestHttpServiceGet(mContext, url, listParamVo, true, callBackView);

    }

    /**
     * 根据 tagid 获取下面所有题号
     *
     * @param courseid     科目编号
     * @param tagid        标签编号
     * @param callBackView
     */
    public void requestUestionIdSbyTagid(String courseid, String tagid, StringCallBackView callBackView) {
        ArrayList<GetParamVo> listParamVo = getListParamVo();
        GetParamVo paramVo = getParamVo();
        paramVo.setParam("courseid");
        paramVo.setValue(courseid);
        listParamVo.add(paramVo);
        GetParamVo paramVo1 = getParamVo();
        paramVo1.setParam("tagid");
        paramVo1.setValue(tagid);
        listParamVo.add(paramVo1);
        String url = getUrl(mContext, R.string.http_questionidsbytagid);
        requestHttpServiceGet(mContext, url, listParamVo, true, callBackView);

    }

    /**
     * 获取模拟考试试卷
     *
     * @param courseid     科目编号
     * @param callBackView
     */
    public void requestExamchapte(String courseid, StringCallBackView callBackView) {
        UserInfomVo login = isLogin(mContext);
        if (login == null) {
            return;
        }
        UserBean user = login.getData().getUser();
        ArrayList<GetParamVo> listParamVo = getListParamVo();
        GetParamVo paramVo = getParamVo();
        paramVo.setParam("courseid");
        paramVo.setValue(courseid);
        listParamVo.add(paramVo);
        GetParamVo paramVo2 = getParamVo();
        paramVo2.setParam("staffid");
        paramVo2.setValue(String.valueOf(user.getId()));
        listParamVo.add(paramVo2);
        String url = getUrl(mContext, R.string.http_examchapter);
        requestHttpServiceGet(mContext, url, listParamVo, true, callBackView);

    }

    /**
     * 提交收藏
     *
     * @param isfav        true 收藏 false 取消
     * @param targetid     练习题编号
     * @param callBackView
     */
    public void subimtfavpost(String targetid, boolean isfav, StringCallBackView callBackView) {
        UserInfomVo login = isLogin(mContext);
        if (login == null) {
            return;
        }
        UserBean user = login.getData().getUser();
        JSONObject obj = getJsonObj();
        try {
            obj.put("staffid", user.getId());
            obj.put("targetid", targetid);
            obj.put("isfav", isfav);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        String url = getUrl(mContext, R.string.http_favpost);
        requestHttpServciePost(mContext, url, obj, true, callBackView);
    }


    /**
     * 提交收藏
     *
     * @param targetid     练习题编号
     * @param callBackView
     */
    public void subimtDelectQuestionPost(String targetid, StringCallBackView callBackView) {
        UserInfomVo login = isLogin(mContext);
        if (login == null) {
            return;
        }
        UserBean user = login.getData().getUser();
        JSONObject obj = getJsonObj();
        try {
            obj.put("staffid", user.getId());
            obj.put("targetid", targetid);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        String url = getUrl(mContext, R.string.http_questionremoveerrsetpost);
        requestHttpServciePost(mContext, url, obj, true, callBackView);
    }


    /**
     * 提交做题记录
     *
     * @param isRight      true 对 false 错
     * @param targetid     练习题编号
     * @param callBackView
     */
    public void subimtQuestionHispost(String targetid, boolean isRight, StringCallBackView callBackView) {
        UserInfomVo login = isLogin(mContext);
        if (login == null) {
            return;
        }
        UserBean user = login.getData().getUser();
        JSONObject obj = getJsonObj();
        try {
            obj.put("staffid", user.getId());
            obj.put("targetid", targetid);
            obj.put("isright", isRight);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        String url = getUrl(mContext, R.string.http_questionhispost);
        requestHttpServciePost(mContext, url, obj, true, callBackView);
    }

    /**
     * 获取网课产品列表
     * @param callBackView
     */
    public void requestBankProduct( StringCallBackView callBackView) {
        String url = getUrl(mContext, R.string.http_questionbankproduct);
        requestHttpServiceGet(mContext, url, null, true, callBackView);

    }
}
