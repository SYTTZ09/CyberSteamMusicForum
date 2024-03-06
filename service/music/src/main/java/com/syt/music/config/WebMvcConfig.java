package com.syt.music.config;

import com.syt.music.filter.MusicFilter;
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
                registry.addInterceptor(new MusicFilter());
        List<String> interceptPathList = new ArrayList<String>() {{
            add("/**");
        }};
        interceptorRegistration.addPathPatterns(interceptPathList);
    }
}
