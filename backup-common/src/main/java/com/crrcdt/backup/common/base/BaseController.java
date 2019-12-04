package com.crrcdt.backup.common.base;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * <p>
 * 控制器基类
 * </p>
 *
 * @author lyh
 * @date 2019年11月1日09:18:51
 */
public class BaseController {

    private final static Logger logger = LoggerFactory.getLogger(BaseController.class);

    /**
     * <p>统一异常处理</p>
     *
     * @param request   HttpServletRequest对象
     * @param exception Exception对象
     */
    @ExceptionHandler
    public BaseResult exceptionHandler(HttpServletRequest request, Exception exception) {
        String ex = getErrorInfoFromException(exception);
        logger.error("统一异常处理：{}", ex);
        String header = "X-Requested-With";
        String xmlHttpRequest = "XMLHttpRequest";
        if (null != request.getHeader(header) && xmlHttpRequest.equalsIgnoreCase(request.getHeader(header))) {
            request.setAttribute("requestHeader", "ajax");
        }

        if (exception instanceof HttpMessageNotReadableException) {
            return BaseResult.validatorResultCreate("哇哦,向后台传递的数据格式不正确,请前台开发人员确认……");
        }
        return BaseResult.failResultCreate("哎呀,后台没有响应,请重试.\n如果还是不可用,请告诉运维人员让他们查日志,赶紧找到原因啊", exception.toString());
    }

    /**
     * <p>获取异常信息</p>
     *
     * @param e Exception对象
     * @return String
     */
    private String getErrorInfoFromException(Exception e) {
        try {
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            e.printStackTrace(pw);
            StringBuffer error = sw.getBuffer();
            return error.toString();
        } catch (Exception e2) {
            return "ErrorInfoFromException";
        }
    }

}
