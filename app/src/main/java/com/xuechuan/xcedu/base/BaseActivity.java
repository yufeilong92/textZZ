package com.xuechuan.xcedu.base;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.lzy.okgo.OkGo;
import com.xuechuan.xcedu.R;
import com.xuechuan.xcedu.XceuAppliciton.MyAppliction;
import com.xuechuan.xcedu.ui.AgreementActivity;
import com.xuechuan.xcedu.ui.InfomDetailActivity;
import com.xuechuan.xcedu.ui.LoginActivity;
import com.xuechuan.xcedu.utils.StringUtil;
import com.xuechuan.xcedu.vo.UserInfomVo;

import java.util.Timer;
import java.util.TimerTask;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: xcedu
 * @Package com.xuechuan.xcedu.base
 * @Description: 基类
 * @author: L-BackPacker
 * @date: 2018/4/10 10:08
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018
 */
public abstract class BaseActivity extends AppCompatActivity {
    /**
     * 传入参数-标题
     */
    public static final String CSTR_EXTRA_TITLE_STR = "title";
    private String mBaseTitle;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (this.getIntent() != null) {
            Uri data = this.getIntent().getData();
            if (data != null) {
                String title = data.getQueryParameter("title");
                String isarticle = data.getQueryParameter("isarticle");
                String url = data.getQueryParameter("url");
                String shareurl = data.getQueryParameter("shareurl");
                String articleid = data.getQueryParameter("articleid");
                Log.e("第三方调取===", "onCreate: " + title + "//"
                        + isarticle + "//" + url + "//" + shareurl + "//" + articleid);
                MyAppliction.getInstance().setIsAtricle(isarticle);
                MyAppliction.getInstance().setShareParems(url, articleid, title, shareurl);
                doShareActivity();
            }
        }
        if ((getIntent().getFlags() & Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT) != 0) {
            finish();
            return;
        }
        Intent intent = getIntent();
        mBaseTitle = intent.getStringExtra(CSTR_EXTRA_TITLE_STR);
        MyAppliction.getInstance().addActivity(this);
        initContentView(savedInstanceState);
        if (!StringUtil.isEmpty(mBaseTitle)) {
            setTitle(mBaseTitle);
        }

    }

    public void delectShare() {
        MyAppliction.getInstance().setIsAtricle(null);
        MyAppliction.getInstance().delectShareParems();
    }


    public static class ShareParems {
        public String title;
        public String url;
        public String shareurl;
        public String articleid;
    }

    private void doShareActivity() {
        if (StringUtil.isEmpty(MyAppliction.getInstance().getIsAtricle())) {
            return;
        }
        if (MyAppliction.getInstance().getIsAtricle().equals("0")) {
            doIntentAct(new Infom(), MyAppliction.getInstance().getShareParems());
            return;
        }
        if (MyAppliction.getInstance().getIsAtricle().equals("1")) {
            doIntentAct(new WenZhang(), MyAppliction.getInstance().getShareParems());
        }
    }

    protected abstract void initContentView(Bundle savedInstanceState);


    private void setTitle(String str) {
        TextView title = (TextView) findViewById(R.id.activity_title_text);
        title.setText(str);
    }

    public void onHomeClick(View view) {
        finish();
    }

    protected String getTextStr(View view) {
        if (view instanceof TextView) {
            TextView tv = (TextView) view;
            return tv.getText().toString().trim();
        }
        if (view instanceof Button) {
            Button btn = (Button) view;
            return btn.getText().toString().trim();
        }
        if (view instanceof EditText) {
            EditText et = (EditText) view;
            return et.getText().toString().trim();
        }
        return null;
    }

    protected String getStringWithId(int id) {
        return getResources().getString(id);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        OkGo.getInstance().cancelTag(this);
        MyAppliction.getInstance().finishActivity(this);
    }

    protected void finishAll() {
        MyAppliction.getInstance().exit();
    }


    public interface ShareActivity {
        public void startAct(ShareParems shareParems);
    }

    public class Infom implements ShareActivity {
        @Override
        public void startAct(ShareParems shareParems) {
            UserInfomVo infom = MyAppliction.getInstance().getUserInfom();
            if (infom == null) {
                Intent intent = new Intent(BaseActivity.this, LoginActivity.class);
                startActivity(intent);
                return;
            }
            delectShare();
            final Intent intent = AgreementActivity.newInstance(BaseActivity.this, shareParems.url, AgreementActivity.SHAREMARK,
                    shareParems.title, shareParems.shareurl);
//            startActivity(intent);
            Timer timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    startActivity(intent);
                }
            }, 300);
        }
    }

    public class WenZhang implements ShareActivity {
        @Override
        public void startAct(ShareParems shareParems) {
            UserInfomVo infom = MyAppliction.getInstance().getUserInfom();
            if (infom == null || infom.getData().getUser() == null) {
                Intent intent = new Intent(BaseActivity.this, LoginActivity.class);
                startActivity(intent);
                return;
            }
            delectShare();
            final Intent intent = InfomDetailActivity.startInstance(BaseActivity.this, shareParems.url,
                    String.valueOf(shareParems.articleid), shareParems.title);
//            startActivity(intent);
            Timer timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    startActivity(intent);
                }
            }, 300);
        }
    }


    public void doIntentAct(ShareActivity activity, ShareParems shareParems) {
        activity.startAct(shareParems);
    }
}
