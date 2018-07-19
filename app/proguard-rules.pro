# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile
# copyright zhonghanwen
# copyright zhonghanwen

##-------------------------------------------基本不用动区域--------------------------------------------
##---------------------------------基本指令区----------------------------------
#-optimizationpasses 5
#-dontusemixedcaseclassnames
#-dontskipnonpubliclibraryclasses
#-dontpreverify
#-verbose
#-dontwarn
#-dontskipnonpubliclibraryclassmembers
#-ignorewarnings
#-optimizations !code/simplification/arithmetic,!field/*,!class/merging/*
#
#
# -keepattributes Exceptions,InnerClasses,Signature,Deprecated,SourceFile,LineNumberTable,*Annotation*,EnclosingMethod
#
## 保持 native 方法不被混淆
#-keepclasseswithmembernames class * {
#    native <methods>;
#}
#-keepclasseswithmembers class * {
#    public <init>(android.content.Context, android.util.AttributeSet);
#}
#-keepclasseswithmembers class * {
#    public <init>(android.content.Context, android.util.AttributeSet, int);
#}
#
#
## 泛型与反射
#-keepattributes Signature
#-keepattributes EnclosingMethod
#-keepattributes *Annotation*
#
##四大组件ProGuard配置代码
#-keep public class * extends android.app.Activity
#-keep public class * extends android.app.Application
#-keep public class * extends android.app.Service
#-keep public class * extends android.content.BroadcastReceiver
#-keep public class * extends android.content.ContentProvider
#-keep public class * extends android.app.backup.BackupAgentHelper
#-keep public class * extends android.preference.Preference
#-keep public class com.android.vending.licensing.ILicensingService
#-keep public class * extends android.os.IInterface
#
##针对support4包的配置
#-dontwarn android.support.**
#-dontwarn android.support.v4.**
#-keep class android.support.v4.** { *; }
#-keep public class * extends android.support.v4.**
#-keep public class * extends android.support.v4.app.Fragment
#
#-dontwarn android.support.**
#-dontwarn android.support.v7.**
#-keep class android.support.v7.** { *; }
#-keep public class * extends android.support.v7.**
#-keep public class * extends android.support.v7.app.Fragment
#
##针对gson的配置
#-keep class com.google.gson.** {*;}
##-keep class com.google.**{*;}
#-keep class sun.misc.Unsafe { *; }
#-keep class com.google.gson.stream.** { *; }
#-keep class com.google.gson.examples.android.model.** { *; }
#-keep class com.google.** {
#    <fields>;
#    <methods>;
#}
#-keepclassmembers class * implements java.io.Serializable {
#    static final long serialVersionUID;
#    private static final java.io.ObjectStreamField[] serialPersistentFields;
#    private void writeObject(java.io.ObjectOutputStream);
#    private void readObject(java.io.ObjectInputStream);
#    java.lang.Object writeReplace();
#    java.lang.Object readResolve();
#}
#-dontwarn com.google.gson.**
#
##针对greenDao数据库的配置
#-keep class de.greenrobot.dao.** {*;}
##保持greenDao的方法不被混淆 用来保持生成的表名不被混淆
#-keepclassmembers class * extends de.greenrobot.dao.AbstractDao {
# public static java.lang.String TABLENAME;
#}
# -keep class **$Properties
#
## 保持 Parcelable 不被混淆
#-keep class * implements android.os.Parcelable {
#  public static final android.os.Parcelable$Creator *;
#}
## 保持自定义控件类不被混淆
#-keepclasseswithmembers class * {
#    public <init>(android.content.Context,android.util.AttributeSet);
#}
## 保持自定义控件类不被混淆
#-keepclasseswithmembers class * {
#    public <init>(android.content.Context,android.util.AttributeSet,int);
#}
## 保持自定义控件类不被混淆
#-keepclassmembers class * extends android.app.Activity {
#    public void *(android.view.View);
#}
#
##---------------------------------webview------------------------------------
#-keepclassmembers class fqcn.of.javascript.interface.for.Webview {
#   public *;
#}
#-keepclassmembers class * extends android.webkit.WebViewClient {
#    public void *(android.webkit.WebView, java.lang.String, android.graphics.Bitmap);
#    public boolean *(android.webkit.WebView, java.lang.String);
#}
#-keepclassmembers class * extends android.webkit.WebViewClient {
#    public void *(android.webkit.WebView, jav.lang.String);
#}
##---------------------------------实体类---------------------------------
##修改成你对应的包名
#-keep class [com.xuechuan.xcedu].** { *; }
#
#
##这个主要是在layout 中写的onclick方法android:onclick="onClick"，不进行混淆
#-keepclassmembers class * extends android.app.Activity {
#   public void *(android.view.View);
#}
#-keepclassmembers class * implements java.io.Serializable {
#    static final long serialVersionUID;
#    private static final java.io.ObjectStreamField[] serialPersistentFields;
#    private void writeObject(java.io.ObjectOutputStream);
#    private void readObject(java.io.ObjectInputStream);
#    java.lang.Object writeReplace();
#    java.lang.Object readResolve();
#}
##// natvie 方法不混淆
#-keepclasseswithmembernames class * {
#    native <methods>;
#}
#-keep class **.R$* {
# *;
#}
#
#-keepclassmembers class * {
#    void *(*Event);
#}
#
##---------------------------------第三方包-------------------------------
#
##支付宝支付
#-keep class com.alipay.android.app.IAlixPay{*;}
#-keep class com.alipay.android.app.IAlixPay$Stub{*;}
#-keep class com.alipay.android.app.IRemoteServiceCallback{*;}
#-keep class com.alipay.android.app.IRemoteServiceCallback$Stub{*;}
#-keep class com.alipay.sdk.app.PayTask{ public *;}
#-keep class com.alipay.sdk.app.AuthTask{ public *;}
#-keep public class * extends android.os.IInterface
##微信支付
#-keep class com.tencent.mm.sdk.openapi.WXMediaMessage {*;}
#-keep class com.tencent.mm.sdk.openapi.** implements com.tencent.mm.sdk.openapi.WXMediaMessage$IMediaObject {*;}
#-keep class com.tencent.wxop.** { *; }
#-dontwarn com.tencent.mm.**
#-keep class com.tencent.mm.**{*;}
#
#-keep class sun.misc.Unsafe { *; }
#
#-keep class com.taobao.** {*;}
#-keep class com.alibaba.** {*;}
#-keep class com.alipay.** {*;}
#-dontwarn com.taobao.**
#-dontwarn com.alibaba.**
#-dontwarn com.alipay.**
#
#-keep class com.ut.** {*;}
#-dontwarn com.ut.**
#
#-keep class com.ta.** {*;}
#-dontwarn com.ta.**
#
#-keep class anet.**{*;}
#-keep class org.android.spdy.**{*;}
#-keep class org.android.agoo.**{*;}
#-dontwarn anet.**
#-dontwarn org.android.spdy.**
#-dontwarn org.android.agoo.**
#
#-keepclasseswithmembernames class com.xiaomi.**{*;}
#-keep public class * extends com.xiaomi.mipush.sdk.PushMessageReceiver
#
#-dontwarn com.xiaomi.push.service.b
#
#-keep class org.apache.http.**
#-keep interface org.apache.http.**
#-dontwarn org.apache.**
###百度定位
#-keep class com.baidu.** {*;}
#-keep class vi.com.** {*;}
#-dontwarn com.baidu.**
#
### okhttp
#-dontwarn com.squareup.okhttp.**
#-keep class com.squareup.okhttp.{*;}
#
## ========= 友盟 =================
#-dontshrink
#-dontoptimize
#-dontwarn com.google.android.maps.**
#-dontwarn android.webkit.WebView
#-dontwarn com.umeng.**
#-dontwarn com.tencent.weibo.sdk.**
#-dontwarn com.facebook.**
#
#
#-keep enum com.facebook.**
#-keepattributes Exceptions,InnerClasses,Signature
#-keepattributes *Annotation*
#-keepattributes SourceFile,LineNumberTable
#
#-keep public interface com.facebook.**
#-keep public interface com.tencent.**
#-keep public interface com.umeng.socialize.**
#-keep public interface com.umeng.socialize.sensor.**
#-keep public interface com.umeng.scrshot.**
#
#-keep public class com.umeng.socialize.* {*;}
#-keep public class javax.**
#-keep public class android.webkit.**
#
#-keep class com.facebook.**
#-keep class com.umeng.scrshot.**
#-keep public class com.tencent.** {*;}
#-keep class com.umeng.socialize.sensor.**
#
#-keep class com.tencent.mm.sdk.modelmsg.WXMediaMessage {*;}
#
#-keep class com.tencent.mm.sdk.modelmsg.** implements com.tencent.mm.sdk.modelmsg.WXMediaMessage$IMediaObject {*;}
#
#-keep class im.yixin.sdk.api.YXMessage {*;}
#-keep class im.yixin.sdk.api.** implements im.yixin.sdk.api.YXMessage$YXMessageData{*;}
##下面中括号的地方需要要填你的包名
#-keep public class [com.xuechuan.xcedu].R$*{
#    public static final int *;
#}
#-keepclassmembers class * {
#   public <init> (org.json.JSONObject);
#}
#-keepclassmembers enum * {
#    public static **[] values();
#    public static ** valueOf(java.lang.String);
#}
#
##友盟自动更新
#-keep public class com.umeng.fb.ui.ThreadView {
#}
#-keep public class * extends com.umeng.**
## 以下包不进行过滤
#-keep class com.umeng.** { *; }
#
##eventbus 3.0
#-keepattributes *Annotation*
#-keepclassmembers class ** {
#    @org.greenrobot.eventbus.Subscribe <methods>;
#}
#-keep enum org.greenrobot.eventbus.ThreadMode { *; }
#-keepclassmembers class * extends org.greenrobot.eventbus.util.ThrowableFailureEvent {
#    <init>(java.lang.Throwable);
#}
##EventBus
#-keepclassmembers class ** {
#    public void onEvent*(**);
#}
#
## support design
#-dontwarn android.support.design.**
#-keep class android.support.design.** { *; }
#-keep interface android.support.design.** { *; }
#-keep public class android.support.design.R$* { *; }
#
##jpush极光推送
#-dontwarn cn.jpush.**
#-keep class cn.jpush.** { *; }
##fastJson
#-dontwarn com.alibaba.fastjson.**
#-keep class com.alibaba.fastjson.** { *; }
##=======项目=========
#-keep class com.xuechuan.xcedu.adapter.** {*;}
#-keep class com.xuechuan.xcedu.base.** {*;}
##-keep class com.xuechuan.xcedu.ui.** {*;}
##-keep class com.xuechuan.xcedu.event.**{*;}
#-keep class com.xuechuan.xcedu.fragment.**{*;}
#-keep class com.xuechuan.xcedu.jg.**{*;}
#-keep class com.xuechuan.xcedu.mvp.**{*;}
#-keep class com.xuechuan.xcedu.weidgt.**{*;}
#-keep class com.xuechuan.xcedu.utils.** {*;}
#-keep class com.xuechuan.xcedu.net.** {*;}
#-keep class com.xuechuan.xcedu.player.** {*;}
#-keep class com.xuechuan.xcedu.vo.** {*;}
#-keep class com.xuechuan.xcedu.** {*;}
#
#-keep class com.luck.picture.lib.** {*;}
#-keep class com.yalantis.ucrop.** {*;}
#-keep class com.vector.update_app.** {*;}
#
#-keep class  com.easefun.polyv.** {*;}
#-keep class com.chinanetcenter.wcs.**{*;}
#-keep class org.apache.http.**{*;}
#
##-keep com.easefun.polyvsdk.** {*;}
##-keep com.easefun.polyvsdk.module.abi.** {*;}
##-keep com.easefun.polyvsdk.** {*;}
##-keep com.easefun.polyvsdk.player.abi.** {*;}
##-keep com.easefun.polyvsdk.** {*;}
##-keep com.easefun.polyvsdk.ijk.** {*;}
##
##-keep class com.chinanetcenter.wcs.**{*;}
##-keep class org.apache.http.**{*;}
#
#
##-keep de.hdodenhof:circleimageview.** {*;}
##-keep com.youth.banner:banner.** {*;}
##-keep com.github.hackware1993:MagicIndicator.** {*;}
##-keep pub.devrel:easypermissions.** {*;}
##-keep com.github.addappcn:android-pickers.** {*;}
#
#
#
##请避免混淆Bugly，在Proguard混淆文件中增加以下配置：
#-dontwarn com.tencent.bugly.**
#-keep public class com.tencent.bugly.**{*;}
##==================================
##表示混淆时不使用大小写混合类名
#-dontusemixedcaseclassnames
##表示不跳过library中的非public的类
#-dontskipnonpubliclibraryclasses
##打印混淆的详细信息
#-verbose
#
## Optimization is turned off by default. Dex does not like code run
## through the ProGuard optimize and preverify steps (and performs some
## of these optimizations on its own).
#-dontoptimize
###表示不进行校验,这个校验作用 在java平台上的
#-dontpreverify
## Note that if you want to enable optimization, you cannot just
## include optimization flags in your own project configuration file;
## instead you will need to point to the
## "proguard-android-optimize.txt" file instead of this one from your
## project.properties file.
#
#-keepattributes *Annotation*
#-keep public class com.google.vending.licensing.ILicensingService
#-keep public class com.android.vending.licensing.ILicensingService
#
## For native methods, see http://proguard.sourceforge.net/manual/examples.html#native
#-keepclasseswithmembernames class * {
#    native <methods>;
#}
#
## keep setters in Views so that animations can still work.
## see http://proguard.sourceforge.net/manual/examples.html#beans
#-keepclassmembers public class * extends android.view.View {
#   void set*(***);
#   *** get*();
#}
#
## We want to keep methods in Activity that could be used in the XML attribute onClick
#-keepclassmembers class * extends android.app.Activity {
#   public void *(android.view.View);
#}
#
## For enumeration classes, see http://proguard.sourceforge.net/manual/examples.html#enumerations
#-keepclassmembers enum * {
#    public static **[] values();
#    public static ** valueOf(java.lang.String);
#}
#
#-keepclassmembers class * implements android.os.Parcelable {
#  public static final android.os.Parcelable$Creator CREATOR;
#}
#
#-keepclassmembers class **.R$* {
#    public static <fields>;
#}
#
## The support library contains references to newer platform versions.
## Don't warn about those in case this app is linking against an older
## platform version.  We know about them, and they are safe.
#-dontwarn android.support.**
#
## Understand the @Keep support annotation.
#-keep class android.support.annotation.Keep
#
#-keep @android.support.annotation.Keep class * {*;}
#
#-keepclasseswithmembers class * {
#    @android.support.annotation.Keep <methods>;
#}
#
#-keepclasseswithmembers class * {
#    @android.support.annotation.Keep <fields>;
#}
#
#-keepclasseswithmembers class * {
#    @android.support.annotation.Keep <init>(...);
#}
#
##忽略警告
#-ignorewarnings
##保证是独立的jar,没有任何项目引用,如果不写就会认为我们所有的代码是无用的,从而把所有的代码压缩掉,导出一个空的jar
#-dontshrink
##保护泛型
#-keepattributes Signature
#-keep class com.easefun.polyvsdk.**{*;}
#-keep class com.chinanetcenter.wcs.**{*;}
#-keep class org.apache.http.**{*;}
#-keep class tv.danmaku.ijk.media.**{*;}