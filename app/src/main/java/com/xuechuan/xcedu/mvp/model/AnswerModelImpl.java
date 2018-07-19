package com.xuechuan.xcedu.mvp.model;

import android.content.Context;

import com.lzy.okgo.model.Response;
import com.xuechuan.xcedu.mvp.view.RequestResulteView;
import com.xuechuan.xcedu.net.BankService;
import com.xuechuan.xcedu.net.view.StringCallBackView;
import com.xuechuan.xcedu.utils.L;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: xcedu
 * @Package com.xuechuan.xcedu.mvp.model
 * @Description: 答题卡
 * @author: L-BackPacker
 * @date: 2018/5/2 11:14
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018
 */
public class AnswerModelImpl implements AnswerModel {

    /**
     * 获取章节下所有练习题库题号
     *
     * @param context
     * @param id
     * @param view
     */
    @Override
    public void getTextContent(Context context, String id, final RequestResulteView view) {
        BankService service = new BankService(context);
        service.requestChapterQuestionids(id, new StringCallBackView() {
            @Override
            public void onSuccess(Response<String> response) {
                view.success(response.body().toString());
            }

            @Override
            public void onError(Response<String> response) {
                view.error(response.message());
            }
        });
    }

    /**
     * 题干详情
     *
     * @param context
     * @param id
     * @param view
     */
    @Override
    public void getTextDetailContent(Context context, String id, final RequestResulteView view) {
        BankService bankService = new BankService(context);
        bankService.requestDetail(id, new StringCallBackView() {
            @Override
            public void onSuccess(Response<String> response) {
                view.success(response.body().toString());
            }

            @Override
            public void onError(Response<String> response) {
                view.error(response.message());
            }
        });
    }

    /**
     * 提交收藏
     *
     * @param context
     * @param isFav
     * @param id
     * @param view
     */
    @Override
    public void SubmitCollectContent(Context context, boolean isFav, String id, final RequestResulteView view) {
        BankService service = new BankService(context);
        service.subimtfavpost(id, isFav, new StringCallBackView() {
            @Override
            public void onSuccess(Response<String> response) {
                view.success(response.body().toString());
            }

            @Override
            public void onError(Response<String> response) {
                view.error(response.message());
            }
        });
    }

    /**
     * 提交做题结果
     *
     * @param context
     * @param isRight
     * @param id
     * @param view
     */
    @Override
    public void SubmitDoResult(Context context, boolean isRight, String id, final RequestResulteView view) {
        BankService service = new BankService(context);
        service.subimtQuestionHispost(id, isRight, new StringCallBackView() {
            @Override
            public void onSuccess(Response<String> response) {
                view.success(response.body().toString());
            }

            @Override
            public void onError(Response<String> response) {
                view.success(response.message());
            }
        });
    }

    /***
     * 移除错误
     * @param context
     * @param targetid
     * @param view
     */
    @Override
    public void SubmitWoringQuestionDelect(Context context, String targetid, final RequestResulteView view) {
        BankService bankService = new BankService(context);
        bankService.subimtDelectQuestionPost(targetid, new StringCallBackView() {
            @Override
            public void onSuccess(Response<String> response) {
                view.success(response.body().toString());
            }

            @Override
            public void onError(Response<String> response) {
                view.error(response.message());
            }
        });
    }
}
