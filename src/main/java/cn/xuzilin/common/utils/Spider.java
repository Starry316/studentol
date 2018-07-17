package cn.xuzilin.common.utils;


import cn.xuzilin.common.dto.Msg;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSession;
import java.io.IOException;
import java.util.Map;

/**
 * Created by me.kkz' on 2017/11/20.
 */
public class Spider {
    final private String userAgent="Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/60.0.3112.78 Safari/537.36";
    private Map<String,String> cookiesMap=null;

    public Msg getStudentInfo(String username, String password){
        String campus=null;
        String trueName=null;
        Msg msg=new Msg();
        password=new MD5Util().MD5(password);//密码MD5加密
        Connection.Response response=null;
        Connection con = Jsoup.connect("http://bkjwt.sdu.edu.cn/").header("User-Agent", userAgent);//访问登录页面
        try {
            /*跳过证书验证*/
/*
            trustAllHttpsCertificates();
            HttpsURLConnection.setDefaultHostnameVerifier(hv);
*/
            /*储存访问登录页面时获取的cookie*/
            response=con.execute();
            cookiesMap=response.cookies();
            /*尝试登录*/
            response=con.url("http://bkjwt.sdu.edu.cn/b/ajaxLogin")
                    .method(Connection.Method.POST)
                    .cookies(cookiesMap)
                    .ignoreContentType(true)
                    .data("j_username",username)
                    .data("j_password", password)
                    .timeout(0)
                    .execute();
            /*登录成功后获取个人信息*/
            response=con.url("http://bkjwt.sdu.edu.cn/b/grxx/xs/xjxx/detail")
                    .cookies(cookiesMap)
                    .timeout(0)
                    .method(Connection.Method.GET)
                    .execute();
            if(200!=response.statusCode()){
                msg.setCode(1);
                msg.setMsg("用户名或密码错误");
                return msg;
            }
            String var = response.body();


            //System.out.println("看这里！！！:"+var);
            trueName = var.substring(var.lastIndexOf("\"xm\"") + 6, var.indexOf("\"ywxm\"") - 2);
            campus = var.substring(var.lastIndexOf("\"xsjc\"")+8,var.indexOf("\"zym\"")-2);
            msg.setCode(0);
            msg.setMsg("登录成功");
            msg.setObj(new String[]{trueName,campus});
        }catch (Exception e){
            e.printStackTrace();
            msg.err("系统繁忙,请稍后重试");
        }finally {
            return msg;
        }
    }

    public Msg getTeacherInfo(String username,String password){
        Msg msg=new Msg();
        String lt=null;
        String trueName=null;
        Document document=null;
        Map<String,String> cookies=null;
        Connection.Response response=null;
        Connection conn= Jsoup.connect("https://cas.sdu.edu.cn/lyuapServer/login")
                .header("User-Agent",userAgent)
                .followRedirects(true)
                .method(Connection.Method.GET)
                .timeout(0);
        try {
            response=conn.execute();
            cookies=response.cookies();
            lt=conn.get().select("input[name=\"lt\"]").attr("value");
            System.out.println("lt: "+lt);
            response=conn.url("https://cas.sdu.edu.cn/lyuapServer/login")
                    .method(Connection.Method.POST)
                    .cookies(cookies)
                    .data("username",username)
                    .data("password",password)
                    .data("j_captcha_response","1234")
                    .data("lt",lt)
                    .data("_eventId","submit")
                    .data("submit","登录")
                    .timeout(0)
                    .execute();
            cookies.putAll(response.cookies());
            /*登陆失败时*/
            if (Integer.valueOf(response.header("Content-Length"))<7000) {
                msg.setCode(1);
                msg.setMsg("用户名或密码错误");
                return msg;
            }
            document=conn.url("http://www.oa.sdu.edu.cn/admin/index.do").cookies(cookies).get();
        } catch (IOException e) {
            e.printStackTrace();
            msg.err("系统繁忙,请稍后重试");
        }
        trueName=document.select("div[class=\"k-header\"]").toString();
        trueName=trueName.substring(trueName.indexOf(":&nbsp")+7,trueName.indexOf("&nbsp; 身份"));
        msg.setCode(0);
        msg.setMsg("登录成功");
        msg.setObj(trueName);
        return msg;
    }

    /*以下为忽略证书认证的设置*/
    static HostnameVerifier hv = new HostnameVerifier() {
        public boolean verify(String urlHostName, SSLSession session) {
            System.out.println("Warning: URL Host: " + urlHostName + " vs. "
                    + session.getPeerHost());
            return true;
        }
    };

    private static void trustAllHttpsCertificates() throws Exception {
        javax.net.ssl.TrustManager[] trustAllCerts = new javax.net.ssl.TrustManager[1];
        javax.net.ssl.TrustManager tm = new miTM();
        trustAllCerts[0] = tm;
        javax.net.ssl.SSLContext sc = javax.net.ssl.SSLContext
                .getInstance("SSL");
        sc.init(null, trustAllCerts, null);
        HttpsURLConnection.setDefaultSSLSocketFactory(sc
                .getSocketFactory());
    }

    static class miTM implements javax.net.ssl.TrustManager,
            javax.net.ssl.X509TrustManager {
        public java.security.cert.X509Certificate[] getAcceptedIssuers() {
            return null;
        }

        public boolean isServerTrusted(
                java.security.cert.X509Certificate[] certs) {
            return true;
        }

        public boolean isClientTrusted(
                java.security.cert.X509Certificate[] certs) {
            return true;
        }

        public void checkServerTrusted(
                java.security.cert.X509Certificate[] certs, String authType)
                throws java.security.cert.CertificateException {
            return;
        }

        public void checkClientTrusted(
                java.security.cert.X509Certificate[] certs, String authType)
                throws java.security.cert.CertificateException {
            return;
        }
    }
}
