package cn.xuzilin.core.shiro.token;

import com.alibaba.fastjson.JSON;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import cn.xuzilin.common.po.UserEntity;
import cn.xuzilin.common.utils.LoggerUtil;

/**
 * Created by Starry on 2018/7/15.
 */
public class TokenManager {

    public static Subject getSubject() {
        return SecurityUtils.getSubject();
    }

    public static Session getSession() {
        return getSubject().getSession();
    }


    public static UserEntity getUserToken(){
        return (UserEntity) getSubject().getPrincipal();
    }
    public static void login(UserEntity user){
        UserToken token = new UserToken();
        token.setAccount(user.getAccount());
        token.setPass(user.getPass());
        token.setRememberMe(false);
        getSubject().login(token);
    }
    public static void logout() {
        getSubject().logout();
    }


    public static void save(Object key, Object value) {
        getSession().setAttribute(key, value);
    }

    public static Object get(Object key) {
        return getSession().getAttribute(key);
    }

    public static Object getAndRemove(Object key) {
        Object ans = getSession().getAttribute(key);
        getSession().removeAttribute(key);
        return ans;
    }

    public static void remove(Object key) {
        getSession().removeAttribute(key);
    }
}
