package com.crrcdt.backup.utils;

import com.crrcdt.backup.jwt.JwtTokenUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * WebUtil
 * </p>
 *
 * @author lyh
 * @date 2019年11月1日11:53:38
 */
public class WebUtil {

    private static final Logger logger = LoggerFactory.getLogger(WebUtil.class);

    public static String getCurrentUserId() {
        // 根据token ，取得用户Id
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (requestAttributes == null) {
            return "";
        }
        return getCurrentUserId(requestAttributes.getRequest());
    }

    public static String getCurrentUserId(HttpServletRequest httpServletRequest) {
        String currentUserId;
        String authToken = null;
        try {
            authToken = RequestParameterUtil.getToken(httpServletRequest);
        } catch (Exception e) {
            logger.error("error:", e);
        }
        currentUserId = JwtTokenUtil.getUserIdFromToken(authToken);

        if (StringUtils.isNotBlank(currentUserId)) {
            return currentUserId;
        }
        return currentUserId;
    }

}
