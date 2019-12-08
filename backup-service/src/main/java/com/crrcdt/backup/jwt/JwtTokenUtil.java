package com.crrcdt.backup.jwt;

import com.crrcdt.backup.common.utils.PropertiesFileUtil;
import com.crrcdt.backup.model.UserInfo;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>Token生成工具类</p>
 *
 * @author lyh
 * @date 2019年11月6日11:16:05
 */
@Component
public class JwtTokenUtil implements Serializable {

    private static final String CLAIM_KEY_USER_ID = "sub";
    private static final String CLAIM_KEY_CREATED = "created";
    private static String secret = PropertiesFileUtil.getInstance("config").get("jwt.secret");
    private static Long expiration = Long.parseLong(PropertiesFileUtil.getInstance("config").get("jwt.expiration").trim());

    /**
     * 刷新token时间节点(失效前多长时间)
     */
    private static Long expireCheck = Long.parseLong(PropertiesFileUtil.getInstance("config").get("jwt.expireCheck").trim());

    /**
     * 根据token获取用户id
     *
     * @param token
     * @return
     */

    public static String getUserIdFromToken(String token) {
        String userId = null;
        try {
            final Claims claims = getClaimsFromToken(token);
            if (null != claims) {

                userId = claims.getSubject();
            }
        } catch (Exception e) {
            userId = null;
        }
        return userId;
    }

    /**
     * 根据token生成sessionId
     */
    public static String getSessionIdFromToken(String token) {
        String userId = null;

        try {
            final Claims claims = getClaimsFromToken(token);
            if (claims != null) {
                userId = claims.getSubject();
                Date createDate = new Date((Long) claims.get(CLAIM_KEY_CREATED));
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd_HHmmss");
                userId = userId + "_" + sdf.format(createDate);
            }

        } catch (Exception e) {
            e.printStackTrace();
            userId = null;
        }
        return userId;
    }

    public static Date getCreatedDateFromToken(String token) {
        Date created = null;
        try {
            final Claims claims = getClaimsFromToken(token);
            if (claims != null) {
                created = new Date((Long) claims.get(CLAIM_KEY_CREATED));
            }
        } catch (Exception e) {
            e.printStackTrace();
            created = null;
        }
        return created;
    }

    public static Date getExpirationDateFromToken(String token) {
        Date expiration = null;
        try {
            final Claims claims = getClaimsFromToken(token);
            if (claims != null) {
                expiration = claims.getExpiration();
            }
        } catch (Exception e) {
            e.printStackTrace();
            expiration = null;
        }
        return expiration;
    }

    private static Claims getClaimsFromToken(String token) {
        Claims claims;
        try {
            claims = Jwts.parser()
                    .setSigningKey(secret)
                    .parseClaimsJws(token)
                    .getBody();
        } catch (Exception e) {
            claims = null;
        }
        return claims;
    }

    private static Date generateExpirationDate() {
        return new Date(System.currentTimeMillis() + expiration * 1000);
    }

    /**
     * 判断token 是否需要刷新
     * 当失效时间在 失效期检查范围内，返回true
     */
    public static Boolean isTokenNeedRefresh(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        if (expiration != null) {
            return expiration.before(new Date(System.currentTimeMillis() + expireCheck * 1000));
        } else {
            //没有失效日期,不刷新
            return false;
        }
    }

    private static Boolean isCreatedBeforeLastPasswordReset(Date created, Date lastPasswordReset) {
        return (lastPasswordReset != null && created.before(lastPasswordReset));
    }

    /**
     * 生成token
     *
     * @param info
     * @return
     */
    public static String generateToken(UserInfo info) {
        Map<String, Object> claims = new HashMap<>(2);
        claims.put(CLAIM_KEY_USER_ID, info.getId());
        claims.put(CLAIM_KEY_CREATED, new Date());
        return generateToken(claims);
    }

    public static String generateToken(Map<String, Object> claims) {
        JwtBuilder builder = Jwts.builder()
                .setClaims(claims)
                .setExpiration(generateExpirationDate())
                .signWith(SignatureAlgorithm.HS512, secret);
        return builder.compact();
    }

    public static Boolean canTokenBeRefreshed(String token, Date lastPasswordReset) {
        final Date created = getCreatedDateFromToken(token);
        return !isCreatedBeforeLastPasswordReset(created, lastPasswordReset);
    }

    /**
     * 刷新token
     *
     * @param token
     * @return
     */
    public static String refreshToken(String token) {
        String refreshedToken;
        try {
            final Claims claims = getClaimsFromToken(token);
            if (claims != null) {
                claims.put(CLAIM_KEY_CREATED, new Date());
            }
            refreshedToken = generateToken(claims);

        } catch (Exception e) {
            refreshedToken = null;
        }
        return refreshedToken;
    }

    /**
     * 验证token是否过期等
     */
    public static Boolean validateToken(String token) {
        try {
            //如果token过期,username 取得为null
            String username = getUserIdFromToken(token);
            Date created = getCreatedDateFromToken(token);
            return username != null && !"".equals(username);
        } catch (Exception e) {
            return false;
        }
    }

}

