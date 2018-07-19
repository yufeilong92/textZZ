package com.xuechuan.xcedu.service;

import android.app.IntentService;
import android.content.Intent;
import android.content.Context;
import android.text.TextUtils;

import com.lzy.okgo.model.Response;
import com.xuechuan.xcedu.net.HomeService;
import com.xuechuan.xcedu.net.view.StringCallBackView;
import com.xuechuan.xcedu.utils.FileReadUtil;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p>
 * TODO: Customize class - update intent actions, extra parameters and static
 * helper methods.
 */
public class UpdataLogSevice extends IntentService {

    private static final String ACTION_FOO = "com.xuechuan.xcedu.service.action.FOO";


    private static final String UPDATAID = "com.xuechuan.xcedu.service.extra.PARAM1";
    private static final String UPDATABAN = "com.xuechuan.xcedu.service.extra.PARAM2";
    private static final String UPDATAVIDEOID = "com.xuechuan.xcedu.service.extra.PARAM3";
    private static final String UPDATAREGITID= "com.xuechuan.xcedu.service.extra.PARAM4";

    public UpdataLogSevice() {
        super("MyIntentService");
    }

    public static void startActionFoo(Context context, String vid, String ban,String videoid,String regitid) {
        Intent intent = new Intent(context, UpdataLogSevice.class);
        intent.setAction(ACTION_FOO);
        intent.putExtra(UPDATAID, vid);
        intent.putExtra(UPDATABAN, ban);
        intent.putExtra(UPDATAVIDEOID, videoid);
        intent.putExtra(UPDATAREGITID, regitid);
        context.startService(intent);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_FOO.equals(action)) {
                final String vid = intent.getStringExtra(UPDATAID);
                final String banben = intent.getStringExtra(UPDATABAN);
                final String videoid = intent.getStringExtra(UPDATAVIDEOID);
                final String ragith = intent.getStringExtra(UPDATAREGITID);
                handleActionFoo(vid, banben,videoid,ragith);
            }
        }
    }

    private void handleActionFoo(String vid, String banben,String videoid, String ragith) {
        FileReadUtil util = FileReadUtil.getInstance(getBaseContext());
        String s = util.loadFromSdFile();
        if (TextUtils.isEmpty(s)) {
            stopSelf();
            return;
        }
        StringBuffer buffer = new StringBuffer();
        buffer.append("视频的id为" + vid);
        buffer.append("，" + banben);
        buffer.append("，视频本地id为" + videoid);
        buffer.append("，码率为" + ragith);
        buffer.append(s);
        HomeService service = new HomeService(getApplicationContext());
        service.updataLog(buffer.toString(), new StringCallBackView() {
            @Override
            public void onSuccess(Response<String> response) {
                stopSelf();
            }

            @Override
            public void onError(Response<String> response) {
                stopSelf();
            }
        });
    }


}
