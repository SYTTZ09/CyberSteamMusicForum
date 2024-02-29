package com.syt.gateway.app.filter;

import com.syt.util.JwtUtil;
import io.jsonwebtoken.Claims;
import org.apache.commons.lang.StringUtils;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
public class AuthorizeFilter implements Ordered, GlobalFilter {
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        // 获取 request response
        ServerHttpRequest request = exchange.getRequest();
        ServerHttpResponse response = exchange.getResponse();
        // 判断是否是登录请求
        if (request.getURI().getPath().contains("/account/login") ||
                request.getURI().getPath().contains("/account/register") ||
                request.getURI().getPath().contains("/account/resetPassword") ||
                request.getURI().getPath().contains("/account/activate")
        ) {
            // 放行
            return chain.filter(exchange);
        }
        // 获取 token
        String token = request.getHeaders().getFirst("token");
        // token 是否存在
        if (StringUtils.isBlank(token)) {
            response.setStatusCode(HttpStatus.UNAUTHORIZED);
            return response.setComplete();
        }
        // token 是否有效
        Claims claims = JwtUtil.getClaims(token);
        int status = JwtUtil.verify(claims);
        switch (status) {
            case -1:
            case 0:
                break;
            case 1:
            case 2:
                response.setStatusCode(HttpStatus.UNAUTHORIZED);
                return response.setComplete();
        }
        // 放行
        return chain.filter(exchange);
    }

    /**
     * 优先级设置 值越小 优先级越高
     *
     * @return
     */
    @Override
    public int getOrder() {
        return 0;
    }
}
