package com.syt.music.filter;

import com.syt.util.thread.UserIdThreadLocalUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class MusicFilter implements HandlerInterceptor {
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
        String userId = request.getHeader("userId");
        if (StringUtils.isNotBlank(userId)) {
            UserIdThreadLocalUtil.setUserId(Integer.valueOf(userId));
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
        UserIdThreadLocalUtil.clear();
    }
}
