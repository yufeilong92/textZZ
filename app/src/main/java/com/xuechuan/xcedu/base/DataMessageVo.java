package com.xuechuan.xcedu.base;

import android.Manifest;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: xcedu
 * @Package com.xuechuan.xcedu.base
 * @Description: todo
 * @author: L-BackPacker
 * @date: 2018/4/16 16:42
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018
 */
public class DataMessageVo {
    //微信
    public static final String APP_ID = "wx0c71e64b9e151c84";

    //接受微信登录广播
    public static final String WEI_LOGIN_ACTION = "com.weixinlogin.com";
    //用户换取access_token的code，仅在ErrCode为0时有效
    public static final String WEICODE = "code";
    //第三方程序发送时用来标识其请求的唯一性的标志，由第三方程序调用sendReq时传入，由微信终端回传，state字符串长度不能超过1K
    public static final String WEISTATE = "state";
    //用户表示
    public static final String STAFFID = "staffid";
    //时间撮
    public static final String TIMESTAMP = "timeStamp";
    //随机数
    public static final String NONCE = "nonce";
    //加密串
    public static final String SIGNATURE = "signature";
    //请求token广播
    public static final String ACITON = "com.xuechaun.action";
    //请求体
    public static final String HTTPAPPLICAITON = "application/json";
    //网络
    public static final String HTTP_AC = "ac";
    //版本（1.2.3）
    public static final String HTTP_VERSION_NAME = "version_name";
    //版本（123）
    public static final String HTTP_VERSION_CODE = "version_code";
    //手机平台（android/ios）
    public static final String HTTP_DEVICE_PLATFORM = "device_platform";
    //手机型号
    public static final String HTTP_DEVICE_TYPE = "device_type";
    //手机品牌
    public static final String HTTP_DEVICE_BRAND = "device_brand";
    //操作系统版本（8.0.0）
    public static final String HTTP_OS_VERSION = "os_version";
    //分辨率
    public static final String HTTP_RESOLUTION = "resolution";
    //dpi
    public static final String HTTP_DPI = "dpi";
    //定位信息
    public static final String LOCATIONACTION = "com.xuechaun.loaction";
    //默认请求记录数
    public static final int CINT_PANGE_SIZE = 10;
    //ac文章评论
    public static final String USERTYPEAC = "ac";
    //a文章
    public static  final String USERTYPEA = "a";
    //qc问题评论
    public static final String USERTYPEQC = "qc";
    //vc视频评论
    public static final String USERTYPEVC = "vc";
    //文章
    public static final String USERTYOEARTICLE = "article";
    //视频
    public static  final String USERTYOEVIDEO = "question video";
    //问题
    public static final String QUESTION = "question";
    //文章
    public static final String ARTICLE = "article";
    //视频
    public static final String VIDEO = "video";
    //案例
    public static final String MARKTYPECASE = "typecase";
    //技术
    public static final  String MARKTYPESKILL = "typeskill";
    //综合
    public static final String MARKTYPECOLLORT = "typecoloct";
    //文章标识
    public static final String MARKTYPEORDER = "typeorder";
    //需要的权限
    public static final String[] Persmission = {
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.CAMERA};
    //微信支付
    public static final int PAYTYPE_WEIXIN = 2;
    //支付包
    public static final int PAYTYPE_ZFB = 3;
    //余额
    public static final int PAYTYPE_YUER = 1;
    //兑换码
    public static final int PAYTYPE_DUHAUN = 5;
    //微信id
    public static final String WEIXINVID = "wx0c71e64b9e151c84";
    //取消订单
    public static final String CANCELORDER = "cancel";
    //删除订单
    public static final String DELETEORDER = "delete";
    //全部订单
    public static final String ORDERALL = "0";
    //未完成
    public static String ORDERAOB = "-1";
    //已完成
    public static final String ORDERACOM = "10";
    //友盟key
    public static final String YOUMENGKID = "5b067baaf43e483f20000093";
    //qq id
    public static final String QQAPP_ID = "1106864981";
    //qq key
    public static final  String QQAPP_KEY = "rthjDUujg74JKfsq";
    //WEIBO key
    public static final  String WEI_KEY= "1739335166";
    //WEIBO Servcie
    public static final String WEIKEY_SECRET = "8efea2ca3decb2c4173ae44b9942287e";
    //APP_SECRET
    public static final  String WEIXINAPP_SECRET = "19a5f96dc807032fcb7f9d2a289ec0a1";
    //协议网址
    public static final String AGREEMENT = "http://www.xuechuanedu.cn/agreement-policy/fuwuxieyi.html";
    //wifi
    public static final String WIFI="wifi";
    //移动
    public static final String MONET ="monet";
    //没网
    public static final String NONETWORK="nonetwork";
//        title,
//                articleid,
//                url,
//                shareurl,
//                isarticle
//
//
//        isarticle=1,文章
//        isarticle=0,资讯
    public static final String BUGLYAPPID="20b66083e6";
    public static final boolean BUGLYAPPID_UP=false;
    public static final String BUGLYAPPIDKEY="d959ecd6-9225-4689-afeb-0c8dff63fb3d";

    public static final String Boli="保利版本2.3.3";

}
