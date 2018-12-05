package cn.mauth.account.aspect;

import cn.mauth.account.core.util.HttpUtil;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

@Aspect
@Order(1)
@Component
public class LogAop {

    private final Logger logger= LoggerFactory.getLogger(this.getClass());

    @Pointcut("execution(public * cn.mauth.account.controller.*.*(..))")
    public void log(){}

    @Before("log()")
    public void before(JoinPoint joinPoint){

        HttpServletRequest request= HttpUtil.getRequest();

        String uri=request.getRequestURI();
        String type=request.getMethod();
        String className=joinPoint.getSignature().getDeclaringTypeName();
        String method=joinPoint.getSignature().getName();

        logger.info("\n{\n\turi:{},\n\ttype:{}\n\tclassName:{},\n\tmethod:{},\n\targs:{}\n}",uri,type,className,method, Arrays.toString(joinPoint.getArgs()));
    }
}
