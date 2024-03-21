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
        // 判断是否是放行请求
        String url = request.getURI().getPath();
        if (url.contains("/user/")) {
            if (url.contains("/account/login") ||
                    url.contains("/account/register") ||
                    url.contains("/account/activate") ||
                    url.contains("/account/forgetPassword") ||
                    url.contains("/account/resetPassword")
            ) {
                // 放行
                return chain.filter(exchange);
            }
        } else if (url.contains("/music/")) {
            if (url.contains("/indexList/")
            ) {
                // 放行
                return chain.filter(exchange);
            }
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
            // 有效
            case -1:
            case 0:
                // 获取 userId
                Object userId = claims.get("id");
                // 存储到 header 中
                ServerHttpRequest serverHttpRequest = request.mutate().headers(httpHeaders -> {
                    httpHeaders.add("userId", userId + "");
                }).build();
                // 重置请求
                exchange.mutate().request(serverHttpRequest);
                break;
            // 过期
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
