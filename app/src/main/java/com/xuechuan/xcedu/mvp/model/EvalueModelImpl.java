package com.xuechuan.xcedu.mvp.model;

import android.content.Context;

import com.lzy.okgo.model.Response;
import com.xuechuan.xcedu.mvp.view.RequestResulteView;
import com.xuechuan.xcedu.net.BankService;
import com.xuechuan.xcedu.net.CurrencyService;
import com.xuechuan.xcedu.net.view.StringCallBackView;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: xcedu
 * @Package com.xuechuan.xcedu.mvp.model
 * @Description: todo
 * @author: L-BackPacker
 * @date: 2018/5/3 15:53
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018
 */
public class EvalueModelImpl implements EvalueModel {

    @Override
    public void SubmitContent(Context context, String targetid, String comment, String commentid, String usetype, final RequestResulteView view) {
        CurrencyService service = new CurrencyService(context);
        service.submitConmment(targetid, comment, commentid, usetype, new StringCallBackView() {
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

    @Override
    public void reqeustTwoEvalueContent(Context context,int page, String questionid, String commentid, final RequestResulteView view) {
        BankService bankService = new BankService(context);
        bankService.requestCommentComment(page,questionid, commentid, new StringCallBackView() {
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

    @Override
    public void reqeustOneEvalueContent(Context context, int page, String questionid, final RequestResulteView view) {
        BankService service = new BankService(context);
        service.reqiestQuestionCmment(questionid, page, new StringCallBackView() {
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
