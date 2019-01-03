package com.zhang.yong.demothree.security.exception;

import com.zhang.yong.demothree.vo.JsonObject;
import org.apache.shiro.ShiroException;
import org.apache.shiro.authz.UnauthenticatedException;
import org.apache.shiro.authz.UnauthorizedException;
import org.apache.shiro.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 * 统一捕捉shiro的异常，返回给前台一个json信息，前台根据这个信息显示对应的提示，或者做页面的跳转。
 */
@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    //不满足@RequiresGuest注解时抛出的异常信息
    private static final String GUEST_ONLY = "Attempting to perform a guest-only operation";

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public JsonObject handleException(Exception e) {
        String eName = e.getClass().getSimpleName();
        log.error("执行出错：{}",eName);
        e.printStackTrace();
        return JsonObject.error("系统出现异常：" + e.getLocalizedMessage());
    }

    @ExceptionHandler(MessageException.class)
    @ResponseBody
    public JsonObject handleMessageException(MessageException e) {
        String eName = e.getClass().getSimpleName();
        log.error("业务流程执行出错：{}",eName);
        e.printStackTrace();
        return JsonObject.error(e.getLocalizedMessage());
    }

    @ExceptionHandler(ShiroException.class)
    @ResponseBody
    public JsonObject handleShiroException(ShiroException e) {
        String eName = e.getClass().getSimpleName();
        log.error("shiro执行出错：{}",eName);
        e.printStackTrace();
        return JsonObject.error("鉴权或授权过程出错");
    }

    @ExceptionHandler(UnauthenticatedException.class)
    @ResponseBody
    public JsonObject UnauthenticatedException(UnauthenticatedException e) {
        e.printStackTrace();
        String eMsg = e.getMessage();
        if (StringUtils.startsWithIgnoreCase(eMsg,GUEST_ONLY)){
            return JsonObject.error("只允许游客访问，若您已登录，请先退出登录");
        }else{
            return JsonObject.error("用户未登录");
        }
    }

    @ExceptionHandler(UnauthorizedException.class)
    @ResponseBody
    public JsonObject UnauthorizedException() {
        return JsonObject.error("用户没有访问权限");
    }

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseBody
    public JsonObject IllegalArgumentException() {
        return JsonObject.error("非法参数");
    }

    @ExceptionHandler(NullPointerException.class)
    @ResponseBody
    public JsonObject NullPointerException() {
        return JsonObject.error("空指针");
    }

}