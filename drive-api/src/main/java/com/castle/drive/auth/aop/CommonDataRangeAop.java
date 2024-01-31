package com.castle.drive.auth.aop;

import com.castle.drive.common.constant.AopConstant;
import com.castle.drive.framework.query.DataRangeQuery;
import com.castle.drive.util.DataRangeUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ArrayUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

/**
 * @author YuLong
 * @Date 2024/1/30 15:53
 * @Classname CommonDataRangeAop
 * @Description 公共数据范围Aop
 */
@Slf4j
@Aspect
@Component
public class CommonDataRangeAop {

    @Around(AopConstant.COMMON_POINTCUT)
    public Object doAround(ProceedingJoinPoint joinPoint) throws Throwable {
        Object[] args = joinPoint.getArgs();
        if (ArrayUtils.isEmpty(args)) {
            return joinPoint.proceed();
        }
        for (Object arg : args) {
            if (arg instanceof DataRangeQuery) {
                DataRangeQuery dataRangeQuery = (DataRangeQuery) arg;
                DataRangeUtil.handleCommonLogin(dataRangeQuery);
                break;
            }
        }
        return joinPoint.proceed();
    }
}
