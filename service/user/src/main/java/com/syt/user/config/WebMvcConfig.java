package com.syt.user.config;

import com.syt.user.filter.IsLoginFilter;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        InterceptorRegistration interceptorRegistration =
                registry.addInterceptor(new IsLoginFilter());
        List<String> interceptPathList = new ArrayList<String>() {{
            add("/isLogin");
        }};
        interceptorRegistration.addPathPatterns(interceptPathList);
    }
}
