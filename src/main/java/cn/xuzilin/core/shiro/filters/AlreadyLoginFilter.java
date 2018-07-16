package cn.xuzilin.core.shiro.filters;

import org.apache.shiro.web.filter.AccessControlFilter;
import org.apache.shiro.web.util.WebUtils;
import cn.xuzilin.common.po.UserEntity;
import cn.xuzilin.core.shiro.token.TokenManager;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

public class AlreadyLoginFilter extends AccessControlFilter {
    @Override
    protected boolean isAccessAllowed(ServletRequest servletRequest, ServletResponse servletResponse, Object o) throws Exception {
        UserEntity token = TokenManager.getUserToken();
        return token == null;
    }

    @Override
    protected boolean onAccessDenied(ServletRequest servletRequest, ServletResponse servletResponse) throws Exception {
        WebUtils.issueRedirect(servletRequest, servletResponse, "/back/index");
        return false;
    }
}
