package com.nova.service.impl;

import com.nova.domain.model.LoginUserEntity;
import com.nova.service.TokenService;
import com.nova.common.constant.SysConstans;
import com.nova.common.util.IdGenerateUtil;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Service
public class TokenServiceImpl implements TokenService {

    private static Cache<String, LoginUserEntity> tokenCache = CacheBuilder.newBuilder()
            .expireAfterAccess(4, TimeUnit.HOURS).build();

    private String header = "token";

    private String secret = "jsmdhqissieiieodjsm";

    private int expireTime = 604800000;

    protected static final long MILLIS_SECOND = 1000;

    protected static final long MILLIS_MINUTE = 60 * MILLIS_SECOND;

    private static final Long MILLIS_MINUTE_TEN = 20 * 60 * 1000L;

    @Override
    public LoginUserEntity getLoginUser(HttpServletRequest request) {
        String token = getToken(request);
        if (StringUtils.isNotEmpty(token)) {
            Claims claims = parseToken(token);
            String userKey = (String) claims.get(SysConstans.LOGIN_USER_KEY);
            LoginUserEntity user = tokenCache.getIfPresent(userKey);
            return user;
        }
        return null;
    }

    @Override
    public void verifyToken(LoginUserEntity loginUser) {
        long expireTime = loginUser.getExpireTime();
        long currentTime = System.currentTimeMillis();
        if (expireTime - currentTime <= MILLIS_MINUTE_TEN) {
            refreshToken(loginUser);
        }
    }

    private String getToken(HttpServletRequest request) {
        String token = request.getHeader(header);

        if (StringUtils.isNotEmpty(token) && token.startsWith(SysConstans.TOKEN_PREFIX)) {
            token = token.replace(SysConstans.TOKEN_PREFIX, "");
        }
        return token;
    }

    private Claims parseToken(String token) {
        return Jwts.parser()
                .setSigningKey(secret)
                .parseClaimsJws(token)
                .getBody();
    }

    @Override
    public void refreshToken(LoginUserEntity loginUserEntity) {
        loginUserEntity.setLoginTime(System.currentTimeMillis());
        loginUserEntity.setExpireTime(loginUserEntity.getLoginTime() + expireTime * MILLIS_SECOND);
        String userKey = loginUserEntity.getToken();
        tokenCache.put(userKey, loginUserEntity);
    }

    @Override
    public String createToken(LoginUserEntity loginUserEntity) {
        String token = IdGenerateUtil.randomUUID();
        loginUserEntity.setToken(token);
        refreshToken(loginUserEntity);

        Map<String, Object> claims = new HashMap<>();
        claims.put(SysConstans.LOGIN_USER_KEY, token);
        return createToken(claims);
    }

    @Override
    public void delLoginUser(String token) {
        if (StringUtils.isNotEmpty(token)) {
            tokenCache.invalidate(token);
        }
    }

    private String createToken(Map<String, Object> claims) {
        String token = Jwts.builder()
                .setClaims(claims)
                .setExpiration(generateExpirationDate())
                .signWith(SignatureAlgorithm.HS512, secret).compact();
        return token;
    }

    private Date generateExpirationDate() {
        return new Date(System.currentTimeMillis() + 604800 * 1000);
    }
}
