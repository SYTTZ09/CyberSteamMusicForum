package com.syt.util;

import io.jsonwebtoken.*;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.*;

public class JwtUtil {
    // token 有效期
    // 毫秒 秒 分 时 天
    private static final int TOKEN_TIME_OUT = 1000 * 60 * 60 * 12 * 15;
    // 密钥
    private static final String TOKEN_ENCRYPT_KEY = "09848939HR9FWF985704830G35GH934FJ9483T4389F4J3GH";
    // 刷新间隔
    private static final int REFRESH_TIME = 1000 * 60 * 60 * 12;

    /**
     * 生成 JWT
     *
     * @param id
     * @return
     */
    public static String getJwt(Integer id) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("id", id);
        long currentTime = System.currentTimeMillis();
        return Jwts.builder()
                .setId(UUID.randomUUID().toString())
                .setIssuedAt(new Date(currentTime)) // 签发时间
                .setSubject("user_auth") // 说明
                .setIssuer("syt") // 签发者
                .setAudience("user") // 接收者
                .compressWith(CompressionCodecs.GZIP) // 压缩方式
                .signWith(SignatureAlgorithm.HS512, generalKey()) // 加密方式
                .setExpiration((new Date(currentTime + TOKEN_TIME_OUT))) // 过期时间
                .addClaims(claims) // 负载
                .compact();
    }

    /**
     * 由字符串生成加密key
     *
     * @return
     */
    public static SecretKey generalKey() {
        byte[] encodedKey = Base64.getEncoder().encode(TOKEN_ENCRYPT_KEY.getBytes());
        SecretKey key = new SecretKeySpec(encodedKey, 0, encodedKey.length, "AES");
        return key;
    }


    /**
     * 获取token中的claims信息
     *
     * @param token
     * @return
     */
    private static Jws<Claims> getJws(String token) {
        return Jwts.parser()
                .setSigningKey(generalKey())
                .parseClaimsJws(token);
    }

    /**
     * 获取payload body信息
     *
     * @param token
     * @return
     */
    public static Claims getClaims(String token) {
        try {
            return getJws(token).getBody();
        }catch (ExpiredJwtException e){
            return null;
        }
    }

    /**
     * 获取hearder body信息
     *
     * @param token
     * @return
     */
    public static JwsHeader getHeader(String token) {
        return getJws(token).getHeader();
    }

    /**
     * 是否过期
     *
     * @param claims
     * @return -1 有效   0 有效   1 过期   2 过期
     */
    public static int verify(Claims claims) {
        if(claims == null){
            return 1;
        }
        try {
            claims.getExpiration()
                    .before(new Date());
            // 需要自动刷新 TOKEN
            if((claims.getExpiration().getTime() - System.currentTimeMillis()) > REFRESH_TIME){
                return -1;
            }else {
                return 0;
            }
        } catch (ExpiredJwtException ex) {
            return 1;
        }catch (Exception e){
            return 2;
        }
    }
}
