package com.castle.drive.auth.aop;

import com.castle.drive.common.constant.AopConstant;
import com.castle.drive.framework.query.DataRangeQuery;
import com.castle.drive.util.DataRangeUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ArrayUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

/**
 * @author YuLong
 * @Date 2024/2/1 17:36
 * @Classname DataRangeAop
 * @Description 数据范围Aop
 */
@Slf4j
@Aspect
@Component
@ConditionalOnProperty(name = "login.admin.enable", havingValue = "true", matchIfMissing = true)
public class DataRangeAop {

    @Around(AopConstant.ADMIN_POINTCUT)
    public Object doAround(ProceedingJoinPoint joinPoint) throws Throwable {
        Object[] args = joinPoint.getArgs();
        if (ArrayUtils.isEmpty(args)) {
            return joinPoint.proceed();
        }
        for (Object arg : args) {
            if (arg instanceof DataRangeQuery) {
                DataRangeQuery dataRangeQuery = (DataRangeQuery) arg;
                DataRangeUtil.handleAdminLogin(dataRangeQuery);
                break;
            }
        }
        return joinPoint.proceed();
    }


}
