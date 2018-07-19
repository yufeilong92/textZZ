package com.xuechuan.xcedu.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.widget.ScrollView;

import com.xuechuan.xcedu.R;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: MyApplication2
 * @Package com.xuechuan.myapplication
 * @Description: todo
 * @author: L-BackPacker
 * @date: 2018/6/4 17:20
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018
 */
public class ScreenShot {
    /**
     * 截取scrollview的屏幕
     * @param scrollView
     * @return
     */
    public static Bitmap getBitmapByView(Context context, ScrollView scrollView) {
        int h = 0;
        Bitmap bitmap = null;
        // 获取scrollview实际高度
        for (int i = 0; i < scrollView.getChildCount(); i++) {
            h += scrollView.getChildAt(i).getHeight();
            scrollView.getChildAt(i).setBackgroundColor(
                    Color.parseColor("#ffffff"));
        }
        // 创建对应大小的bitmap
        bitmap = Bitmap.createBitmap(scrollView.getWidth(), h,
                Bitmap.Config.RGB_565);
        final Canvas canvas = new Canvas(bitmap);
        scrollView.draw(canvas);
        //要拼接的图片
        Bitmap bitmap1 = BitmapFactory.decodeResource(context.getResources(), R.drawable.qbank_shareimg);
        //拼后图片
        Bitmap bitmap2 = ImgerSplit.add2Bitmap(bitmap, bitmap1);
        //压缩
        Bitmap bitmap4 = compressImage(bitmap2);
        return bitmap4;
    }

    /**
     * 压缩图片
     * @param image
     * @return
     */
    public static Bitmap compressImage(Bitmap image) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        // 质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
        image.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        int options = 50;
        // 循环判断如果压缩后图片是否大于100kb,大于继续压缩
        while (baos.toByteArray().length / 1024 > 100&&options>=0) {
            // 重置baos
            baos.reset();
            // 这里压缩options%，把压缩后的数据存放到baos中
            image.compress(Bitmap.CompressFormat.JPEG, options, baos);
            // 每次都减少10
            options -= 10;
        }
        // 把压缩后的数据baos存放到ByteArrayInputStream中
        ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());
        // 把ByteArrayInputStream数据生成图片
        Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, null);
        return bitmap;
    }

    /**
     * 保存到sdcard
     * @param b
     * @return
     */
    public static String savePic(Bitmap b) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss",
                Locale.US);
        String dbDir = android.os.Environment.getExternalStorageDirectory().toString();
        dbDir += "/xuechuan";
        dbDir += "/imgage";//数据库所在目录
        String dbPath = dbDir;//数据库路径
        //判断目录是否存在，不存在则创建该目录
//        File dirFile = new File(dbDir);
        File dbFile = new File(dbPath);
        if (!dbFile.exists())
            dbFile.mkdirs();
        //数据库文件是否创建成功
        boolean isFileCreateSuccess = false;
        //判断文件是否存在，不存在则创建该文件
        if (!dbFile.exists()) {
            try {
                isFileCreateSuccess=dbFile.createNewFile();//创建文件
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        File outfile=null;
        if (isFileCreateSuccess){
            outfile=dbFile;
        }else {
            outfile = new File("/sdcard/image");
        }
        // 如果文件不存在，则创建一个新文件
        if (!outfile.isDirectory()) {
            try {
                outfile.mkdir();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        String fname = outfile + "/" + sdf.format(new Date()) + ".png";
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(fname);
            if (null != fos) {
                b.compress(Bitmap.CompressFormat.PNG, 90, fos);
                fos.flush();
                fos.close();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return fname;
    }

}
