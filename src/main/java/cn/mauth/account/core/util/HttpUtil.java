package cn.mauth.account.core.util;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

public final class HttpUtil {

    public static HttpServletRequest getRequest(){

        ServletRequestAttributes attributes=(ServletRequestAttributes)RequestContextHolder.getRequestAttributes();

        return attributes.getRequest();

    }

    public void addSession(String key,Object obj){
        HttpUtil.getRequest().getSession().setAttribute(key,obj);
    }

    public static Object getSession(String key){
        return HttpUtil.getRequest().getSession().getAttribute(key);
    }
}
