package cn.xuzilin.core.shiro.filters;

import org.apache.shiro.web.filter.AccessControlFilter;
import org.apache.shiro.web.util.WebUtils;
import cn.xuzilin.common.consts.ResponseStatus;
import cn.xuzilin.common.utils.LoggerUtil;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by 96428 on 2017/9/7.
 * This in weixin-edu, com.outstudio.weixin.core.shiro.filters
 * 禁止用户访问的界面
 */
public class ForbidFilter extends AccessControlFilter {
    @Override
    protected boolean isAccessAllowed(ServletRequest servletRequest, ServletResponse servletResponse, Object o) throws Exception {
        return false;
    }

    @Override
    protected boolean onAccessDenied(ServletRequest servletRequest, ServletResponse servletResponse) throws Exception {
        if (ShiroFilterUtil.isAjax(servletRequest)) {
            Map<Object, Object> map = new HashMap<>();
            map.put("status", 500);
            map.put("message", "该地址无法被访问!");
            ShiroFilterUtil.writeJsonToResponse(servletResponse, map);
            return false;
        }

//        LoggerUtil.fmtDebug(getClass(), "重定向到 : /open/back/error");
//        WebUtils.issueRedirect(servletRequest, servletResponse, "/open/back/error");
//        LoggerUtil.fmtDebug(getClass(), "重定向到 : /page/error");
        WebUtils.issueRedirect(servletRequest, servletResponse, " /page/error");
        return false;
    }
}
