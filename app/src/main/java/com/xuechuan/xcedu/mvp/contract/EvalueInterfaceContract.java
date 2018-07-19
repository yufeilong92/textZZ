package com.xuechuan.xcedu.mvp.contract;

import android.content.Context;

import com.xuechuan.xcedu.mvp.view.RequestResulteView;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: xcedu
 * @Package com.xuechuan.xcedu.mvp.contract
 * @Description: todo
 * @author: L-BackPacker
 * @date: 2018/6/1 20:21
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018
 */
public interface EvalueInterfaceContract {
    interface Model {
        public void SubmitContent(Context context, String targetid, String comment, String commentid,String usetype, RequestResulteView view);
        public void requestEvalueTwo(Context context,int page, String commentid, String type, RequestResulteView view);
    }

    interface View {
        public void EvalueTwoSuc(String com);
        public void EvalueTwoErro(String com);
        public void EvalueTwoSucMore(String com);
        public void EvalueTwoErroMore(String com);
        public void submitEvalueSuccess(String con);
        public void submitEvalueError(String con);

    }

    interface Presenter {
        public void initModelView(Model model,View view);
        public void SubmitContent(Context context, String targetid, String comment, String commentid,String usetype);
        public void requestEvalueTwo(Context context,int page, String commentid, String type);
        public void requestEvalueTwoMore(Context context,int page, String commentid, String type);
    }
}
