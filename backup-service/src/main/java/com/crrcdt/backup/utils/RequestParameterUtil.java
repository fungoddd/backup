package com.crrcdt.backup.utils;

import com.crrcdt.backup.common.constant.BackupConstant;
import com.crrcdt.backup.common.utils.PropertiesFileUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URLEncodedUtils;

import javax.servlet.http.HttpServletRequest;
import java.nio.charset.Charset;
import java.util.List;

/**
 * <p>
 * Request工具类
 * </p>
 *
 * @author lyh
 * @date 2019年11月6日13:54:02
 */
public class RequestParameterUtil {

    private static String tokenHeader = PropertiesFileUtil.getInstance("config").get("jwt.header");
    private static String swaggerToken = PropertiesFileUtil.getInstance("config").get("jwt.swaggerToken");

    /**
     * 获取token
     *
     * @param httpServletRequest HttpServletRequest
     * @return token
     */
    public static String getToken(HttpServletRequest httpServletRequest) {
        // 取得request Header中token信息
        String authToken = httpServletRequest.getHeader(tokenHeader);
        // 当有token参数时，取得参数中toke
        String paraToken = httpServletRequest.getParameter(tokenHeader);
        if (org.apache.commons.lang3.StringUtils.isNotBlank(paraToken)) {
            authToken = paraToken;
        }
        // 如果没有token,取refer中参数值（单独部署的页面，通过para设置token，内部ajax获取信息时，url中无token信息。例如：工作流编辑页面）
        if (org.apache.commons.lang3.StringUtils.isBlank(authToken) && httpServletRequest.getHeader(BackupConstant.REFERRER) != null) {
            List<NameValuePair> ls = URLEncodedUtils.parse(httpServletRequest.getHeader("Referer"), Charset.forName("UTF-8"));
            for (NameValuePair item : ls) {
                if (item.getName().equals(tokenHeader)) {
                    authToken = item.getValue();
                    break;
                }
            }
        }
        // swagger 判断，如果是 swagger-ui.html来源，则赋值(测试），当且token为空时
        if (StringUtils.isBlank(authToken)) {
            if (httpServletRequest.getHeader(BackupConstant.REFERRER) != null && httpServletRequest
                    .getHeader(BackupConstant.REFERRER)
                    .indexOf(BackupConstant.SWAGGER) > 0) {
                authToken = swaggerToken;
            }
        }
        return authToken;
    }

}
