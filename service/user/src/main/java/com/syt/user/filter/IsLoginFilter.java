package com.syt.user.filter;

import com.syt.util.thread.TokenThreadLocalUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class IsLoginFilter implements HandlerInterceptor {
    /**
     * 前置处理器
     * 获取 header 中的 userId，并存入当前线程
     * @param request
     * @param response
     * @param handler
     * @return
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = request.getHeader("token");
        if (StringUtils.isNotBlank(token)) {
            TokenThreadLocalUtil.setToken(token);
        }
        return true;
    }

    /**
     * 后置处理器
     * 清理线程中的数据
     * @param request
     * @param response
     * @param handler
     * @param modelAndView
     * @throws Exception
     */
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        TokenThreadLocalUtil.clear();
    }
}