package com.shy.auth.impl;

import com.shy.auth.TokenParseException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import com.shy.auth.AuthUser;
import com.shy.auth.BeanUtil;
import com.shy.auth.TokenParser;

import java.text.SimpleDateFormat;
import java.util.Locale;

/**
 * JWT方式解析token
 * @author shy
 * @date 2023-03-27
 */
public final class JwtTokenParser implements TokenParser {

    private String secret;

    private Claims claims;

    public JwtTokenParser(String secret) {
        if(secret == null){
            throw new IllegalArgumentException("secret 未配置");
        }
        this.secret = secret;
    }


    @Override
    public <T extends AuthUser> T parse(Class<T> clz, String token) throws TokenParseException {

        try {
            claims = Jwts.parser()
                    .setSigningKey(secret.getBytes())
                    .parseClaimsJws(token).getBody();

            Object uid = claims.get("uid");
            //提取出系统内的userId,后续映射的时候,字段类型就相同了
            claims.put("uid", Long.valueOf(uid.toString()));
            T t = BeanUtil.mapToBean(clz, claims);
            return t;
        } catch (Exception e) {
            e.printStackTrace();
            throw new TokenParseException(e);
        }

    }

    /**
     * 获取过期时间
     *
     * @return
     */
    public long getExpiration() {
        long tokenDate = getFormatDate(claims.getExpiration().toString());
        return tokenDate;
    }


    private long getFormatDate(String dateFormat) {
        try {
            SimpleDateFormat sdf1 = new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy", Locale.ENGLISH);

            return sdf1.parse(dateFormat).getTime() / 1000;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }
}
